package com.lidroid.plugin.mop;

import com.lidroid.plugin.util.ReflectUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-19
 * Time: AM 9:34
 */
public class MopAdapter {

    public void mopAction(Object... args) {

        StackTraceElement callerTraceElement = ReflectUtil.getCallerMethodName();

        if (callerTraceElement != null) {
            MopImplCaller mopImplCaller = new MopImplCaller(callerTraceElement, args);
            mopImplCaller.execAction();
        }
    }

    public List<Object> mopFunc(Object... args) {

        StackTraceElement callerTraceElement = ReflectUtil.getCallerMethodName();

        if (callerTraceElement != null) {
            MopImplCaller mopImplCaller = new MopImplCaller(callerTraceElement, args);
            return mopImplCaller.execFunc();
        }
        return null;
    }

    public Object mopFuncSingle(Object... args) {

        StackTraceElement callerTraceElement = ReflectUtil.getCallerMethodName();

        if (callerTraceElement != null) {
            MopImplCaller mopImplCaller = new MopImplCaller(callerTraceElement, args);
            return mopImplCaller.execFuncSingle();
        }
        return null;
    }
}
