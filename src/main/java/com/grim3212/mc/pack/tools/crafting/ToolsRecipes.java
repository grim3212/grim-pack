package com.grim3212.mc.pack.tools.crafting;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.util.RecipeHelper;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ToolsRecipes {

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		IForgeRegistry<IRecipe> r = evt.getRegistry();

		r.register(new BackpackRecipe().setRegistryName(new ResourceLocation(GrimPack.modID, "backpack_recipe")));
		r.register(new PelletBagRecipe().setRegistryName(new ResourceLocation(GrimPack.modID, "pellet_bag_recipe")));
	}

	public static ResourceLocation extinguisherRecipe;
	public static ResourceLocation extinguisherRefillRecipe;
	public static ResourceLocation emptyRecipe;
	public static List<ResourceLocation> black;
	public static List<ResourceLocation> blackTools;
	public static List<ResourceLocation> blackArmor;
	public static List<ResourceLocation> chisels;
	public static List<ResourceLocation> hammers;
	public static List<ResourceLocation> machetes;
	public static List<ResourceLocation> regular;
	public static List<ResourceLocation> reinforced;
	public static List<ResourceLocation> tools;
	public static List<ResourceLocation> advCanisters;
	public static List<ResourceLocation> basicCanisters;
	public static List<ResourceLocation> pellets;
	public static List<ResourceLocation> basics;
	public static List<ResourceLocation> specials;
	public static List<ResourceLocation> mobs;

	public static void initRecipes() {
		extinguisherRecipe = RecipeHelper.createPath("extinguisher");
		extinguisherRefillRecipe = RecipeHelper.createPath("extinguisher_alt");
		emptyRecipe = RecipeHelper.createPath("mask_0");

		black = ImmutableList.of(RecipeHelper.createPath("black_diamond_block"), RecipeHelper.createPath("black_diamond"));
		chisels = ImmutableList.of(RecipeHelper.createPath("iron_chisel"), RecipeHelper.createPath("diamond_chisel"));
		blackTools = ImmutableList.of(RecipeHelper.createPath("black_diamond_pickaxe"), RecipeHelper.createPath("black_diamond_sword"), RecipeHelper.createPath("black_diamond_axe"), RecipeHelper.createPath("black_diamond_hoe"), RecipeHelper.createPath("black_diamond_shovel"));
		blackArmor = ImmutableList.of(RecipeHelper.createPath("black_diamond_helmet"), RecipeHelper.createPath("black_diamond_chestplate"), RecipeHelper.createPath("black_diamond_leggings"), RecipeHelper.createPath("black_diamond_boots"));
		hammers = ImmutableList.copyOf(RecipeHelper.getAllPathsEnd("_hammer"));
		machetes = ImmutableList.of(RecipeHelper.createPath("machete_wood"), RecipeHelper.createPath("machete_stone"), RecipeHelper.createPath("machete_gold"), RecipeHelper.createPath("machete_iron"), RecipeHelper.createPath("machete_diamond"));
		regular = ImmutableList.of(RecipeHelper.createPath("building_wand"), RecipeHelper.createPath("breaking_wand"), RecipeHelper.createPath("mining_wand"));
		reinforced = ImmutableList.of(RecipeHelper.createPath("reinforced_building_wand"), RecipeHelper.createPath("reinforced_breaking_wand"), RecipeHelper.createPath("reinforced_mining_wand"));
		basicCanisters = ImmutableList.of(RecipeHelper.createPath("empty_energy_canister"), RecipeHelper.createPath("energy_canister"));
		advCanisters = ImmutableList.of(RecipeHelper.createPath("advanced_empty_energy_canister"), RecipeHelper.createPath("advanced_energy_canister"));
		pellets = ImmutableList.copyOf(RecipeHelper.getAllPaths("sling_pellet_"));
		basics = ImmutableList.of(RecipeHelper.createPath("spear"), RecipeHelper.createPath("spear_alt"), RecipeHelper.createPath("iron_spear"), RecipeHelper.createPath("diamond_spear"));
		specials = ImmutableList.of(RecipeHelper.createPath("explosive_spear"), RecipeHelper.createPath("fire_spear"), RecipeHelper.createPath("slime_spear"), RecipeHelper.createPath("light_spear"), RecipeHelper.createPath("lightning_spear"));

		if (CoreConfig.useIndustry) {
			tools = ImmutableList.of(RecipeHelper.createPath("wooden_multi_tool"), RecipeHelper.createPath("stone_multi_tool"), RecipeHelper.createPath("golden_multi_tool"), RecipeHelper.createPath("iron_multi_tool"), RecipeHelper.createPath("diamond_multi_tool"), RecipeHelper.createPath("obsidian_multi_tool"), RecipeHelper.createPath("black_diamond_multi_tool"), RecipeHelper.createPath("steel_multi_tool"));
		} else {
			tools = ImmutableList.of(RecipeHelper.createPath("wooden_multi_tool"), RecipeHelper.createPath("stone_multi_tool"), RecipeHelper.createPath("golden_multi_tool"), RecipeHelper.createPath("iron_multi_tool"), RecipeHelper.createPath("diamond_multi_tool"), RecipeHelper.createPath("obsidian_multi_tool"), RecipeHelper.createPath("black_diamond_multi_tool"));
		}

		ImmutableList.Builder<ResourceLocation> maskBuilder = ImmutableList.builder();
		for (int i = 1; i < 20; i++)
			maskBuilder.add(RecipeHelper.createPath("mask_" + i));
		mobs = maskBuilder.build();

	}
}
