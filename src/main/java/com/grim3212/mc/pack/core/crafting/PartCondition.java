package com.grim3212.mc.pack.core.crafting;

import com.google.gson.JsonObject;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.config.CoreConfig;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class PartCondition implements ICondition {

	private static final ResourceLocation NAME = new ResourceLocation(GrimPack.modID, "part_enabled");
	private final String part;

	public PartCondition(String part) {
		this.part = part;
	}

	@Override
	public ResourceLocation getID() {
		return NAME;
	}

	@Override
	public boolean test() {
		switch (part) {
		case "cuisine":
			return CoreConfig.useCuisine.get();
		case "decor":
			return CoreConfig.useDecor.get();
		case "industry":
			return CoreConfig.useIndustry.get();
		case "tools":
			return CoreConfig.useTools.get();
		case "util":
			return CoreConfig.useTools.get();
		case "world":
			return CoreConfig.useWorld.get();
		default:
			return false;
		}
	}

	public static class Serializer implements IConditionSerializer<PartCondition> {
		public static final Serializer INSTANCE = new Serializer();

		@Override
		public void write(JsonObject json, PartCondition value) {
			json.addProperty("part", value.part);
		}

		@Override
		public PartCondition read(JsonObject json) {
			return new PartCondition(JSONUtils.getString(json, "part"));
		}

		@Override
		public ResourceLocation getID() {
			return PartCondition.NAME;
		}
	}
}
