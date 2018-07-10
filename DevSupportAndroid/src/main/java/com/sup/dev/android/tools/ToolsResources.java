package com.sup.dev.android.tools;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import android.util.TypedValue;

import com.sup.dev.android.R;
import com.sup.dev.android.app.SupAndroid;
import com.sup.dev.java.tools.ToolsColor;

public class ToolsResources {

    public static String getString(@StringRes int r, Object... args) {
        return (r < 0) ? null : String.format(getString(r), args);
    }

    public static int getStringId(String name) {
        return SupAndroid.appContext.getResources().getIdentifier(name, "string", SupAndroid.appContext.getPackageName());
    }

    public static String getString(String name) {
        return getString(getStringId(name));
    }

    public static String getString(@StringRes int r) {
        return r <= 0 ? null : SupAndroid.appContext.getResources().getString(r);
    }

    public static String getPlural(@PluralsRes int r, int value) {
        return r <= 0 ? null : SupAndroid.appContext.getResources().getQuantityString(r, value);
    }

    public static String[] getStringArray(@ArrayRes int r) {
        return r <= 0 ? null : SupAndroid.appContext.getResources().getStringArray(r);
    }

    public static Drawable getDrawable(@DrawableRes int r) {
        return r <= 0 ? null : SupAndroid.appContext.getResources().getDrawable(r);
    }

    public static Drawable getDrawableFromAttr(@AttrRes int r) {
        int[] attrs = new int[] { r};
        TypedArray ta = SupAndroid.activity.obtainStyledAttributes(attrs);
        Drawable drawable= ta.getDrawable(0);
        ta.recycle();
        return drawable;
    }

    public static int getColorId(String name) {
        return SupAndroid.appContext.getResources().getIdentifier(name, "color", SupAndroid.appContext.getPackageName());
    }

    public static int getColor(String name) {
        return getColor(getColorId(name));
    }


    public static int getColor(@ColorRes int r) {
        return r <= 0 ? 0 : SupAndroid.appContext.getResources().getColor(r);
    }

    public static int getAccentColor(Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        return value.data;
    }

    public static int getPrimaryColor(Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    public static int getPrimaryDarkColor(Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, value, true);
        return value.data;
    }

    public static int getAccentAlphaColor(Context context) {
        return ToolsColor.setAlpha(106, getAccentColor(context));
    }

    public static int getBackgroundColor(Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.windowBackground, value, true);
        return value.data;
    }

    public static Bitmap getBitmap(@DrawableRes int res) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeResource(SupAndroid.appContext.getResources(), res, options);

        if (bitmap != null) return bitmap;

        Bitmap fromDrawable = ToolsBitmap.getFromDrawable(getDrawable(res));

        return fromDrawable;
    }


    public static Drawable getDrawable(String name) {
        return getDrawable(getDrawableId(name));
    }

    public static int getDrawableId(String name) {
        return SupAndroid.appContext.getResources().getIdentifier(name, "drawable", SupAndroid.appContext.getPackageName());
    }

    public static Bitmap getBitmap(String name) {
        return getBitmap(getDrawableId(name));
    }

}
