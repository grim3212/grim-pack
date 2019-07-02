package com.grim3212.mc.pack.decor.crafting;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.util.JSONUtils;
import net.minecraftforge.common.crafting.IConditionSerializer;

public class DecorConditionFactory implements IConditionSerializer {

	@Override
	public BooleanSupplier parse(JsonObject json) {
		String value = JSONUtils.getString(json, "subpart", "");

		if (CoreConfig.useDecor.get()) {
			switch (value) {
			case "alarm":
				return () -> DecorConfig.subpartAlarm.get();
			case "cages":
				return () -> DecorConfig.subpartCages.get();
			case "calendar":
				return () -> DecorConfig.subpartCalendar.get();
			case "colorizer":
				return () -> DecorConfig.subpartColorizer.get();
			case "decorations":
				return () -> DecorConfig.subpartDecorations.get();
			case "fireplaces":
				return () -> DecorConfig.subpartColorizer.get() && DecorConfig.subpartFireplaces.get();
			case "fluro":
				return () -> DecorConfig.subpartFluro.get();
			case "frames":
				return () -> DecorConfig.subpartFrames.get();
			case "furniture":
				return () -> DecorConfig.subpartColorizer.get() && DecorConfig.subpartFurniture.get();
			case "lampposts":
				return () -> DecorConfig.subpartColorizer.get() && DecorConfig.subpartLampPosts.get();
			case "lanterns":
				return () -> DecorConfig.subpartLanterns.get();
			case "lightbulbs":
				return () -> DecorConfig.subpartLightBulbs.get();
			case "neonsign":
				return () -> DecorConfig.subpartNeonSign.get();
			case "slopes":
				return () -> DecorConfig.subpartColorizer.get() && DecorConfig.subpartSlopes.get();
			case "wallclock":
				return () -> DecorConfig.subpartWallClock.get();
			case "wallpaper":
				return () -> DecorConfig.subpartWallpaper.get();
			default:
				throw new JsonParseException("SubPart '" + value + "' is either misspelled or doesn't exist!");
			}
		}
		// Decor is not even loaded
		return () -> false;
	}

}
