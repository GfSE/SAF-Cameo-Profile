package com.haarer.saf.rules;

import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RuleTrigger {

    Class<? extends Element> type();

    String[] stereotypes() default {};

    String[] skipIfHas() default {};
}
