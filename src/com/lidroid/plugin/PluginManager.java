package com.lidroid.plugin;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: PM 5:18
 */
public class PluginManager {

    private static PluginManager instance;

    /**
     * get the instance of PluginManager.
     *
     * @return
     */
    public static PluginManager getInstance() {
        if (instance == null && factory != null && factory.container != null) {
            instance = new PluginManager();
        }
        return instance;
    }

    private PluginManager() {
    }

    private static PluginFactory factory;

    /**
     * init the PluginManager.
     *
     * @param container
     */
    protected static void init(final Container container) {
        factory = new PluginFactory(container);
        factory.loadAllModules();
    }

    public Container getContainer() {
        return factory.container;
    }

    public void reLoadPlugin() {
        factory.loadAllModules();
    }

    public Module getModuleByPackageName(String packageName) {
        return factory.getModuleByPackageName(packageName);
    }

    public ConcurrentHashMap<String, Module> getAllModules() {
        return factory.getAllModules();
    }

    public void setModuleDisabled(Module module, boolean disabled) {
        module.setDisabled(disabled);
        factory.container.onModuleDisableChanged(module, disabled);
    }

    public void stopModule(Module module) {
        module.onStop();
        factory.container.onModuleStopped(module);
    }

    public void uninstallModule(Module module) {
        module.onStop();
        factory.removeModule(module);
        factory.container.onModuleUninstalled(module);
    }

    public void uninstallModule(String packageName) {
        Module module = factory.getModuleByPackageName(packageName);
        if (module != null) {
            this.uninstallModule(module);
        }
    }

    /**
     * send a msg
     *
     * @param msg
     * @param from
     * @param to
     */
    public void sendMessage(PluginMessage msg, final Plugin from, final Plugin to) {
        to.onMessageReceived(msg, from);
    }

    /**
     * reply a msg
     *
     * @param msg
     * @param from
     * @param to
     * @param result
     */
    public void respondMessage(PluginMessage msg, final Plugin from, final Plugin to, Object result) {
        to.onMessageResponded(msg, from, result);
    }
}
