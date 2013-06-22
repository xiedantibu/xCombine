package com.lidroid.plugin;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: PM 12:38
 */
public interface ModuleCircleLifeEvent {

    void onModuleFound(String packageName);

    void onModuleValidateError(String packageName, String errorMessage);

    void onModuleDependenceError(Module module, PluginDependence dependence);

    void onModuleInitialised(Module module);

    void onModuleLoaded(Module module);

    void onModuleStopped(Module module);

    void onModuleDisableChanged(Module module, boolean disabled);

    void onModuleUninstalled(Module module);
}
