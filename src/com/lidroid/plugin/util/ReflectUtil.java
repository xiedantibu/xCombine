package com.lidroid.plugin.util;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-19
 * Time: PM 9:20
 */
public class ReflectUtil {

    public static StackTraceElement getCurrentMethodName() {
        StackTraceElement callerTraceElement = null;
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        return stacks[3];
    }

    public static StackTraceElement getCallerMethodName() {
        StackTraceElement callerTraceElement = null;
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        return stacks[4];
    }

    /**
     * 获取反射的Method类型，
     * 不支持 args包含基本类型数据与它对应的引用类型混杂的情况。
     *
     * @param clazz
     * @param methodName
     * @param args
     * @return
     */
    public static Method getMethod(Class clazz, String methodName, Object... args) {
        Method result = null;
        Class[] types = null;
        if (args != null) {
            types = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                types[i] = args[i].getClass();
                if (types[i].equals(Integer.class)) {
                    types[i] = int.class;
                } else if (types[i].equals(Boolean.class)) {
                    types[i] = boolean.class;
                } else if (types[i].equals(Long.class)) {
                    types[i] = long.class;
                } else if (types[i].equals(Float.class)) {
                    types[i] = float.class;
                } else if (types[i].equals(Double.class)) {
                    types[i] = double.class;
                } else if (types[i].equals(Byte.class)) {
                    types[i] = byte.class;
                } else if (types[i].equals(Short.class)) {
                    types[i] = short.class;
                }
            }
        }
        try {
            result = clazz.getDeclaredMethod(methodName, types);
        } catch (NoSuchMethodException e) {
            for (int i = 0; i < args.length; i++) {
                types[i] = args[i].getClass();
            }
            try {
                result = clazz.getDeclaredMethod(methodName, types);
            } catch (NoSuchMethodException e1) {
            }
        }
        return result;
    }
}
