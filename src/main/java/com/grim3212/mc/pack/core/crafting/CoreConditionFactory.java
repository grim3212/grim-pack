package com.grim3212.mc.pack.core.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.grim3212.mc.pack.core.config.CoreConfig;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class CoreConditionFactory implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String value = JsonUtils.getString(json, "subpart", "");

		switch (value) {
		case "aluminum":
			return () -> CoreConfig.subpartAluminum;
		case "coal":
			return () -> CoreConfig.subpartCoal;
		case "graphite":
			return () -> CoreConfig.subpartGraphite;
		case "iron":
			return () -> CoreConfig.subpartIron;
		case "rubber":
			return () -> CoreConfig.subpartRubber;
		case "steel":
			return () -> CoreConfig.subpartSteel;
		default:
			throw new JsonParseException("Part '" + value + "' is either misspelled or doesn't exist!");
		}
	}

}
