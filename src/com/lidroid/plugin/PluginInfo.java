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

package com.lidroid.plugin;

import android.graphics.drawable.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: AM 11:16
 */
public class PluginInfo {
    public String name;
    public Drawable icon;
    public final String packageName;

    public int versionCode;
    public String versionName;

    public PluginInfo(
            String name,
            Drawable icon,
            String packageName,
            int versionCode,
            String versionName) {
        this.name = name;
        this.icon = icon;
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.versionName = versionName;
    }

    @Override
    public int hashCode() {
        return packageName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginInfo that = (PluginInfo) o;

        return packageName.equals(that.packageName);
    }

    @Override
    public String toString() {
        return String.format("%s : %s", name, packageName);
    }
}
