package com.grim3212.mc.pack.world.util;

import java.util.Map;

import com.google.common.collect.Maps;
import com.grim3212.mc.pack.core.config.ConfigUtils;

import net.minecraft.block.state.IBlockState;

public class KillingFungusWhitelist {

	public static Map<IBlockState, Boolean> dirt = Maps.newHashMap();
	public static Map<IBlockState, Boolean> smoothstone = Maps.newHashMap();
	public static Map<IBlockState, Boolean> waterLeaves = Maps.newHashMap();
	public static Map<IBlockState, Boolean> sandGravel = Maps.newHashMap();
	public static Map<IBlockState, Boolean> rocks = Maps.newHashMap();
	public static Map<IBlockState, Boolean> allEating = Maps.newHashMap();
	public static Map<IBlockState, Boolean> forestEating = Maps.newHashMap();

	public static boolean isAllEatingBlacklisted(IBlockState block) {
		return ConfigUtils.isStateFound(allEating, block);
	}

	public static boolean isForestEatingWhitelisted(IBlockState block) {
		return ConfigUtils.isStateFound(forestEating, block);
	}

	public static boolean isDirtWhitelisted(IBlockState block) {
		return ConfigUtils.isStateFound(dirt, block);
	}

	public static boolean isSmoothWhitelisted(IBlockState block) {
		return ConfigUtils.isStateFound(smoothstone, block);
	}

	public static boolean isWaterLeavesWhitelisted(IBlockState block) {
		return ConfigUtils.isStateFound(waterLeaves, block);
	}

	public static boolean isSandGravelWhitelisted(IBlockState block) {
		return ConfigUtils.isStateFound(sandGravel, block);
	}

	public static boolean isRocksWhitelisted(IBlockState block) {
		return ConfigUtils.isStateFound(rocks, block);
	}
}
