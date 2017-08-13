package com.grim3212.mc.pack.cuisine.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.cuisine.GrimCuisine;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class CuisineConfig extends GrimConfig {

	public static final String CONFIG_NAME = "cuisine";
	public static final String CONFIG_GENERAL_NAME = "cuisine.general";
	public static final String CONFIG_PARTS_NAME = "cuisine.parts";

	public static boolean generateCocoaTrees;
	public static boolean subpartChocolate;
	public static boolean subpartDairy;
	public static boolean subpartDragonFruit;
	public static boolean subpartHealth;
	public static boolean subpartPie;
	public static boolean subpartSoda;

	@Override
	public String name() {
		return CONFIG_NAME;
	}

	@Override
	public void syncSubparts() {
		subpartChocolate = config.get(CONFIG_PARTS_NAME, "Enable SubPart chocolate", true).setRequiresMcRestart(true).getBoolean();
		subpartDairy = config.get(CONFIG_PARTS_NAME, "Enable SubPart dairy", true).setRequiresMcRestart(true).getBoolean();
		subpartDragonFruit = config.get(CONFIG_PARTS_NAME, "Enable SubPart dragon fruit", true).setRequiresMcRestart(true).getBoolean();
		subpartHealth = config.get(CONFIG_PARTS_NAME, "Enable SubPart health", true).setRequiresMcRestart(true).getBoolean();
		subpartPie = config.get(CONFIG_PARTS_NAME, "Enable SubPart pie", true).setRequiresMcRestart(true).getBoolean();
		subpartSoda = config.get(CONFIG_PARTS_NAME, "Enable SubPart soda", true).setRequiresMcRestart(true).getBoolean();
	}

	@Override
	public void syncConfig() {
		generateCocoaTrees = config.get(CONFIG_GENERAL_NAME, "Generate Cocoa Trees", true).getBoolean();

		subpartChocolate = config.get(CONFIG_PARTS_NAME, "Enable SubPart chocolate", true).setRequiresMcRestart(true).getBoolean();
		subpartDairy = config.get(CONFIG_PARTS_NAME, "Enable SubPart dairy", true).setRequiresMcRestart(true).getBoolean();
		subpartDragonFruit = config.get(CONFIG_PARTS_NAME, "Enable SubPart dragon fruit", true).setRequiresMcRestart(true).getBoolean();
		subpartHealth = config.get(CONFIG_PARTS_NAME, "Enable SubPart health", true).setRequiresMcRestart(true).getBoolean();
		subpartPie = config.get(CONFIG_PARTS_NAME, "Enable SubPart pie", true).setRequiresMcRestart(true).getBoolean();
		subpartSoda = config.get(CONFIG_PARTS_NAME, "Enable SubPart soda", true).setRequiresMcRestart(true).getBoolean();
		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement("cuisineGeneralCfg", "grimpack.cuisine.cfg.general", new ConfigElement(GrimCuisine.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("cuisineSubPartCfg", "grimpack.cuisine.cfg.subparts", new ConfigElement(GrimCuisine.INSTANCE.getConfig().getCategory(CONFIG_PARTS_NAME)).getChildElements()));
		return list;
	}

	@Override
	public void readFromServer(PacketBuffer buffer) {
		subpartChocolate = buffer.readBoolean();
		subpartDairy = buffer.readBoolean();
		subpartDragonFruit = buffer.readBoolean();
		subpartHealth = buffer.readBoolean();
		subpartPie = buffer.readBoolean();
		subpartSoda = buffer.readBoolean();
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		buffer.writeBoolean(subpartChocolate);
		buffer.writeBoolean(subpartDairy);
		buffer.writeBoolean(subpartDragonFruit);
		buffer.writeBoolean(subpartHealth);
		buffer.writeBoolean(subpartPie);
		buffer.writeBoolean(subpartSoda);
	}
}
