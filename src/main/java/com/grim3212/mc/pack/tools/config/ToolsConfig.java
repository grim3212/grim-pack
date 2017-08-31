package com.grim3212.mc.pack.tools.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.ConfigUtils;
import com.grim3212.mc.pack.core.config.ConfigUtils.ArmorMaterialHolder;
import com.grim3212.mc.pack.core.config.ConfigUtils.ToolMaterialHolder;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.items.ItemBreakingWand;
import com.grim3212.mc.pack.tools.items.ItemMiningWand;
import com.grim3212.mc.pack.tools.items.ItemPowerStaff;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ToolsConfig extends GrimConfig {

	public static final String CONFIG_NAME = "tools";
	public static final String CONFIG_GENERAL_NAME = "tools.general";
	public static final String CONFIG_SLINGSHOT_NAME = "tools.slingshot";
	public static final String CONFIG_FIST_NAME = "tools.ultimatefist";
	public static final String CONFIG_BOOMERANG_NAME = "tools.boomerang";
	public static final String CONFIG_PARTS_NAME = "tools.subparts";

	public static ToolMaterialHolder blackDiamondToolMaterial;
	public static ToolMaterialHolder obsidianToolMaterial;
	public static ArmorMaterialHolder maskArmorMaterial;
	public static ArmorMaterialHolder blackDiamondArmorMaterial;

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

	public static float grenadeLauncherExplosion;

	// Subparts
	public static boolean subpartBackpacks;
	public static boolean subpartBlackDiamond;
	public static boolean subpartBoomerangs;
	public static boolean subpartBuckets;
	public static boolean subpartChisel;
	public static boolean subpartExtinguisher;
	public static boolean subpartGrenadeLauncher;
	public static boolean subpartHammers;
	public static boolean subpartKnives;
	public static boolean subpartMachetes;
	public static boolean subpartMasks;
	public static boolean subpartMultiTools;
	public static boolean subpartPokeball;
	public static boolean subpartPortableWorkbench;
	public static boolean subpartPowerstaff;
	public static boolean subpartRayGuns;
	public static boolean subpartSlingshots;
	public static boolean subpartSpears;
	public static boolean subpartUltimateFist;
	public static boolean subpartWands;

	@Override
	public String name() {
		return CONFIG_NAME;
	}

	@Override
	public void syncSubparts() {
		subpartBackpacks = config.get(CONFIG_PARTS_NAME, "Enable SubPart backpacks", true).setRequiresMcRestart(true).getBoolean();
		subpartBlackDiamond = config.get(CONFIG_PARTS_NAME, "Enable SubPart black diamond", true).setRequiresMcRestart(true).getBoolean();
		subpartBoomerangs = config.get(CONFIG_PARTS_NAME, "Enable SubPart boomerangs", true).setRequiresMcRestart(true).getBoolean();
		subpartBuckets = config.get(CONFIG_PARTS_NAME, "Enable SubPart buckets", true).setRequiresMcRestart(true).getBoolean();
		subpartChisel = config.get(CONFIG_PARTS_NAME, "Enable SubPart chisel", true).setRequiresMcRestart(true).getBoolean();
		subpartExtinguisher = config.get(CONFIG_PARTS_NAME, "Enable SubPart extinguisher", true).setRequiresMcRestart(true).getBoolean();
		subpartGrenadeLauncher = config.get(CONFIG_PARTS_NAME, "Enable SubPart grenade launcher", true).setRequiresMcRestart(true).getBoolean();
		subpartHammers = config.get(CONFIG_PARTS_NAME, "Enable SubPart hammers", true).setRequiresMcRestart(true).getBoolean();
		subpartKnives = config.get(CONFIG_PARTS_NAME, "Enable SubPart knives", true).setRequiresMcRestart(true).getBoolean();
		subpartMachetes = config.get(CONFIG_PARTS_NAME, "Enable SubPart machetes", true).setRequiresMcRestart(true).getBoolean();
		subpartMasks = config.get(CONFIG_PARTS_NAME, "Enable SubPart masks", true).setRequiresMcRestart(true).getBoolean();
		subpartMultiTools = config.get(CONFIG_PARTS_NAME, "Enable SubPart multitools", true).setRequiresMcRestart(true).getBoolean();
		subpartPokeball = config.get(CONFIG_PARTS_NAME, "Enable SubPart pokeball", true).setRequiresMcRestart(true).getBoolean();
		subpartPortableWorkbench = config.get(CONFIG_PARTS_NAME, "Enable SubPart portable workbench", true).setRequiresMcRestart(true).getBoolean();
		subpartPowerstaff = config.get(CONFIG_PARTS_NAME, "Enable SubPart powerstaff", true).setRequiresMcRestart(true).getBoolean();
		subpartRayGuns = config.get(CONFIG_PARTS_NAME, "Enable SubPart ray guns", true).setRequiresMcRestart(true).getBoolean();
		subpartSlingshots = config.get(CONFIG_PARTS_NAME, "Enable SubPart slingshots", true).setRequiresMcRestart(true).getBoolean();
		subpartSpears = config.get(CONFIG_PARTS_NAME, "Enable SubPart spears", true).setRequiresMcRestart(true).getBoolean();
		subpartUltimateFist = config.get(CONFIG_PARTS_NAME, "Enable SubPart ultimate fist", true).setRequiresMcRestart(true).getBoolean();
		subpartWands = config.get(CONFIG_PARTS_NAME, "Enable SubPart wands", true).setRequiresMcRestart(true).getBoolean();
	}

	@Override
	public void syncFirst() {
		multiToolDurabilityMultiplier = (float) config.get(CONFIG_GENERAL_NAME, "Multitool durability multiplier", 3).getDouble();

		if (subpartBlackDiamond) {
			blackDiamondToolMaterial = ConfigUtils.loadToolMaterial(new ToolMaterialHolder("black_diamond", 4, 5122, 15F, 5F, 20));
			blackDiamondArmorMaterial = ConfigUtils.loadArmorMaterial(new ArmorMaterialHolder("black_diamond", 35, new int[] { 4, 8, 10, 4 }, 20, 2.5F));
		}

		if (subpartMultiTools)
			obsidianToolMaterial = ConfigUtils.loadToolMaterial(new ToolMaterialHolder("obsidian", 3, 3333, 9.5F, 7f, 14));

		if (subpartMasks)
			maskArmorMaterial = ConfigUtils.loadArmorMaterial(new ArmorMaterialHolder("mask", 5, new int[] { 1, 2, 3, 1 }, 15, 0.0F));
	}

	@Override
	public void syncConfig() {
		syncSubparts();
		syncFirst();

		ConfigUtils.setCurrentPart(GrimTools.partName);

		if (subpartUltimateFist) {
			fistEntityDamage = config.get(CONFIG_FIST_NAME, "Ultimate Fist Damage Against Entity's", 1561).getDouble();
			fistBlockBreakSpeed = config.get(CONFIG_FIST_NAME, "Ultimate Fist Block Breaking Speed", 64).getInt();
			fistAttackSpeed = config.get(CONFIG_FIST_NAME, "Ultimate Fist Attack Speed", 0.0F).getDouble();
			fistHasDurability = config.get(CONFIG_FIST_NAME, "Ultimate Fist Can Be Damaged", false).getBoolean();
			fistDurabilityAmount = config.get(CONFIG_FIST_NAME, "Ultimate Fist Durability Amount", 1561).getInt();
		}

		if (subpartGrenadeLauncher)
			grenadeLauncherExplosion = (float) config.get(CONFIG_GENERAL_NAME, "Grenade Launcher Explosion Strength", 4.0f).getDouble();

		if (subpartWands) {
			ENABLE_free_build_mode = config.get(CONFIG_GENERAL_NAME, "Enable Free Build Mode", false).getBoolean();
			ENABLE_bedrock_breaking = config.get(CONFIG_GENERAL_NAME, "Enable Bedrock Breaking", false).getBoolean();
			ENABLE_easy_mining_obsidian = config.get(CONFIG_GENERAL_NAME, "Enable Easy Mining Obsidian", false).getBoolean();

			BLOCKS_Destructive_wand_spared_ores = config.get(CONFIG_GENERAL_NAME, "Destructive Wand Spared Ores", new String[] { "oredict:oreGold", "oredict:oreIron", "oredict:oreCoal", "oredict:oreLapis", "minecraft:mob_spawner", "oredict:chest", "oredict:oreDiamond", "oredict:oreRedstone", "minecraft:lit_redstone_ore", "oredict:oreEmerald", "oredict:oreQuartz", "oredict:oreUranium", "oredict:oreAluminum", "oredict:oreOil", "oredict:oreElement115", "oredict:oreBlackDiamond", "oredict:oreRandomite" }).getStringList();
			BLOCKS_Mining_wand_ores_for_surface_mining = config.get(CONFIG_GENERAL_NAME, "Mining Wand Ores for Surface Mining", new String[] { "oredict:oreGold", "oredict:oreIron", "oredict:oreCoal", "oredict:oreLapis", "oredict:oreDiamond", "oredict:oreRedstone", "minecraft:lit_redstone_ore", "oredict:oreEmerald", "oredict:oreQuartz", "oredict:oreUranium", "oredict:oreAluminum", "oredict:oreOil", "oredict:oreElement115", "oredict:oreBlackDiamond", "oredict:oreRandomite" }).getStringList();

			ConfigUtils.loadBlocksOntoMap(BLOCKS_Destructive_wand_spared_ores, ItemBreakingWand.ores);
			ConfigUtils.loadBlocksOntoMap(BLOCKS_Mining_wand_ores_for_surface_mining, ItemMiningWand.m_ores);
		}

		if (subpartPowerstaff) {
			restrictPowerStaffBlocks = config.get(CONFIG_GENERAL_NAME, "Restrict powerstaff blocks", false).getBoolean();
			powerstaff_pull_push_blocks = config.get(CONFIG_GENERAL_NAME, "Blocks allowed when restrict powerstaff is active", new String[] { "dirt", "grass", "cobblestone", "stone" }).getStringList();

			ConfigUtils.loadBlocksOntoMap(powerstaff_pull_push_blocks, ItemPowerStaff.allowedBlocks);
		}

		if (subpartBlackDiamond)
			generateBlackDiamond = config.get(CONFIG_GENERAL_NAME, "Generate Black Diamond", true).getBoolean();

		if (subpartRayGuns)
			generateElement115 = config.get(CONFIG_GENERAL_NAME, "Generate Element 115", true).getBoolean();

		if (subpartSlingshots) {
			stonePelletDamage = Float.parseFloat(config.get(CONFIG_SLINGSHOT_NAME, "Stone Pellet Damage", 1.0f).setRequiresMcRestart(true).getString());
			ironPelletDamage = Float.parseFloat(config.get(CONFIG_SLINGSHOT_NAME, "Iron Pellet Damage", 6.0f).setRequiresMcRestart(true).getString());
			netherrackPelletDamage = Float.parseFloat(config.get(CONFIG_SLINGSHOT_NAME, "Netherrack Pellet Damage", 5.0f).setRequiresMcRestart(true).getString());
			lightPelletDamage = Float.parseFloat(config.get(CONFIG_SLINGSHOT_NAME, "Light Pellet Damage", 1.0f).setRequiresMcRestart(true).getString());
			firePelletDamage = Float.parseFloat(config.get(CONFIG_SLINGSHOT_NAME, "Fire Pellet Damage", 1.5f).setRequiresMcRestart(true).getString());
			explosivePelletDamage = Float.parseFloat(config.get(CONFIG_SLINGSHOT_NAME, "Explosive Pellet Damage", 1.5f).setRequiresMcRestart(true).getString());
		}

		if (subpartBoomerangs) {
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
		}

		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		if (subpartWands || subpartPowerstaff || subpartBlackDiamond || subpartRayGuns)
			list.add(new DummyCategoryElement("toolsGeneralCfg", "grimpack.tools.cfg.general", new ConfigElement(GrimTools.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements()));
		if (subpartUltimateFist)
			list.add(new DummyCategoryElement("toolsUltimateFistCfg", "grimpack.tools.cfg.ultimatefist", new ConfigElement(GrimTools.INSTANCE.getConfig().getCategory(CONFIG_FIST_NAME)).getChildElements()));
		if (subpartSlingshots)
			list.add(new DummyCategoryElement("toolsSlingshotCfg", "grimpack.tools.cfg.slingshot", new ConfigElement(GrimTools.INSTANCE.getConfig().getCategory(CONFIG_SLINGSHOT_NAME)).getChildElements()));
		if (subpartBoomerangs)
			list.add(new DummyCategoryElement("toolsBoomerangCfg", "grimpack.tools.cfg.boomerang", new ConfigElement(GrimTools.INSTANCE.getConfig().getCategory(CONFIG_BOOMERANG_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("toolsSubPartCfg", "grimpack.tools.cfg.subparts", new ConfigElement(GrimTools.INSTANCE.getConfig().getCategory(CONFIG_PARTS_NAME)).getChildElements()));
		return list;
	}

	@Override
	public void readFromServer(PacketBuffer buffer) {
		subpartBackpacks = buffer.readBoolean();
		subpartBlackDiamond = buffer.readBoolean();
		subpartBoomerangs = buffer.readBoolean();
		subpartBuckets = buffer.readBoolean();
		subpartChisel = buffer.readBoolean();
		subpartExtinguisher = buffer.readBoolean();
		subpartGrenadeLauncher = buffer.readBoolean();
		subpartHammers = buffer.readBoolean();
		subpartKnives = buffer.readBoolean();
		subpartMachetes = buffer.readBoolean();
		subpartMasks = buffer.readBoolean();
		subpartMultiTools = buffer.readBoolean();
		subpartPokeball = buffer.readBoolean();
		subpartPortableWorkbench = buffer.readBoolean();
		subpartPowerstaff = buffer.readBoolean();
		subpartRayGuns = buffer.readBoolean();
		subpartSlingshots = buffer.readBoolean();
		subpartSpears = buffer.readBoolean();
		subpartUltimateFist = buffer.readBoolean();
		subpartWands = buffer.readBoolean();
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		buffer.writeBoolean(subpartBackpacks);
		buffer.writeBoolean(subpartBlackDiamond);
		buffer.writeBoolean(subpartBoomerangs);
		buffer.writeBoolean(subpartBuckets);
		buffer.writeBoolean(subpartChisel);
		buffer.writeBoolean(subpartExtinguisher);
		buffer.writeBoolean(subpartGrenadeLauncher);
		buffer.writeBoolean(subpartHammers);
		buffer.writeBoolean(subpartKnives);
		buffer.writeBoolean(subpartMachetes);
		buffer.writeBoolean(subpartMasks);
		buffer.writeBoolean(subpartMultiTools);
		buffer.writeBoolean(subpartPokeball);
		buffer.writeBoolean(subpartPortableWorkbench);
		buffer.writeBoolean(subpartPowerstaff);
		buffer.writeBoolean(subpartRayGuns);
		buffer.writeBoolean(subpartSlingshots);
		buffer.writeBoolean(subpartSpears);
		buffer.writeBoolean(subpartUltimateFist);
		buffer.writeBoolean(subpartWands);
	}
}
