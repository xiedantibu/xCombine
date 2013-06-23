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

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: PM 2:21
 */
public abstract class Module extends Plugin {

    public final static String APPOINT_MODULE_CLASS_NAME = "CustomModule";

    public Module(Context context) {
        super(context);
    }

    public abstract List<PluginDependence> getPluginDependencies();

    public abstract boolean isDisabled();

    public abstract void setDisabled(boolean disabled);

    public abstract void onStart();

    public abstract void onResume();

    public abstract void onStop();
}
