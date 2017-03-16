package com.grim3212.mc.pack.world.event;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.items.WorldItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class WorldAchievements {

	public static Achievement WORLD_START;
	public static Achievement FUNGUS;
	public static Achievement BUILDING_FUNGUS;
	public static Achievement ORE_BUILDING_FUNGUS;
	public static Achievement FUNGICIDE;
	public static Achievement TREASURE;
	public static Achievement ICE_PIXIE;

	public static void init() {
		MinecraftForge.EVENT_BUS.register(new WorldAchievements());

		WORLD_START = Utils.addAchievement("achievement.world_start", "world_start", 5, 0, new ItemStack(WorldItems.gunpowder_reed_item), GrimCore.OPEN_MANUAL);
		FUNGUS = Utils.addAchievement("achievement.fungus", "fungus", 7, 1, new ItemStack(WorldBlocks.fungus_growing), WORLD_START);
		BUILDING_FUNGUS = Utils.addAchievement("achievement.building_fungus", "building_fungus", 9, 1, new ItemStack(WorldBlocks.fungus_building), FUNGUS);
		FUNGICIDE = Utils.addAchievement("achievement.fungicide", "fungicide", 11, 1, new ItemStack(WorldItems.fungicide), BUILDING_FUNGUS);
		ORE_BUILDING_FUNGUS = Utils.addAchievement("achievement.ore_building_fungus", "ore_building_fungus", 11, 3, new ItemStack(WorldBlocks.fungus_ore_building), FUNGICIDE).setSpecial();
		TREASURE = Utils.addAchievement("achievement.treasure", "treasure", 4, 1, new ItemStack(Blocks.CHEST), WORLD_START);
		ICE_PIXIE = Utils.addAchievement("achievement.ice_pixie", "ice_pixie", 2, 1, new ItemStack(Blocks.ICE), TREASURE).setSpecial();
	}

	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event) {
		Item item = event.crafting.getItem();
		EntityPlayer player = event.player;

		if (item == Item.getItemFromBlock(WorldBlocks.fungus_growing)) {
			player.addStat(FUNGUS);
		} else if (item == Item.getItemFromBlock(WorldBlocks.fungus_building)) {
			player.addStat(BUILDING_FUNGUS);
		} else if (item == Item.getItemFromBlock(WorldBlocks.fungus_ore_building)) {
			player.addStat(ORE_BUILDING_FUNGUS);
		} else if (item == WorldItems.fungicide) {
			player.addStat(FUNGICIDE);
		}
	}
}
