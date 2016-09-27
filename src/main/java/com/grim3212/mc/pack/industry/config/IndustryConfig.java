package com.grim3212.mc.pack.industry.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.industry.GrimIndustry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class IndustryConfig extends GrimConfig {

	public static final String CONFIG_NAME = "industry";
	public static final String CONFIG_GENERAL_NAME = "industry.general";
	public static final String CONFIG_EXTRUDER_NAME = "industry.extruder";

	public static List<ItemStack> workbenchUpgradeList = new ArrayList<ItemStack>();

	public static boolean generateUranium;
	public static boolean generateAluminum;
	public static boolean generateOilOre;

	// Workbench Upgrades
	public static String[] workbenchList;
	public static boolean useWorkbenchUpgrades;
	public static boolean useWhitelist;
	public static boolean returnDefaultIfListed;

	// Extruder settings
	public static int fuelPerMinedBlock;
	public static int fuelPerExtrudedBlock;
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
	
	//Uranium Damage - add by SH
	public static int uraniumDamage;

	// Client Only Settings
	public static boolean showFanParticles;

	@Override
	public void syncConfig() {
		generateUranium = config.get(CONFIG_GENERAL_NAME, "Generate Uranium", true).getBoolean();
		generateAluminum = config.get(CONFIG_GENERAL_NAME, "Generate Aluminum", true).getBoolean();
		generateOilOre = config.get(CONFIG_GENERAL_NAME, "Generate Oil Ore", true).getBoolean();
		useWorkbenchUpgrades = config.get(CONFIG_GENERAL_NAME, "Do workbench upgrades double result", false).getBoolean();
		useWhitelist = config.get(CONFIG_GENERAL_NAME, "Workbench whitelist else blacklist", false).getBoolean();
		workbenchList = config.get(CONFIG_GENERAL_NAME, "Workbench black or white list", new String[] { "minecraft:diamond_block" }).getStringList();
		returnDefaultIfListed = config.get(CONFIG_GENERAL_NAME, "Return Default Recipe If Listed", true).getBoolean();
		showFanParticles = config.get(CONFIG_GENERAL_NAME, "Show Fan Particles", true).getBoolean();

		fuelPerMinedBlock = config.get(CONFIG_EXTRUDER_NAME, "Fuel per mined block", 350).getInt();
		fuelPerExtrudedBlock = config.get(CONFIG_EXTRUDER_NAME, "Fuel per extruded block", 200).getInt();
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
		speedModifierMagmaCream = (float) config.get(CONFIG_EXTRUDER_NAME, "Magmacream speed modifier", 2.2F).getDouble();

		registerWorkbenchList(workbenchList, IndustryConfig.workbenchUpgradeList);

		uraniumDamage = config.get(CONFIG_GENERAL_NAME, "Damage from Uranium Ore", 4).getInt();
		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement("industryGeneralCfg", "grimpack.industry.cfg.general", new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("industryExtruderCfg", "grimpack.industry.cfg.extruder", new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(CONFIG_EXTRUDER_NAME)).getChildElements()));
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

	public void registerWorkbenchList(String[] string, List<ItemStack> stacklist) {
		if (string.length > 0) {
			if (stacklist != null) {
				stacklist.clear();

				for (String name : string) {

					String[] split = name.split(":");

					if (split.length == 3) {
						Item item = Item.REGISTRY.getObject(new ResourceLocation(split[0], split[1]));
						if (item != null)
							stacklist.add(new ItemStack(item, 1, Integer.valueOf(split[2])));
						else
							GrimLog.error(GrimIndustry.partName, "Tried to add [" + split[0] + ":" + split[1] + "] to Workbench List. ITEM NOT REGISTERED");
					} else {
						Item item = Item.REGISTRY.getObject(new ResourceLocation(name));
						if (item != null)
							stacklist.add(new ItemStack(item, 1, 0));
						else
							GrimLog.error(GrimIndustry.partName, "Tried to add [" + name + "] to Workbench List. ITEM NOT REGISTERED");
					}
				}
			}
		}
	}
}
