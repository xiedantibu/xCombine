/*
 * Copyright (c) 2013. wyouflf(wyouflf@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.lidroid.plugin.mop;

import com.lidroid.plugin.Container;
import com.lidroid.plugin.Module;
import com.lidroid.plugin.PluginManager;
import com.lidroid.plugin.util.ReflectUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-19
 * Time: AM 10:32
 */
public class MopImplCaller {

    private String methodName;

    private List<Object> targets;

    private Object[] args;

    private MopAgent mopAgent;

    private static PluginManager pluginManager;

    protected MopImplCaller(StackTraceElement callerStackTraceElement, Object... args) {

        if (pluginManager == null) {
            pluginManager = PluginManager.getInstance();
            if (pluginManager == null) {
                return;
            }
        }

        this.args = args;

        String callerClassName = callerStackTraceElement.getClassName();
        methodName = callerStackTraceElement.getMethodName();

        Container container = pluginManager.getContainer();

        boolean isContainer = !callerClassName.endsWith(Module.APPOINT_MODULE_CLASS_NAME);

        // get mopAgent
        if (isContainer) {
            mopAgent = MopAgentUtil.getMopAgentAnnotation(
                    container.getContext().getClass(),
                    methodName, args);
            if (mopAgent == null) {
                mopAgent = MopAgentUtil.getMopAgentAnnotation(
                        container.getClass(),
                        methodName, args);
            }
        } else {
            String packageName = callerClassName.substring(0, callerClassName.lastIndexOf("."));
            Class clazz = pluginManager.getModuleByPackageName(packageName).getClass();
            mopAgent = MopAgentUtil.getMopAgentAnnotation(
                    clazz,
                    methodName, args);
        }

        if (mopAgent == null) {
            try {
                Class clazz = Class.forName(callerClassName);
                mopAgent = MopAgentUtil.getMopAgentAnnotation(
                        clazz,
                        methodName, args);
            } catch (Exception e) {
            }
        }

        // add target object
        if (mopAgent != null) {
            switch (mopAgent.targetType()) {
                case Container: {
                    targets = new ArrayList<Object>(2);
                    targets.add(container.getMainActivity());
                    targets.add(container);
                    break;
                }
                case Module: {
                    targets = new ArrayList<Object>(1);
                    targets.add(pluginManager.getModuleByPackageName(mopAgent.packageName()));
                    break;
                }
                case AllModules: {
                    targets = new ArrayList<Object>();
                    ConcurrentHashMap<String, Module> modules = pluginManager.getAllModules();
                    if (modules != null) {
                        for (Module module : modules.values()) {
                            targets.add(module);
                        }
                    }
                    break;
                }
                case All: {
                    targets = new ArrayList<Object>();
                    targets.add(container.getMainActivity());
                    targets.add(container);
                    ConcurrentHashMap<String, Module> modules = pluginManager.getAllModules();
                    if (modules != null) {
                        for (Module module : modules.values()) {
                            targets.add(module);
                        }
                    }
                    break;
                }
            }
        }
    }

    protected void execAction() {
        if (targets != null) {
            for (Object receiver : targets) {
                execOneAction(receiver, this.methodName, this.args);
            }
        }
    }

    protected List<Object> execFunc() {
        List<Object> results = null;
        if (targets != null) {
            for (Object receiver : targets) {
                Object ret = execOneFunc(receiver, this.methodName, this.args);
                if (ret != null) {
                    results.add(ret);
                }
            }
        }
        return results;
    }

    protected Object execFuncSingle() {
        if (targets != null) {
            for (Object receiver : targets) {
                Object result = execOneFunc(receiver, this.methodName, this.args);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private void execOneAction(Object receiver, String methodName, Object... args) {
        Method method = ReflectUtil.getMethod(receiver.getClass(), methodName, args);
        if (method != null &&
                method.getAnnotation(MopAgent.class) == null &&
                (mopAgent.ignoreMopImpl() || method.getAnnotation(MopImpl.class) != null)) {
            try {
                method.invoke(receiver, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }
        }
    }

    private Object execOneFunc(Object receiver, String methodName, Object... args) {
        Method method = ReflectUtil.getMethod(receiver.getClass(), methodName, args);
        if (method != null &&
                method.getAnnotation(MopAgent.class) == null &&
                (mopAgent.ignoreMopImpl() || method.getAnnotation(MopImpl.class) != null)) {
            try {
                return method.invoke(receiver, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }
        }
        return null;
    }
}
