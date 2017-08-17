package com.grim3212.mc.pack.world.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.ConfigUtils;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.world.GrimWorld;
import com.grim3212.mc.pack.world.util.KillingFungusWhitelist;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class WorldConfig extends GrimConfig {

	public static final String CONFIG_NAME = "world";
	public static final String CONFIG_GENERAL_NAME = "world.general";
	public static final String CONFIG_WORLD_GEN_NAME = "world.world-gen-expanded";
	public static final String CONFIG_PARTS_NAME = "world.subparts";
	public static final String CONFIG_FLOATING_ISLANDS_NAME = "world.floating-islands";
	public static final String CONFIG_FUNGUS_NAME = "world.fungus";

	public static String[] DIRT_EATING_BLOCKS_POSSIBLE;
	public static String[] SMOOTHSTONE_EATING_BLOCKS_POSSIBLE;
	public static String[] WATER_LEAVES_EATING_BLOCKS_POSSIBLE;
	public static String[] SAND_GRAVEL_EATING_BLOCKS_POSSIBLE;
	public static String[] ROCKS_EATING_BLOCKS_POSSIBLE;
	public static String[] ALL_EATING_BLOCKS_POSSIBLE;
	public static String[] FOREST_EATING_BLOCKS_POSSIBLE;

	public static int frequencyWheatField;
	public static int frequencySaplings;
	public static int frequencyTreeStumps;
	public static int frequencyCactusFields;
	public static int frequencySandstonePillars;
	public static int frequencySandPits;
	public static int frequencyMelons;
	public static int spawnrate;
	public static int sizevariancefrom7;
	public static boolean fire;

	public static int glowstoneSeedPlantHeight;

	// Sync to client
	public static boolean generateFlatBedRockSurface;
	public static boolean generateFlatBedRockNether;
	public static boolean corruption;
	public static boolean generateCorruption;
	public static boolean spawnParabuzzys;

	// Subparts
	public static boolean subpart8BitMobs;
	public static boolean subpartCorruption;
	public static boolean subpartDesertWells;
	public static boolean subpartFlatBedrock;
	public static boolean subpartFloatingIslands;
	public static boolean subpartFungus;
	public static boolean subpartGlowstoneSeeds;
	public static boolean subpartGunpowderReeds;
	public static boolean subpartIcePixie;
	public static boolean subpartMorePeople;
	public static boolean subpartRandomite;
	public static boolean subpartTreasureMobs;
	public static boolean subpartWorldGenExpanded;

	@Override
	public String name() {
		return CONFIG_NAME;
	}

	@Override
	public void syncConfig() {
		if (subpartCorruption) {
			fire = config.get(CONFIG_GENERAL_NAME, "Enable Fire", true).getBoolean();
			corruption = config.get(CONFIG_GENERAL_NAME, "Enable Corruption", false).getBoolean();
			generateCorruption = config.get(CONFIG_GENERAL_NAME, "Generate Corruption Blocks", false).getBoolean();
		}

		if (subpart8BitMobs)
			spawnParabuzzys = config.get(CONFIG_GENERAL_NAME, "Spawn Parabuzzys", true).getBoolean();

		if (subpartFlatBedrock) {
			generateFlatBedRockSurface = config.get(CONFIG_GENERAL_NAME, "Generate Flat Bedrock Surface", true).getBoolean();
			generateFlatBedRockNether = config.get(CONFIG_GENERAL_NAME, "Generate Flat Bedrock Nether", true).getBoolean();
		}

		if (subpartGlowstoneSeeds)
			glowstoneSeedPlantHeight = config.get(CONFIG_GENERAL_NAME, "Glowstone seed plant height", 15).getInt();

		if (subpartWorldGenExpanded) {
			config.addCustomCategoryComment(CONFIG_WORLD_GEN_NAME, "Change the values to decide how rare or common the different world gen items spawn. Larger values means rarer.");
			frequencyWheatField = config.get(CONFIG_WORLD_GEN_NAME, "Frequency Wheat Field", 350).getInt();
			frequencySaplings = config.get(CONFIG_WORLD_GEN_NAME, "Frequency Saplings", 200).getInt();
			frequencyTreeStumps = config.get(CONFIG_WORLD_GEN_NAME, "Frequency Tree Stumps", 200).getInt();
			frequencyCactusFields = config.get(CONFIG_WORLD_GEN_NAME, "Frequency Cactus Fields", 400).getInt();
			frequencySandstonePillars = config.get(CONFIG_WORLD_GEN_NAME, "Frequency Sandstone Pillars", 400).getInt();
			frequencySandPits = config.get(CONFIG_WORLD_GEN_NAME, "Frequency Sand Pits", 600).getInt();
			frequencyMelons = config.get(CONFIG_WORLD_GEN_NAME, "Frequency Melons", 200).getInt();
		}

		if (subpartFloatingIslands) {
			config.addCustomCategoryComment(CONFIG_FLOATING_ISLANDS_NAME, "Floating Islands configuration options. Spawn rate becomes more rare as the number grows.");
			spawnrate = config.get(CONFIG_FLOATING_ISLANDS_NAME, "Spawn Rate", 100).getInt();
			sizevariancefrom7 = config.get(CONFIG_FLOATING_ISLANDS_NAME, "Size Variance", 5).getInt();
		}

		if (subpartFungus) {
			config.addCustomCategoryComment(CONFIG_FUNGUS_NAME, "Add blocks to each of the different eating fungus's whitelists and blacklists respectively.");
			DIRT_EATING_BLOCKS_POSSIBLE = config.get(CONFIG_FUNGUS_NAME, "Dirt Eating Whitelist", new String[] { "grass", "dirt", "farmland" }).getStringList();
			SMOOTHSTONE_EATING_BLOCKS_POSSIBLE = config.get(CONFIG_FUNGUS_NAME, "Smooth Stone Fungus Whitelist", new String[] { "stone", "monster_egg" }).getStringList();
			WATER_LEAVES_EATING_BLOCKS_POSSIBLE = config.get(CONFIG_FUNGUS_NAME, "Water and Leaves Fungus Whitelist", new String[] { "sapling", "flowing_water", "water", "leaves", "leaves2", "web", "tallgrass", "deadbush", "yellow_flower", "red_flower", "brown_mushroom", "red_mushroom", "wheat", "reeds", "melon_stem", "pumpkin_stem", "vine", "waterlily", "nether_wart", "cocoa", "carrots", "potatoes", "double_plant" }).getStringList();
			SAND_GRAVEL_EATING_BLOCKS_POSSIBLE = config.get(CONFIG_FUNGUS_NAME, "Sand and Eating Fungus Whitelist", new String[] { "sand", "gravel", "soul_sand" }).getStringList();
			ROCKS_EATING_BLOCKS_POSSIBLE = config.get(CONFIG_FUNGUS_NAME, "Rocks Eating Whitelist", new String[] { "cobblestone", "sandstone", "netherrack", "monster_egg", "stonebrick" }).getStringList();
			ALL_EATING_BLOCKS_POSSIBLE = config.get(CONFIG_FUNGUS_NAME, "All Eating Blacklist", new String[] { "air", "bedrock", "obsidian" }).getStringList();
			FOREST_EATING_BLOCKS_POSSIBLE = config.get(CONFIG_FUNGUS_NAME, "Forest Eating Whitelist", new String[] { "log", "log2", "leaves", "leaves2" }).getStringList();

			ConfigUtils.loadBlocksOntoMap(ALL_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.allEating);
			ConfigUtils.loadBlocksOntoMap(FOREST_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.forestEating);
			ConfigUtils.loadBlocksOntoMap(DIRT_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.dirt);
			ConfigUtils.loadBlocksOntoMap(SMOOTHSTONE_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.smoothstone);
			ConfigUtils.loadBlocksOntoMap(WATER_LEAVES_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.waterLeaves);
			ConfigUtils.loadBlocksOntoMap(SAND_GRAVEL_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.sandGravel);
			ConfigUtils.loadBlocksOntoMap(ROCKS_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.rocks);
		}

		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		if (subpart8BitMobs || subpartCorruption || subpartGlowstoneSeeds || subpartFlatBedrock)
			list.add(new DummyCategoryElement("worldGeneralCfg", "grimpack.world.cfg.general", new ConfigElement(GrimWorld.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements()));
		if (subpartFungus)
			list.add(new DummyCategoryElement("worldFungusCfg", "grimpack.world.cfg.fungus", new ConfigElement(GrimWorld.INSTANCE.getConfig().getCategory(CONFIG_FUNGUS_NAME)).getChildElements()));
		if (subpartWorldGenExpanded)
			list.add(new DummyCategoryElement("worldWorldGenCfg", "grimpack.world.cfg.world-gen", new ConfigElement(GrimWorld.INSTANCE.getConfig().getCategory(CONFIG_WORLD_GEN_NAME)).getChildElements()));
		if (subpartFloatingIslands)
			list.add(new DummyCategoryElement("worldFloatingIslandsCfg", "grimpack.world.cfg.floatingIslands", new ConfigElement(GrimWorld.INSTANCE.getConfig().getCategory(CONFIG_FLOATING_ISLANDS_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("worldSubPartCfg", "grimpack.world.cfg.subparts", new ConfigElement(GrimWorld.INSTANCE.getConfig().getCategory(CONFIG_PARTS_NAME)).getChildElements()));
		return list;
	}

	@Override
	public void readFromServer(PacketBuffer buffer) {
		if (subpartFlatBedrock) {
			generateFlatBedRockSurface = buffer.readBoolean();
			generateFlatBedRockNether = buffer.readBoolean();
		}

		if (subpartCorruption)
			corruption = buffer.readBoolean();
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		if (subpartFlatBedrock) {
			buffer.writeBoolean(generateFlatBedRockSurface);
			buffer.writeBoolean(generateFlatBedRockNether);
		}

		if (subpartCorruption)
			buffer.writeBoolean(corruption);
	}
}
