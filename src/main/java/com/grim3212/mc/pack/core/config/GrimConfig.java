package com.grim3212.mc.pack.core.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.Loader;

public class GrimConfig {

	public Configuration config;

	public GrimConfig() {
		config = new Configuration(new File(Loader.instance().getConfigDir(), Loader.instance().activeModContainer().getModId() + ".cfg"));
	}

	/**
	 * Used to load config options early that item construction may depend on
	 */
	public void syncFirst() {
	}

	/**
	 * Called after PreInit of each GrimPart this should sync all available
	 * config options
	 */
	public void syncConfig() {
		if (config.hasChanged()) {
			config.save();
		}
	}

	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		return list;
	}

	public void readFromServer(PacketBuffer buffer) {

	}

	public void writeToClient(PacketBuffer buffer) {

	}
}
