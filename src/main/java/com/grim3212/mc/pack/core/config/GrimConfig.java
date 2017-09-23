package com.grim3212.mc.pack.core.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.grim3212.mc.pack.GrimPack;

import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.Loader;

public abstract class GrimConfig {

	public Configuration config;

	public GrimConfig() {
		config = new Configuration(new File(Loader.instance().getConfigDir(), GrimPack.modID + "/" + name() + ".cfg"));
	}

	/**
	 * Used to load config options early that item construction may depend on
	 */
	public void syncSubparts() {
	}

	/**
	 * Used to load config options early that item construction may depend on
	 */
	public void syncFirst() {
	}

	public String name() {
		return "grimpack";
	}

	/**
	 * Called after PreInit of each GrimPart this should sync all available
	 * config options
	 */
	public void syncConfig() {
		if (config.hasChanged()) {
			config.save();
		}
	}

	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		return list;
	}

	public void readFromServer(PacketBuffer buffer) {

	}

	public void writeToClient(PacketBuffer buffer) {

	}

	protected List<String> getImageUrls() {
		return Lists.newArrayList();
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
