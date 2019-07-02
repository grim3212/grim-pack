package com.grim3212.mc.pack.industry.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.industry.config.IndustryConfig;

import net.minecraft.util.JSONUtils;
import net.minecraftforge.common.crafting.IConditionSerializer;

public class IndustryConditionFactory implements IConditionSerializer {

	@Override
	public BooleanSupplier parse(JsonObject json) {
		String value = JSONUtils.getString(json, "subpart", "");

		if (CoreConfig.useIndustry.get()) {
			switch (value) {
			case "bridges":
				return () -> IndustryConfig.subpartBridges.get();
			case "chunkloader":
				return () -> IndustryConfig.subpartConveyor.get();
			case "conveyor":
				return () -> IndustryConfig.subpartConveyor.get();
			case "decoration":
				return () -> IndustryConfig.subpartDecoration.get();
			case "doors":
				return () -> IndustryConfig.subpartDoors.get();
			case "elemental":
				return () -> IndustryConfig.subpartElementalBlocks.get();
			case "extruder":
				return () -> IndustryConfig.subpartExtruder.get();
			case "icemaker":
				return () -> IndustryConfig.subpartIceMaker.get();
			case "fans":
				return () -> IndustryConfig.subpartFans.get();
			case "gates":
				return () -> IndustryConfig.subpartGates.get();
			case "gravity":
				return () -> IndustryConfig.subpartGravity.get();
			case "hlights":
				return () -> IndustryConfig.subpartHLights.get();
			case "machines":
				return () -> IndustryConfig.subpartMachines.get();
			case "metalworks":
				return () -> IndustryConfig.subpartMetalWorks.get();
			case "nuclear":
				return () -> IndustryConfig.subpartNuclear.get();
			case "rways":
				return () -> IndustryConfig.subpartRWays.get();
			case "sensors":
				return () -> IndustryConfig.subpartSensors.get();
			case "shapedcharges":
				return () -> IndustryConfig.subpartShapedCharges.get();
			case "spikes":
				return () -> IndustryConfig.subpartSpikes.get();
			case "steel":
				return () -> IndustryConfig.subpartSteel.get();
			case "storage":
				return () -> IndustryConfig.subpartStorage.get();
			case "torches":
				return () -> IndustryConfig.subpartTorches.get();
			case "workbench":
				return () -> IndustryConfig.subpartWorkbenchUpgrades.get();
			default:
				throw new JsonParseException("SubPart '" + value + "' is either misspelled or doesn't exist!");
			}
		} // Industry is not even loaded
		return () -> false;
	}

}
