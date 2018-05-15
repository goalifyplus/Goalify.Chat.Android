package com.goalify.chat.android.settings.about.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.goalify.chat.android.BuildConfig
import com.goalify.chat.android.R
import com.goalify.chat.android.util.extensions.textContent
import kotlinx.android.synthetic.main.about_view.*
import kotlinx.android.synthetic.main.app_bar_password.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setupToolbar()
        setupViews()
    }

    private fun setupViews() {
        text_version_name.text = getString(R.string.msg_version, BuildConfig.VERSION_NAME)
        text_build_number.text = getString(R.string.msg_build, BuildConfig.VERSION_CODE)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        text_change_password.textContent = getString(R.string.title_about)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.close_enter, R.anim.close_exit)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }
}
