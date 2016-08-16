package com.grim3212.mc.pack.industry.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.industry.GrimIndustry;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class IndustryConfig extends GrimConfig {

	public static final String CONFIG_NAME = "industry";
	public static final String CONFIG_GENERAL_NAME = "industry.general";

	public static boolean generateUranium;
	public static boolean generateAluminum;
	public static boolean generateOilOre;
	public static boolean useWorkbenchUpgrades;

	@Override
	public void syncConfig() {
		generateUranium = config.get(CONFIG_GENERAL_NAME, "Generate Uranium", true).getBoolean();
		generateAluminum = config.get(CONFIG_GENERAL_NAME, "Generate Aluminum", true).getBoolean();
		generateOilOre = config.get(CONFIG_GENERAL_NAME, "Generate Oil Ore", true).getBoolean();
		useWorkbenchUpgrades = config.get(CONFIG_GENERAL_NAME, "Do workbench upgrades double result", false).getBoolean();

		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements());
		return list;
	}

	@Override
	public void readFromServer(PacketBuffer buffer) {
		useWorkbenchUpgrades = buffer.readBoolean();
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		buffer.writeBoolean(useWorkbenchUpgrades);
	}
}
