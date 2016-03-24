package com.grim3212.mc.decor.config;

import com.grim3212.mc.core.config.GrimConfig;

public class DecorConfig extends GrimConfig {

	// public static boolean giveManualOnJoin;

	@Override
	public void syncConfig() {
		// giveManualOnJoin = config.get(Configuration.CATEGORY_GENERAL, "Give
		// Instruction Manual on World Join", true).getBoolean();

		super.syncConfig();
	}
}
