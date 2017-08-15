package com.grim3212.mc.pack.industry.init;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.industry.config.IndustryConfig;

import net.minecraft.util.ResourceLocation;

public class IndustryRecipes {

	public static List<ResourceLocation> armor;
	public static List<ResourceLocation> control;
	public static List<ResourceLocation> gates;
	public static List<ResourceLocation> garages;
	public static List<ResourceLocation> paint;
	public static List<ResourceLocation> doors;
	public static List<ResourceLocation> coaliron;
	public static List<ResourceLocation> steeltools;
	public static List<ResourceLocation> alumstuff;
	public static List<ResourceLocation> portableUpgrades;
	public static List<ResourceLocation> fuelstuff;
	public static List<ResourceLocation> sensors;
	public static List<ResourceLocation> workbenches;
	public static List<ResourceLocation> attracting;
	public static List<ResourceLocation> repulsing;
	public static List<ResourceLocation> gravitoring;
	public static List<ResourceLocation> htorches;
	public static List<ResourceLocation> rways;
	public static List<ResourceLocation> decoration;
	public static List<ResourceLocation> others;
	public static List<ResourceLocation> buckladd;
	public static List<ResourceLocation> steelstuff;

	public static void initRecipes() {
		if (IndustryConfig.subpartWorkbenchUpgrades) {
			portableUpgrades = ImmutableList.of(RecipeHelper.createPath("portable_diamond_workbench"), RecipeHelper.createPath("portable_iron_workbench"));
			workbenches = ImmutableList.of(RecipeHelper.createPath("iron_workbench"), RecipeHelper.createPath("diamond_workbench"));
		}

		if (IndustryConfig.subpartMetalWorks) {
			alumstuff = ImmutableList.of(RecipeHelper.createPath("aluminum_can"), RecipeHelper.createPath("aluminum_shaft"));
			buckladd = ImmutableList.of(RecipeHelper.createPath("aluminum_ladder"), RecipeHelper.createPath("bucket"));
		}

		if (IndustryConfig.subpartMachines)
			fuelstuff = ImmutableList.of(RecipeHelper.createPath("super_crude_oil"), RecipeHelper.createPath("fuel"), RecipeHelper.createPath("fuel_tank"));

		if (IndustryConfig.subpartSteel) {
			steelstuff = ImmutableList.of(RecipeHelper.createPath("steel_pipe"), RecipeHelper.createPath("steel_frame"), RecipeHelper.createPath("steel_block"), RecipeHelper.createPath("steel_ingot"), RecipeHelper.createPath("steel_shaft"));
			steeltools = ImmutableList.of(RecipeHelper.createPath("steel_sword"), RecipeHelper.createPath("steel_pickaxe"), RecipeHelper.createPath("steel_shovel"), RecipeHelper.createPath("steel_axe"), RecipeHelper.createPath("steel_hoe"));
			coaliron = ImmutableList.of(RecipeHelper.createPath("coal_dust"), RecipeHelper.createPath("coal_iron_ingot"));
		}

		if (IndustryConfig.subpartDoors)
			doors = ImmutableList.of(RecipeHelper.createPath("door_chain_item"), RecipeHelper.createPath("door_glass_item"), RecipeHelper.createPath("door_steel_item"));

		if (IndustryConfig.subpartGates) {
			gates = ImmutableList.of(RecipeHelper.createPath("gate_grating"), RecipeHelper.createPath("castle_gate"));
			garages = ImmutableList.of(RecipeHelper.createPath("garage_panel"), RecipeHelper.createPath("garage"));
		}

		if (IndustryConfig.subpartNuclear)
			armor = ImmutableList.of(RecipeHelper.createPath("anti_radiation_helmet"), RecipeHelper.createPath("anti_radiation_chest"), RecipeHelper.createPath("anti_radiation_legs"), RecipeHelper.createPath("anti_radiation_boots"));

		if (IndustryConfig.subpartGravity) {
			control = ImmutableList.of(RecipeHelper.createPath("gravity_controller"), RecipeHelper.createPath("low_gravity_controller"));
			attracting = ImmutableList.of(RecipeHelper.createPath("attractor"), RecipeHelper.createPath("direction_attractor"));
			repulsing = ImmutableList.of(RecipeHelper.createPath("repulsor"), RecipeHelper.createPath("direction_repulsor"));
			gravitoring = ImmutableList.of(RecipeHelper.createPath("gravitor"), RecipeHelper.createPath("direction_gravitor"));
		}

		if (IndustryConfig.subpartDecoration) {
			others = ImmutableList.of(RecipeHelper.createPath("chain_fence"), RecipeHelper.createPath("fountain"), RecipeHelper.createPath("camo_plate"));
			paint = ImmutableList.of(RecipeHelper.createPath("paint_roller_white"), RecipeHelper.createPath("paint_roller_red"), RecipeHelper.createPath("paint_roller_green"), RecipeHelper.createPath("paint_roller_blue"));
			decoration = ImmutableList.of(RecipeHelper.createPath("concrete"), RecipeHelper.createPath("horizontal_siding"), RecipeHelper.createPath("vertical_siding"));
		}

		if (IndustryConfig.subpartRWays)
			rways = ImmutableList.of(RecipeHelper.createPath("rway"), RecipeHelper.createPath("rway_light_off"), RecipeHelper.createPath("rway_manhole"));

		if (IndustryConfig.subpartHLights)
			htorches = ImmutableList.of(RecipeHelper.createPath("halogen_torch"), RecipeHelper.createPath("halogen_torch_alt"));

		if (IndustryConfig.subpartSensors)
			sensors = ImmutableList.of(RecipeHelper.createPath("wooden_sensor"), RecipeHelper.createPath("stone_sensor"), RecipeHelper.createPath("iron_sensor"), RecipeHelper.createPath("netherrack_sensor"));
	}
}
