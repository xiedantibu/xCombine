package com.lidroid.plugin;

import android.content.Context;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: PM 2:21
 */
public abstract class Module extends Plugin {

    public final static String APPOINT_MODULE_CLASS_NAME = "CustomModule";

    public Module(Context context) {
        super(context);
    }

    public abstract List<PluginDependence> getPluginDependencies();

    public abstract boolean isDisabled();

    public abstract void setDisabled(boolean disabled);

    public abstract void onStart();

    public abstract void onResume();

    public abstract void onStop();
}
