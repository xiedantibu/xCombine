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

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import com.lidroid.plugin.mop.MopAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: PM 2:22
 * <p/>
 * the parent of Container and Module
 */
public abstract class Plugin extends MopAdapter {

    private PluginInfo info;

    protected final Context context;

    public Plugin(Context context) {
        this.context = context;
    }

    public final Context getContext() {
        return context;
    }

    /**
     * send a msg among plugins,
     * the format of msg comply with appointments of receiver.
     *
     * @param msg
     * @param to
     */
    public final void sendMessage(PluginMessage msg, final Plugin to) {
        PluginManager.getInstance().sendMessage(msg, this, to);
    }

    /**
     * deal with the msg that it received,
     * and return the result to the plugin(arg. from).
     * use PluginManager.respondMessage(msg, this, from，result); to reply.
     *
     * @param msg
     * @param from
     */
    public abstract void onMessageReceived(PluginMessage msg, final Plugin from);

    /**
     * receive the result from the plugin(arg. from).
     *
     * @param msg
     * @param from
     * @param result
     */
    public abstract void onMessageResponded(PluginMessage msg, final Plugin from, Object result);

    public final PluginInfo getPluginInfo() {
        if (info == null) {
            Context context = getContext();
            String packageName = context.getPackageName();
            PackageManager pm = context.getPackageManager();

            try {
                PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
                ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);

                Drawable icon = pm.getApplicationIcon(packageName);
                String name = (String) applicationInfo.loadLabel(pm);
                int versionCode = packageInfo.versionCode;
                String versionName = packageInfo.versionName;

                info = new PluginInfo(
                        name,
                        icon,
                        packageName,
                        versionCode,
                        versionName
                );

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plugin)) return false;

        Plugin plugin = (Plugin) o;

        if (!info.equals(plugin.info)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return info.hashCode();
    }
}
