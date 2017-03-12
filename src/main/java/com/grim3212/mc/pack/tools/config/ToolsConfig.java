package com.grim3212.mc.pack.tools.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.items.ItemBreakingWand;
import com.grim3212.mc.pack.tools.items.ItemMiningWand;
import com.grim3212.mc.pack.tools.items.ItemPowerStaff;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ToolsConfig extends GrimConfig {

	public static final String CONFIG_NAME = "tools";
	public static final String CONFIG_GENERAL_NAME = "tools.general";
	public static final String CONFIG_SLINGSHOT_NAME = "tools.slingshot";
	public static final String CONFIG_FIST_NAME = "tools.ultimatefist";
	public static final String CONFIG_BOOMERANG_NAME = "tools.boomerang";

	public static boolean ENABLE_free_build_mode;
	public static boolean ENABLE_bedrock_breaking;
	public static boolean ENABLE_easy_mining_obsidian;
	public static String[] BLOCKS_Destructive_wand_spared_ores;
	public static String[] BLOCKS_Mining_wand_ores_for_surface_mining;
	public static float multiToolDurabilityMultiplier;
	public static double fistEntityDamage;
	public static double fistAttackSpeed;
	public static float fistBlockBreakSpeed;
	public static boolean fistHasDurability;
	public static int fistDurabilityAmount;
	public static boolean restrictPowerStaffBlocks;
	public static String[] powerstaff_pull_push_blocks;
	public static boolean generateBlackDiamond;
	public static boolean generateElement115;

	// Boomerang config
	public static boolean turnAroundItem;
	public static boolean turnAroundMob;
	public static boolean breaksTorches;
	public static boolean breaksPlants;
	public static boolean hitsButtons;
	public static boolean turnAroundSwitch;
	public static int woodBoomerangRange;
	public static int woodBoomerangDamage;
	public static int diamondBoomerangRange;
	public static int diamondBoomerangDamage;
	public static boolean diamondBoomerangFollows;

	// Slingshot config
	public static float stonePelletDamage;
	public static float ironPelletDamage;
	public static float netherrackPelletDamage;
	public static float lightPelletDamage;
	public static float firePelletDamage;
	public static float explosivePelletDamage;
	
	@Override
	public void syncFirst() {
		multiToolDurabilityMultiplier = (float) config.get(CONFIG_GENERAL_NAME, "Multitool durability multiplier", 3).getDouble();
	}

	@Override
	public void syncConfig() {
		fistEntityDamage = config.get(CONFIG_FIST_NAME, "Ultimate Fist Damage Against Entity's", 1561).getDouble();
		fistBlockBreakSpeed = config.get(CONFIG_FIST_NAME, "Ultimate Fist Block Breaking Speed", 64).getInt();
		fistAttackSpeed = config.get(CONFIG_FIST_NAME, "Ultimate Fist Attack Speed", 0.0F).getDouble();
		fistHasDurability = config.get(CONFIG_FIST_NAME, "Ultimate Fist Can Be Damaged", false).getBoolean();
		fistDurabilityAmount = config.get(CONFIG_FIST_NAME, "Ultimate Fist Durability Amount", 1561).getInt();

		multiToolDurabilityMultiplier = (float) config.get(CONFIG_GENERAL_NAME, "Multitool durability multiplier", 3).getDouble();

		ENABLE_free_build_mode = config.get(CONFIG_GENERAL_NAME, "Enable Free Build Mode", false).getBoolean();
		ENABLE_bedrock_breaking = config.get(CONFIG_GENERAL_NAME, "Enable Bedrock Breaking", false).getBoolean();
		ENABLE_easy_mining_obsidian = config.get(CONFIG_GENERAL_NAME, "Enable Easy Mining Obsidian", false).getBoolean();

		BLOCKS_Destructive_wand_spared_ores = config.get(CONFIG_GENERAL_NAME, "Destructive Wand Spared Ores", new String[] { "gold_ore", "iron_ore", "coal_ore", "lapis_ore", "mossy_cobblestone", "mob_spawner", "chest", "diamond_ore", "redstone_ore", "lit_redstone_ore", "emerald_ore", "quartz_ore" }).getStringList();
		BLOCKS_Mining_wand_ores_for_surface_mining = config.get(CONFIG_GENERAL_NAME, "Mining Wand Ores for Surface Mining", new String[] { "gold_ore", "iron_ore", "coal_ore", "lapis_ore", "diamond_ore", "redstone_ore", "lit_redstone_ore", "emerald_ore", "quartz_ore" }).getStringList();

		restrictPowerStaffBlocks = config.get(CONFIG_GENERAL_NAME, "Restrict powerstaff blocks", false).getBoolean();
		powerstaff_pull_push_blocks = config.get(CONFIG_GENERAL_NAME, "Blocks allowed when restrict powerstaff is active", new String[] { "dirt" }).getStringList();
		generateBlackDiamond = config.get(CONFIG_GENERAL_NAME, "Generate Black Diamond", true).getBoolean();
		generateElement115 = config.get(CONFIG_GENERAL_NAME, "Generate Element 115", true).getBoolean();

		stonePelletDamage = Float.parseFloat(config.get(CONFIG_SLINGSHOT_NAME, "Stone Pellet Damage", 1.0f).setRequiresMcRestart(true).getString());
		ironPelletDamage = Float.parseFloat(config.get(CONFIG_SLINGSHOT_NAME, "Iron Pellet Damage", 6.0f).setRequiresMcRestart(true).getString());
		netherrackPelletDamage = Float.parseFloat(config.get(CONFIG_SLINGSHOT_NAME, "Netherrack Pellet Damage", 5.0f).setRequiresMcRestart(true).getString());
		lightPelletDamage = Float.parseFloat(config.get(CONFIG_SLINGSHOT_NAME, "Light Pellet Damage", 1.0f).setRequiresMcRestart(true).getString());
		firePelletDamage = Float.parseFloat(config.get(CONFIG_SLINGSHOT_NAME, "Fire Pellet Damage", 1.5f).setRequiresMcRestart(true).getString());
		explosivePelletDamage = Float.parseFloat(config.get(CONFIG_SLINGSHOT_NAME, "Explosive Pellet Damage", 1.5f).setRequiresMcRestart(true).getString());

		turnAroundItem = config.get(CONFIG_BOOMERANG_NAME, "Turn Around Items", false).getBoolean();
		turnAroundMob = config.get(CONFIG_BOOMERANG_NAME, "Turn Around Mobs", false).getBoolean();
		breaksTorches = config.get(CONFIG_BOOMERANG_NAME, "Breaks Torches", false).getBoolean();
		breaksPlants = config.get(CONFIG_BOOMERANG_NAME, "Breaks Plants", false).getBoolean();
		hitsButtons = config.get(CONFIG_BOOMERANG_NAME, "Hits Buttons", true).getBoolean();
		turnAroundSwitch = config.get(CONFIG_BOOMERANG_NAME, "Turn Around Switch", true).getBoolean();
		woodBoomerangRange = config.get(CONFIG_BOOMERANG_NAME, "Wood Boomerang Range", 20).getInt();
		woodBoomerangDamage = config.get(CONFIG_BOOMERANG_NAME, "Wood Boomerang Damage", 1).getInt();
		diamondBoomerangRange = config.get(CONFIG_BOOMERANG_NAME, "Diamond Boomerang Range", 30).getInt();
		diamondBoomerangDamage = config.get(CONFIG_BOOMERANG_NAME, "Diamond Boomerang Damage", 5).getInt();
		diamondBoomerangFollows = config.get(CONFIG_BOOMERANG_NAME, "Diamond Boomerang Follows", false).getBoolean();

		registerBlocksPossible(powerstaff_pull_push_blocks, ItemPowerStaff.allowedBlocks);
		registerBlocksPossible(BLOCKS_Destructive_wand_spared_ores, ItemBreakingWand.ores);
		registerBlocksPossible(BLOCKS_Mining_wand_ores_for_surface_mining, ItemMiningWand.m_ores);

		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement("toolsGeneralCfg", "grimpack.tools.cfg.general", new ConfigElement(GrimTools.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("toolsUltimateFistCfg", "grimpack.tools.cfg.ultimatefist", new ConfigElement(GrimTools.INSTANCE.getConfig().getCategory(CONFIG_FIST_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("toolsSlingshotCfg", "grimpack.tools.cfg.slingshot", new ConfigElement(GrimTools.INSTANCE.getConfig().getCategory(CONFIG_SLINGSHOT_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("toolsBoomerangCfg", "grimpack.tools.cfg.boomerang", new ConfigElement(GrimTools.INSTANCE.getConfig().getCategory(CONFIG_BOOMERANG_NAME)).getChildElements()));
		return list;
	}

	public void registerBlocksPossible(String[] string, ArrayList<Block> blocklist) {
		// Clear to make sure we are not getting duplicates
		blocklist.clear();

		if (string.length > 0) {
			for (String u : string) {
				Block block = Block.REGISTRY.getObject(new ResourceLocation(u));
				if (block != null) {
					blocklist.add(block);
				} else {
					GrimLog.error(GrimTools.partName, "Can't find block with name: " + u);
				}
			}
		}
	}
}
