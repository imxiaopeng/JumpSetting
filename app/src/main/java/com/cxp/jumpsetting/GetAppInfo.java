package com.cxp.jumpsetting;

/**
 * Created by Administrator on 2018/4/9.
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**ApplicationInfo 中为我们封装了一系列app的属性和相关的东西，
 这里只获取了我需要的几个内容没有兴趣的可以看下ApplicationInfo 都封装了什么*/
public class GetAppInfo {
    private List<PackageInfo> packageInfo;
    private PackageManager packageManager;
    private List<AppItem> list;

    public GetAppInfo(Context context, List<PackageInfo> packageInfo) {
        this.packageInfo = packageInfo;
        packageManager = context.getPackageManager();
        list = new ArrayList<>();
    }


    public List<AppItem> getApps() {
        for (PackageInfo info : packageInfo) {
            ApplicationInfo appInfo = info.applicationInfo;
            //去除系统应用
            if (!filterApp(appInfo)) {
                continue;
            }
            //拿到应用程序的图标
            Drawable icon = appInfo.loadIcon(packageManager);
            //拿到应用程序的程序名
            String appName = appInfo.loadLabel(packageManager).toString();
            //拿到应用程序的包名
            String packageName = appInfo.packageName;
            //拿到应用程序apk路径
            String apkePath = appInfo.sourceDir;
            //获取应用程序启动意图
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            AppItem appItem = new AppItem(icon, appName, packageName, intent);
            list.add(appItem);
        }
        return list;
    }
    /**
     * 判断某一个应用程序是不是系统的应用程序，
     * 如果是返回true，否则返回false。
     */
    public boolean filterApp(ApplicationInfo info) {
        //有些系统应用是可以更新的，如果用户自己下载了一个系统的应用来更新了原来的，它还是系统应用，这个就是判断这种情况的
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
            //判断是不是系统应用
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }
}