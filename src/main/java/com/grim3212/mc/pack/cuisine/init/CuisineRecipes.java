package com.grim3212.mc.pack.cuisine.init;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;

import net.minecraft.util.ResourceLocation;

public class CuisineRecipes {

	public static ResourceLocation cocoaRecipe;
	public static List<ResourceLocation> sodas;
	public static List<ResourceLocation> carbon;
	public static List<ResourceLocation> health;
	public static List<ResourceLocation> food;
	public static List<ResourceLocation> candy;
	public static List<ResourceLocation> eggs;
	public static List<ResourceLocation> sandwiches;
	public static List<ResourceLocation> pie;
	public static List<ResourceLocation> extra;
	public static List<ResourceLocation> cheeseRecipe;
	public static List<ResourceLocation> choc;

	public static void initRecipes() {
		if (CuisineConfig.subpartSoda) {
			carbon = ImmutableList.of(RecipeHelper.createPath("soda_1"), RecipeHelper.createPath("soda_3"), RecipeHelper.createPath("soda_11"));
			sodas = ImmutableList.of(RecipeHelper.createPath("soda_0"), RecipeHelper.createPath("soda_2"), RecipeHelper.createPath("soda_4"), RecipeHelper.createPath("soda_5"), RecipeHelper.createPath("soda_6"), RecipeHelper.createPath("soda_7"), RecipeHelper.createPath("soda_8"), RecipeHelper.createPath("soda_9"), RecipeHelper.createPath("soda_10"), RecipeHelper.createPath("soda_12"));
		}

		if (CuisineConfig.subpartHealth) {
			health = ImmutableList.of(RecipeHelper.createPath("bandage"), RecipeHelper.createPath("healthpack"), RecipeHelper.createPath("healthpack_super"));
			food = ImmutableList.of(RecipeHelper.createPath("sweets"), RecipeHelper.createPath("powered_sugar"), RecipeHelper.createPath("powered_sweets"));
		}

		if (CuisineConfig.subpartDairy) {
			cheeseRecipe = ImmutableList.of(RecipeHelper.createPath("cheese"), RecipeHelper.createPath("cheese_block"));
			sandwiches = ImmutableList.of(RecipeHelper.createPath("knife"), RecipeHelper.createPath("bread_slice"), RecipeHelper.createPath("cheese_burger"), RecipeHelper.createPath("hot_cheese"));
			eggs = ImmutableList.of(RecipeHelper.createPath("mixer"), RecipeHelper.createPath("eggs_unmixed"), RecipeHelper.createPath("eggs_mixed"));
		}

		if (CuisineConfig.subpartChocolate) {
			cocoaRecipe = RecipeHelper.createPath("dye_3");
			choc = ImmutableList.of(RecipeHelper.createPath("chocolate_bar"), RecipeHelper.createPath("chocolate_block"));
			candy = ImmutableList.of(RecipeHelper.createPath("wrapper"), RecipeHelper.createPath("chocolate_bar_wrapped"));
		}

		if (CuisineConfig.subpartPie) {
			extra = ImmutableList.of(RecipeHelper.createPath("pan"), RecipeHelper.createPath("dough"), RecipeHelper.createPath("knife"), RecipeHelper.createPath("pumpkin_slice"));
			pie = ImmutableList.copyOf(RecipeHelper.getAllPaths("raw_"));
		}
	}

}
