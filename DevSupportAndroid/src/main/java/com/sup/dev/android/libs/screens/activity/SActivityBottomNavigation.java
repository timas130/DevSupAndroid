package com.sup.dev.android.libs.screens.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.sup.dev.android.R;
import com.sup.dev.android.libs.screens.Navigator;
import com.sup.dev.android.libs.screens.Screen;
import com.sup.dev.android.tools.ToolsResources;
import com.sup.dev.android.tools.ToolsView;
import com.sup.dev.android.views.views.ViewChip;
import com.sup.dev.android.views.views.ViewIcon;
import com.sup.dev.android.views.widgets.WidgetMenu;
import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java.classes.callbacks.simple.Callback1;

import java.util.ArrayList;

public abstract class SActivityBottomNavigation extends SActivity {

    private WidgetMenu widgetMenu;

    private LinearLayout vContainerRoot;
    private LinearLayout vContainer;
    private boolean navigationVisible = true;
    private boolean screenBottomNavigationAllowed = true;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        vContainerRoot = findViewById(R.id.screen_activity_bottom_navigation_container_root);
        vContainer = findViewById(R.id.screen_activity_bottom_navigation_container);

        updateNavigationVisible();
    }

    public NavigationItem addExtra(){
        widgetMenu= new WidgetMenu();

        return addIcon(vContainer.getChildCount(), ToolsResources.getDrawableFromAttrId(R.attr.ic_menu), v -> {
            widgetMenu.asSheetShow();
        });
    }

    public void setNavigationVisible(boolean b) {
        navigationVisible = b;
        updateNavigationVisible();
    }

    @Override
    public void setScreen(Screen screen, Navigator.Animation animation) {
        super.setScreen(screen, animation);
        screenBottomNavigationAllowed = screen.isBottomNavigationAllowed();
        updateNavigationVisible();
    }

    private void updateNavigationVisible(){
        boolean b = navigationVisible && screenBottomNavigationAllowed;
        vContainerRoot.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    public NavigationItem addIcon(@DrawableRes int icon, Callback1<View> onClick) {
        return addIcon(vContainer.getChildCount()-(widgetMenu==null?0:1), icon, onClick);
    }

    protected NavigationItem addIcon(int position, @DrawableRes int icon, Callback1<View> onClick) {
        NavigationItem navigationItem = new NavigationItem(this);

        navigationItem.vIcon.setImageResource(icon);
        navigationItem.vIcon.setOnClickListener(onClick::callback);
        vContainer.addView(navigationItem.view, position);
        ((LinearLayout.LayoutParams) navigationItem.view.getLayoutParams()).weight = 1;
        ((LinearLayout.LayoutParams) navigationItem.view.getLayoutParams()).gravity = Gravity.CENTER;

        return navigationItem;
    }

    public void clearNavigation(){
        widgetMenu.clear();
    }

    public void addNavigationView(View view){
        widgetMenu.addView(view);
    }

    public void hideNavigation(){
        widgetMenu.hide();
    }

    public void addNavigationItem(@StringRes int text, @DrawableRes int icon, Callback onClick){
        widgetMenu.add(text, w -> onClick.callback()).icon(icon);
    }

    @Override
    protected int getLayout() {
        return R.layout.screen_activity_bottom_navigation;
    }

    //
    //   Navigation Item
    //

    public static class NavigationItem{

        public final View view;
        public final ViewIcon vIcon;
        public final ViewChip vChip;

        public NavigationItem(Context context){
            view = ToolsView.inflate(context, R.layout.screen_activity_bottom_navigation_item);
            vIcon = view.findViewById(R.id.icon);
            vChip = view.findViewById(R.id.chip);
        }

    }


}