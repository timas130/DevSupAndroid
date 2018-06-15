package com.sup.dev.android.views.dialogs_adwanced;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.sup.dev.android.app.SupAndroid;
import com.sup.dev.android.tools.ToolsAndroid;
import com.sup.dev.android.tools.ToolsView;
import com.sup.dev.android.views.widgets.layouts.LayoutMaxSizes;

import java.lang.ref.WeakReference;

public abstract class Dialog {

    private WeakReference<AppCompatDialog> dialog;
    private boolean enabled;
    private boolean cancelable;

    public View instanceView(Context viewContext) {
        return getLayoutRes() != 0 ? ToolsView.inflate(viewContext, getLayoutRes()) : null;
    }

    public abstract int getLayoutRes();

    public abstract void bindView(View view);

    public <K extends Dialog> K show() {
        SupAndroid.mvpActivity(activity -> show(activity));
        return (K) this;
    }

    @CallSuper
    protected void onPreShow(View view) {

    }

    @CallSuper
    protected void onShow(View view) {

    }

    @CallSuper
    protected void onHide() {

    }

    public void hide() {
        if (dialog != null && dialog.get() != null)
            dialog.get().dismiss();
    }

    public <K extends Dialog> K show(Context viewContext) {

        View view = instanceView(viewContext);
        bindView(view);

        AppCompatDialog dialog = new AppCompatDialog(viewContext);
        this.dialog = new WeakReference<>(dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        onPreShow(view);
        dialog.setCancelable(cancelable && enabled);
        dialog.setCanceledOnTouchOutside(cancelable && enabled);
        dialog.setOnCancelListener(dialogInterface -> onHide());

        LayoutMaxSizes layoutMaxSizes = new LayoutMaxSizes(viewContext) {
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                setMaxWidthParentPercent(ToolsAndroid.isScreenPortrait() ? 90 : 70);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        };
        layoutMaxSizes.setMaxWidth(600);
        layoutMaxSizes.setUseScreenWidthAsParent(true);
        layoutMaxSizes.setAlwaysMaxW(true);
        layoutMaxSizes.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setContentView(layoutMaxSizes);
        dialog.show();

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);        //  Без этой строки диалог умирает при повороте экрана

        onShow(view);

        return (K) this;
    }

    //
    //  Setters
    //

    public <K extends Dialog>K setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return (K) this;
    }

    public <K extends Dialog>K setEnabled(boolean enabled) {
        this.enabled = enabled;
        return (K) this;
    }

    //
    //  Getters
    //

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isCancelable() {
        return cancelable;
    }
}
