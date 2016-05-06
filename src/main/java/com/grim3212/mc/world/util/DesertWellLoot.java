package com.grim3212.mc.world.util;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.core.util.GrimLog;
import com.grim3212.mc.world.GrimWorld;

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
		GrimLog.info(GrimWorld.modName, "Adding desert well loot");

		level10storage.add(new WellLootStorage(new ItemStack(Items.bone), 1, 3));
		level10storage.add(new WellLootStorage(new ItemStack(Items.string), 1, 1));
		level10storage.add(new WellLootStorage(new ItemStack(Blocks.cobblestone), 1, 6));
		level10storage.add(new WellLootStorage(new ItemStack(Blocks.web), 1, 1));
		level10storage.add(new WellLootStorage(new ItemStack(Blocks.dirt), 1, 6));

		level15storage.add(new WellLootStorage(new ItemStack(Items.leather), 1, 4));
		level15storage.add(new WellLootStorage(new ItemStack(Items.iron_ingot), 1, 3));
		level15storage.add(new WellLootStorage(new ItemStack(Items.slime_ball), 1, 4));
		level15storage.add(new WellLootStorage(new ItemStack(Items.wheat_seeds), 1, 4));

		level20storage.add(new WellLootStorage(new ItemStack(Items.melon_seeds), 1, 3));
		level20storage.add(new WellLootStorage(new ItemStack(Items.pumpkin_seeds), 1, 3));
		level20storage.add(new WellLootStorage(new ItemStack(Items.iron_ingot), 1, 4));
		level20storage.add(new WellLootStorage(new ItemStack(Items.slime_ball), 1, 5));
		level20storage.add(new WellLootStorage(new ItemStack(Items.quartz), 1, 3));
		level20storage.add(new WellLootStorage(new ItemStack(Items.fishing_rod), 1, 1));

		level25storage.add(new WellLootStorage(new ItemStack(Items.gold_ingot), 1, 4));
		level25storage.add(new WellLootStorage(new ItemStack(Items.redstone), 1, 6));
		level25storage.add(new WellLootStorage(new ItemStack(Items.ghast_tear), 1, 3));
		level25storage.add(new WellLootStorage(new ItemStack(Items.melon_seeds), 1, 4));
		level25storage.add(new WellLootStorage(new ItemStack(Items.iron_ingot), 1, 5));
		level25storage.add(new WellLootStorage(new ItemStack(Items.quartz), 1, 4));
		level25storage.add(new WellLootStorage(new ItemStack(Items.experience_bottle), 1, 3));
		level25storage.add(new WellLootStorage(new ItemStack(Items.blaze_rod), 1, 5));

		level30storage.add(new WellLootStorage(new ItemStack(Items.diamond), 1, 4));
		level30storage.add(new WellLootStorage(new ItemStack(Items.emerald), 1, 4));
		level30storage.add(new WellLootStorage(new ItemStack(Items.experience_bottle), 1, 4));
		level30storage.add(new WellLootStorage(new ItemStack(Items.redstone), 1, 7));
		level30storage.add(new WellLootStorage(new ItemStack(Items.nether_star), 1, 1));
		level30storage.add(new WellLootStorage(new ItemStack(Items.blaze_rod), 1, 6));
		level30storage.add(new WellLootStorage(new ItemStack(Items.ender_pearl), 1, 4));
		level30storage.add(new WellLootStorage(new ItemStack(Items.nether_wart), 1, 5));
		level30storage.add(new WellLootStorage(new ItemStack(Items.quartz), 1, 5));
	}
}
