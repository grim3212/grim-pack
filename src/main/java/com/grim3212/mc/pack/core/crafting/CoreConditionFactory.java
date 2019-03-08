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
			return () -> CoreConfig.subpartAluminum.get();
		case "coal":
			return () -> CoreConfig.subpartCoal.get();
		case "graphite":
			return () -> CoreConfig.subpartGraphite.get();
		case "iron":
			return () -> CoreConfig.subpartIron.get();
		case "rubber":
			return () -> CoreConfig.subpartRubber.get();
		case "steel":
			return () -> CoreConfig.subpartSteel.get();
		default:
			throw new JsonParseException("SubPart '" + value + "' is either misspelled or doesn't exist!");
		}
	}

}
