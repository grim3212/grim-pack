package com.grim3212.mc.pack.core.config;

import net.minecraftforge.common.config.Configuration;

public class CoreConfig extends GrimConfig {

	public static boolean giveManualOnJoin;

	@Override
	public void syncConfig() {
		giveManualOnJoin = config.get(Configuration.CATEGORY_GENERAL, "Give Instruction Manual on World Join", true).getBoolean();

		super.syncConfig();
	}
}
