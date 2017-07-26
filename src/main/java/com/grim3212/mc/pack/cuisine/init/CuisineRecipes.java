package com.grim3212.mc.pack.cuisine.init;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.util.RecipeHelper;

import net.minecraft.util.ResourceLocation;

public class CuisineRecipes {

	public static ResourceLocation cocoaRecipe;
	public static List<ResourceLocation> sodas = Lists.newArrayList();
	public static List<ResourceLocation> carbon = Lists.newArrayList();
	public static List<ResourceLocation> health = Lists.newArrayList();
	public static List<ResourceLocation> food = Lists.newArrayList();
	public static List<ResourceLocation> candy = Lists.newArrayList();
	public static List<ResourceLocation> eggs = Lists.newArrayList();
	public static List<ResourceLocation> sandwiches = Lists.newArrayList();
	public static List<ResourceLocation> utensils = Lists.newArrayList();
	public static List<ResourceLocation> pie = Lists.newArrayList();
	public static List<ResourceLocation> extra = Lists.newArrayList();
	public static List<ResourceLocation> cheeseRecipe = Lists.newArrayList();
	public static List<ResourceLocation> choc = Lists.newArrayList();

	public static void initRecipes() {
		carbon.addAll(Arrays.asList(RecipeHelper.createPath("cuisine/soda_1"), RecipeHelper.createPath("cuisine/soda_3"), RecipeHelper.createPath("cuisine/soda_11")));
		sodas.addAll(Arrays.asList(RecipeHelper.createPath("cuisine/soda_0"), RecipeHelper.createPath("cuisine/soda_2"), RecipeHelper.createPath("cuisine/soda_4"), RecipeHelper.createPath("cuisine/soda_5"), RecipeHelper.createPath("cuisine/soda_6"), RecipeHelper.createPath("cuisine/soda_7"), RecipeHelper.createPath("cuisine/soda_8"), RecipeHelper.createPath("cuisine/soda_9"), RecipeHelper.createPath("cuisine/soda_10"), RecipeHelper.createPath("cuisine/soda_12")));
		health.addAll(Arrays.asList(RecipeHelper.createPath("cuisine/bandage"), RecipeHelper.createPath("cuisine/healthpack"), RecipeHelper.createPath("cuisine/healthpack_super")));
		food.addAll(Arrays.asList(RecipeHelper.createPath("cuisine/sweets"), RecipeHelper.createPath("cuisine/powered_sugar"), RecipeHelper.createPath("cuisine/powered_sweets")));
		cheeseRecipe.addAll(Arrays.asList(RecipeHelper.createPath("cuisine/cheese"), RecipeHelper.createPath("cuisine/cheese_block")));
		cocoaRecipe = RecipeHelper.createPath("cuisine/dye_3");
		choc.addAll(Arrays.asList(RecipeHelper.createPath("cuisine/chocolate_bar"), RecipeHelper.createPath("cuisine/chocolate_block")));
		candy.addAll(Arrays.asList(RecipeHelper.createPath("cuisine/wrapper"), RecipeHelper.createPath("cuisine/chocolate_bar_wrapped")));
		utensils.addAll(Arrays.asList(RecipeHelper.createPath("cuisine/knife"), RecipeHelper.createPath("cuisine/mixer"), RecipeHelper.createPath("cuisine/pan")));
		extra.addAll(Arrays.asList(RecipeHelper.createPath("cuisine/dough"), RecipeHelper.createPath("cuisine/pumpkin_slice")));
		sandwiches.addAll(Arrays.asList(RecipeHelper.createPath("cuisine/bread_slice"), RecipeHelper.createPath("cuisine/cheese_burger"), RecipeHelper.createPath("cuisine/hot_cheese")));
		eggs.addAll(Arrays.asList(RecipeHelper.createPath("cuisine/eggs_unmixed"), RecipeHelper.createPath("cuisine/eggs_mixed")));
		pie.addAll(RecipeHelper.getAllPaths("cuisine/raw_"));
	}

}
