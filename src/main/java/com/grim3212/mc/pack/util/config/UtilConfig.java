package com.grim3212.mc.pack.util.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.tools.GrimTools;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class UtilConfig extends GrimConfig {

	public static final String CONFIG_NAME = "util";

	public static double frd_power;
	public static double frd_lift;
	public static boolean soundEnabled;
	public static boolean useOldSound;
	public static boolean spawnGraves;
	public static boolean infiniteLava;
	public static boolean doubleDoors;

	@Override
	public void syncConfig() {
		frd_power = config.get(CONFIG_NAME, "Horizontal Pushing Force", 2.0D).getDouble();
		frd_lift = config.get(CONFIG_NAME, "Upward Pushing Force", 0.8D).getDouble();
		soundEnabled = config.get(CONFIG_NAME, "Sound Enabled", true).getBoolean();
		useOldSound = config.get(CONFIG_NAME, "Use Original FusRoDah Sound", false).getBoolean();
		spawnGraves = config.get(CONFIG_NAME, "Spawn graves on player death", true).getBoolean();
		infiniteLava = config.get(CONFIG_NAME, "Use Infinite Lava", false).getBoolean();
		doubleDoors = config.get(CONFIG_NAME, "Use Double Doors", true).getBoolean();

		super.syncConfig();
	}

	public static List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(GrimTools.INSTANCE.getConfig().getCategory(CONFIG_NAME)).getChildElements());
		return list;
	}
}
