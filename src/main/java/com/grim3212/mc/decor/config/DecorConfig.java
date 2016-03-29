package com.grim3212.mc.decor.config;

import com.grim3212.mc.core.config.GrimConfig;

import net.minecraftforge.common.config.Configuration;

public class DecorConfig extends GrimConfig {

	public static boolean dyeFrames;
	public static boolean burnFrames;

	@Override
	public void syncConfig() {
		dyeFrames = config.get(Configuration.CATEGORY_GENERAL, "DyeFrames", true).getBoolean();
		burnFrames = config.get(Configuration.CATEGORY_GENERAL, "BurnFrames", true).getBoolean();

		super.syncConfig();
	}
}
