package com.grim3212.mc.pack.tools.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.grim3212.mc.pack.tools.config.ToolsConfig;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class ToolsConditionFactory implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String value = JsonUtils.getString(json, "subpart", "");

		switch (value) {
		case "backpacks":
			return () -> ToolsConfig.subpartBackpacks;
		case "blackdiamond":
			return () -> ToolsConfig.subpartBlackDiamond;
		case "boomerangs":
			return () -> ToolsConfig.subpartBoomerangs;
		case "buckets":
			return () -> ToolsConfig.subpartBuckets;
		case "chisel":
			return () -> ToolsConfig.subpartChisel;
		case "extinguisher":
			return () -> ToolsConfig.subpartExtinguisher;
		case "grenadelauncher":
			return () -> ToolsConfig.subpartGrenadeLauncher;
		case "hammers":
			return () -> ToolsConfig.subpartHammers;
		case "knives":
			return () -> ToolsConfig.subpartKnives;
		case "machetes":
			return () -> ToolsConfig.subpartMachetes;
		case "masks":
			return () -> ToolsConfig.subpartMasks;
		case "multitools":
			return () -> ToolsConfig.subpartMultiTools;
		case "pokeball":
			return () -> ToolsConfig.subpartPokeball;
		case "portableworkbench":
			return () -> ToolsConfig.subpartPortableWorkbench;
		case "powerstaff":
			return () -> ToolsConfig.subpartPowerstaff;
		case "rayguns":
			return () -> ToolsConfig.subpartRayGuns;
		case "slingshots":
			return () -> ToolsConfig.subpartSlingshots;
		case "spears":
			return () -> ToolsConfig.subpartSpears;
		case "ultimatefist":
			return () -> ToolsConfig.subpartUltimateFist;
		case "wands":
			return () -> ToolsConfig.subpartWands;
		default:
			throw new JsonParseException("Part '" + value + "' is either misspelled or doesn't exist!");
		}
	}

}
