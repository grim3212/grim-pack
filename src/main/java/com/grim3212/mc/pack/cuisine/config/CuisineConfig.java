package com.grim3212.mc.pack.cuisine.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.GrimConfig;

import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.DummyConfigElement;

import com.grim3212.mc.pack.cuisine.GrimCuisine;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class CuisineConfig extends GrimConfig {

	public static final String CONFIG_GENERAL_NAME = "cuisine.general";

	public static boolean generateCocoaTrees;

	@Override
	public void syncConfig() {
		generateCocoaTrees = config.get(CONFIG_GENERAL_NAME, "Generate Cocoa Trees", true).getBoolean();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement("cuisineGeneralCfg", "grimpack.cuisine.cfg.general", new ConfigElement(GrimCuisine.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements()));
		return list;
	}
}
