package com.lidroid.plugin;

import android.graphics.drawable.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: AM 11:16
 */
public class PluginInfo {
    public String name;
    public Drawable icon;
    public final String packageName;

    public int versionCode;
    public String versionName;

    public PluginInfo(
            String name,
            Drawable icon,
            String packageName,
            int versionCode,
            String versionName) {
        this.name = name;
        this.icon = icon;
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.versionName = versionName;
    }

    @Override
    public int hashCode() {
        return packageName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginInfo that = (PluginInfo) o;

        return packageName.equals(that.packageName);
    }

    @Override
    public String toString() {
        return String.format("%s : %s", name, packageName);
    }
}
