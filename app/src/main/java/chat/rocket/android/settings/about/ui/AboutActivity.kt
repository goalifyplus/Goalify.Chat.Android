package com.goalify.chat.android.settings.about.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        val versionName = resources.getString(R.string.msg_version) +" "+BuildConfig.VERSION_NAME
        val versionCode = resources.getString(R.string.msg_build)+" #"+ BuildConfig.VERSION_CODE
        text_version_name.text = versionName
        text_build_number.text = versionCode
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        text_change_password.textContent = resources.getString(R.string.title_about)
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
