package com.sup.dev.android.views.screens

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sup.dev.android.R
import com.sup.dev.android.app.SupAndroid
import com.sup.dev.android.libs.screens.Screen
import com.sup.dev.android.libs.screens.navigator.NavigationAction
import com.sup.dev.android.libs.screens.navigator.Navigator
import com.sup.dev.android.tools.ToolsResources
import com.sup.dev.android.tools.ToolsView


class SAlert(
        title: String?,
        text: String?,
        action: String?,
        image: Int = 0,
        imageFul: Int = 0,
        var onAction: ((SAlert) -> Unit)?
) : Screen(R.layout.screen_alert) {

    companion object {

        var GLOBAL_SHOW_WHOOPS = true

        fun showNetwork(action: NavigationAction, onRetry: (SAlert) -> Unit) {
            Navigator.action(action, instanceNetwork(onRetry))
        }

        fun instanceNetwork(onRetry: (SAlert) -> Unit) = instanceMessage(SupAndroid.TEXT_ERROR_NETWORK, SupAndroid.TEXT_APP_RETRY, SupAndroid.IMG_ERROR_NETWORK, true, onRetry)

        fun showGone(action: NavigationAction) {
            Navigator.action(action, instanceGone())
        }

        fun instanceGone() = instanceMessage(SupAndroid.TEXT_ERROR_GONE, SupAndroid.TEXT_APP_BACK, SupAndroid.IMG_ERROR_GONE, true) {Navigator.remove(it)}

        fun showMessage(text:Int, action:Int, img:Int, actionNavigation: NavigationAction, onAction:((SAlert) -> Unit)? = {Navigator.remove(it)}) {
            Navigator.action(actionNavigation, instanceMessage(ToolsResources.s(text), ToolsResources.s(action), img, true, onAction))
        }

        fun showMessage(text:String?, action:String?, img:Int, actionNavigation: NavigationAction, onAction:((SAlert) -> Unit)? = {Navigator.remove(it)}) {
            Navigator.action(actionNavigation, instanceMessage(text, action, img, true, onAction))
        }

        fun instanceMessage(text:String?, action:String?, img:Int, tryToShowWhoops:Boolean, onAction:((SAlert) -> Unit)?):SAlert{
            val screen =  SAlert(
                    if (tryToShowWhoops && GLOBAL_SHOW_WHOOPS) SupAndroid.TEXT_APP_WHOOPS else null,
                    text,
                    action,
                    img
            ) { Navigator.back() }
            screen.onAction = onAction
            screen.isNavigationVisible = false
            screen.isNavigationAllowed = false
            screen.isNavigationAnimation = false
            screen.navigationBarColor = ToolsResources.getColorAttr(R.attr.content_background_screen)
            screen.statusBarColor = ToolsResources.getColorAttr(R.attr.content_background_screen)
            return screen
        }
    }

    constructor(title: String?, text: String?, action: String?, onAction: ((SAlert) -> Unit)?) : this(title, text, action, 0, onAction) {}

    constructor(title: String?, text: String?, action: String?, image: Int, onAction: ((SAlert) -> Unit)?) : this(title, text, action, image, 0, onAction) {}

    private val vTitle: TextView = findViewById(R.id.vTitle)
    private val vText: TextView = findViewById(R.id.vText)
    private val vAction: TextView = findViewById(R.id.vAction)
    private val vImage: ImageView = findViewById(R.id.vImage)
    private val vImageFull: ImageView = findViewById(R.id.vImageFull)

    init {

        ToolsView.setTextOrGone(vTitle, title)
        vText.text = text
        vAction.text = action

        if (image != 0) {
            vImage.setImageResource(image)
            vImage.visibility = View.VISIBLE
        } else {
            vImage.setImageBitmap(null)
            vImage.visibility = View.GONE
        }

        if (imageFul != 0) {
            vImageFull.setImageResource(imageFul)
            vImageFull.visibility = View.VISIBLE
        } else {
            vImageFull.setImageBitmap(null)
            vImageFull.visibility = View.GONE
        }

        vAction.setOnClickListener { onAction?.invoke(this) }

    }

}