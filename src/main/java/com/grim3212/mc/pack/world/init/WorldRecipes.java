package com.grim3212.mc.pack.world.init;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.util.RecipeHelper;

import net.minecraft.util.ResourceLocation;

public class WorldRecipes {

	public static List<ResourceLocation> greed = Lists.newArrayList();
	public static List<ResourceLocation> greenFungus = Lists.newArrayList();
	public static List<ResourceLocation> coloredFungus = Lists.newArrayList();
	public static List<ResourceLocation> buildingFungus = Lists.newArrayList();
	public static List<ResourceLocation> orebuildingFungus = Lists.newArrayList();
	public static List<ResourceLocation> layerbuildingFungus = Lists.newArrayList();
	public static List<ResourceLocation> mazeFungus = Lists.newArrayList();
	public static List<ResourceLocation> acidFungus = Lists.newArrayList();
	public static List<ResourceLocation> breakingFungus = Lists.newArrayList();
	public static List<ResourceLocation> vertFungus = Lists.newArrayList();

	public static void initRecipes() {
		greed.addAll(Arrays.asList(RecipeHelper.createPath("world/gunpowder"), RecipeHelper.createPath("world/gunpowder_reed_item")));
		greenFungus.addAll(Arrays.asList(RecipeHelper.createPath("world/fungus_growing_0"), RecipeHelper.createPath("world/fungus_growing_8")));

		for (int i = 1; i < 8; i++) {
			coloredFungus.add(RecipeHelper.createPath("world/fungus_growing_" + i));
			coloredFungus.add(RecipeHelper.createPath("world/fungus_growing_" + (i + 8)));
		}

		buildingFungus.addAll(RecipeHelper.getAllPaths("world/fungus_building_"));
		buildingFungus.addAll(Arrays.asList(RecipeHelper.createPath("world/fungus_ore_building_0"), RecipeHelper.createPath("world/fungus_killing_7"), RecipeHelper.createPath("world/fungus_killing_15")));

		for (int i = 0; i < 16; i++)
			layerbuildingFungus.add(RecipeHelper.createPath("world/fungus_layer_building_" + i));

		mazeFungus.add(RecipeHelper.createPath("world/fungus_maze_0"));
		acidFungus.addAll(Arrays.asList(RecipeHelper.createPath("world/fungus_killing_0"), RecipeHelper.createPath("world/fungus_killing_1"), RecipeHelper.createPath("world/fungus_killing_1_alt")));

		for (int i = 2; i < 7; i++)
			breakingFungus.addAll(RecipeHelper.getAllPaths("world/fungus_killing_" + i));
		breakingFungus.addAll(RecipeHelper.getAllPaths("world/fungus_killing_14"));

		for (int i = 8; i < 13; i++)
			vertFungus.add(RecipeHelper.createPath("world/fungus_killing_" + i));
		vertFungus.addAll(RecipeHelper.getAllPaths("world/fungus_killing_13"));
	}

}
