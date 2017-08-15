package com.grim3212.mc.pack.industry.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.grim3212.mc.pack.industry.config.IndustryConfig;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class IndustryConditionFactory implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String value = JsonUtils.getString(json, "subpart", "");

		switch (value) {
		case "conveyor":
			return () -> IndustryConfig.subpartConveyor;
		case "decoration":
			return () -> IndustryConfig.subpartDecoration;
		case "doors":
			return () -> IndustryConfig.subpartDoors;
		case "elemental":
			return () -> IndustryConfig.subpartElementalBlocks;
		case "extruder":
			return () -> IndustryConfig.subpartExtruder;
		case "icemaker":
			return () -> IndustryConfig.subpartIceMaker;
		case "ironsticks":
			return () -> IndustryConfig.subpartCommon;
		case "fans":
			return () -> IndustryConfig.subpartFans;
		case "gates":
			return () -> IndustryConfig.subpartGates;
		case "gravity":
			return () -> IndustryConfig.subpartGravity;
		case "hlights":
			return () -> IndustryConfig.subpartHLights;
		case "machines":
			return () -> IndustryConfig.subpartMachines;
		case "metalworks":
			return () -> IndustryConfig.subpartMetalWorks;
		case "nuclear":
			return () -> IndustryConfig.subpartNuclear;
		case "rways":
			return () -> IndustryConfig.subpartRWays;
		case "sensors":
			return () -> IndustryConfig.subpartSensors;
		case "spikes":
			return () -> IndustryConfig.subpartSpikes;
		case "steel":
			return () -> IndustryConfig.subpartSteel;
		case "storage":
			return () -> IndustryConfig.subpartStorage;
		case "workbench":
			return () -> IndustryConfig.subpartWorkbenchUpgrades;
		default:
			return () -> false;
		}
	}

}
