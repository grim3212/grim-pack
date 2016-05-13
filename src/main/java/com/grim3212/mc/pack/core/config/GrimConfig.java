package com.grim3212.mc.pack.core.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class GrimConfig {

	public Configuration config;

	public GrimConfig() {
		config = new Configuration(new File(Loader.instance().getConfigDir(), Loader.instance().activeModContainer().getModId() + ".cfg"));
		syncConfig();
	}

	public void syncConfig() {
		if (config.hasChanged()) {
			config.save();
		}
	}
}
