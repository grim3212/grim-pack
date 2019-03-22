package com.grim3212.mc.pack.core.config;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public abstract class GrimConfig {

	public ForgeConfigSpec SERVER_CONFIG;
	public ForgeConfigSpec CLIENT_CONFIG;

	public GrimConfig() {
		initServer(new ForgeConfigSpec.Builder()).ifPresent(spec -> {
			SERVER_CONFIG = spec;
			ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG, GrimPack.modID + "_" + this.name() + "-server.toml");
			loadConfig(SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve(GrimPack.modID + "_" + this.name() + "-server.toml"));
		});
		initClient(new ForgeConfigSpec.Builder()).ifPresent(spec -> {
			CLIENT_CONFIG = spec;
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG, GrimPack.modID + "_" + this.name() + "-client.toml");
			loadConfig(CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(GrimPack.modID + "_" + this.name() + "-client.toml"));
		});
	}

	public Optional<ForgeConfigSpec> initClient(ForgeConfigSpec.Builder clientBuilder) {
		return Optional.empty();
	}

	public Optional<ForgeConfigSpec> initServer(ForgeConfigSpec.Builder serverBuilder) {
		return Optional.empty();
	}

	public String name() {
		return "grimpack";
	}

	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		return list;
	}

	protected List<String> getImageUrls() {
		return Lists.newArrayList();
	}

	private void loadConfig(ForgeConfigSpec spec, Path path) {
		GrimLog.debug(name(), "Loading config file " + path);

		final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();

		GrimLog.debug(name(), "Built TOML config for " + path.toString());
		configData.load();
		GrimLog.debug(name(), "Loaded TOML config file " + path.toString());
		spec.setConfig(configData);
	}

	@SubscribeEvent
	public void onLoad(final ModConfig.Loading configEvent) {
		GrimLog.debug(name(), "Loaded config file " + configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public void onFileChange(final ModConfig.ConfigReloading configEvent) {
		GrimLog.fatal(name(), "Config just got changed on the file system!");
	}

	public JsonObject deconstruct() {
		JsonObject json = new JsonObject();

		json.addProperty("id", "config");
		json.addProperty("name", I18n.format("grimpack.doc.configname"));
		json.addProperty("config", this.name());

		// Allow a config file to contain image urls as well
		if (!getImageUrls().isEmpty()) {
			JsonArray urls = new JsonArray();
			for (String s : getImageUrls())
				urls.add(s);

			json.add("imageUrls", urls);
		}

		JsonArray elements = new JsonArray();
		for (IConfigElement ele : getConfigItems()) {
			if (ele.isProperty()) {
				elements.add(deconstructProp(ele));
			} else {
				elements.add(deconstructCat(ele));
			}
		}
		json.add("pages", elements);

		return json;
	}

	private JsonObject deconstructCat(IConfigElement ele) {
		if (ele.isProperty())
			throw new IllegalArgumentException("Property was passed to deconstruct category!");

		JsonObject category = new JsonObject();
		category.addProperty("name", I18n.format(ele.getName()));
		category.addProperty("comment", I18n.format(ele.getComment()));
		category.addProperty("mcrestart", ele.requiresMcRestart());
		category.addProperty("worldrestart", ele.requiresWorldRestart());
		category.addProperty("showInGui", ele.showInGui());
		category.addProperty("qualified", ele.getQualifiedName());

		// Check if category
		JsonArray children = new JsonArray();
		for (IConfigElement child : ele.getChildElements()) {
			if (!child.isProperty()) {
				// Recursively get elements
				children.add(this.deconstructCat(child));
			} else {
				children.add(deconstructProp(child));
			}
		}
		category.add("children", children);

		return category;
	}

	private static final String MIN_INT = Integer.toString(Integer.MIN_VALUE);
	private static final String MAX_INT = Integer.toString(Integer.MAX_VALUE);

	private static final String MAX_DOUBLE = Double.toString(Double.MAX_VALUE);
	private static final String MIN_DOUBLE = Double.toString(-Double.MAX_VALUE);

	private JsonObject deconstructProp(IConfigElement child) {
		if (!child.isProperty())
			throw new IllegalArgumentException("Category was passed to deconstruct property!");

		JsonObject prop = new JsonObject();

		prop.addProperty("name", I18n.format(child.getName()));
		if (child.getComment() != null)
			prop.addProperty("comment", I18n.format(child.getComment() + ".tooltip"));
		else
			prop.addProperty("comment", I18n.format(child.getName() + ".tooltip"));
		prop.addProperty("mcrestart", child.requiresMcRestart());
		prop.addProperty("worldrestart", child.requiresWorldRestart());
		prop.addProperty("showInGui", child.showInGui());

		JsonArray defaults = new JsonArray();
		ConfigGuiType type = child.getType();
		prop.addProperty("type", type.toString());
		if (child.isList()) {
			for (Object o : child.getDefaults()) {
				defaults.add((String) o);
			}

			if (child.getMaxListLength() != -1)
				prop.addProperty("maxlistlength", child.getMaxListLength());
		} else {
			defaults.add((String) child.getDefault());
		}
		prop.add("defaults", defaults);

		String minValue = (String) child.getMinValue();
		String maxValue = (String) child.getMaxValue();

		if (type == ConfigGuiType.INTEGER) {
			if (!minValue.equals(MIN_INT))
				prop.addProperty("minvalue", minValue);

			if (!maxValue.equals(MAX_INT))
				prop.addProperty("maxvalue", maxValue);
		} else if (type == ConfigGuiType.DOUBLE) {
			if (!minValue.equals(MIN_DOUBLE))
				prop.addProperty("minvalue", minValue);

			if (!maxValue.equals(MAX_DOUBLE))
				prop.addProperty("maxvalue", maxValue);
		}

		return prop;
	}
}
