package com.grim3212.mc.pack.core.crafting;

import java.util.function.BooleanSupplier;

import org.apache.logging.log4j.util.Strings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.tags.ItemTags;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IConditionSerializer;

public class TagConditionFactory implements IConditionSerializer {

	@Override
	public BooleanSupplier parse(JsonObject json) {
		String value = JsonUtils.getString(json, "tagName", "");

		if (!Strings.isEmpty(value)) {
			return () -> !ItemTags.getCollection().get(new ResourceLocation(value)).getAllElements().isEmpty();
		} else {
			throw new JsonParseException("'tagName' cannot be empty!");
		}
	}
}
