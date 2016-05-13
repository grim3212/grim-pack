package com.grim3212.mc.pack.decor.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.decor.GrimDecor;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen gui) {
		super(gui, getConfigItems(), GrimPack.modID, false, false, GuiConfig.getAbridgedConfigPath(GrimDecor.INSTANCE.getConfig().toString()));
	}

	private static List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		list.addAll(new ConfigElement(GrimDecor.INSTANCE.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());

		list.addAll(new ConfigElement(GrimDecor.INSTANCE.getConfig().getCategory("settings")).getChildElements());

		ConfigElement grillRecipes = new ConfigElement(GrimDecor.INSTANCE.getConfig().getCategory("customgrillrecipes"));

		list.addAll(grillRecipes.getChildElements());

		return list;
	}
}
