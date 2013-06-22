package com.lidroid.plugin.mop;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-19
 * Time: AM 9:11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MopAgent {

    public TargetType targetType();

    public boolean ignoreMopImpl() default false;

    public String packageName() default "";

}
