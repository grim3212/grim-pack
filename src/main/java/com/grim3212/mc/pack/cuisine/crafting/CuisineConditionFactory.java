package com.grim3212.mc.pack.cuisine.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class CuisineConditionFactory implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String value = JsonUtils.getString(json, "subpart", "");

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
			return () -> true;
		}
	}

}
