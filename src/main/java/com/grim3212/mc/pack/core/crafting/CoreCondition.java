package com.grim3212.mc.pack.core.crafting;

import com.google.gson.JsonObject;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.config.CoreConfig;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class CoreCondition implements ICondition {

	private static final ResourceLocation NAME = new ResourceLocation(GrimPack.modID, "core_subpart_enabled");
	private final String subpart;

	public CoreCondition(String subpart) {
		this.subpart = subpart;
	}

	@Override
	public ResourceLocation getID() {
		return NAME;
	}

	@Override
	public boolean test() {
		switch (subpart) {
		case "aluminum":
			return CoreConfig.subpartAluminum.get();
		case "coal":
			return CoreConfig.subpartCoal.get();
		case "graphite":
			return CoreConfig.subpartGraphite.get();
		case "iron":
			return CoreConfig.subpartIron.get();
		case "rubber":
			return CoreConfig.subpartRubber.get();
		case "steel":
			return CoreConfig.subpartSteel.get();
		default:
			return false;
		}
	}

	public static class Serializer implements IConditionSerializer<CoreCondition> {
		public static final Serializer INSTANCE = new Serializer();

		@Override
		public void write(JsonObject json, CoreCondition value) {
			json.addProperty("part", value.subpart);
		}

		@Override
		public CoreCondition read(JsonObject json) {
			return new CoreCondition(JSONUtils.getString(json, "subpart"));
		}

		@Override
		public ResourceLocation getID() {
			return CoreCondition.NAME;
		}
	}

}
