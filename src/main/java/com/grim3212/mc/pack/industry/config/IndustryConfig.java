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
	public static final String CONFIG_EXTRUDER_NAME = "industry.extruder";

	public static boolean generateUranium;
	public static boolean generateAluminum;
	public static boolean generateOilOre;
	public static boolean useWorkbenchUpgrades;

	// Extruder settings
	public static float fuelPerMinedBlock;
	public static float fuelPerExtrudedBlock;
	public static float moveSpeed;
	public static int fuelPerStick;
	public static int fuelPerCoal;
	public static int fuelPerRedstone;
	public static int fuelPerLava;
	public static int fuelPerBlazerod;
	public static int fuelPerMagmaCream;
	public static float speedModifierStick;
	public static float speedModifierCoal;
	public static float speedModifierRedstone;
	public static float speedModifierMagmaCream;

	@Override
	public void syncConfig() {
		generateUranium = config.get(CONFIG_GENERAL_NAME, "Generate Uranium", true).getBoolean();
		generateAluminum = config.get(CONFIG_GENERAL_NAME, "Generate Aluminum", true).getBoolean();
		generateOilOre = config.get(CONFIG_GENERAL_NAME, "Generate Oil Ore", true).getBoolean();
		useWorkbenchUpgrades = config.get(CONFIG_GENERAL_NAME, "Do workbench upgrades double result", false).getBoolean();

		fuelPerMinedBlock = (float) config.get(CONFIG_EXTRUDER_NAME, "Fuel per mined block", 350f).getDouble();
		fuelPerExtrudedBlock = (float) config.get(CONFIG_EXTRUDER_NAME, "Fuel per extruded block", 200f).getDouble();
		moveSpeed = (float) config.get(CONFIG_EXTRUDER_NAME, "Extruder move speed", 0.1f).getDouble();
		fuelPerStick = config.get(CONFIG_EXTRUDER_NAME, "Fuel per stick", 75).getInt();
		fuelPerCoal = config.get(CONFIG_EXTRUDER_NAME, "Fuel per coal", 1600).getInt();
		fuelPerRedstone = config.get(CONFIG_EXTRUDER_NAME, "Fuel per redstone", 2400).getInt();
		fuelPerLava = config.get(CONFIG_EXTRUDER_NAME, "Fuel per lava", 8600).getInt();
		fuelPerBlazerod = config.get(CONFIG_EXTRUDER_NAME, "Fuel per blazerod", 10000).getInt();
		fuelPerMagmaCream = config.get(CONFIG_EXTRUDER_NAME, "Fuel per magma cream", 15000).getInt();
		speedModifierStick = (float) config.get(CONFIG_EXTRUDER_NAME, "Stick speed modifier", 0.3F).getDouble();
		speedModifierCoal = (float) config.get(CONFIG_EXTRUDER_NAME, "Coal speed modifier", 0.8F).getDouble();
		speedModifierRedstone = (float) config.get(CONFIG_EXTRUDER_NAME, "Redstone speed modifier", 1.4F).getDouble();
		speedModifierMagmaCream = (float) config.get(CONFIG_EXTRUDER_NAME, "Magamcream speed modifier", 2.2F).getDouble();

		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements());
		list.addAll(new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(CONFIG_EXTRUDER_NAME)).getChildElements());
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
