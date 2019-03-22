package com.grim3212.mc.pack.decor.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionSerializer;

public class DecorConditionFactory implements IConditionSerializer {

	@Override
	public BooleanSupplier parse(JsonObject json) {
		String value = JsonUtils.getString(json, "subpart", "");

		if (CoreConfig.useDecor.get()) {
			switch (value) {
			case "alarm":
				return () -> DecorConfig.subpartAlarm;
			case "cages":
				return () -> DecorConfig.subpartCages;
			case "calendar":
				return () -> DecorConfig.subpartCalendar;
			case "colorizer":
				return () -> DecorConfig.subpartColorizer;
			case "decorations":
				return () -> DecorConfig.subpartDecorations;
			case "fireplaces":
				return () -> DecorConfig.subpartColorizer && DecorConfig.subpartFireplaces;
			case "flatitemframe":
				return () -> DecorConfig.subpartFlatItemFrame;
			case "fluro":
				return () -> DecorConfig.subpartFluro;
			case "frames":
				return () -> DecorConfig.subpartFrames;
			case "furniture":
				return () -> DecorConfig.subpartColorizer && DecorConfig.subpartFurniture;
			case "lampposts":
				return () -> DecorConfig.subpartColorizer && DecorConfig.subpartLampPosts;
			case "lanterns":
				return () -> DecorConfig.subpartLanterns;
			case "lightbulbs":
				return () -> DecorConfig.subpartLightBulbs;
			case "neonsign":
				return () -> DecorConfig.subpartNeonSign;
			case "slopes":
				return () -> DecorConfig.subpartColorizer && DecorConfig.subpartSlopes;
			case "wallclock":
				return () -> DecorConfig.subpartWallClock;
			case "wallpaper":
				return () -> DecorConfig.subpartWallpaper;
			default:
				throw new JsonParseException("SubPart '" + value + "' is either misspelled or doesn't exist!");
			}
		}
		// Decor is not even loaded
		return () -> false;
	}

}
