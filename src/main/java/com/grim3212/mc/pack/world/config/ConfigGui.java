package com.grim3212.mc.pack.world.config;

import com.grim3212.mc.pack.world.GrimWorld;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen gui) {
		super(gui, new ConfigElement(GrimWorld.INSTANCE.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), GrimWorld.modID, false, false, GuiConfig.getAbridgedConfigPath(GrimWorld.INSTANCE.getConfig().toString()));
	}

}
