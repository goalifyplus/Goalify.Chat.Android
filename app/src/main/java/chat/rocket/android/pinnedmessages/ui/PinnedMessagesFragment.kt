package com.goalify.chat.android.pinnedmessages.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goalify.chat.android.R
import com.goalify.chat.android.chatroom.adapter.ChatRoomAdapter
import com.goalify.chat.android.chatroom.ui.ChatRoomActivity
import com.goalify.chat.android.chatroom.viewmodel.BaseViewModel
import com.goalify.chat.android.helper.EndlessRecyclerViewScrollListener
import com.goalify.chat.android.pinnedmessages.presentation.PinnedMessagesPresenter
import com.goalify.chat.android.pinnedmessages.presentation.PinnedMessagesView
import com.goalify.chat.android.util.extensions.inflate
import com.goalify.chat.android.util.extensions.setVisible
import com.goalify.chat.android.util.extensions.showToast
import com.goalify.chat.android.util.extensions.ui
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_pinned_messages.*
import javax.inject.Inject

fun newInstance(chatRoomId: String, chatRoomType: String) : Fragment {
    return PinnedMessagesFragment().apply {
        arguments = Bundle(1).apply {
            putString(BUNDLE_CHAT_ROOM_ID, chatRoomId)
            putString(BUNDLE_CHAT_ROOM_TYPE, chatRoomType)
        }
    }
}

private const val BUNDLE_CHAT_ROOM_ID = "chat_room_id"
private const val BUNDLE_CHAT_ROOM_TYPE = "chat_room_type"

class PinnedMessagesFragment : Fragment(), PinnedMessagesView {

    private lateinit var chatRoomId: String
    private lateinit var chatRoomType: String
    private lateinit var adapter: ChatRoomAdapter
    @Inject
    lateinit var presenter: PinnedMessagesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)

        val bundle = arguments
        if (bundle != null){
            chatRoomId = bundle.getString(BUNDLE_CHAT_ROOM_ID)
            chatRoomType = bundle.getString(BUNDLE_CHAT_ROOM_TYPE)
        }else{
            requireNotNull(bundle) { "no arguments supplied when the fragment was instantiated" }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = container?.inflate(R.layout.fragment_pinned_messages)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        presenter.loadPinnedMessages(chatRoomId)
    }

    override fun showPinnedMessages(pinnedMessages: List<BaseViewModel<*>>) {
        ui {
            if (recycler_view_pinned.adapter == null){
                adapter = ChatRoomAdapter(chatRoomType,"",null,false)
                recycler_view_pinned.adapter = adapter
                val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                recycler_view_pinned.layoutManager = linearLayoutManager
                recycler_view_pinned.itemAnimator = DefaultItemAnimator()
                if (pinnedMessages.size > 10){
                    recycler_view_pinned.addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager){
                        override fun onLoadMore(page: Int, totalItemsCount: Int, recyclerView: RecyclerView?) {
                            presenter.loadPinnedMessages(chatRoomId)
                        }

                    })
                }
                togglePinView(pinnedMessages.size)
            }
            adapter.appendData(pinnedMessages)
        }
    }

    override fun showMessage(resId: Int) {
        ui {
            showToast(resId)
        }
    }

    override fun showMessage(message: String) {
        ui {
            showToast(message)
        }
    }

    override fun showGenericErrorMessage() = showMessage(getString(R.string.msg_generic_error))

    override fun showLoading() {
        ui{ view_loading.setVisible(true) }
    }

    override fun hideLoading() {
        ui { view_loading.setVisible(false) }
    }

    private fun setupToolbar() {
        (activity as ChatRoomActivity).setupToolbarTitle(getString(R.string.title_pinned_messages))
    }

    private fun togglePinView(size : Int){
        if (size == 0){
            pin_view.setVisible(true)
        }else{
            pin_view.setVisible(false)
        }
    }
}