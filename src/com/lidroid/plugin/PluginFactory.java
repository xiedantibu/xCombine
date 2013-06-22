package com.lidroid.plugin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import dalvik.system.PathClassLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: PM 2:18
 */
public class PluginFactory {

    protected final Container container;

    private static String containerSharedUserID;

    protected PluginFactory(Container container) {
        this.container = container;
        try {
            PackageManager pm = container.context.getPackageManager();
            containerSharedUserID = pm.getPackageInfo(container.getPluginInfo().packageName, 0).sharedUserId;
        } catch (PackageManager.NameNotFoundException e) {
            containerSharedUserID = "com.lidroid.plugin";
        }
    }

    /**
     * all loaded modules.
     * key: packageName
     * value: module
     */
    private ConcurrentHashMap<String, Module> modules = new ConcurrentHashMap<String, Module>();

    /**
     * load all modules，
     * if the module exists，it will not load again。
     * if you need to load it again，please remove it firstly.
     */
    protected void loadAllModules() {

        PackageManager pm = container.context.getPackageManager();
        List<PackageInfo> packageInfoList = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);

        for (PackageInfo info : packageInfoList) {
            if (containerSharedUserID.equals(info.sharedUserId)
                    && !container.getPluginInfo().packageName.equals(info.packageName)) {
                loadModule(info);
            }
        }

        analyzeDependencies();
    }

    /**
     * load one module.
     * if the module exists，it will not load again。
     * if you need to load it again，please remove it firstly.
     */
    protected void loadModule(PackageInfo info) {
        if (!modules.containsKey(info.packageName)) {

            if (!container.verifyPackage(info.packageName)) {
                return;
            }

            // found one module
            container.onModuleFound(info.packageName);

            try {

                Context moduleContext = container.context.createPackageContext(
                        info.packageName,
                        Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);

                PathClassLoader classLoader = new PathClassLoader(
                        info.applicationInfo.sourceDir,
                        container.context.getClassLoader());

                Class clazz = classLoader.loadClass(
                        info.packageName + "." + Module.APPOINT_MODULE_CLASS_NAME);


                Constructor constructor = clazz.getConstructor(Context.class);
                Module module = (Module) constructor.newInstance(moduleContext);

                // init the module completely.
                container.onModuleInitialised(module);


                modules.put(info.packageName, module);

                container.onModuleLoaded(module);

            } catch (PackageManager.NameNotFoundException e) {
                container.onModuleValidateError(
                        info.packageName,
                        container.context.getString(R.string.package_not_found));
            } catch (ClassNotFoundException e) {
                container.onModuleValidateError(
                        info.packageName,
                        container.context.getString(R.string.create_appoint_class_error));
            } catch (NoSuchMethodException e) {
                container.onModuleValidateError(
                        info.packageName,
                        container.context.getString(R.string.create_appoint_class_error));
            } catch (InvocationTargetException e) {
                container.onModuleValidateError(
                        info.packageName,
                        container.context.getString(R.string.create_appoint_class_error));
            } catch (InstantiationException e) {
                container.onModuleValidateError(
                        info.packageName,
                        container.context.getString(R.string.create_appoint_class_error));
            } catch (IllegalAccessException e) {
                container.onModuleValidateError(
                        info.packageName,
                        container.context.getString(R.string.create_appoint_class_error));
            } catch (Exception e) {
                container.onModuleValidateError(
                        info.packageName,
                        container.context.getString(R.string.unknown_error));
            }
        }
    }

    protected void removeModule(Module module) {
        modules.remove(module.getPluginInfo().packageName);
    }

    protected Module getModuleByPackageName(String packageName) {
        Module result = null;
        if (modules.containsKey(packageName)) {
            result = modules.get(packageName);
        }
        return result;
    }

    /**
     * get all the loaded modules.
     * <p/>
     * key: packageName
     * value: module
     */
    protected ConcurrentHashMap<String, Module> getAllModules() {
        return modules;
    }

    private void analyzeDependencies() {
        if (modules.size() > 0) {
            for (Module module : modules.values()) {
                List<PluginDependence> dependencies = module.getPluginDependencies();
                if (dependencies != null && dependencies.size() > 0) {
                    for (PluginDependence dependence : dependencies) {
                        if (modules.containsKey(dependence.packageName)) {
                            if (!compareDependencies(dependence, modules.get(dependence.packageName))) {
                                container.onModuleDependenceError(module, dependence);
                            }
                        } else if (dependence.packageName.equals(container.getPluginInfo().packageName)) {
                            if (!compareDependencies(dependence, container)) {
                                container.onModuleDependenceError(module, dependence);
                            }
                        } else {
                            container.onModuleDependenceError(module, dependence);
                        }
                    }
                }
            }
        }
    }

    /**
     * compare the dependence
     *
     * @param dependence
     * @param plugin
     * @return whether match the dependence or not
     */
    private boolean compareDependencies(PluginDependence dependence, Plugin plugin) {
        if (dependence.packageName.equals(plugin.getPluginInfo().packageName)) {
            switch (dependence.relationship) {
                case eq: {
                    return dependence.versionCode == plugin.getPluginInfo().versionCode;
                }
                case ge: {
                    return dependence.versionCode >= plugin.getPluginInfo().versionCode;
                }
                case gt: {
                    return dependence.versionCode > plugin.getPluginInfo().versionCode;
                }
                case le: {
                    return dependence.versionCode < plugin.getPluginInfo().versionCode;
                }
                case lt: {
                    return dependence.versionCode <= plugin.getPluginInfo().versionCode;
                }
            }
        }
        return true;
    }
}
