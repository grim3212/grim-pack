package com.grim3212.mc.decor.config;

import com.grim3212.mc.decor.GrimDecor;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen gui) {
		super(gui, new ConfigElement(GrimDecor.INSTANCE.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), GrimDecor.modID, false, false, GuiConfig.getAbridgedConfigPath(GrimDecor.INSTANCE.getConfig().toString()));
	}

}
