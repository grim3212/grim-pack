package com.grim3212.mc.pack.util.config;

import com.grim3212.mc.pack.core.config.GrimConfig;

import net.minecraftforge.common.config.Configuration;

public class UtilConfig extends GrimConfig {

	public static double frd_power;
	public static double frd_lift;
	public static boolean soundEnabled;
	public static boolean useOldSound;
	public static boolean spawnGraves;
	public static boolean infiniteLava;
	public static boolean doubleDoors;

	@Override
	public void syncConfig() {
		frd_power = config.get(Configuration.CATEGORY_GENERAL, "Horizontal Pushing Force", 2.0D).getDouble();
		frd_lift = config.get(Configuration.CATEGORY_GENERAL, "Upward Pushing Force", 0.8D).getDouble();
		soundEnabled = config.get(Configuration.CATEGORY_GENERAL, "Sound Enabled", true).getBoolean();
		useOldSound = config.get(Configuration.CATEGORY_GENERAL, "Use Original FusRoDah Sound", false).getBoolean();
		spawnGraves = config.get(Configuration.CATEGORY_GENERAL, "Spawn graves on player death", true).getBoolean();
		infiniteLava = config.get(Configuration.CATEGORY_GENERAL, "Use Infinite Lava", false).getBoolean();
		doubleDoors = config.get(Configuration.CATEGORY_GENERAL, "Use Double Doors", true).getBoolean();

		super.syncConfig();
	}
}
