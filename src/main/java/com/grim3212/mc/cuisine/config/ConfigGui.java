package com.grim3212.mc.cuisine.config;

import com.grim3212.mc.cuisine.GrimCuisine;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen gui) {
		super(gui, new ConfigElement(GrimCuisine.INSTANCE.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), GrimCuisine.modID, false, false, GuiConfig.getAbridgedConfigPath(GrimCuisine.INSTANCE.getConfig().toString()));
	}

}
