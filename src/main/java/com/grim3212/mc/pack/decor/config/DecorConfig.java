package com.grim3212.mc.pack.decor.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.decor.GrimDecor;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class DecorConfig extends GrimConfig {

	public static boolean dyeFrames;
	public static boolean burnFrames;
	public static boolean dyeWallpaper;
	public static boolean copyDye;
	public static boolean burnWallpaper;
	public static float widthWallpaper;
	public static int numWallpapers;
	public static boolean useAllBlocks;
	public static String[] decorationBlocks;
	public static boolean infiniteGrillFuel = false;
	public static boolean enableFirepitNet = true;

	public static HashMap<Item, Item> grillRecipes = Maps.newHashMap();
	public static final String CONFIG_NAME = "decor";

	@Override
	public void syncConfig() {
		dyeFrames = config.get(CONFIG_NAME, "DyeFrames", true).getBoolean();
		burnFrames = config.get(CONFIG_NAME, "BurnFrames", true).getBoolean();
		dyeWallpaper = config.get(CONFIG_NAME, "DyeWallpaper", true).getBoolean();
		copyDye = config.get(CONFIG_NAME, "CopyDye", true).getBoolean();
		burnWallpaper = config.get(CONFIG_NAME, "BurnWallpaper", true).getBoolean();
		widthWallpaper = (float) config.get(CONFIG_NAME, "WallpaperWidth", 1.0D).getDouble();
		numWallpapers = config.get(CONFIG_NAME, "NumWallpapers", 24).getInt();
		useAllBlocks = config.get(CONFIG_NAME, "UseAllBlocks", true).getBoolean();
		decorationBlocks = config.get(CONFIG_NAME, "DecorationBlocks", new String[] { "mossy_cobblestone", "diamond_ore" }).getStringList();

		enableFirepitNet = config.get(CONFIG_NAME, "grimpack.decor.cfg.EnableFirepitNet", true).getBoolean();
		infiniteGrillFuel = config.get(CONFIG_NAME, "grimpack.decor.cfg.InfiniteGrillFuel", false).getBoolean();

		config.get("customgrillrecipes", "grimpack.decor.cfg.recipes", new String[] { "porkchop>cooked_porkchop", "beef>cooked_beef", "chicken>cooked_chicken", "potato>baked_potato", "rabbit>cooked_rabbit", "mutton>cooked_mutton", "fish>cooked_fish" });
		config.addCustomCategoryComment("customgrillrecipes", "Use this to add new grill recipes. \nTo add a new recipe add a line then write out the [RawItemName] separated by a '>' then write out the [CookedItemName]. For mod items make sure to add the modID with a colon ':' and the then the item name. \nExample: grimcuisine:chocolate_ball>grimcuisine:chocolate_bar");

		if (!config.getCategory("customgrillrecipes").isEmpty()) {
			String[] recipes = config.getCategory("customgrillrecipes").get("grimpack.decor.cfg.recipes").getStringList();

			for (int i = 0; i < recipes.length; i++) {
				String[] rawids = recipes[i].split(">");
				Item rawid1 = (Item) Item.REGISTRY.getObject(new ResourceLocation(rawids[0]));
				Item rawid2 = (Item) Item.REGISTRY.getObject(new ResourceLocation(rawids[1]));
				grillRecipes.put(rawid1, rawid2);
			}
		}

		super.syncConfig();
	}

	public static List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement("decorGeneralCfg", "grimpack.decor.cfg.general", new ConfigElement(GrimDecor.INSTANCE.getConfig().getCategory(CONFIG_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("decorGrillRecipesCfg", "grimpack.decor.cfg.grillrecipes", new ConfigElement(GrimDecor.INSTANCE.getConfig().getCategory("customgrillrecipes")).getChildElements()));
		return list;
	}
}
