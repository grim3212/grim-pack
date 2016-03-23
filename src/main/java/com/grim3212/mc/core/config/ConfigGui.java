package com.grim3212.mc.core.config;

import com.grim3212.mc.core.GrimCore;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen gui) {
		super(gui, new ConfigElement(GrimCore.INSTANCE.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), GrimCore.modID, false, false, GuiConfig.getAbridgedConfigPath(GrimCore.INSTANCE.getConfig().toString()));
	}

}
