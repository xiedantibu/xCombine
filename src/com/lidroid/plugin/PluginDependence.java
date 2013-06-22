package com.lidroid.plugin;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: PM 4:00
 */
public class PluginDependence {
    public String packageName;
    public int versionCode;
    public Relationship relationship;

    public enum Relationship {
        eq, ge, gt, le, lt
    }

    @Override
    public String toString() {
        return "PluginDependence{" +
                "packageName='" + packageName + '\'' +
                ", versionCode=" + versionCode +
                ", relationship=" + relationship +
                '}';
    }
}
