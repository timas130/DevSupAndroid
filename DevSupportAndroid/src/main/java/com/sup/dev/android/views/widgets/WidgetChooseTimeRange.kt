package com.sup.dev.android.views.widgets

import android.support.annotation.StringRes
import android.view.View
import android.widget.Button
import com.sup.dev.android.R
import com.sup.dev.android.tools.ToolsResources
import com.sup.dev.android.tools.ToolsView
import com.sup.dev.java.tools.ToolsDate


class WidgetChooseTimeRange : Widget(R.layout.widget_choose_time_range) {

    private val vStart: Button = view.findViewById(R.id.start)
    private val vEnd: Button = view.findViewById(R.id.end)
    private val vCancel: Button = view.findViewById(R.id.cancel)
    private val vEnter: Button = view.findViewById(R.id.enter)

    private var h1: Int = 0
    private var m1: Int = 0
    private var h2: Int = 0
    private var m2: Int = 0

    private var autoHideOnEnter = true

    init {

        vCancel.visibility = View.GONE
        vEnter.visibility = View.GONE

        vStart.setOnClickListener { v ->
            WidgetChooseTime()
                .setOnCancel(ToolsResources.getString(R.string.app_cancel))
                .setOnEnter(ToolsResources.getString(R.string.app_choose)) { dialog, h, m -> setTimeStart(h, m) }
                .asSheetShow()
        }

        vEnd.setOnClickListener { v ->
            WidgetChooseTime()
                .setOnCancel(ToolsResources.getString(R.string.app_cancel))
                .setOnEnter(ToolsResources.getString(R.string.app_choose)) { dialog, h, m -> setTimeEnd(h, m) }
                .asSheetShow()
        }

        setTimeStart(12, 0)
        setTimeEnd(24, 0)
    }

    //
    //  Setters
    //

    fun setTimeStart(h: Int, m: Int): WidgetChooseTimeRange {
        h1 = h
        m1 = m
        vStart.text = ToolsDate.timeToString(h, m)
        return this
    }

    fun setTimeEnd(h: Int, m: Int): WidgetChooseTimeRange {
        h2 = h
        m2 = m
        vEnd.text = ToolsDate.timeToString(h, m)
        return this
    }

    fun setOnEnter(@StringRes s: Int): WidgetChooseTimeRange {
        return setOnEnter(ToolsResources.getString(s))
    }

    fun setOnEnter(@StringRes s: Int, onEnter: (WidgetChooseTimeRange, Int, Int, Int, Int) -> Unit): WidgetChooseTimeRange {
        return setOnEnter(ToolsResources.getString(s), onEnter)
    }

    @JvmOverloads
    fun setOnEnter(
        s: String?,
        onEnter: (WidgetChooseTimeRange, Int, Int, Int, Int) -> Unit = { w, x, y, xx, yy -> }
    ): WidgetChooseTimeRange {
        ToolsView.setTextOrGone(vEnter, s)
        vEnter.setOnClickListener { v ->
            if (autoHideOnEnter)
                hide()
            else
                setEnabled(false)
            onEnter.invoke(this, h1, m1, h2, m2)
        }
        return this
    }

    fun setAutoHideOnEnter(autoHideOnEnter: Boolean): WidgetChooseTimeRange {
        this.autoHideOnEnter = autoHideOnEnter
        return this
    }

    fun setOnCancel(onCancel: (WidgetChooseTimeRange) -> Unit = {}): WidgetChooseTimeRange {
        return setOnCancel(null, onCancel)
    }

    fun setOnCancel(@StringRes s: Int, onCancel: (WidgetChooseTimeRange) -> Unit): WidgetChooseTimeRange {
        return setOnCancel(ToolsResources.getString(s), onCancel)
    }

    @JvmOverloads
    fun setOnCancel(s: String?, onCancel: (WidgetChooseTimeRange) -> Unit = {}): WidgetChooseTimeRange {
        super.setOnHide { b -> onCancel.invoke(this) }
        ToolsView.setTextOrGone(vCancel, s)
        vCancel.setOnClickListener { v ->
            hide()
            onCancel.invoke(this)
        }
        return this
    }

}
