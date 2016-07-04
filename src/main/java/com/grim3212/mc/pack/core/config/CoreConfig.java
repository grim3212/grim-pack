package com.grim3212.mc.pack.core.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.GrimCore;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class CoreConfig extends GrimConfig {

	public static boolean giveManualOnJoin;
	public static final String CONFIG_NAME = "core";

	@Override
	public void syncConfig() {
		giveManualOnJoin = config.get(CONFIG_NAME, "Give Instruction Manual on World Join", true).getBoolean();

		super.syncConfig();
	}

	public static List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(GrimCore.INSTANCE.getConfig().getCategory(CONFIG_NAME)).getChildElements());
		return list;
	}
}
