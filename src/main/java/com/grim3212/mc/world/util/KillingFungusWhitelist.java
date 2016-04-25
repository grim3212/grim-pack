package com.grim3212.mc.world.util;

import java.util.ArrayList;

import net.minecraft.block.Block;

public class KillingFungusWhitelist {

	public static ArrayList<Block> dirt = new ArrayList<Block>();
	public static ArrayList<Block> smoothstone = new ArrayList<Block>();
	public static ArrayList<Block> waterLeaves = new ArrayList<Block>();
	public static ArrayList<Block> sandGravel = new ArrayList<Block>();
	public static ArrayList<Block> rocks = new ArrayList<Block>();
	public static ArrayList<Block> allEating = new ArrayList<Block>();
	public static ArrayList<Block> forestEating = new ArrayList<Block>();

	public static boolean isAllEatingBlacklisted(Block block) {
		return allEating.indexOf(block) != -1;
	}

	public static boolean isForestEatingWhitelisted(Block block) {
		return forestEating.indexOf(block) != -1;
	}

	public static boolean isDirtWhitelisted(Block block) {
		return dirt.indexOf(block) != -1;
	}

	public static boolean isSmoothWhitelisted(Block block) {
		return smoothstone.indexOf(block) != -1;
	}

	public static boolean isWaterLeavesWhitelisted(Block block) {
		return waterLeaves.indexOf(block) != -1;
	}

	public static boolean isSandGravelWhitelisted(Block block) {
		return sandGravel.indexOf(block) != -1;
	}

	public static boolean isRocksWhitelisted(Block block) {
		return rocks.indexOf(block) != -1;
	}
}
