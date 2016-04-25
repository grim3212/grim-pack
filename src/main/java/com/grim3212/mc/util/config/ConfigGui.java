package com.grim3212.mc.util.config;

import com.grim3212.mc.util.GrimUtil;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen gui) {
		super(gui, new ConfigElement(GrimUtil.INSTANCE.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), GrimUtil.modID, false, false, GuiConfig.getAbridgedConfigPath(GrimUtil.INSTANCE.getConfig().toString()));
	}

}
