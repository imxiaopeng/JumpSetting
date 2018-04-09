package com.cxp.jumpsetting;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2018/4/9.
 */

class AppItem {
    private final Drawable icon;
    private final String appName;
    private final String packageName;
    private final Intent intent;

    public AppItem(Drawable icon, String appName, String packageName, Intent intent) {
        this.icon=icon;
        this.appName=appName;
        this.packageName=packageName;
        this.intent=intent;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Intent getIntent() {
        return intent;
    }
}
