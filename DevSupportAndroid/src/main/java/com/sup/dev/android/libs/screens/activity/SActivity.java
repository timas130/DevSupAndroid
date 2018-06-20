package com.sup.dev.android.libs.screens.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.sup.dev.android.androiddevsup.R;
import com.sup.dev.android.app.SupAndroid;
import com.sup.dev.android.libs.screens.SNavigator;
import com.sup.dev.android.libs.screens.Screen;
import com.sup.dev.android.tools.ToolsIntent;
import com.sup.dev.android.tools.ToolsView;
import com.sup.dev.java.classes.Subscription;
import com.sup.dev.java.tools.ToolsThreads;

public class SActivity extends Activity {

    public static boolean started;

    private ViewGroup vContainer;
    private View vTouchLock;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SupAndroid.activity = this;

        applyTheme();

        setContentView(getLayout());
        vContainer = findViewById(R.id.screen_activity_view);
        vTouchLock = findViewById(R.id.screen_activity_touch_lock);

        vTouchLock.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SupAndroid.activityIsVisible = true;
        SNavigator.onActivityResume();

        if (!started) {
            started = true;
            onFirstStart();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        SupAndroid.activityIsVisible = false;
        SNavigator.onActivityStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SNavigator.onActivityDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        SNavigator.onActivityConfigChanged();
    }

    protected void applyTheme() {

    }

    protected int getLayout() {
        return R.layout.screen_activity;
    }

    protected void onFirstStart() {

    }

    //
    //  Events
    //

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        ToolsIntent.onActivityResult(requestCode, resultCode, intent);
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void onViewBackPressed(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (!SNavigator.onBackPressed() && !onLastBackPressed()) {
            started = false;
            finish();
        }
    }

    protected boolean onLastBackPressed() {
        return false;
    }


    //
    //  Fragments
    //

    private Subscription subscriptionTouchLock;

    public void setView(Screen view) {

        if (view == null) {
            finish();
            return;
        }

        View old = vContainer.getChildCount() == 0 ? null : vContainer.getChildAt(0);
        vContainer.addView(view, 0);

        if (old != null) {
            view.setVisibility(View.INVISIBLE);
            vTouchLock.setVisibility(View.VISIBLE);
            ToolsView.fromAlpha(view);
            ToolsView.toAlpha(old, () -> vContainer.removeView(old));
        }

        if(subscriptionTouchLock != null) subscriptionTouchLock.unsubscribe();
        subscriptionTouchLock = ToolsThreads.main(ToolsView.ANIMATION_TIME, () -> vTouchLock.setVisibility(View.GONE));
    }


}
