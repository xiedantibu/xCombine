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
