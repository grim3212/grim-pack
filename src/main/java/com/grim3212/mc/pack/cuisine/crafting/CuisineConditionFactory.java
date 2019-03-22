package com.grim3212.mc.pack.cuisine.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionSerializer;

public class CuisineConditionFactory implements IConditionSerializer {

	@Override
	public BooleanSupplier parse(JsonObject json) {
		String value = JsonUtils.getString(json, "subpart", "");

		if (CoreConfig.useCuisine.get()) {
			switch (value) {
			case "chocolate":
				return () -> CuisineConfig.subpartChocolate.get();
			case "dairy":
				return () -> CuisineConfig.subpartDairy.get();
			case "dragonfruit":
				return () -> CuisineConfig.subpartDragonFruit.get();
			case "health":
				return () -> CuisineConfig.subpartHealth.get();
			case "pie":
				return () -> CuisineConfig.subpartPie.get();
			case "soda":
				return () -> CuisineConfig.subpartSoda.get();
			default:
				throw new JsonParseException("SubPart '" + value + "' is either misspelled or doesn't exist!");
			}
		}
		// Cuisine is not even loaded
		return () -> false;
	}

}
