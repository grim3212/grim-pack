package com.grim3212.mc.pack.industry.event;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class IndustryAchievements {

	public static Achievement INDUSTRY_START;
	public static Achievement ALUMINUM;
	public static Achievement C4;
	public static Achievement BOMB_SHELL;
	public static Achievement NUCLEAR_BOMB;
	public static Achievement SENSOR;
	public static Achievement UPGRADED_SENSOR;
	public static Achievement GRAVITY_SCIENCE;
	public static Achievement STEEL;
	public static Achievement MODERN_FURNACE;

	public static void init() {
		MinecraftForge.EVENT_BUS.register(new IndustryAchievements());

		INDUSTRY_START = Utils.addAchievement("achievement.industry.industry_start", "industry.industry_start", -5, 5, new ItemStack(IndustryBlocks.concrete), GrimCore.OPEN_MANUAL);
		ALUMINUM = Utils.addAchievement("achievement.industry.aluminum", "industry.aluminum", -7, 5, new ItemStack(IndustryItems.aluminum_ingot), INDUSTRY_START);
		C4 = Utils.addAchievement("achievement.industry.c4", "industry.c4", -7, 7, new ItemStack(IndustryBlocks.c4), ALUMINUM);
		BOMB_SHELL = Utils.addAchievement("achievement.industry.bomb_shell", "industry.bomb_shell", -7, 9, new ItemStack(IndustryBlocks.bomb_shell), C4);
		NUCLEAR_BOMB = Utils.addAchievement("achievement.industry.nuclear_bomb", "industry.nuclear_bomb", -7, 11, new ItemStack(IndustryBlocks.nuclear_bomb), BOMB_SHELL).setSpecial();
		SENSOR = Utils.addAchievement("achievement.industry.sensor", "industry.sensor", -5, 7, new ItemStack(IndustryBlocks.specific_sensor), INDUSTRY_START);
		UPGRADED_SENSOR = Utils.addAchievement("achievement.industry.upgraded_sensor", "industry.upgraded_sensor", -5, 9, new ItemStack(IndustryBlocks.upgraded_specific_sensor), SENSOR);
		GRAVITY_SCIENCE = Utils.addAchievement("achievement.industry.gravity_science", "industry.gravity_science", -3, 6, new ItemStack(IndustryBlocks.attractor), INDUSTRY_START);
		STEEL = Utils.addAchievement("achievement.industry.steel", "industry.steel", -5, 3, new ItemStack(IndustryItems.steel_ingot), INDUSTRY_START);
		MODERN_FURNACE = Utils.addAchievement("achievement.industry.modern_furnace", "industry.modern_furnace", -3, 3, new ItemStack(IndustryBlocks.modern_furnace), STEEL);
	}

	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event) {
		Item item = event.crafting.getItem();
		EntityPlayer player = event.player;

		if (item == Item.getItemFromBlock(IndustryBlocks.c4)) {
			player.addStat(C4);
		} else if (item == Item.getItemFromBlock(IndustryBlocks.bomb_shell)) {
			player.addStat(BOMB_SHELL);
		} else if (item == Item.getItemFromBlock(IndustryBlocks.nuclear_bomb)) {
			player.addStat(NUCLEAR_BOMB);
		} else if (item == Item.getItemFromBlock(IndustryBlocks.specific_sensor)) {
			player.addStat(SENSOR);
		} else if (item == Item.getItemFromBlock(IndustryBlocks.upgraded_specific_sensor)) {
			player.addStat(UPGRADED_SENSOR);
		} else if (item == Item.getItemFromBlock(IndustryBlocks.attractor) || item == Item.getItemFromBlock(IndustryBlocks.gravitor) || item == Item.getItemFromBlock(IndustryBlocks.repulsor)) {
			player.addStat(GRAVITY_SCIENCE);
		} else if (item == Item.getItemFromBlock(IndustryBlocks.modern_furnace)) {
			player.addStat(MODERN_FURNACE);
		}
	}

	@SubscribeEvent
	public void onItemSmelted(ItemSmeltedEvent event) {
		Item item = event.smelting.getItem();
		EntityPlayer player = event.player;

		if (item == IndustryItems.aluminum_ingot) {
			player.addStat(ALUMINUM);
		} else if (item == IndustryItems.steel_ingot) {
			player.addStat(STEEL);
		}
	}

}
