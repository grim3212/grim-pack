package com.grim3212.mc.pack.tools.config;

import com.grim3212.mc.pack.tools.GrimTools;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen gui) {
		super(gui, new ConfigElement(GrimTools.INSTANCE.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), GrimTools.modID, false, false, GuiConfig.getAbridgedConfigPath(GrimTools.INSTANCE.getConfig().toString()));
	}

}
