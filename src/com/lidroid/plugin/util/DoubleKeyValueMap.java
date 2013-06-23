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

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-19
 * Time: PM 1:18
 */
public class DoubleKeyValueMap<K1, K2, V> {

    private ConcurrentHashMap<K1, ConcurrentHashMap<K2, V>> k1_k2V_map;

    public DoubleKeyValueMap() {
        this.k1_k2V_map = new ConcurrentHashMap<K1, ConcurrentHashMap<K2, V>>();
    }

    public void put(K1 key1, K2 key2, V value) {
        if (k1_k2V_map.containsKey(key1)) {
            ConcurrentHashMap<K2, V> k2V_map = k1_k2V_map.get(key1);
            if (!k2V_map.containsKey(key2)) {
                k2V_map.put(key2, value);
            }
        } else {
            ConcurrentHashMap<K2, V> k2V_map = new ConcurrentHashMap<K2, V>();
            k2V_map.put(key2, value);
            k1_k2V_map.put(key1, k2V_map);
        }
    }

    public V get(K1 key1, K2 key2) {
        return k1_k2V_map.get(key1).get(key2);
    }

    public boolean containsKey(K1 key1, K2 key2) {
        if (k1_k2V_map.containsKey(key1)) {
            return k1_k2V_map.get(key1).containsKey(key2);
        }
        return false;
    }

    public void clear() {
        if (k1_k2V_map.size() > 0) {
            for (ConcurrentHashMap<K2, V> k2V_map : k1_k2V_map.values()) {
                k2V_map.clear();
            }
            k1_k2V_map.clear();
        }
    }
}
