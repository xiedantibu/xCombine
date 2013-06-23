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
