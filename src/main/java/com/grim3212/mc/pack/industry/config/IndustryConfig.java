package com.grim3212.mc.pack.industry.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.grim3212.mc.pack.core.config.ConfigUtils;
import com.grim3212.mc.pack.core.config.ConfigUtils.ArmorMaterialHolder;
import com.grim3212.mc.pack.core.config.ConfigUtils.ToolMaterialHolder;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.decor.GrimDecor;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class IndustryConfig extends GrimConfig {

	public static final String CONFIG_NAME = "industry";
	public static final String CONFIG_GENERAL_NAME = "industry.general";
	public static final String CONFIG_EXTRUDER_NAME = "industry.extruder";
	public static final String CONFIG_PARTS_NAME = "industry.subparts";

	public static List<ItemStack> workbenchUpgradeList = new ArrayList<ItemStack>();

	public static ToolMaterialHolder steelToolMaterial = new ToolMaterialHolder("steel", 3, 1200, 7.5F, 2.5F, 12);
	public static ArmorMaterialHolder antiRadiationArmorMaterial = new ArmorMaterialHolder("anti_radiation", 5, new int[] { 2, 5, 3, 1 }, 15, 0.0F);
	public static ArmorMaterialHolder gravityArmorMaterial = new ArmorMaterialHolder("gravity", 15, new int[] { 2, 6, 5, 2 }, 9, 0.0F);
	public static ForgeConfigSpec.BooleanValue generateUranium;
	public static ForgeConfigSpec.BooleanValue generateOilOre;

	// Chunk Loader
	public static ForgeConfigSpec.IntValue chunkLoaderSize;
	public static ForgeConfigSpec.IntValue chunkLoaderTimeout;

	// Workbench Upgrades
	public static String[] workbenchList;
	public static ForgeConfigSpec.BooleanValue useWorkbenchUpgrades;
	public static ForgeConfigSpec.BooleanValue useWhitelist;
	public static ForgeConfigSpec.BooleanValue returnDefaultIfListed;

	// Extruder settings
	public static ForgeConfigSpec.IntValue fuelPerMinedBlock;
	public static ForgeConfigSpec.IntValue fuelPerExtrudedBlock;
	public static ForgeConfigSpec.DoubleValue moveSpeed;
	public static ForgeConfigSpec.IntValue fuelPerStick;
	public static ForgeConfigSpec.IntValue fuelPerCoal;
	public static ForgeConfigSpec.IntValue fuelPerRedstone;
	public static ForgeConfigSpec.IntValue fuelPerLava;
	public static ForgeConfigSpec.IntValue fuelPerBlazerod;
	public static ForgeConfigSpec.IntValue fuelPerMagmaCream;
	public static ForgeConfigSpec.DoubleValue speedModifierStick;
	public static ForgeConfigSpec.DoubleValue speedModifierCoal;
	public static ForgeConfigSpec.DoubleValue speedModifierRedstone;
	public static ForgeConfigSpec.DoubleValue speedModifierMagmaCream;

	// Uranium Damage - add by SH
	public static ForgeConfigSpec.IntValue uraniumDamage;

	public static ForgeConfigSpec.IntValue bridgeMaxLength;

	// Client Only Settings
	public static ForgeConfigSpec.BooleanValue showFanParticles;

	// Subparts
	public static ForgeConfigSpec.BooleanValue subpartBridges;
	public static ForgeConfigSpec.BooleanValue subpartChunkLoader;
	public static ForgeConfigSpec.BooleanValue subpartConveyor;
	public static ForgeConfigSpec.BooleanValue subpartDecoration;
	public static ForgeConfigSpec.BooleanValue subpartDoors;
	public static ForgeConfigSpec.BooleanValue subpartElementalBlocks;
	public static ForgeConfigSpec.BooleanValue subpartExtruder;
	public static ForgeConfigSpec.BooleanValue subpartIceMaker;
	public static ForgeConfigSpec.BooleanValue subpartFans;
	public static ForgeConfigSpec.BooleanValue subpartGates;
	public static ForgeConfigSpec.BooleanValue subpartGravity;
	public static ForgeConfigSpec.BooleanValue subpartHLights;
	public static ForgeConfigSpec.BooleanValue subpartMachines;
	public static ForgeConfigSpec.BooleanValue subpartMetalWorks;
	public static ForgeConfigSpec.BooleanValue subpartNuclear;
	public static ForgeConfigSpec.BooleanValue subpartRWays;
	public static ForgeConfigSpec.BooleanValue subpartSensors;
	public static ForgeConfigSpec.BooleanValue subpartShapedCharges;
	public static ForgeConfigSpec.BooleanValue subpartSpikes;
	public static ForgeConfigSpec.BooleanValue subpartSteel;
	public static ForgeConfigSpec.BooleanValue subpartStorage;
	public static ForgeConfigSpec.BooleanValue subpartTorches;
	public static ForgeConfigSpec.BooleanValue subpartWorkbenchUpgrades;

	@Override
	public String name() {
		return CONFIG_NAME;
	}

	@Override
	public Optional<ForgeConfigSpec> initServer(Builder serverBuilder) {
		ConfigUtils.setCurrentPart(GrimDecor.partName);
		serverBuilder.comment(name());

		subpartBridges = serverBuilder.comment("Enable bridges subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartBridges", true);
		subpartChunkLoader = serverBuilder.comment("Enable chunk loaded subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartChunkLoader", true);
		subpartConveyor = serverBuilder.comment("Enable conveyor subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartConveyor", true);
		subpartDecoration = serverBuilder.comment("Enable decoration subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartDecoration", true);
		subpartDoors = serverBuilder.comment("Enable doors subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartDoors", true);
		subpartElementalBlocks = serverBuilder.comment("Enable elemental blocks subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartElementalBlocks", true);
		subpartExtruder = serverBuilder.comment("Enable extruder subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartExtruder", true);
		subpartIceMaker = serverBuilder.comment("Enable ice maker subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartIceMaker", true);
		subpartFans = serverBuilder.comment("Enable fans subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartFans", true);
		subpartGates = serverBuilder.comment("Enable gates subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartGates", true);
		subpartGravity = serverBuilder.comment("Enable gravity subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartGravity", true);
		subpartHLights = serverBuilder.comment("Enable hlights subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartHLights", true);
		subpartMachines = serverBuilder.comment("Enable machines subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartMachines", true);
		subpartMetalWorks = serverBuilder.comment("Enable metal works subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartMetalWorks", true);
		subpartNuclear = serverBuilder.comment("Enable nuclear subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartNuclear", true);
		subpartRWays = serverBuilder.comment("Enable rways subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartRWays", true);
		subpartSensors = serverBuilder.comment("Enable sensors subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartSensors", true);
		subpartShapedCharges = serverBuilder.comment("Enable shaped charges subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartShapedCharges", false);
		subpartSpikes = serverBuilder.comment("Enable spikes subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartSpikes", true);
		subpartSteel = serverBuilder.comment("Enable steel subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartSteel", true);
		subpartStorage = serverBuilder.comment("Enable storage subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartStorage", true);
		subpartTorches = serverBuilder.comment("Enable torches subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartTorches", true);
		subpartWorkbenchUpgrades = serverBuilder.comment("Enable workbench upgrades subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartWorkbenchUpgrades", true);

		useWorkbenchUpgrades = serverBuilder.comment("Do workbench upgrades double result").define(CONFIG_GENERAL_NAME + ".useWorkbenchUpgrades", false);
		useWhitelist = serverBuilder.comment("Workbench whitelist else blacklist").define(CONFIG_GENERAL_NAME + ".useWhitelist", false);
		returnDefaultIfListed = serverBuilder.comment("Return Default Recipe If Listed").define(CONFIG_GENERAL_NAME + ".returnDefaultIfListed", true);

		bridgeMaxLength = serverBuilder.comment("Maximum Bridge Length").defineInRange(CONFIG_EXTRUDER_NAME + ".bridgeMaxLength", 128, 1, 256);
		fuelPerMinedBlock = serverBuilder.comment("Fuel per mined block").defineInRange(CONFIG_EXTRUDER_NAME + ".fuelPerMinedBlock", 400, 0, 100000);
		fuelPerExtrudedBlock = serverBuilder.comment("Fuel per extruded block").defineInRange(CONFIG_EXTRUDER_NAME + ".fuelPerExtrudedBlock", 200, 0, 100000);
		moveSpeed = serverBuilder.comment("Extruder move speed").defineInRange(CONFIG_EXTRUDER_NAME + ".moveSpeed", 0.1D, 0, 10.0D);
		fuelPerStick = serverBuilder.comment("Fuel per stick").defineInRange(CONFIG_EXTRUDER_NAME + ".fuelPerStick", 50, 0, 100000);
		fuelPerCoal = serverBuilder.comment("Fuel per coal").defineInRange(CONFIG_EXTRUDER_NAME + ".fuelPerCoal", 1600, 0, 100000);
		fuelPerRedstone = serverBuilder.comment("Fuel per redstone").defineInRange(CONFIG_EXTRUDER_NAME + ".fuelPerRedstone", 3200, 0, 100000);
		fuelPerLava = serverBuilder.comment("Fuel per lava").defineInRange(CONFIG_EXTRUDER_NAME + ".fuelPerLava", 6400, 0, 100000);
		fuelPerBlazerod = serverBuilder.comment("Fuel per blazerod").defineInRange(CONFIG_EXTRUDER_NAME + ".fuelPerBlazerod", 12800, 0, 100000);
		fuelPerMagmaCream = serverBuilder.comment("Fuel per magma cream").defineInRange(CONFIG_EXTRUDER_NAME + ".fuelPerMagmaCream", 19200, 0, 100000);
		speedModifierStick = serverBuilder.comment("Stick speed modifier").defineInRange(CONFIG_EXTRUDER_NAME + ".speedModifierStick", 0.3D, 0, 10.0D);
		speedModifierCoal = serverBuilder.comment("Coal speed modifier").defineInRange(CONFIG_EXTRUDER_NAME + ".speedModifierCoal", 0.8D, 0, 10.0D);
		speedModifierRedstone = serverBuilder.comment("Redstone speed modifier").defineInRange(CONFIG_EXTRUDER_NAME + ".speedModifierRedstone", 1.4D, 0, 10.0D);
		speedModifierMagmaCream = serverBuilder.comment("Magmacream speed modifier").defineInRange(CONFIG_EXTRUDER_NAME + ".speedModifierMagmaCream", 2.2D, 0, 10.0D);

		generateUranium = serverBuilder.comment("Generate Uranium").define(CONFIG_GENERAL_NAME + ".generateUranium", true);
		uraniumDamage = serverBuilder.comment("Damage from Uranium Ore").defineInRange(CONFIG_GENERAL_NAME + ".uraniumDamage", 4, 0, 256);

		generateOilOre = serverBuilder.comment("Generate Oil Ore").define(CONFIG_GENERAL_NAME + ".generateOilOre", true);
		// String[] fuels = config.get(CONFIG_GENERAL_NAME,
		// "grimpack.industry.cfg.modernfurnace_fuels", new String[] {
		// "grimpack:fuel>25000", "grimpack:fuel_tank>225000" }).getStringList();
		// MachineRecipes.INSTANCE.getModernFurnaceFuel().addAll(ConfigUtils.loadConfigurableFuel(fuels));

		chunkLoaderSize = serverBuilder.comment("Chunk Loader Size", "Square chunks that are kept loaded. Should be odd!").defineInRange(CONFIG_GENERAL_NAME + ".chunkLoaderSize", 5, 1, 32);
		chunkLoaderTimeout = serverBuilder.comment("Chunk Loader Timeout", "Turns off chunkloaders if the owner hasn't been active for this many hours. Zero and below disables this").defineInRange(CONFIG_GENERAL_NAME + ".uraniumDamage", 0, 1, 256);

		return Optional.of(serverBuilder.build());
	}

	@Override
	public Optional<ForgeConfigSpec> initClient(Builder clientBuilder) {
		showFanParticles = clientBuilder.comment("Show Fan Particles").define(CONFIG_GENERAL_NAME + ".showFanParticles", true);

		return Optional.of(clientBuilder.build());
	}
}
