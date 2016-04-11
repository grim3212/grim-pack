package com.grim3212.mc.decor.config;

import java.util.HashMap;

import com.google.common.collect.Maps;
import com.grim3212.mc.core.config.GrimConfig;
import com.grim3212.mc.core.util.GrimLog;
import com.grim3212.mc.decor.GrimDecor;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

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
	public static boolean isFireParticles = false;
	public static boolean enableFirepitNet = true;

	public static HashMap<Item, Item> grillRecipes = Maps.newHashMap();

	@Override
	public void syncConfig() {
		dyeFrames = config.get(Configuration.CATEGORY_GENERAL, "DyeFrames", true).getBoolean();
		burnFrames = config.get(Configuration.CATEGORY_GENERAL, "BurnFrames", true).getBoolean();
		dyeWallpaper = config.get(Configuration.CATEGORY_GENERAL, "DyeWallpaper", true).getBoolean();
		copyDye = config.get(Configuration.CATEGORY_GENERAL, "CopyDye", true).getBoolean();
		burnWallpaper = config.get(Configuration.CATEGORY_GENERAL, "BurnWallpaper", true).getBoolean();
		widthWallpaper = (float) config.get(Configuration.CATEGORY_GENERAL, "WallpaperWidth", 1.0D).getDouble();
		numWallpapers = config.get(Configuration.CATEGORY_GENERAL, "NumWallpapers", 24).getInt();
		useAllBlocks = config.get(Configuration.CATEGORY_GENERAL, "UseAllBlocks", true).getBoolean();
		decorationBlocks = config.get(Configuration.CATEGORY_GENERAL, "DecorationBlocks", new String[] { "mossy_cobblestone", "diamond_ore" }).getStringList();

		enableFirepitNet = config.get("settings", "fireplace.cfg.EnableFirepitNet", true).getBoolean();
		infiniteGrillFuel = config.get("settings", "fireplace.cfg.InfiniteGrillFuel", false).getBoolean();
		isFireParticles = config.get("settings", "fireplace.cfg.IsFireParticles", false).getBoolean();

		config.get("customgrillrecipes", "fireplace.cfg.recipes", new String[] { "porkchop>cooked_porkchop", "beef>cooked_beef", "chicken>cooked_chicken", "potato>baked_potato", "rabbit>cooked_rabbit", "mutton>cooked_mutton", "fish>cooked_fish" });
		config.addCustomCategoryComment("customgrillrecipes", "Use this to add new grill recipes. \nTo add a new recipe add a line then write out the [RawItemName] separated by a '>' then write out the [CookedItemName]. For mod items make sure to add the modID with a colon ':' and the then the item name. \nExample: grimcuisine:chocolate_ball>grimcuisine:chocolate_bar");

		if (!config.getCategory("customgrillrecipes").isEmpty()) {
			String[] recipes = config.getCategory("customgrillrecipes").get("fireplace.cfg.recipes").getStringList();

			for (int i = 0; i < recipes.length; i++) {
				String[] rawids = recipes[i].split(">");
				Item rawid1 = (Item) Item.itemRegistry.getObject(new ResourceLocation(rawids[0]));
				Item rawid2 = (Item) Item.itemRegistry.getObject(new ResourceLocation(rawids[1]));
				grillRecipes.put(rawid1, rawid2);
				GrimLog.info(GrimDecor.modName, (rawid1 != null ? rawid1.getUnlocalizedName() : rawid1) + " will now grill into " + (rawid2 != null ? rawid2.getUnlocalizedName() : rawid2));
			}
		}

		super.syncConfig();
	}
}
