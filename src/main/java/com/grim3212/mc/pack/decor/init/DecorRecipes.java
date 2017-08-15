package com.grim3212.mc.pack.decor.init;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.util.ResourceLocation;

public class DecorRecipes {

	public static ResourceLocation mossy;
	public static List<ResourceLocation> stone;
	public static List<ResourceLocation> chains;
	public static List<ResourceLocation> crafts;
	public static List<ResourceLocation> clocks;
	public static List<ResourceLocation> colorizers;
	public static List<ResourceLocation> lights;
	public static List<ResourceLocation> frames;

	public static void initRecipes() {
		if (DecorConfig.subpartDecorations) {
			mossy = RecipeHelper.createPath("mossy_cobblestone");
			stone = ImmutableList.of(RecipeHelper.createPath("fancy_stone"), RecipeHelper.createPath("stone_0"));
			crafts = ImmutableList.of(RecipeHelper.createPath("craft_bone"), RecipeHelper.createPath("unfired_pot"), RecipeHelper.createPath("unfired_craft"));
		}

		if (DecorConfig.subpartWallClock)
			clocks = ImmutableList.of(RecipeHelper.createPath("clock"), RecipeHelper.createPath("wall_clock"), RecipeHelper.createPath("wall_clock_alt"));

		if (DecorConfig.subpartLightBulbs)
			lights = ImmutableList.of(RecipeHelper.createPath("light_bulb"), RecipeHelper.createPath("glass"));

		if (DecorConfig.subpartCages)
			chains = ImmutableList.of(RecipeHelper.createPath("cage"), RecipeHelper.createPath("chain"));

		if (DecorConfig.subpartColorizer)
			colorizers = ImmutableList.of(RecipeHelper.createPath("hardened_wood"), RecipeHelper.createPath("colorizer"), RecipeHelper.createPath("colorizer_light"));

		if (DecorConfig.subpartFrames)
			frames = ImmutableList.copyOf(RecipeHelper.getAllPaths("frame"));
	}

}
