package com.grim3212.mc.pack.decor.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.decor.GrimDecor;

import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class DecorConfig extends GrimConfig {

	public static boolean dyeFrames;
	public static boolean burnFrames;
	public static boolean dyeWallpaper;
	public static boolean copyDye;
	public static boolean burnWallpaper;
	public static boolean infiniteGrillFuel;

	// Sync to client
	public static int numWallpapers;
	public static boolean useAllBlocks;
	public static String[] decorationBlocks;

	// Client only
	public static float widthWallpaper;
	public static boolean enableFirepitNet;

	public static HashMap<Item, Item> grillRecipes = Maps.newHashMap();
	public static final String CONFIG_NAME = "decor";
	public static final String CONFIG_GENERAL_NAME = "decor.general";
	public static final String CONFIG_GRILL_RECIPES_NAME = "decor.customgrillrecipes";

	@Override
	public void syncConfig() {
		dyeFrames = config.get(CONFIG_GENERAL_NAME, "DyeFrames", true).getBoolean();
		burnFrames = config.get(CONFIG_GENERAL_NAME, "BurnFrames", true).getBoolean();
		dyeWallpaper = config.get(CONFIG_GENERAL_NAME, "DyeWallpaper", true).getBoolean();
		copyDye = config.get(CONFIG_GENERAL_NAME, "CopyDye", true).getBoolean();
		burnWallpaper = config.get(CONFIG_GENERAL_NAME, "BurnWallpaper", true).getBoolean();
		numWallpapers = config.get(CONFIG_GENERAL_NAME, "NumWallpapers", 24).getInt();
		useAllBlocks = config.get(CONFIG_GENERAL_NAME, "UseAllBlocks", true).getBoolean();
		decorationBlocks = config.get(CONFIG_GENERAL_NAME, "DecorationBlocks", new String[] { "mossy_cobblestone", "diamond_ore" }).getStringList();
		infiniteGrillFuel = config.get(CONFIG_GENERAL_NAME, "grimpack.decor.cfg.InfiniteGrillFuel", false).getBoolean();

		widthWallpaper = (float) config.get(CONFIG_GENERAL_NAME, "WallpaperWidth", 1.0D).getDouble();
		enableFirepitNet = config.get(CONFIG_GENERAL_NAME, "grimpack.decor.cfg.EnableFirepitNet", true).getBoolean();

		config.get(CONFIG_GRILL_RECIPES_NAME, "grimpack.decor.cfg.recipes", new String[] { "porkchop>cooked_porkchop", "beef>cooked_beef", "chicken>cooked_chicken", "potato>baked_potato", "rabbit>cooked_rabbit", "mutton>cooked_mutton", "fish>cooked_fish" });
		config.addCustomCategoryComment(CONFIG_GRILL_RECIPES_NAME, "Use this to add new grill recipes. \nTo add a new recipe add a line then write out the [RawItemName] separated by a '>' then write out the [CookedItemName]. For mod items make sure to add the modID with a colon ':' and the then the item name. \nExample: grimcuisine:chocolate_ball>grimcuisine:chocolate_bar");

		if (!config.getCategory(CONFIG_GRILL_RECIPES_NAME).isEmpty()) {
			String[] recipes = config.getCategory(CONFIG_GRILL_RECIPES_NAME).get("grimpack.decor.cfg.recipes").getStringList();

			for (int i = 0; i < recipes.length; i++) {
				String[] rawids = recipes[i].split(">");
				Item rawid1 = (Item) Item.REGISTRY.getObject(new ResourceLocation(rawids[0]));
				Item rawid2 = (Item) Item.REGISTRY.getObject(new ResourceLocation(rawids[1]));
				grillRecipes.put(rawid1, rawid2);
			}
		}

		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement("decorGeneralCfg", "grimpack.decor.cfg.general", new ConfigElement(GrimDecor.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("decorGrillRecipesCfg", "grimpack.decor.cfg.grillrecipes", new ConfigElement(GrimDecor.INSTANCE.getConfig().getCategory(CONFIG_GRILL_RECIPES_NAME)).getChildElements()));
		return list;
	}

	@Override
	public void readFromServer(PacketBuffer buffer) {
		decorationBlocks = ByteBufUtils.readUTF8String(buffer).split(",");
		numWallpapers = buffer.readInt();
		useAllBlocks = buffer.readBoolean();
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		StringBuilder builder = new StringBuilder();
		for (String block : decorationBlocks)
			builder.append(block + ",");

		buffer.writeString(builder.toString());
		buffer.writeInt(numWallpapers);
		buffer.writeBoolean(useAllBlocks);
	}
}
