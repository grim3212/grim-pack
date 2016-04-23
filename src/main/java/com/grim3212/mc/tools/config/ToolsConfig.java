package com.grim3212.mc.tools.config;

import java.util.ArrayList;

import com.grim3212.mc.core.config.GrimConfig;
import com.grim3212.mc.tools.items.ItemBreakingWand;
import com.grim3212.mc.tools.items.ItemMiningWand;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameData;

public class ToolsConfig extends GrimConfig {

	public static boolean ENABLE_free_build_mode;
	public static boolean ENABLE_bedrock_breaking;
	public static boolean ENABLE_easy_mining_obsidian;
	public static String[] BLOCKS_Destructive_wand_spared_ores;
	public static String[] BLOCKS_Mining_wand_ores_for_surface_mining;

	@Override
	public void syncConfig() {
		ENABLE_free_build_mode = config.get(Configuration.CATEGORY_GENERAL, "Enable Free Build Mode", false).getBoolean();
		ENABLE_bedrock_breaking = config.get(Configuration.CATEGORY_GENERAL, "Enable Bedrock Breaking", false).getBoolean();
		ENABLE_easy_mining_obsidian = config.get(Configuration.CATEGORY_GENERAL, "Enable Easy Mining Obsidian", false).getBoolean();

		BLOCKS_Destructive_wand_spared_ores = config.get(Configuration.CATEGORY_GENERAL, "Destructive Wand Spared Ores", new String[] { "gold_ore", "iron_ore", "coal_ore", "lapis_ore", "mossy_cobblestone", "mob_spawner", "chest", "diamond_ore", "redstone_ore", "lit_redstone_ore", "emerald_ore", "quartz_ore" }).getStringList();
		BLOCKS_Mining_wand_ores_for_surface_mining = config.get(Configuration.CATEGORY_GENERAL, "Mining Wand Ores for Surface Mining", new String[] { "gold_ore", "iron_ore", "coal_ore", "lapis_ore", "diamond_ore", "redstone_ore", "lit_redstone_ore", "emerald_ore", "quartz_ore" }).getStringList();

		registerBlocksPossible(BLOCKS_Destructive_wand_spared_ores, ItemBreakingWand.ores);
		registerBlocksPossible(BLOCKS_Mining_wand_ores_for_surface_mining, ItemMiningWand.m_ores);

		super.syncConfig();
	}

	public void registerBlocksPossible(String[] string, ArrayList<Block> blocklist) {
		if (string.length > 0) {
			for (String u : string) {
				try {
					blocklist.add(GameData.getBlockRegistry().getObject(new ResourceLocation(u)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
