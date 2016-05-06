package com.grim3212.mc.industry.config;

import com.grim3212.mc.industry.GrimIndustry;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen gui) {
		super(gui, new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), GrimIndustry.modID, false, false, GuiConfig.getAbridgedConfigPath(GrimIndustry.INSTANCE.getConfig().toString()));
	}

}
