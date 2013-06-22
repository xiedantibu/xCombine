package com.lidroid.test.MainContainer;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.lidroid.plugin.*;
import com.lidroid.plugin.mop.MopAgent;
import com.lidroid.plugin.mop.TargetType;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-18
 * Time: 下午3:06
 */
public class MainContainer extends Container<MainActivity> {

    public final static String TAG = "wyouflf";

    public MainContainer(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public boolean verifyPackage(String packageName) {
        return true;
    }

    @Override
    public void onModuleFound(String packageName) {
        Log.d(TAG, "onModuleFound:" + packageName);
    }

    @Override
    public void onModuleValidateError(String packageName, String errorMessage) {
        Log.d(TAG, "onModuleValidateError:" + packageName + ", error:" + errorMessage);
    }

    @Override
    public void onModuleDependenceError(Module module, PluginDependence dependence) {
        Log.d(TAG, "onModuleDependenceError:" + module.getPluginInfo().packageName + ", " + dependence);
    }

    @Override
    public void onModuleInitialised(Module module) {
        Log.d(TAG, "onModuleInitialised:" + module.getPluginInfo().name);
    }

    @Override
    public void onModuleLoaded(Module module) {
        Log.d(TAG, "onModuleLoaded:" + module.getPluginInfo().name);
    }

    @Override
    public void onModuleStopped(Module module) {
        Log.d(TAG, "onModuleStopped:" + module.getPluginInfo().name);
    }

    @Override
    public void onModuleDisableChanged(Module module, boolean disabled) {
        Log.d(TAG, "onModuleDisableChanged:" + module.getPluginInfo().name + ", disabled " + disabled);
    }

    @Override
    public void onModuleUninstalled(Module module) {
        Log.d(TAG, "onModuleUninstalled:" + module.getPluginInfo().name);
    }

    @Override
    public void onMessageReceived(PluginMessage msg, Plugin from) {
        Log.d(TAG, "onMessageReceived:" + msg.content + ", args:" + msg.args);
        PluginManager.getInstance().respondMessage(msg, this, from, "he he, i'm a container!");
    }

    @Override
    public void onMessageResponded(PluginMessage msg, Plugin from, Object result) {
        Log.d(TAG, "onMessageResponded:" + result);
        getMainActivity().textViewSetText((String) result);
    }

    @MopAgent(targetType = TargetType.Module, packageName = "com.lidroid.test.ModuleTest")
    public View test(TextView b) {
        return (View) mopFuncSingle(b);
    }
}
