package com.grim3212.mc.pack.cuisine.events;

import com.grim3212.mc.pack.cuisine.GrimCuisine;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class AchievementEvents {

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		ItemStack stack = event.getItem().getEntityItem();
		Item item = stack.getItem();
		EntityPlayer player = event.getEntityPlayer();

		if (item == CuisineItems.cocoa_fruit) {
			player.addStat(GrimCuisine.COCOA);
		}
	}

	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event) {
		Item item = event.crafting.getItem();
		EntityPlayer player = event.player;

		if (item == CuisineItems.soda && (event.crafting.getMetadata() != 1 && event.crafting.getMetadata() != 3 && event.crafting.getMetadata() != 11)) {
			player.addStat(GrimCuisine.SODA);
		} else if (item == CuisineItems.pan) {
			player.addStat(GrimCuisine.PIE_PAN);
		} else if (item == Item.getItemFromBlock(CuisineBlocks.apple_pie) || item == Item.getItemFromBlock(CuisineBlocks.chocolate_pie) || item == Item.getItemFromBlock(CuisineBlocks.melon_pie) || item == Item.getItemFromBlock(CuisineBlocks.pork_pie) || item == Item.getItemFromBlock(CuisineBlocks.pumpkin_pie)) {
			player.addStat(GrimCuisine.SODA);
		} else if (item == Item.getItemFromBlock(CuisineBlocks.chocolate_bar_mould)) {
			player.addStat(GrimCuisine.CHOCOLATE_MOULD);
		} else if (item == CuisineItems.chocolate_bar_wrapped) {
			player.addStat(GrimCuisine.CHOCOLATE_BAR);
		} else if (item == Item.getItemFromBlock(CuisineBlocks.chocolate_cake)) {
			player.addStat(GrimCuisine.CHOCOLATE_MOULD);
		}
	}

	@SubscribeEvent
	public void onItemSmelted(ItemSmeltedEvent event) {
		Item item = event.smelting.getItem();
		EntityPlayer player = event.player;

		if (item == CuisineItems.chocolate_bowl_hot) {
			player.addStat(GrimCuisine.HOT_CHOCOLATE);
		}
	}
}
