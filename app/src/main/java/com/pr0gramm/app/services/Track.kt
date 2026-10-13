package com.pr0gramm.app.services

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import com.pr0gramm.app.Stats
import com.pr0gramm.app.feed.FeedFilter
import com.pr0gramm.app.orm.Vote
import com.pr0gramm.app.util.di.InjectorAware
import com.pr0gramm.app.util.di.injector

/**
 * Tracking hooks. The fork removed Firebase, so events go only to local Stats.
 */
@SuppressLint("StaticFieldLeak")
object Track : InjectorAware {
    private lateinit var context: Context
    override val injector by lazy { context.injector }

    private var installerTracked = false

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }

    @Suppress("UNUSED_PARAMETER")
    private fun send(eventType: String) {
        // ponytail: no analytics backend in the fork
    }

    fun loginStarted() {
        send("loginStarted")
        Stats().increment("login.started")
    }

    fun loginSuccessful() {
        send("login")

        Stats().increment("login.succeeded")
    }

    fun loginFailed(type: String) {
        send("login")

        Stats().increment("login.failed", "reason:$type")
    }

    fun logout() {
        send("logout")
    }

    fun writeComment(root: Boolean) {
        send("write_comment")
    }

    fun writeMessage() {
        send("write_message")
    }

    fun searchImage() {
        send("search_image")
    }

    fun votePost(vote: Vote) {
        send("vote")
    }

    fun voteTag(vote: Vote) {
        send("vote")
    }

    fun voteComment(vote: Vote) {
        send("vote")
    }

    fun gotoFirefoxFocusWebsite() {
        send("goto_firefox_website")
    }

    fun openBrowser(type: String) {
        send("open_browser")
    }

    fun upload(size: Long) {
        val categoryStart = size / (512 * 1024) * 512

        @SuppressLint("DefaultLocale")
        val sizeCategory = "%d-%d kb".format(categoryStart, categoryStart + 512)

        send("upload")
    }

    fun inboxNotificationShown() {
        send("inbox_notification_shown")
    }

    fun inboxNotificationClosed(method: String) {
        send("inbox_notification_close")
    }

    fun preloadCurrentFeed(size: Int) {
        send("preload_feed")
    }

    fun inviteSent() {
        send("invite_sent")
    }

    fun registerLinkClicked() {
        send("aff_register_clicked")
    }

    fun faqClicked() {
        send("aff_faq_clicked")
    }

    fun privacyClicked() {
        send("aff_privacy_clicked")
    }

    fun imprintClicked() {
        send("aff_imprint_clicked")
    }

    fun passwordChanged() {
        send("password_changed")
    }

    fun specialMenuActionClicked(uri: Uri) {
        send("special_menu_item")
    }

    @Suppress("UNUSED_PARAMETER")
    fun updateUserState(state: AuthState) {
    }

    fun openFeed(filter: FeedFilter) {
        send("view_feed")
    }

    fun viewItem(itemId: Long) {
        send("view_item")
    }

    fun inboxActivity() {
        send("inbox")
    }

    fun openZoomView(itemId: Long) {
        send("zoom_view")
    }

    fun adEvent(type: String) {
        send("ad_$type")
    }

    fun installer(name: String?) {
        if (!installerTracked) {
            installerTracked = true

            send("installer")
        }
    }

    data class AuthState(val authorized: Boolean, val premium: Boolean)
}
