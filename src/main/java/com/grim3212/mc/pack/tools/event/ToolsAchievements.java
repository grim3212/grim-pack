package com.grim3212.mc.pack.tools.event;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class ToolsAchievements {

	public static Achievement TOOLS_START;
	public static Achievement OBSIDIAN_BUCKET;
	public static Achievement BLACK_DIAMOND;
	public static Achievement BLACK_TOOLS;
	public static Achievement BLACK_ARMOR;
	public static Achievement SPEAR;
	public static Achievement SPEAR_LIGHTNING;
	public static Achievement BOOMERANG;
	public static Achievement DIAMOND_BOOMERANG;
	public static Achievement MAGIC_WAND;
	public static Achievement BETTER_MAGIC_WAND;
	public static Achievement POKEBALL;
	public static Achievement ELEMENT_115;
	public static Achievement RAY_GUN;
	public static Achievement ADVANCED_RAY_GUN;

	public static void init() {
		MinecraftForge.EVENT_BUS.register(new ToolsAchievements());

		TOOLS_START = Utils.addAchievement("achievement.tools.tools_start", "tools.tools_start", 5, -5, new ItemStack(ToolsItems.backpack), GrimCore.OPEN_MANUAL);
		OBSIDIAN_BUCKET = Utils.addAchievement("achievement.tools.obsidian_bucket", "tools.obsidian_bucket", 5, -2, new ItemStack(ToolsItems.obsidian_bucket), TOOLS_START);
		BLACK_DIAMOND = Utils.addAchievement("achievement.tools.black_diamond", "tools.black_diamond", 5, -8, new ItemStack(ToolsItems.black_diamond), TOOLS_START);
		BLACK_TOOLS = Utils.addAchievement("achievement.tools.black_tools", "tools.black_tools", 7, -8, new ItemStack(ToolsItems.black_diamond_pickaxe), BLACK_DIAMOND);
		BLACK_ARMOR = Utils.addAchievement("achievement.tools.black_armor", "tools.black_armor", 9, -8, new ItemStack(ToolsItems.black_diamond_helmet), BLACK_DIAMOND);
		SPEAR = Utils.addAchievement("achievement.tools.spear", "tools.spear", 3, -6, new ItemStack(ToolsItems.spear), TOOLS_START);
		SPEAR_LIGHTNING = Utils.addAchievement("achievement.tools.spear_lightning", "tools.spear_lightning", 1, -6, new ItemStack(ToolsItems.lightning_spear), SPEAR);
		BOOMERANG = Utils.addAchievement("achievement.tools.boomerang", "tools.boomerang", 8, -4, new ItemStack(ToolsItems.boomerang), TOOLS_START);
		DIAMOND_BOOMERANG = Utils.addAchievement("achievement.tools.diamond_boomerang", "tools.diamond_boomerang", 10, -4, new ItemStack(ToolsItems.diamond_boomerang), BOOMERANG);
		MAGIC_WAND = Utils.addAchievement("achievement.tools.magic_wand", "tools.magic_wand", 3, -3, new ItemStack(ToolsItems.building_wand), TOOLS_START);
		BETTER_MAGIC_WAND = Utils.addAchievement("achievement.tools.better_magic_wand", "tools.better_magic_wand", 3, -1, new ItemStack(ToolsItems.reinforced_building_wand), MAGIC_WAND);
		POKEBALL = Utils.addAchievement("achievement.tools.pokeball", "tools.pokeball", 8, -6, new ItemStack(ToolsItems.pokeball), TOOLS_START);
		ELEMENT_115 = Utils.addAchievement("achievement.tools.element_115", "tools.element_115", 7, -3, new ItemStack(ToolsItems.element_115), TOOLS_START);
		RAY_GUN = Utils.addAchievement("achievement.tools.ray_gun", "tools.ray_gun", 9, -3, new ItemStack(ToolsItems.ray_gun), ELEMENT_115);
		ADVANCED_RAY_GUN = Utils.addAchievement("achievement.tools.advanced_ray_gun", "tools.advanced_ray_gun", 9, -1, new ItemStack(ToolsItems.advanced_ray_gun), RAY_GUN);
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		Item item = event.getItem().getEntityItem().getItem();
		EntityPlayer player = event.getEntityPlayer();

		if (item == ToolsItems.element_115) {
			player.addStat(ELEMENT_115);
		}
	}

	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event) {
		Item item = event.crafting.getItem();
		EntityPlayer player = event.player;

		if (item == ToolsItems.pokeball) {
			player.addStat(POKEBALL);
		} else if (item == ToolsItems.black_diamond_pickaxe || item == ToolsItems.black_diamond_axe || item == ToolsItems.black_diamond_shovel || item == ToolsItems.black_diamond_hoe || item == ToolsItems.black_diamond_sword) {
			player.addStat(BLACK_TOOLS);
		} else if (item == ToolsItems.black_diamond_helmet || item == ToolsItems.black_diamond_chestplate || item == ToolsItems.black_diamond_leggings || item == ToolsItems.black_diamond_boots) {
			player.addStat(BLACK_ARMOR);
		} else if (item == ToolsItems.spear) {
			player.addStat(SPEAR);
		} else if (item == ToolsItems.lightning_spear) {
			player.addStat(SPEAR_LIGHTNING);
		} else if (item == ToolsItems.boomerang) {
			player.addStat(BOOMERANG);
		} else if (item == ToolsItems.diamond_boomerang) {
			player.addStat(DIAMOND_BOOMERANG);
		} else if (item == ToolsItems.building_wand || item == ToolsItems.breaking_wand || item == ToolsItems.mining_wand) {
			player.addStat(MAGIC_WAND);
		} else if (item == ToolsItems.reinforced_building_wand || item == ToolsItems.reinforced_breaking_wand || item == ToolsItems.reinforced_mining_wand) {
			player.addStat(BETTER_MAGIC_WAND);
		} else if (item == ToolsItems.ray_gun) {
			player.addStat(RAY_GUN);
		} else if (item == ToolsItems.advanced_ray_gun) {
			player.addStat(ADVANCED_RAY_GUN);
		}
	}

	@SubscribeEvent
	public void onItemSmelted(ItemSmeltedEvent event) {
		Item item = event.smelting.getItem();
		EntityPlayer player = event.player;

		if (item == ToolsItems.black_diamond) {
			player.addStat(BLACK_ARMOR);
		}
	}
}
