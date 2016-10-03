package com.grim3212.mc.pack.world.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.world.GrimWorld;
import com.grim3212.mc.pack.world.util.KillingFungusWhitelist;

import net.minecraft.block.Block;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class WorldConfig extends GrimConfig {

	public static final String CONFIG_NAME = "world";
	public static final String CONFIG_GENERAL_NAME = "world.general";
	public static final String FLOATING_ISLANDS_CONFIG_NAME = "world.floating-islands";
	public static final String FUNGUS_CONFIG_NAME = "world.fungus";

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
	public static boolean generateRandomite;
	public static boolean generateGunpowderReeds;
	public static boolean spawnIcePixies;
	public static boolean spawnTreasureMobs;

	// Sync to client
	public static boolean generateFlatBedRockSurface;
	public static boolean generateFlatBedRockNether;
	public static boolean generateFI;
	public static boolean replaceDesertWells;
	public static boolean corruption;
	public static boolean generateCorruption;
	public static boolean spawnMorePeople;	

	public static String[] DIRT_EATING_BLOCKS_POSSIBLE;
	public static String[] SMOOTHSTONE_EATING_BLOCKS_POSSIBLE;
	public static String[] WATER_LEAVES_EATING_BLOCKS_POSSIBLE;
	public static String[] SAND_GRAVEL_EATING_BLOCKS_POSSIBLE;
	public static String[] ROCKS_EATING_BLOCKS_POSSIBLE;
	public static String[] ALL_EATING_BLOCKS_POSSIBLE;
	public static String[] FOREST_EATING_BLOCKS_POSSIBLE;

	@Override
	public void syncConfig() {
		fire = config.get(CONFIG_GENERAL_NAME, "Enable Fire", true).getBoolean();
		corruption = config.get(CONFIG_GENERAL_NAME, "Enable Corruption", false).getBoolean();
		generateCorruption = config.get(CONFIG_GENERAL_NAME, "Generate Corruption Blocks", false).getBoolean();
		generateRandomite = config.get(CONFIG_GENERAL_NAME, "Generate Randomite", true).getBoolean();
		generateGunpowderReeds = config.get(CONFIG_GENERAL_NAME, "Generate Gunpowder Reeds", true).getBoolean();
		spawnIcePixies = config.get(CONFIG_GENERAL_NAME, "Spawn Ice Pixies", true).getBoolean();
		spawnTreasureMobs = config.get(CONFIG_GENERAL_NAME, "Spawn Treasure Mobs", true).getBoolean();
		spawnMorePeople = config.get(CONFIG_GENERAL_NAME, "Spawn more people", false).getBoolean();

		glowstoneSeedPlantHeight = config.get(CONFIG_GENERAL_NAME, "Glowstone seed plant height", 15).getInt();

		config.addCustomCategoryComment(CONFIG_GENERAL_NAME, "Change the values to decide how rare or common the different world gen items spawn. Larger values means rarer.");
		frequencyWheatField = config.get(CONFIG_GENERAL_NAME, "Frequency Wheat Field", 350).getInt();
		frequencySaplings = config.get(CONFIG_GENERAL_NAME, "Frequency Saplings", 200).getInt();
		frequencyTreeStumps = config.get(CONFIG_GENERAL_NAME, "Frequency Tree Stumps", 200).getInt();
		frequencyCactusFields = config.get(CONFIG_GENERAL_NAME, "Frequency Cactus Fields", 400).getInt();
		frequencySandstonePillars = config.get(CONFIG_GENERAL_NAME, "Frequency Sandstone Pillars", 400).getInt();
		frequencySandPits = config.get(CONFIG_GENERAL_NAME, "Frequency Sand Pits", 600).getInt();
		frequencyMelons = config.get(CONFIG_GENERAL_NAME, "Frequency Melons", 200).getInt();

		generateFlatBedRockSurface = config.get(CONFIG_GENERAL_NAME, "Generate Flat Bedrock Surface", true).getBoolean();
		generateFlatBedRockNether = config.get(CONFIG_GENERAL_NAME, "Generate Flat Bedrock Nether", true).getBoolean();
		replaceDesertWells = config.get(CONFIG_GENERAL_NAME, "Replace Desert Wells", true).getBoolean();

		config.addCustomCategoryComment(FLOATING_ISLANDS_CONFIG_NAME, "Floating Islands configuration options. Spawn rate becomes more rare as the number grows.");
		spawnrate = config.get(FLOATING_ISLANDS_CONFIG_NAME, "Spawn Rate", 100).getInt();
		sizevariancefrom7 = config.get(FLOATING_ISLANDS_CONFIG_NAME, "Size Variance", 5).getInt();
		generateFI = config.get(FLOATING_ISLANDS_CONFIG_NAME, "Generate Floating Islands", true).getBoolean();

		config.addCustomCategoryComment(FUNGUS_CONFIG_NAME, "Add block !NAMES! to each of the different eating fungus's whitelists and blacklists respectively.");
		DIRT_EATING_BLOCKS_POSSIBLE = config.get(FUNGUS_CONFIG_NAME, "Dirt Eating Whitelist", new String[] { "grass", "dirt", "farmland" }).getStringList();
		SMOOTHSTONE_EATING_BLOCKS_POSSIBLE = config.get(FUNGUS_CONFIG_NAME, "Smooth Stone Fungus Whitelist", new String[] { "stone", "monster_egg" }).getStringList();
		WATER_LEAVES_EATING_BLOCKS_POSSIBLE = config.get(FUNGUS_CONFIG_NAME, "Water and Leaves Fungus Whitelist", new String[] { "sapling", "flowing_water", "water", "leaves", "leaves2", "web", "tallgrass", "deadbush", "yellow_flower", "red_flower", "brown_mushroom", "red_mushroom", "wheat", "reeds", "melon_stem", "pumpkin_stem", "vine", "waterlily", "nether_wart", "cocoa", "carrots", "potatoes", "double_plant" }).getStringList();
		SAND_GRAVEL_EATING_BLOCKS_POSSIBLE = config.get(FUNGUS_CONFIG_NAME, "Sand and Eating Fungus Whitelist", new String[] { "sand", "gravel", "soul_sand" }).getStringList();
		ROCKS_EATING_BLOCKS_POSSIBLE = config.get(FUNGUS_CONFIG_NAME, "Rocks Eating Whitelist", new String[] { "cobblestone", "sandstone", "netherrack", "monster_egg", "stonebrick" }).getStringList();
		ALL_EATING_BLOCKS_POSSIBLE = config.get(FUNGUS_CONFIG_NAME, "All Eating Blacklist", new String[] { "air", "bedrock", "obsidian" }).getStringList();
		FOREST_EATING_BLOCKS_POSSIBLE = config.get(FUNGUS_CONFIG_NAME, "Forest Eating Whitelist", new String[] { "log", "log2", "leaves", "leaves2" }).getStringList();

		registerBlocksPossible(ALL_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.allEating);
		registerBlocksPossible(FOREST_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.forestEating);
		registerBlocksPossible(DIRT_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.dirt);
		registerBlocksPossible(SMOOTHSTONE_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.smoothstone);
		registerBlocksPossible(WATER_LEAVES_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.waterLeaves);
		registerBlocksPossible(SAND_GRAVEL_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.sandGravel);
		registerBlocksPossible(ROCKS_EATING_BLOCKS_POSSIBLE, KillingFungusWhitelist.rocks);

		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement("worldGeneralCfg", "grimpack.world.cfg.general", new ConfigElement(GrimWorld.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("worldFungusCfg", "grimpack.world.cfg.fungus", new ConfigElement(GrimWorld.INSTANCE.getConfig().getCategory(FUNGUS_CONFIG_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("worldFloatingIslandsCfg", "grimpack.world.cfg.floatingIslands", new ConfigElement(GrimWorld.INSTANCE.getConfig().getCategory(FLOATING_ISLANDS_CONFIG_NAME)).getChildElements()));
		return list;
	}

	@Override
	public void readFromServer(PacketBuffer buffer) {
		generateFlatBedRockSurface = buffer.readBoolean();
		generateFlatBedRockNether = buffer.readBoolean();
		generateFI = buffer.readBoolean();
		replaceDesertWells = buffer.readBoolean();
		corruption = buffer.readBoolean();
		spawnMorePeople = buffer.readBoolean();
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		buffer.writeBoolean(generateFlatBedRockSurface);
		buffer.writeBoolean(generateFlatBedRockNether);
		buffer.writeBoolean(generateFI);
		buffer.writeBoolean(replaceDesertWells);
		buffer.writeBoolean(corruption);
		buffer.writeBoolean(spawnMorePeople);
	}

	public static void registerBlocksPossible(String[] string, ArrayList<Block> blocklist) {
		if (string.length > 0) {
			for (String u : string) {
				try {
					blocklist.add(Block.REGISTRY.getObject(new ResourceLocation(u)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
