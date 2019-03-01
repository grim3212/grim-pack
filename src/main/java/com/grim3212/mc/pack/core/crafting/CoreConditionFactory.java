package com.grim3212.mc.pack.core.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.grim3212.mc.pack.core.config.CoreConfig;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionSerializer;

public class CoreConditionFactory implements IConditionSerializer {

	@Override
	public BooleanSupplier parse(JsonObject json) {
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
			throw new JsonParseException("SubPart '" + value + "' is either misspelled or doesn't exist!");
		}
	}

}
