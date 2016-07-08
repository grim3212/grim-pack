package com.grim3212.mc.pack.core.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
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

	public static List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		return list;
	}
}
