package com.sup.dev.android.views.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.sup.dev.android.R
import com.sup.dev.android.app.SupAndroid
import com.sup.dev.android.tools.ToolsResources
import com.sup.dev.android.tools.ToolsView
import com.sup.dev.android.views.animations.AnimationFocus


class ViewAvatar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private val paint: Paint
    private val animationFocus: AnimationFocus

    val imageView: ViewCircleImage
    val chip: ViewChip
    private val vTouch: ViewDraw

    private var roundBackgroundColor: Int = 0

    //
    //  Getters
    //

    val text: String
        get() = chip.text

    init {

        SupAndroid.initEditMode(this)
        val focusColor = ToolsResources.getColor(R.color.focus)

        paint = Paint()
        paint.isAntiAlias = true

        val view:View = ToolsView.inflate(context, R.layout.view_avatar)
        imageView = view.findViewById(R.id.dev_sup_image)
        chip = view.findViewById(R.id.dev_sup_chip)
        vTouch = view.findViewById(R.id.dev_sup_avatar_touch)

        chip.visibility = View.GONE

        isEnabled = false

        addView(view)

        val a = context.obtainStyledAttributes(attrs, R.styleable.ViewAvatar, 0, 0)
        val src = a.getResourceId(R.styleable.ViewAvatar_android_src, R.color.blue_700)
        val text = a.getString(R.styleable.ViewAvatar_ViewAvatar_chipText)
        val chipBackground = a.getColor(R.styleable.ViewAvatar_ViewAvatar_chipBackground, 0)
        val srcIcon = a.getResourceId(R.styleable.ViewAvatar_ViewAvatar_chipIcon, 0)
        val iconUseBackground = a.getBoolean(R.styleable.ViewAvatar_ViewAvatar_chipIconUseBackground, false)
        val iconPadding = a.getDimension(R.styleable.ViewAvatar_ViewAvatar_chipIconPadding, 0f)
        val chipSize = a.getDimension(R.styleable.ViewAvatar_ViewAvatar_chipSize, ToolsView.dpToPx(18f).toFloat())
        val roundBackgroundColor = a.getColor(R.styleable.ViewAvatar_ViewAvatar_avatarBackground, 0x00000000)
        a.recycle()

        animationFocus = AnimationFocus(vTouch, focusColor)

        setImage(src)
        chip.setSize(ToolsView.pxToDp(chipSize))
        chip.setIconPadding(ToolsView.pxToDp(iconPadding))
        chip.setIcon(srcIcon)
        chip.text = text
        chip.setUseIconBackground(iconUseBackground)
        if (chipBackground != 0) chip.setChipBackground(chipBackground)

        vTouch.setOnDraw { canvas ->
            paint.color = animationFocus.update()
            canvas.drawCircle(vTouch.width / 2f, vTouch.height / 2f, vTouch.height / 2f, paint)
        }

        setRoundBackgroundColor(roundBackgroundColor)
    }

    fun updateChipVisible() {

        if (!chip.hasIcon() && chip.text.isEmpty())
            chip.visibility = View.GONE
        else
            chip.visibility = View.VISIBLE
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = roundBackgroundColor
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (Math.min(width, height) / 2).toFloat(), paint)
        super.onDraw(canvas)
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams) {
        if (params.width == FrameLayout.LayoutParams.WRAP_CONTENT && params.height == FrameLayout.LayoutParams.WRAP_CONTENT) {
            params.width = ToolsView.dpToPx(48f)
            params.height = ToolsView.dpToPx(48f)
        }

        if (params.width > 0 && params.height == FrameLayout.LayoutParams.WRAP_CONTENT)
            params.height = params.width
        if (params.height > 0 && params.width == FrameLayout.LayoutParams.WRAP_CONTENT) params.width = params.height

        super.setLayoutParams(params)

    }

    //
    //  Setters
    //


    fun setCircleBackgroundColorResource(@ColorRes roundBackgroundColorRes: Int) {
        setRoundBackgroundColor(ToolsResources.getColor(roundBackgroundColorRes))
    }

    fun setRoundBackgroundColor(roundBackgroundColor: Int) {
        this.roundBackgroundColor = roundBackgroundColor
        setWillNotDraw(roundBackgroundColor == 0x00000000)
        invalidate()
    }

    fun setChipText(t: String) {
        chip.text = t
        updateChipVisible()
    }

    fun setChipIcon(@DrawableRes icon: Int) {
        chip.setIcon(icon)
        updateChipVisible()
    }


    override fun setOnClickListener(l: View.OnClickListener?) {
        vTouch.setOnClickListener(l)
        vTouch.isClickable = l != null
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        imageView.isEnabled = enabled
    }

    override fun setClickable(clickable: Boolean) {
        super.setClickable(clickable)
        imageView.isClickable = clickable
    }

    fun setImage(@DrawableRes image: Int) {
        if (image != 0)
            imageView.setImageResource(image)
        else
            imageView.setImageBitmap(null)

    }

    fun setImage(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }
}
