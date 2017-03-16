package com.grim3212.mc.pack.cuisine.event;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class CuisineAchievements {

	public static Achievement BAKE_CHOCOLATE_CAKE;
	public static Achievement COCOA;
	public static Achievement CHOCOLATE_MOULD;
	public static Achievement CHOCOLATE_BAR;
	public static Achievement HOT_CHOCOLATE;
	public static Achievement SODA;
	public static Achievement PIE_PAN;
	public static Achievement PIE;
	public static Achievement CUISINE_START;

	public static void init() {
		MinecraftForge.EVENT_BUS.register(new CuisineAchievements());

		CUISINE_START = Utils.addAchievement("achievement.cuisine_start", "cuisine_start", 5, 5, new ItemStack(CuisineItems.cheese), GrimCore.OPEN_MANUAL);
		SODA = Utils.addAchievement("achievement.soda", "soda", 5, 3, new ItemStack(CuisineItems.soda), CUISINE_START);
		PIE_PAN = Utils.addAchievement("achievement.pie_pan", "pie_pan", 5, 7, new ItemStack(CuisineItems.pan), CUISINE_START);
		PIE = Utils.addAchievement("achievement.pie", "pie", 5, 9, new ItemStack(CuisineBlocks.apple_pie), PIE_PAN);
		COCOA = Utils.addAchievement("achievement.cocoa", "cocoa", 7, 5, new ItemStack(CuisineItems.cocoa_dust), CUISINE_START);
		CHOCOLATE_MOULD = Utils.addAchievement("achievement.chocolate_mould", "chocolate_mould", 9, 3, new ItemStack(CuisineBlocks.chocolate_bar_mould), COCOA);
		CHOCOLATE_BAR = Utils.addAchievement("achievement.chocolate_bar", "chocolate_bar", 9, 5, new ItemStack(CuisineItems.chocolate_bar_wrapped), CHOCOLATE_MOULD);
		HOT_CHOCOLATE = Utils.addAchievement("achievement.hot_chocolate", "hot_chocolate", 7, 7, new ItemStack(CuisineItems.chocolate_bowl_hot), COCOA);
		BAKE_CHOCOLATE_CAKE = Utils.addAchievement("achievement.bake_chocolate_cake", "bake_chocolate_cake", 7, 9, new ItemStack(CuisineBlocks.chocolate_cake), HOT_CHOCOLATE);
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		ItemStack stack = event.getItem().getEntityItem();
		Item item = stack.getItem();
		EntityPlayer player = event.getEntityPlayer();

		if (item == CuisineItems.cocoa_fruit) {
			player.addStat(COCOA);
		}
	}

	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event) {
		Item item = event.crafting.getItem();
		EntityPlayer player = event.player;

		if (item == CuisineItems.soda && (event.crafting.getMetadata() != 1 && event.crafting.getMetadata() != 3 && event.crafting.getMetadata() != 11)) {
			player.addStat(SODA);
		} else if (item == CuisineItems.pan) {
			player.addStat(PIE_PAN);
		} else if (item == Item.getItemFromBlock(CuisineBlocks.apple_pie) || item == Item.getItemFromBlock(CuisineBlocks.chocolate_pie) || item == Item.getItemFromBlock(CuisineBlocks.melon_pie) || item == Item.getItemFromBlock(CuisineBlocks.pork_pie) || item == Item.getItemFromBlock(CuisineBlocks.pumpkin_pie)) {
			player.addStat(SODA);
		} else if (item == Item.getItemFromBlock(CuisineBlocks.chocolate_bar_mould)) {
			player.addStat(CHOCOLATE_MOULD);
		} else if (item == CuisineItems.chocolate_bar_wrapped) {
			player.addStat(CHOCOLATE_BAR);
		} else if (item == Item.getItemFromBlock(CuisineBlocks.chocolate_cake)) {
			player.addStat(CHOCOLATE_MOULD);
		}
	}

	@SubscribeEvent
	public void onItemSmelted(ItemSmeltedEvent event) {
		Item item = event.smelting.getItem();
		EntityPlayer player = event.player;

		if (item == CuisineItems.chocolate_bowl_hot) {
			player.addStat(HOT_CHOCOLATE);
		}
	}
}
