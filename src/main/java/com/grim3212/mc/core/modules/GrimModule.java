package com.grim3212.mc.core.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface GrimModule {

	String id();
	
	String name();
	
	String version();
	
	String[] requiredModules();
	
	String[] requiredMods();
}
