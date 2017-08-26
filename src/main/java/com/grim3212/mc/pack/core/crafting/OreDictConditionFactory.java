package com.grim3212.mc.pack.core.crafting;

import java.util.function.BooleanSupplier;

import javax.annotation.Nullable;

import org.apache.logging.log4j.util.Strings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictConditionFactory implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String value = JsonUtils.getString(json, "oreName", "");

		if (!Strings.isEmpty(value)) {
			return () -> checkOres(OreDictionary.getOres(value, false));
		} else {
			throw new JsonParseException("'oreName' cannot be empty!");
		}
	}

	private boolean checkOres(@Nullable NonNullList<ItemStack> ores) {
		if (ores != null && !ores.isEmpty())
			return true;
		return false;
	}
}
