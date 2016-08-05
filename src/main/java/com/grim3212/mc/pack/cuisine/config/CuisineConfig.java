package com.grim3212.mc.pack.cuisine.config;

import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.cuisine.GrimCuisine;

public class CuisineConfig extends GrimConfig {

	@Override
	public void syncConfig() {
		super.syncConfig();
	}

	@Override
	public void updateManual() {
		GrimCuisine.proxy.registerManual(GrimCuisine.INSTANCE.getModSection());
	}
}
