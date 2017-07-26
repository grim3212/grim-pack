package com.grim3212.mc.pack.cuisine.init;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.grim3212.mc.pack.core.util.RecipeHelper;

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
	public static List<ResourceLocation> utensils;
	public static List<ResourceLocation> pie;
	public static List<ResourceLocation> extra;
	public static List<ResourceLocation> cheeseRecipe;
	public static List<ResourceLocation> choc;

	public static void initRecipes() {
		carbon = ImmutableList.of(RecipeHelper.createPath("soda_1"), RecipeHelper.createPath("soda_3"), RecipeHelper.createPath("soda_11"));
		sodas = ImmutableList.of(RecipeHelper.createPath("soda_0"), RecipeHelper.createPath("soda_2"), RecipeHelper.createPath("soda_4"), RecipeHelper.createPath("soda_5"), RecipeHelper.createPath("soda_6"), RecipeHelper.createPath("soda_7"), RecipeHelper.createPath("soda_8"), RecipeHelper.createPath("soda_9"), RecipeHelper.createPath("soda_10"), RecipeHelper.createPath("soda_12"));
		health = ImmutableList.of(RecipeHelper.createPath("bandage"), RecipeHelper.createPath("healthpack"), RecipeHelper.createPath("healthpack_super"));
		food = ImmutableList.of(RecipeHelper.createPath("sweets"), RecipeHelper.createPath("powered_sugar"), RecipeHelper.createPath("powered_sweets"));
		cheeseRecipe = ImmutableList.of(RecipeHelper.createPath("cheese"), RecipeHelper.createPath("cheese_block"));
		cocoaRecipe = RecipeHelper.createPath("dye_3");
		choc = ImmutableList.of(RecipeHelper.createPath("chocolate_bar"), RecipeHelper.createPath("chocolate_block"));
		candy = ImmutableList.of(RecipeHelper.createPath("wrapper"), RecipeHelper.createPath("chocolate_bar_wrapped"));
		utensils = ImmutableList.of(RecipeHelper.createPath("knife"), RecipeHelper.createPath("mixer"), RecipeHelper.createPath("pan"));
		extra = ImmutableList.of(RecipeHelper.createPath("dough"), RecipeHelper.createPath("pumpkin_slice"));
		sandwiches = ImmutableList.of(RecipeHelper.createPath("bread_slice"), RecipeHelper.createPath("cheese_burger"), RecipeHelper.createPath("hot_cheese"));
		eggs = ImmutableList.of(RecipeHelper.createPath("eggs_unmixed"), RecipeHelper.createPath("eggs_mixed"));
		pie = ImmutableList.copyOf(RecipeHelper.getAllPaths("raw_"));
	}

}
