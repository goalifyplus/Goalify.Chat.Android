package chat.rocket.android.fragment.sidebar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import chat.rocket.android.BuildConfig;
import chat.rocket.android.R;
import chat.rocket.android.RocketChatCache;
import chat.rocket.android.api.MethodCallHelper;
import chat.rocket.android.fragment.AbstractFragment;
import chat.rocket.android.fragment.sidebar.dialog.AbstractAddRoomDialogFragment;
import chat.rocket.android.fragment.sidebar.dialog.AddChannelDialogFragment;
import chat.rocket.android.fragment.sidebar.dialog.AddDirectMessageDialogFragment;
import chat.rocket.android.helper.LogcatIfError;
import chat.rocket.android.helper.TextUtils;
import chat.rocket.android.layouthelper.chatroom.RoomListManager;
import chat.rocket.android.model.ddp.RoomSubscription;
import chat.rocket.android.model.ddp.User;
import chat.rocket.android.realm_helper.RealmHelper;
import chat.rocket.android.realm_helper.RealmListObserver;
import chat.rocket.android.realm_helper.RealmObjectObserver;
import chat.rocket.android.realm_helper.RealmStore;
import chat.rocket.android.renderer.UserRenderer;
import chat.rocket.android.widget.RocketChatAvatar;

public class SidebarMainFragment extends AbstractFragment {

  private String hostname;
  private RoomListManager roomListManager;
  private RealmListObserver<RoomSubscription> roomsObserver;
  private RealmObjectObserver<User> currentUserObserver;
  private MethodCallHelper methodCallHelper;

  public SidebarMainFragment() {
  }

  /**
   * create SidebarMainFragment with hostname.
   */
  public static SidebarMainFragment create(String hostname) {
    Bundle args = new Bundle();
    args.putString("hostname", hostname);

    SidebarMainFragment fragment = new SidebarMainFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle args = getArguments();
    hostname = args == null ? null : args.getString("hostname");
    if (!TextUtils.isEmpty(hostname)) {
      RealmHelper realmHelper = RealmStore.get(hostname);
      if (realmHelper != null) {
        roomsObserver = realmHelper
            .createListObserver(
                realm -> realm.where(RoomSubscription.class).equalTo(RoomSubscription.OPEN, true)
                    .findAll())
            .setOnUpdateListener(list -> roomListManager.setRooms(list));

        currentUserObserver = realmHelper
            .createObjectObserver(User::queryCurrentUser)
            .setOnUpdateListener(this::onCurrentUser);

        methodCallHelper = new MethodCallHelper(getContext(), hostname);
      }
    }
  }

  @Override
  protected int getLayout() {
    if (hostname == null) {
      return R.layout.simple_screen;
    } else {
      return R.layout.fragment_sidebar_main;
    }
  }

  @Override
  protected void onSetupView() {
    if (hostname == null) {
      return;
    }

    setupUserActionToggle();
    setupUserStatusButtons();
    setupLogoutButton();
    setupAddChannelButton();
    setupVersionInfo();

    roomListManager = new RoomListManager(
        rootView.findViewById(R.id.unread_title),
        (LinearLayout) rootView.findViewById(R.id.unread_container),
        (LinearLayout) rootView.findViewById(R.id.channels_container),
        (LinearLayout) rootView.findViewById(R.id.direct_messages_container));
    roomListManager.setOnItemClickListener(view -> {
      RocketChatCache.get(view.getContext()).edit()
          .putString(RocketChatCache.KEY_SELECTED_ROOM_ID, view.getRoomId())
          .apply();
    });
  }

  private void setupUserActionToggle() {
    final CompoundButton toggleUserAction =
        ((CompoundButton) rootView.findViewById(R.id.toggle_user_action));
    toggleUserAction.setFocusableInTouchMode(false);
    rootView.findViewById(R.id.user_info_container).setOnClickListener(view -> {
      toggleUserAction.toggle();
    });
    RxCompoundButton.checkedChanges(toggleUserAction)
        .compose(bindToLifecycle())
        .subscribe(RxView.visibility(rootView.findViewById(R.id.user_action_outer_container)));
  }

  private void setupUserStatusButtons() {
    rootView.findViewById(R.id.btn_status_online).setOnClickListener(view ->
        updateCurrentUserStatus(User.STATUS_ONLINE));
    rootView.findViewById(R.id.btn_status_away).setOnClickListener(view ->
        updateCurrentUserStatus(User.STATUS_AWAY));
    rootView.findViewById(R.id.btn_status_busy).setOnClickListener(view ->
        updateCurrentUserStatus(User.STATUS_BUSY));
    rootView.findViewById(R.id.btn_status_invisible).setOnClickListener(view ->
        updateCurrentUserStatus(User.STATUS_OFFLINE));
  }

  private void updateCurrentUserStatus(String status) {
    if (methodCallHelper != null) {
      methodCallHelper.setUserStatus(status).continueWith(new LogcatIfError());
      closeUserActionContainer();
    }
  }

  private void onCurrentUser(User user) {
    onRenderCurrentUser(user);
    updateRoomListMode(user);
  }

  private void onRenderCurrentUser(User user) {
    if (user != null && !TextUtils.isEmpty(hostname)) {
      new UserRenderer(getContext(), user)
          .avatarInto((RocketChatAvatar) rootView.findViewById(R.id.current_user_avatar), hostname)
          .usernameInto((TextView) rootView.findViewById(R.id.current_user_name))
          .statusColorInto((ImageView) rootView.findViewById(R.id.current_user_status));
    }
  }

  private void updateRoomListMode(User user) {
    if (user == null || user.getSettings() == null || user.getSettings().getPreferences() == null) {
      return;
    }
    roomListManager.setUnreadRoomMode(user.getSettings().getPreferences().isUnreadRoomsMode());
  }

  private void setupLogoutButton() {
    rootView.findViewById(R.id.btn_logout).setOnClickListener(view -> {
      if (methodCallHelper != null) {
        methodCallHelper.logout().continueWith(new LogcatIfError());
        closeUserActionContainer();
      }
    });
  }

  private void closeUserActionContainer() {
    final CompoundButton toggleUserAction =
        ((CompoundButton) rootView.findViewById(R.id.toggle_user_action));
    if (toggleUserAction != null && toggleUserAction.isChecked()) {
      toggleUserAction.setChecked(false);
    }
  }

  private void setupAddChannelButton() {
    rootView.findViewById(R.id.btn_add_channel).setOnClickListener(view -> {
      showAddRoomDialog(AddChannelDialogFragment.create(hostname));
    });

    rootView.findViewById(R.id.btn_add_direct_message).setOnClickListener(view -> {
      showAddRoomDialog(AddDirectMessageDialogFragment.create(hostname));
    });
  }

  private void setupVersionInfo() {
    TextView versionInfoView = (TextView) rootView.findViewById(R.id.version_info);
    versionInfoView.setText(getString(R.string.version_info_text, BuildConfig.VERSION_NAME));
  }

  private void showAddRoomDialog(DialogFragment dialog) {
    dialog.show(getFragmentManager(), AbstractAddRoomDialogFragment.class.getSimpleName());
  }

  @Override
  public void onResume() {
    super.onResume();
    if (roomsObserver != null) {
      roomsObserver.sub();
      currentUserObserver.sub();
    }
  }

  @Override
  public void onPause() {
    if (roomsObserver != null) {
      currentUserObserver.unsub();
      roomsObserver.unsub();
    }
    super.onPause();
  }
}