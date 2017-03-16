package com.grim3212.mc.pack.decor.event;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.item.DecorItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class DecorAchievements {

	public static Achievement DECOR_START;
	public static Achievement COLORIZER;
	public static Achievement COLORIZER_LAMP;
	public static Achievement COLORIZER_HIDDEN;
	public static Achievement COLORIZER_FIREPLACE;

	public static void init() {
		MinecraftForge.EVENT_BUS.register(new DecorAchievements());

		DECOR_START = Utils.addAchievement("achievement.decor_start", "decor_start", -5, -5, new ItemStack(DecorBlocks.calendar), GrimCore.OPEN_MANUAL);
		COLORIZER = Utils.addAchievement("achievement.colorizer", "colorizer", -7, -5, new ItemStack(DecorBlocks.colorizer), DECOR_START);
		COLORIZER_LAMP = Utils.addAchievement("achievement.colorizer_lamp", "colorizer_lamp", -7, -3, new ItemStack(DecorItems.lamp_item), COLORIZER);
		COLORIZER_HIDDEN = Utils.addAchievement("achievement.colorizer_hidden", "colorizer_hidden", -7, -7, new ItemStack(DecorItems.decor_door_item), COLORIZER);
		COLORIZER_FIREPLACE = Utils.addAchievement("achievement.colorizer_fireplace", "colorizer_fireplace", -9, -5, new ItemStack(DecorBlocks.fireplace), COLORIZER);
	}

	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event) {
		Item item = event.crafting.getItem();
		EntityPlayer player = event.player;

		if (item == Item.getItemFromBlock(DecorBlocks.colorizer)) {
			player.addStat(COLORIZER);
		} else if (item == DecorItems.lamp_item) {
			player.addStat(COLORIZER_LAMP);
		} else if (item == DecorItems.decor_door_item) {
			player.addStat(COLORIZER_HIDDEN);
		} else if (item == Item.getItemFromBlock(DecorBlocks.fireplace)) {
			player.addStat(COLORIZER_HIDDEN);
		}
	}
}
