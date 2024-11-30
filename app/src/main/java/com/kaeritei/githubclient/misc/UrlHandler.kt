package com.kaeritei.githubclient.misc

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.kaeritei.githubclient.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UrlHandler
    @Inject
    constructor() {
        /**
         * URLをブラウザで開く処理
         * @param context
         * @param url ブラウザで開くURL
         */
        fun handleBrowseUrl(
            context: Context,
            url: String,
        ) {
            val uri = Uri.parse(url)
            if (uri == null || uri.scheme == null) {
                return
            }

            try {
                CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .build()
                    .launchUrl(context, uri)
            } catch (e: ActivityNotFoundException) {
                // 端末にインテントを処理できるネイティブアプリが見つからない場合の処理
                AlertDialog.Builder(context).setMessage(R.string.browser_app_not_found_error_message)
                    .setPositiveButton(context.getString(R.string.ok_button_label)) { _, _ -> }
                    .create()
                    .show()
            }
        }
    }
