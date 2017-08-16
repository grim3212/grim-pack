package com.grim3212.mc.pack.core.crafting;

import java.util.function.BooleanSupplier;

import org.apache.logging.log4j.util.Strings;

import com.google.gson.JsonObject;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictConditionFactory implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String value = JsonUtils.getString(json, "oreName", "");

		if (!Strings.isEmpty(value)) {
			return () -> OreDictionary.doesOreNameExist(value);
		}

		return () -> false;
	}

}
