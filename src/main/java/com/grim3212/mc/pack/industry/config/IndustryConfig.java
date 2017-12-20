package com.grim3212.mc.pack.industry.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.ConfigUtils;
import com.grim3212.mc.pack.core.config.ConfigUtils.ArmorMaterialHolder;
import com.grim3212.mc.pack.core.config.ConfigUtils.ToolMaterialHolder;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.util.MachineRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class IndustryConfig extends GrimConfig {

	public static final String CONFIG_NAME = "industry";
	public static final String CONFIG_GENERAL_NAME = "industry.general";
	public static final String CONFIG_EXTRUDER_NAME = "industry.extruder";
	public static final String CONFIG_PARTS_NAME = "industry.subparts";

	public static List<ItemStack> workbenchUpgradeList = new ArrayList<ItemStack>();

	public static ToolMaterialHolder steelToolMaterial = new ToolMaterialHolder("steel", 3, 1200, 7.5F, 2.5F, 12);
	public static ArmorMaterialHolder antiRadiationArmorMaterial = new ArmorMaterialHolder("anti_radiation", 5, new int[] { 2, 5, 3, 1 }, 15, 0.0F);
	public static ArmorMaterialHolder gravityArmorMaterial = new ArmorMaterialHolder("gravity", 15, new int[] { 2, 6, 5, 2 }, 9, 0.0F);
	public static boolean generateUranium;
	public static boolean generateOilOre;

	//Chunk Loader
	public static int chunkLoaderSize;
	public static int chunkLoaderTimeout;

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

	// Uranium Damage - add by SH
	public static float uraniumDamage;

	public static int bridgeMaxLength;

	// Client Only Settings
	public static boolean showFanParticles;

	// Subparts
	public static boolean subpartBridges;
	public static boolean subpartChunkLoader;
	public static boolean subpartConveyor;
	public static boolean subpartDecoration;
	public static boolean subpartDoors;
	public static boolean subpartElementalBlocks;
	public static boolean subpartExtruder;
	public static boolean subpartIceMaker;
	public static boolean subpartFans;
	public static boolean subpartGates;
	public static boolean subpartGravity;
	public static boolean subpartHLights;
	public static boolean subpartMachines;
	public static boolean subpartMetalWorks;
	public static boolean subpartNuclear;
	public static boolean subpartRWays;
	public static boolean subpartSensors;
	public static boolean subpartShapedCharges;
	public static boolean subpartSpikes;
	public static boolean subpartSteel;
	public static boolean subpartStorage;
	public static boolean subpartTorches;
	public static boolean subpartWorkbenchUpgrades;

	@Override
	public String name() {
		return CONFIG_NAME;
	}

	@Override
	public void syncFirst() {
		ConfigUtils.setCurrentPart(GrimIndustry.partName);
		if (subpartSteel)
			steelToolMaterial.load();
		if (subpartGravity)
			gravityArmorMaterial.load();
		if (subpartNuclear)
			antiRadiationArmorMaterial.load();
	}

	@Override
	public void syncSubparts() {
		subpartBridges = config.get(CONFIG_PARTS_NAME, "Enable SubPart bridges", true).setRequiresMcRestart(true).getBoolean();
		subpartChunkLoader = config.get(CONFIG_PARTS_NAME, "Enable SubPart chunk loader", true).setRequiresMcRestart(true).getBoolean();
		subpartConveyor = config.get(CONFIG_PARTS_NAME, "Enable SubPart conveyor", true).setRequiresMcRestart(true).getBoolean();
		subpartDecoration = config.get(CONFIG_PARTS_NAME, "Enable SubPart decoration", true).setRequiresMcRestart(true).getBoolean();
		subpartDoors = config.get(CONFIG_PARTS_NAME, "Enable SubPart doors", true).setRequiresMcRestart(true).getBoolean();
		subpartElementalBlocks = config.get(CONFIG_PARTS_NAME, "Enable SubPart elemental blocks", true).setRequiresMcRestart(true).getBoolean();
		subpartExtruder = config.get(CONFIG_PARTS_NAME, "Enable SubPart extruder", true).setRequiresMcRestart(true).getBoolean();
		subpartIceMaker = config.get(CONFIG_PARTS_NAME, "Enable SubPart ice maker", true).setRequiresMcRestart(true).getBoolean();
		subpartFans = config.get(CONFIG_PARTS_NAME, "Enable SubPart fans", true).setRequiresMcRestart(true).getBoolean();
		subpartGates = config.get(CONFIG_PARTS_NAME, "Enable SubPart gates", true).setRequiresMcRestart(true).getBoolean();
		subpartGravity = config.get(CONFIG_PARTS_NAME, "Enable SubPart gravity", true).setRequiresMcRestart(true).getBoolean();
		subpartHLights = config.get(CONFIG_PARTS_NAME, "Enable SubPart hlights", true).setRequiresMcRestart(true).getBoolean();
		subpartMachines = config.get(CONFIG_PARTS_NAME, "Enable SubPart machines", true).setRequiresMcRestart(true).getBoolean();
		subpartMetalWorks = config.get(CONFIG_PARTS_NAME, "Enable SubPart metal works", true).setRequiresMcRestart(true).getBoolean();
		subpartNuclear = config.get(CONFIG_PARTS_NAME, "Enable SubPart nuclear", true).setRequiresMcRestart(true).getBoolean();
		subpartRWays = config.get(CONFIG_PARTS_NAME, "Enable SubPart rways", true).setRequiresMcRestart(true).getBoolean();
		subpartSensors = config.get(CONFIG_PARTS_NAME, "Enable SubPart sensors", true).setRequiresMcRestart(true).getBoolean();
		subpartShapedCharges = config.get(CONFIG_PARTS_NAME, "Enable SubPart shaped charges", false).setRequiresMcRestart(true).getBoolean();
		subpartSpikes = config.get(CONFIG_PARTS_NAME, "Enable SubPart spikes", true).setRequiresMcRestart(true).getBoolean();
		subpartSteel = config.get(CONFIG_PARTS_NAME, "Enable SubPart steel", true).setRequiresMcRestart(true).getBoolean();
		subpartStorage = config.get(CONFIG_PARTS_NAME, "Enable SubPart storage", true).setRequiresMcRestart(true).getBoolean();
		subpartTorches = config.get(CONFIG_PARTS_NAME, "Enable SubPart torches", true).setRequiresMcRestart(true).getBoolean();
		subpartWorkbenchUpgrades = config.get(CONFIG_PARTS_NAME, "Enable SubPart workbench upgrades", true).setRequiresMcRestart(true).getBoolean();
	}

	@Override
	public void syncConfig() {
		syncSubparts();
		syncFirst();

		ConfigUtils.setCurrentPart(GrimIndustry.partName);

		if (subpartWorkbenchUpgrades) {
			useWorkbenchUpgrades = config.get(CONFIG_GENERAL_NAME, "Do workbench upgrades double result", false).getBoolean();
			useWhitelist = config.get(CONFIG_GENERAL_NAME, "Workbench whitelist else blacklist", false).getBoolean();
			workbenchList = config.get(CONFIG_GENERAL_NAME, "Workbench black or white list", new String[] { "oredict:blockDiamond", "oredict:gemDiamond", "oredict:blockIron", "oredict:ingotIron", "oredict:blockGold", "oredict:ingotGold", "oredict:blockLapis", "oredict:gemLapis", "oredict:blockRedstone", "oredict:dustRedstone", "oredict:blockEmerald", "oredict:gemEmerald", "oredict:blockQuartz", "oredict:gemQuartz", "oredict:blockCoal", "minecraft:coal", "oredict:blockBlackDiamond", "oredict:gemBlackDiamond", "oredict:blockSteel", "oredict:ingotSteel" }).getStringList();
			returnDefaultIfListed = config.get(CONFIG_GENERAL_NAME, "Return Default Recipe If Listed", true).getBoolean();

			ConfigUtils.loadItemsOntoList(workbenchList, IndustryConfig.workbenchUpgradeList);
		}

		if (subpartFans)
			showFanParticles = config.get(CONFIG_GENERAL_NAME, "Show Fan Particles", true).getBoolean();

		if (subpartBridges)
			bridgeMaxLength = config.get(CONFIG_GENERAL_NAME, "Maximum Bridge Length", 128).getInt();

		if (subpartExtruder) {
			fuelPerMinedBlock = config.get(CONFIG_EXTRUDER_NAME, "Fuel per mined block", 400).getInt();
			fuelPerExtrudedBlock = config.get(CONFIG_EXTRUDER_NAME, "Fuel per extruded block", 200).getInt();
			moveSpeed = (float) config.get(CONFIG_EXTRUDER_NAME, "Extruder move speed", 0.1f).getDouble();
			fuelPerStick = config.get(CONFIG_EXTRUDER_NAME, "Fuel per stick", 50).getInt();
			fuelPerCoal = config.get(CONFIG_EXTRUDER_NAME, "Fuel per coal", 1600).getInt();
			fuelPerRedstone = config.get(CONFIG_EXTRUDER_NAME, "Fuel per redstone", 3200).getInt();
			fuelPerLava = config.get(CONFIG_EXTRUDER_NAME, "Fuel per lava", 6400).getInt();
			fuelPerBlazerod = config.get(CONFIG_EXTRUDER_NAME, "Fuel per blazerod", 12800).getInt();
			fuelPerMagmaCream = config.get(CONFIG_EXTRUDER_NAME, "Fuel per magma cream", 19200).getInt();
			speedModifierStick = (float) config.get(CONFIG_EXTRUDER_NAME, "Stick speed modifier", 0.3F).getDouble();
			speedModifierCoal = (float) config.get(CONFIG_EXTRUDER_NAME, "Coal speed modifier", 0.8F).getDouble();
			speedModifierRedstone = (float) config.get(CONFIG_EXTRUDER_NAME, "Redstone speed modifier", 1.4F).getDouble();
			speedModifierMagmaCream = (float) config.get(CONFIG_EXTRUDER_NAME, "Magmacream speed modifier", 2.2F).getDouble();
		}

		if (subpartNuclear) {
			generateUranium = config.get(CONFIG_GENERAL_NAME, "Generate Uranium", true).getBoolean();
			uraniumDamage = config.get(CONFIG_GENERAL_NAME, "Damage from Uranium Ore", 4).getInt();
		}

		if (subpartMachines) {
			generateOilOre = config.get(CONFIG_GENERAL_NAME, "Generate Oil Ore", true).getBoolean();
			String[] fuels = config.get(CONFIG_GENERAL_NAME, "grimpack.industry.cfg.modernfurnace_fuels", new String[] { "grimpack:fuel>25000", "grimpack:fuel_tank>225000" }).getStringList();
			MachineRecipes.INSTANCE.getModernFurnaceFuel().addAll(ConfigUtils.loadConfigurableFuel(fuels));
		}

		if(subpartChunkLoader){
			chunkLoaderSize = config.get(CONFIG_GENERAL_NAME, "Chunk Loader Size", 5, "Square chunks that are kept loaded. Should be odd!",  1, 32).getInt();
			chunkLoaderTimeout = config.get(CONFIG_GENERAL_NAME, "Chunk Loader Timeout", 0, "Turns off chunkloaders if the owner hasn't been active for this many hours. Zero and below disables this").getInt();
		}

		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		if (subpartWorkbenchUpgrades || subpartMachines || subpartFans || subpartNuclear || subpartBridges)
			list.add(new DummyCategoryElement("industryGeneralCfg", "grimpack.industry.cfg.general", new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements()));
		if (subpartExtruder)
			list.add(new DummyCategoryElement("industryExtruderCfg", "grimpack.industry.cfg.extruder", new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(CONFIG_EXTRUDER_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("industrySubPartCfg", "grimpack.industry.cfg.subparts", new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(CONFIG_PARTS_NAME)).getChildElements()));
		return list;
	}

	@Override
	public void readFromServer(PacketBuffer buffer) {
		subpartBridges = buffer.readBoolean();
		subpartChunkLoader = buffer.readBoolean();
		subpartConveyor = buffer.readBoolean();
		subpartDecoration = buffer.readBoolean();
		subpartDoors = buffer.readBoolean();
		subpartElementalBlocks = buffer.readBoolean();
		subpartExtruder = buffer.readBoolean();
		subpartIceMaker = buffer.readBoolean();
		subpartFans = buffer.readBoolean();
		subpartGates = buffer.readBoolean();
		subpartGravity = buffer.readBoolean();
		subpartHLights = buffer.readBoolean();
		subpartMachines = buffer.readBoolean();
		subpartMetalWorks = buffer.readBoolean();
		subpartNuclear = buffer.readBoolean();
		subpartRWays = buffer.readBoolean();
		subpartSensors = buffer.readBoolean();
		subpartShapedCharges = buffer.readBoolean();
		subpartSpikes = buffer.readBoolean();
		subpartSteel = buffer.readBoolean();
		subpartStorage = buffer.readBoolean();
		subpartTorches = buffer.readBoolean();
		subpartWorkbenchUpgrades = buffer.readBoolean();

		if (subpartWorkbenchUpgrades)
			useWorkbenchUpgrades = buffer.readBoolean();
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		buffer.writeBoolean(subpartBridges);
		buffer.writeBoolean(subpartChunkLoader);
		buffer.writeBoolean(subpartConveyor);
		buffer.writeBoolean(subpartDecoration);
		buffer.writeBoolean(subpartDoors);
		buffer.writeBoolean(subpartElementalBlocks);
		buffer.writeBoolean(subpartExtruder);
		buffer.writeBoolean(subpartIceMaker);
		buffer.writeBoolean(subpartFans);
		buffer.writeBoolean(subpartGates);
		buffer.writeBoolean(subpartGravity);
		buffer.writeBoolean(subpartHLights);
		buffer.writeBoolean(subpartMachines);
		buffer.writeBoolean(subpartMetalWorks);
		buffer.writeBoolean(subpartNuclear);
		buffer.writeBoolean(subpartRWays);
		buffer.writeBoolean(subpartSensors);
		buffer.writeBoolean(subpartShapedCharges);
		buffer.writeBoolean(subpartSpikes);
		buffer.writeBoolean(subpartSteel);
		buffer.writeBoolean(subpartStorage);
		buffer.writeBoolean(subpartTorches);
		buffer.writeBoolean(subpartWorkbenchUpgrades);

		if (subpartWorkbenchUpgrades)
			buffer.writeBoolean(useWorkbenchUpgrades);
	}
}
