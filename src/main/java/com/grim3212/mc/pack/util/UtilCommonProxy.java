package com.grim3212.mc.pack.util;

import com.grim3212.mc.pack.core.proxy.CommonProxy;

import net.minecraftforge.fml.client.config.GuiConfigEntries.IConfigEntry;

public class UtilCommonProxy extends CommonProxy {

	public Class<? extends IConfigEntry> getSliderClass() {
		return null;
	}

	@Override
	public void preInit() {
	}

}
