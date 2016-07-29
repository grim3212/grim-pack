package com.grim3212.mc.pack.industry.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.industry.GrimIndustry;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class IndustryConfig extends GrimConfig {

	public static final String CONFIG_NAME = "industry";

	public static boolean generateUranium;
	public static boolean generateAluminum;
	public static boolean generateOilOre;
	public static boolean useWorkbenchUpgrades;

	@Override
	public void syncConfig() {
		generateUranium = config.get(CONFIG_NAME, "Generate Uranium", true).getBoolean();
		generateAluminum = config.get(CONFIG_NAME, "Generate Aluminum", true).getBoolean();
		generateOilOre = config.get(CONFIG_NAME, "Generate Oil Ore", true).getBoolean();
		useWorkbenchUpgrades = config.get(CONFIG_NAME, "Do workbench upgrades double result", true).getBoolean();

		super.syncConfig();
	}

	public static List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(CONFIG_NAME)).getChildElements());
		return list;
	}
}
