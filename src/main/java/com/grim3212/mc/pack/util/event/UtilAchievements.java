package com.grim3212.mc.pack.util.event;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.util.init.UtilBlocks;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public class UtilAchievements {

	public static Achievement UTIL_START;
	public static Achievement DIE;
	public static Achievement FUS_RO_DAH;
	
	public static void init(){
		UTIL_START = Utils.addAchievement("achievement.util_start", "util_start", -5, 0, new ItemStack(Items.COMPASS), GrimCore.OPEN_MANUAL);
		DIE = Utils.addAchievement("achievement.die", "die", -7, 0, new ItemStack(UtilBlocks.grave), UTIL_START);
		FUS_RO_DAH = Utils.addAchievement("achievement.fus_ro_dah", "fus_ro_dah", -5, -2, ItemStack.EMPTY, UTIL_START);
	}
	
}
