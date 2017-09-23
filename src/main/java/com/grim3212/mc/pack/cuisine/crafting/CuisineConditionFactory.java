package com.grim3212.mc.pack.cuisine.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class CuisineConditionFactory implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String value = JsonUtils.getString(json, "subpart", "");

		if (CoreConfig.useCuisine) {
			switch (value) {
			case "chocolate":
				return () -> CuisineConfig.subpartChocolate;
			case "dairy":
				return () -> CuisineConfig.subpartDairy;
			case "dragonfruit":
				return () -> CuisineConfig.subpartDragonFruit;
			case "health":
				return () -> CuisineConfig.subpartHealth;
			case "pie":
				return () -> CuisineConfig.subpartPie;
			case "soda":
				return () -> CuisineConfig.subpartSoda;
			default:
				throw new JsonParseException("SubPart '" + value + "' is either misspelled or doesn't exist!");
			}
		}
		// Cuisine is not even loaded
		return () -> false;
	}

}
