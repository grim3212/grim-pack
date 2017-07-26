package com.grim3212.mc.pack.decor.init;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.util.RecipeHelper;

import net.minecraft.util.ResourceLocation;

public class DecorRecipes {

	public static ResourceLocation mossy;
	public static List<ResourceLocation> stone = Lists.newArrayList();
	public static List<ResourceLocation> chains = Lists.newArrayList();
	public static List<ResourceLocation> crafts = Lists.newArrayList();
	public static List<ResourceLocation> clocks = Lists.newArrayList();
	public static List<ResourceLocation> colorizers = Lists.newArrayList();
	public static List<ResourceLocation> lights = Lists.newArrayList();
	public static List<ResourceLocation> frames = Lists.newArrayList();

	public static void initRecipes() {
		mossy = RecipeHelper.createPath("decor/mossy_cobblestone");
		clocks.add(RecipeHelper.createPath("decor/clock"));
		clocks.addAll(RecipeHelper.getAllPaths("decor/wall_clock"));
		lights.addAll(Arrays.asList(RecipeHelper.createPath("decor/light_bulb"), RecipeHelper.createPath("decor/glass")));
		crafts.addAll(Arrays.asList(RecipeHelper.createPath("decor/craft_bone"), RecipeHelper.createPath("decor/unfired_pot"), RecipeHelper.createPath("decor/unfired_craft")));
		stone.addAll(Arrays.asList(RecipeHelper.createPath("decor/fancy_stone"), RecipeHelper.createPath("decor/stone_0")));
		chains.addAll(Arrays.asList(RecipeHelper.createPath("decor/cage"), RecipeHelper.createPath("decor/chain")));
		colorizers.addAll(Arrays.asList(RecipeHelper.createPath("decor/hardened_wood"), RecipeHelper.createPath("decor/colorizer"), RecipeHelper.createPath("decor/colorizer_light")));
		frames.addAll(RecipeHelper.getAllPaths("decor/frame"));
	}

}
