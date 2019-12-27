package com.vm.shadowsocks;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.vm.shadowsocks.utils.ToastUtils;

/**
 * @Copyright © 2018 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2018/8/3 17:03
 * @Author: sanbo
 */
public class App extends Application {


    public static int[] getScreenSize(Context context) {
        int[] size = new int[2];

        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
        }
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception ignored) {
            }
        }

        size[0] = widthPixels;
        size[1] = heightPixels;
        return size;
    }

    /**
     * 适配小米mix虚拟导航栏
     *
     * @param context
     * @return
     */
    public static int getHeightOfNavigationBar(Context context) {
        //如果小米手机开启了全面屏手势隐藏了导航栏则返回 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Settings.Global.getInt(context.getContentResolver(),
                    "force_fsg_nav_bar", 0) != 0) {
                return 0;
            }
        }
        int realHeight = getScreenSize(context)[1];

        Display d = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;

        return realHeight - displayHeight;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
    }

    //    https://www.jianshu.com/p/b7594ae3900f
    // 动态设置全面屏
    public void setMaxAspect() {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (applicationInfo != null) {
            applicationInfo.metaData.putString("android.max_aspect", "2.1");
        }
    }

    /**
     * 获得屏幕高度
     *
     * @return
     */
    public int getScreenHeight() {
        Resources resource = getResources();
        DisplayMetrics displayMetrics = resource.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public int getNativeBarHeight() {
        Resources resource = getResources();
        int result = 0;
        int resourceId = resource.getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = resource.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * NavigationBar
     *
     * @return
     */
    public int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
