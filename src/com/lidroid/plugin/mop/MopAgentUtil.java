package com.lidroid.plugin.mop;

import com.lidroid.plugin.util.DoubleKeyValueMap;
import com.lidroid.plugin.util.ReflectUtil;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-19
 * Time: AM 11:31
 */
public class MopAgentUtil {

    /**
     * key1: class
     * key2: methodName
     * value: mopAgent
     */
    private static DoubleKeyValueMap<Class, String, MopAgent> runAsLocalMap =
            new DoubleKeyValueMap<Class, String, MopAgent>();

    public static MopAgent getMopAgentAnnotation(Class clazz, String methodName, Object... args) {

        MopAgent result = null;
        if (runAsLocalMap.containsKey(clazz, methodName)) {
            result = runAsLocalMap.get(clazz, methodName);
        } else {
            Method method = ReflectUtil.getMethod(clazz, methodName, args);
            if (method != null) {
                result = method.getAnnotation(MopAgent.class);
            }
        }
        return result;
    }

}
