package com.grim3212.mc.pack.core.config;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.GrimCore;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen gui) {
		super(gui, new ConfigElement(GrimCore.INSTANCE.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), GrimPack.modID, false, false, GuiConfig.getAbridgedConfigPath(GrimCore.INSTANCE.getConfig().toString()));
	}

}
