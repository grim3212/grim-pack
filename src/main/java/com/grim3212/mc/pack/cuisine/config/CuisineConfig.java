package com.grim3212.mc.pack.cuisine.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.GrimConfig;

import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class CuisineConfig extends GrimConfig {

	@Override
	public void syncConfig() {
		super.syncConfig();
	}

	public static List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyConfigElement("blank", "NO OPTIONS YET", ConfigGuiType.STRING, "grimpack.cuisine.cfg.blank"));
		return list;
	}
}
