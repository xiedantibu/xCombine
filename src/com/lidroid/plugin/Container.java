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

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import com.lidroid.plugin.mop.MopAgent;
import com.lidroid.plugin.mop.TargetType;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: PM 2:21
 * <p/>
 * the container of all modules
 */
public abstract class Container<T extends Activity> extends Plugin implements ModuleCircleLifeEvent {

    private InstallationReceiver installationReceiver = new InstallationReceiver();

    public Container(T mainActivity) {
        super(mainActivity);
        PluginManager.init(this);
    }

    public T getMainActivity() {
        return (T) this.context;
    }

    /**
     * on start work，
     * please call it when mainActivity onStart.
     */
    @MopAgent(targetType = TargetType.AllModules, ignoreMopImpl = true)
    public final void onStart() {

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        this.context.registerReceiver(installationReceiver, filter);

        mopAction();
    }

    /**
     * on resume work，
     * please call it when mainActivity onResume.
     */
    @MopAgent(targetType = TargetType.AllModules, ignoreMopImpl = true)
    public final void onResume() {
        mopAction();

        PluginManager.getInstance().reLoadPlugin();
    }

    /**
     * on stop work，
     * please call it when mainActivity onStop.
     */
    @MopAgent(targetType = TargetType.AllModules, ignoreMopImpl = true)
    public final void onStop() {
        mopAction();

        this.context.unregisterReceiver(installationReceiver);
    }

    /**
     * verify package，
     * if return false, it will not load the package.
     *
     * @param packageName
     * @return
     */
    public abstract boolean verifyPackage(String packageName);
}
