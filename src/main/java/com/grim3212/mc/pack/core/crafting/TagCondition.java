package com.grim3212.mc.pack.core.crafting;

import org.apache.logging.log4j.util.Strings;

import com.google.gson.JsonObject;
import com.grim3212.mc.pack.GrimPack;

import net.minecraft.tags.ItemTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class TagCondition implements ICondition {

	private static final ResourceLocation NAME = new ResourceLocation(GrimPack.modID, "tag_exists");
	private final String tag;

	public TagCondition(String tag) {
		this.tag = tag;
	}

	@Override
	public ResourceLocation getID() {
		return NAME;
	}

	@Override
	public boolean test() {
		if (!Strings.isEmpty(tag)) {
			return !ItemTags.getCollection().get(new ResourceLocation(tag)).getAllElements().isEmpty();
		}

		return false;
	}

	public static class Serializer implements IConditionSerializer<TagCondition> {
		public static final Serializer INSTANCE = new Serializer();

		@Override
		public void write(JsonObject json, TagCondition value) {
			json.addProperty("part", value.tag);
		}

		@Override
		public TagCondition read(JsonObject json) {
			return new TagCondition(JSONUtils.getString(json, "tagName"));
		}

		@Override
		public ResourceLocation getID() {
			return TagCondition.NAME;
		}
	}
}
