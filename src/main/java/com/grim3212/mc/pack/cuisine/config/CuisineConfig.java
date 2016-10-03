package com.grim3212.mc.pack.cuisine.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.GrimConfig;
<<<<<<< HEAD

import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.DummyConfigElement;
=======
import com.grim3212.mc.pack.cuisine.GrimCuisine;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
import net.minecraftforge.fml.client.config.IConfigElement;

public class CuisineConfig extends GrimConfig {

<<<<<<< HEAD
	public static final String CONFIG_NAME = "cuisine";

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyConfigElement("blank", "No Config Options", ConfigGuiType.STRING, "grimpack.cuisine.cfg.general.blank"));
		return list;
	}

	@Override
	public void syncConfig() {
		super.syncConfig();
=======
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
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
	}
}
