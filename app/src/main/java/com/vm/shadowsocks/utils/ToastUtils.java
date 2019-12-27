package com.vm.shadowsocks.utils;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;


/**
 * @Copyright © 2018 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2018/8/3 17:05
 * @Author: sanbo
 */
public final class ToastUtils {

    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static Toast mToast;
    private static int sGravity = -1;
    private static int sXOffset = -1;
    private static int sYOffset = -1;
    private static int sBgColor = COLOR_DEFAULT;
    private static int sBgResource = -1;
    private static int sMsgColor = COLOR_DEFAULT;
    private static int sMsgTextSize = -1;

    private static Application mApp;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Application app) {
        if (app != null) {
            mApp = app;
        } else {
            hookGetApp();
        }
    }

    private static void hookGetApp() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object at = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(at);
            if (app != null) {
                mApp = (Application) app;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Set the gravity.
     *
     * @param gravity The gravity.
     * @param xOffset X-axis offset, in pixel.
     * @param yOffset Y-axis offset, in pixel.
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        sGravity = gravity;
        sXOffset = xOffset;
        sYOffset = yOffset;
    }

    /**
     * Set the color of background.
     *
     * @param backgroundColor The color of background.
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        sBgColor = backgroundColor;
    }

    /**
     * Set the resource of background.
     *
     * @param bgResource The resource of background.
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        sBgResource = bgResource;
    }

    /**
     * Set the color of message.
     *
     * @param msgColor The color of message.
     */
    public static void setMsgColor(@ColorInt final int msgColor) {
        sMsgColor = msgColor;
    }

    /**
     * Set the text size of message.
     *
     * @param textSize The text size of message.
     */
    public static void setMsgTextSize(final int textSize) {
        sMsgTextSize = textSize;
    }

    /**
     * Show the mToast for a short period of time.
     *
     * @param text The text.
     */
    public static void showShort(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * Show the mToast for a short period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * Show the mToast for a short period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showShort(@StringRes final int resId, final Object... args) {
        if (args != null && args.length == 0) {
            show(resId, Toast.LENGTH_SHORT);
        } else {
            show(resId, Toast.LENGTH_SHORT, args);
        }
    }

    /**
     * Show the mToast for a short period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showShort(final String format, final Object... args) {
        if (args != null && args.length == 0) {
            show(format, Toast.LENGTH_SHORT);
        } else {
            show(format, Toast.LENGTH_SHORT, args);
        }
    }

    /**
     * Show the mToast for a long period of time.
     *
     * @param text The text.
     */
    public static void showLong(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

    /**
     * Show the mToast for a long period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showLong(@StringRes final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    public static void show(@NonNull final String text) {
        show(text, Toast.LENGTH_LONG);
    }

    public static void show(final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * Show the mToast for a long period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showLong(@StringRes final int resId, final Object... args) {
        if (args != null && args.length == 0) {
            show(resId, Toast.LENGTH_SHORT);
        } else {
            show(resId, Toast.LENGTH_LONG, args);
        }
    }

    /**
     * Show the mToast for a long period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showLong(final String format, final Object... args) {
        if (args != null && args.length == 0) {
            show(format, Toast.LENGTH_SHORT);
        } else {
            show(format, Toast.LENGTH_LONG, args);
        }
    }

    /**
     * Show custom mToast for a short period of time.
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomShort(@LayoutRes final int layoutId) {
        final View view = getView(layoutId);
        show(view, Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * Show custom mToast for a long period of time.
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomLong(@LayoutRes final int layoutId) {
        final View view = getView(layoutId);
        show(view, Toast.LENGTH_LONG);
        return view;
    }

    /**
     * Cancel the mToast.
     */
    public static void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    private static void show(@StringRes final int resId, final int duration) {
        try {
            show(mApp.getResources().getText(resId).toString(), duration);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void show(@StringRes final int resId, final int duration, final Object... args) {

        try {
            show(String.format(mApp.getResources().getString(resId), args), duration);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void show(final String format, final int duration, final Object... args) {
        show(String.format(format, args), duration);
    }

    private static void show(final CharSequence text, final int duration) {
        HANDLER.post(new Runnable() {
            @SuppressLint("ShowToast")
            @Override
            public void run() {
                try {
                    cancel();
                    mToast = Toast.makeText(mApp, text, duration);
                    final TextView tvMessage = mToast.getView().findViewById(android.R.id.message);
                    if (sMsgColor != COLOR_DEFAULT) {
                        tvMessage.setTextColor(sMsgColor);
                    }
                    if (sMsgTextSize != -1) {
                        tvMessage.setTextSize(sMsgTextSize);
                    }
                    if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                        mToast.setGravity(sGravity, sXOffset, sYOffset);
                    }
                    setBg(tvMessage);
                    showToast();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void show(final View view, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                try {
                    cancel();
                    mToast = new Toast(mApp);
                    mToast.setView(view);
                    mToast.setDuration(duration);
                    if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                        mToast.setGravity(sGravity, sXOffset, sYOffset);
                    }
                    setBg();
                    showToast();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void showToast() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            try {
                Field field = View.class.getDeclaredField("mContext");
                field.setAccessible(true);
                field.set(mToast.getView(), new ApplicationContextWrapperForApi25());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        mToast.show();
    }

    private static void setBg() {
        if (sBgResource != -1) {
            final View toastView = mToast.getView();
            toastView.setBackgroundResource(sBgResource);
        } else if (sBgColor != COLOR_DEFAULT) {
            final View toastView = mToast.getView();
            Drawable background = toastView.getBackground();
            if (background != null) {
                background.setColorFilter(
                        new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN)
                );
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    toastView.setBackground(new ColorDrawable(sBgColor));
                } else {
                    toastView.setBackgroundDrawable(new ColorDrawable(sBgColor));
                }
            }
        }
    }

    private static void setBg(final TextView tvMsg) {
        if (sBgResource != -1) {
            final View toastView = mToast.getView();
            toastView.setBackgroundResource(sBgResource);
            tvMsg.setBackgroundColor(Color.TRANSPARENT);
        } else if (sBgColor != COLOR_DEFAULT) {
            final View toastView = mToast.getView();
            Drawable tvBg = toastView.getBackground();
            Drawable msgBg = tvMsg.getBackground();
            if (tvBg != null && msgBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
                tvMsg.setBackgroundColor(Color.TRANSPARENT);
            } else if (tvBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else if (msgBg != null) {
                msgBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else {
                toastView.setBackgroundColor(sBgColor);
            }
        }
    }

    private static View getView(@LayoutRes final int layoutId) {
        LayoutInflater inflate = null;
        try {
            inflate = (LayoutInflater) mApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return inflate != null ? inflate.inflate(layoutId, null) : null;
    }

    private static final class ApplicationContextWrapperForApi25 extends ContextWrapper {

        ApplicationContextWrapperForApi25() {
            super(mApp);
        }

        @Override
        public Context getApplicationContext() {
            return this;
        }

        @Override
        public Object getSystemService(@NonNull String name) {
            if (Context.WINDOW_SERVICE.equals(name)) {
                // noinspection ConstantConditions
                return new WindowManagerWrapper(
                        (WindowManager) getBaseContext().getSystemService(name)
                );
            }
            return super.getSystemService(name);
        }

        private static final class WindowManagerWrapper implements WindowManager {

            private final WindowManager base;

            private WindowManagerWrapper(@NonNull WindowManager base) {
                this.base = base;
            }

            @Override
            public Display getDefaultDisplay() {
                return base.getDefaultDisplay();
            }

            @Override
            public void removeViewImmediate(View view) {
                base.removeViewImmediate(view);
            }

            @Override
            public void addView(View view, ViewGroup.LayoutParams params) {
                try {
                    base.addView(view, params);
                } catch (Throwable e) {
                    Log.e("WindowManagerWrapper", "[addView]", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
                base.updateViewLayout(view, params);
            }

            @Override
            public void removeView(View view) {
                base.removeView(view);
            }
        }
    }
}