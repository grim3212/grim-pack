package com.grim3212.mc.pack.core.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.grim3212.mc.pack.core.config.CoreConfig;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class PartConditionFactory implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String value = JsonUtils.getString(json, "part", "core");

		switch (value) {
		case "cuisine":
			return () -> CoreConfig.useCuisine == true;
		case "decor":
			return () -> CoreConfig.useDecor == true;
		case "industry":
			return () -> CoreConfig.useIndustry == true;
		case "tools":
			return () -> CoreConfig.useTools == true;
		case "util":
			return () -> CoreConfig.useTools == true;
		case "world":
			return () -> CoreConfig.useWorld == true;
		default:
			throw new JsonParseException("Part '" + value + "' is either misspelled or doesn't exist!");
		}
	}

}
