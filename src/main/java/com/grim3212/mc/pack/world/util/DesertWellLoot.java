package com.grim3212.mc.pack.world.util;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.world.GrimWorld;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class DesertWellLoot {

	public static List<WellLootStorage> level10storage = new ArrayList<WellLootStorage>();
	public static List<WellLootStorage> level15storage = new ArrayList<WellLootStorage>();
	public static List<WellLootStorage> level20storage = new ArrayList<WellLootStorage>();
	public static List<WellLootStorage> level25storage = new ArrayList<WellLootStorage>();
	public static List<WellLootStorage> level30storage = new ArrayList<WellLootStorage>();

	public static void init() {
		GrimLog.info(GrimWorld.partName, "Adding desert well loot");

		level10storage.add(new WellLootStorage(new ItemStack(Items.BONE), 1, 3));
		level10storage.add(new WellLootStorage(new ItemStack(Items.STRING), 1, 1));
		level10storage.add(new WellLootStorage(new ItemStack(Blocks.COBBLESTONE), 1, 6));
		level10storage.add(new WellLootStorage(new ItemStack(Blocks.WEB), 1, 1));
		level10storage.add(new WellLootStorage(new ItemStack(Blocks.DIRT), 1, 6));

		level15storage.add(new WellLootStorage(new ItemStack(Items.LEATHER), 1, 4));
		level15storage.add(new WellLootStorage(new ItemStack(Items.IRON_INGOT), 1, 3));
		level15storage.add(new WellLootStorage(new ItemStack(Items.SLIME_BALL), 1, 4));
		level15storage.add(new WellLootStorage(new ItemStack(Items.WHEAT_SEEDS), 1, 4));

		level20storage.add(new WellLootStorage(new ItemStack(Items.MELON_SEEDS), 1, 3));
		level20storage.add(new WellLootStorage(new ItemStack(Items.PUMPKIN_SEEDS), 1, 3));
		level20storage.add(new WellLootStorage(new ItemStack(Items.IRON_INGOT), 1, 4));
		level20storage.add(new WellLootStorage(new ItemStack(Items.SLIME_BALL), 1, 5));
		level20storage.add(new WellLootStorage(new ItemStack(Items.QUARTZ), 1, 3));
		level20storage.add(new WellLootStorage(new ItemStack(Items.FISHING_ROD), 1, 1));

		level25storage.add(new WellLootStorage(new ItemStack(Items.GOLD_INGOT), 1, 4));
		level25storage.add(new WellLootStorage(new ItemStack(Items.REDSTONE), 1, 6));
		level25storage.add(new WellLootStorage(new ItemStack(Items.GHAST_TEAR), 1, 3));
		level25storage.add(new WellLootStorage(new ItemStack(Items.MELON_SEEDS), 1, 4));
		level25storage.add(new WellLootStorage(new ItemStack(Items.IRON_INGOT), 1, 5));
		level25storage.add(new WellLootStorage(new ItemStack(Items.QUARTZ), 1, 4));
		level25storage.add(new WellLootStorage(new ItemStack(Items.EXPERIENCE_BOTTLE), 1, 3));
		level25storage.add(new WellLootStorage(new ItemStack(Items.BLAZE_ROD), 1, 5));

		level30storage.add(new WellLootStorage(new ItemStack(Items.DIAMOND), 1, 4));
		level30storage.add(new WellLootStorage(new ItemStack(Items.EMERALD), 1, 4));
		level30storage.add(new WellLootStorage(new ItemStack(Items.EXPERIENCE_BOTTLE), 1, 4));
		level30storage.add(new WellLootStorage(new ItemStack(Items.REDSTONE), 1, 7));
		level30storage.add(new WellLootStorage(new ItemStack(Items.NETHER_STAR), 1, 1));
		level30storage.add(new WellLootStorage(new ItemStack(Items.BLAZE_ROD), 1, 6));
		level30storage.add(new WellLootStorage(new ItemStack(Items.ENDER_PEARL), 1, 4));
		level30storage.add(new WellLootStorage(new ItemStack(Items.NETHER_WART), 1, 5));
		level30storage.add(new WellLootStorage(new ItemStack(Items.QUARTZ), 1, 5));
	}
}
