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
			return () -> CuisineConfig.subpartChocolate == true;
		case "dairy":
			return () -> CuisineConfig.subpartDairy == true;
		case "dragonfruit":
			return () -> CuisineConfig.subpartDragonFruit == true;
		case "health":
			return () -> CuisineConfig.subpartHealth == true;
		case "pie":
			return () -> CuisineConfig.subpartPie == true;
		case "soda":
			return () -> CuisineConfig.subpartSoda == true;
		default:
			return () -> true;
		}
	}

}
