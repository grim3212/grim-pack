package com.grim3212.mc.pack.world.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.grim3212.mc.pack.world.config.WorldConfig;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class WorldConditionFactory implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String value = JsonUtils.getString(json, "subpart", "");

		switch (value) {
		case "8bitmobs":
			return () -> WorldConfig.subpart8BitMobs;
		case "glowstoneseeds":
			return () -> WorldConfig.subpartGlowstoneSeeds;
		case "fungus":
			return () -> WorldConfig.subpartFungus;
		case "gunpowderreeds":
			return () -> WorldConfig.subpartGunpowderReeds;
		case "corruption":
			return () -> WorldConfig.subpartCorruption;
		case "icepixie":
			return () -> WorldConfig.subpartIcePixie;
		case "morepeople":
			return () -> WorldConfig.subpartMorePeople;
		case "randomite":
			return () -> WorldConfig.subpartRandomite;
		case "treasuremob":
			return () -> WorldConfig.subpartTreasureMobs;
		case "desertwells":
			return () -> WorldConfig.subpartDesertWells;
		case "flatbedrock":
			return () -> WorldConfig.subpartFlatBedrock;
		case "floatingislands":
			return () -> WorldConfig.subpartFloatingIslands;
		case "floatingworldtype":
			return () -> WorldConfig.subpartFloatingIslandWorldType;
		case "worldgenexpanded":
			return () -> WorldConfig.subpartWorldGenExpanded;
		default:
			throw new JsonParseException("Part '" + value + "' is either misspelled or doesn't exist!");
		}
	}
}