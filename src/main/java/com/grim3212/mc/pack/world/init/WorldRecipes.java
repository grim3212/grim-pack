package com.grim3212.mc.pack.world.init;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.config.WorldConfig;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WorldRecipes {

	public static List<ResourceLocation> greed;
	public static List<ResourceLocation> greenFungus;
	public static List<ResourceLocation> coloredFungus;
	public static List<ResourceLocation> buildingFungus;
	public static List<ResourceLocation> orebuildingFungus;
	public static List<ResourceLocation> layerbuildingFungus;
	public static List<ResourceLocation> mazeFungus;
	public static List<ResourceLocation> acidFungus;
	public static List<ResourceLocation> breakingFungus;
	public static List<ResourceLocation> vertFungus;
	public static List<ItemStack> runes;

	public static void initRecipes() {
		if (WorldConfig.subpartGunpowderReeds)
			greed = ImmutableList.of(RecipeHelper.createPath("gunpowder"), RecipeHelper.createPath("gunpowder_reed_item"));

		if (WorldConfig.subpartFungus) {
			greenFungus = ImmutableList.of(RecipeHelper.createPath("fungus_growing_0"), RecipeHelper.createPath("fungus_growing_8"));

			coloredFungus = Lists.newArrayList();
			for (int i = 1; i < 8; i++) {
				coloredFungus.add(RecipeHelper.createPath("fungus_growing_" + i));
				coloredFungus.add(RecipeHelper.createPath("fungus_growing_" + (i + 8)));
			}

			buildingFungus = Lists.newArrayList();
			buildingFungus.addAll(RecipeHelper.getAllPaths("fungus_building_"));
			buildingFungus.addAll(Arrays.asList(RecipeHelper.createPath("fungus_ore_building_0"), RecipeHelper.createPath("fungus_killing_7"), RecipeHelper.createPath("fungus_killing_15")));

			layerbuildingFungus = Lists.newArrayList();
			for (int i = 0; i < 16; i++)
				layerbuildingFungus.add(RecipeHelper.createPath("fungus_layer_building_" + i));

			mazeFungus = ImmutableList.of(RecipeHelper.createPath("fungus_maze_0"));
			acidFungus = ImmutableList.of(RecipeHelper.createPath("fungus_killing_0"), RecipeHelper.createPath("fungus_killing_1"), RecipeHelper.createPath("fungus_killing_1_alt"));

			breakingFungus = Lists.newArrayList();
			for (int i = 2; i < 7; i++)
				breakingFungus.addAll(RecipeHelper.getAllPaths("fungus_killing_" + i));
			breakingFungus.addAll(RecipeHelper.getAllPaths("fungus_killing_14"));

			vertFungus = Lists.newArrayList();
			for (int i = 8; i < 13; i++)
				vertFungus.add(RecipeHelper.createPath("fungus_killing_" + i));
			vertFungus.addAll(RecipeHelper.getAllPaths("fungus_killing_13"));

			runes = Lists.newArrayList();
			for (int i = 0; i < 16; i++)
				runes.add(new ItemStack(WorldBlocks.rune, 1, i));
		}
	}

}
