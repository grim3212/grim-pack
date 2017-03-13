package com.grim3212.mc.pack.decor.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Maps;
import com.grim3212.mc.pack.core.config.ConfigUtils;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.config.Recipe;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.decor.GrimDecor;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
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
	public static boolean flipBlocks;
	public static boolean consumeBlock;

	// Client only
	public static float widthWallpaper;
	public static boolean enableFirepitNet;
	public static int smoothness;

	public static HashMap<ItemStack, ItemStack> grillRecipes = Maps.newHashMap();
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

		// TODO: Change this to use new configutils
		decorationBlocks = config.get(CONFIG_GENERAL_NAME, "DecorationBlocks", new String[] { "mossy_cobblestone", "diamond_ore" }).getStringList();
		infiniteGrillFuel = config.get(CONFIG_GENERAL_NAME, "grimpack.decor.cfg.InfiniteGrillFuel", false).getBoolean();

		flipBlocks = config.get(CONFIG_GENERAL_NAME, "Flip Blocks in Flat Item Frames", false).getBoolean();

		widthWallpaper = (float) config.get(CONFIG_GENERAL_NAME, "WallpaperWidth", 1.0D).getDouble();
		enableFirepitNet = config.get(CONFIG_GENERAL_NAME, "grimpack.decor.cfg.EnableFirepitNet", true).getBoolean();
		smoothness = config.get(CONFIG_GENERAL_NAME, "SlopeSmoothness", 2).getInt();
		consumeBlock = config.get(CONFIG_GENERAL_NAME, "Colorizers Consume Block", false).getBoolean();

		config.get(CONFIG_GRILL_RECIPES_NAME, "grimpack.decor.cfg.recipes", new String[] { "porkchop>cooked_porkchop", "beef>cooked_beef", "chicken>cooked_chicken", "potato>baked_potato", "rabbit>cooked_rabbit", "mutton>cooked_mutton", "fish>cooked_fish", "grimpack:chocolate_bowl>grimpack:chocolate_bowl_hot" });
		config.addCustomCategoryComment(CONFIG_GRILL_RECIPES_NAME, "Use this to add new grill recipes. \nTo add a new recipe add a line then write out the [RawItemName] separated by a '>' then write out the [CookedItemName]. For mod items make sure to add the modID with a colon ':' and the then the item name. \nExample: grimpack:chocolate_ball>grimpack:chocolate_bar");

		if (!config.getCategory(CONFIG_GRILL_RECIPES_NAME).isEmpty()) {
			String[] recipes = config.getCategory(CONFIG_GRILL_RECIPES_NAME).get("grimpack.decor.cfg.recipes").getStringList();
			loadGrillRecipes(recipes);
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
		flipBlocks = buffer.readBoolean();
		consumeBlock = buffer.readBoolean();
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		StringBuilder builder = new StringBuilder();
		for (String block : decorationBlocks)
			builder.append(block + ",");

		buffer.writeString(builder.toString());
		buffer.writeInt(numWallpapers);
		buffer.writeBoolean(useAllBlocks);
		buffer.writeBoolean(flipBlocks);
		buffer.writeBoolean(consumeBlock);
	}

	public void loadGrillRecipes(String[] config) {
		DecorConfig.grillRecipes.clear();

		List<Recipe> recipes = ConfigUtils.loadConfigurableRecipes(config, false);

		for (Recipe recipe : recipes) {
			GrimLog.info(GrimDecor.partName, "Registered grill recipe: " + recipe);
			DecorConfig.grillRecipes.put(recipe.getInput(), recipe.getOutput());
		}
	}

	public static boolean grillRecipesContain(ItemStack stack) {
		Iterator<ItemStack> itr = DecorConfig.grillRecipes.keySet().iterator();
		while (itr.hasNext()) {
			ItemStack compare = itr.next();

			if (RecipeHelper.compareItemStacks(stack, compare)) {
				return true;
			}
		}

		return false;
	}

	public static ItemStack getOutput(ItemStack stack) {
		Iterator<ItemStack> itr = DecorConfig.grillRecipes.keySet().iterator();
		while (itr.hasNext()) {
			ItemStack compare = itr.next();
			if (RecipeHelper.compareItemStacks(stack, compare)) {
				// Copy so we don't mess with the ItemStack
				return DecorConfig.grillRecipes.get(compare).copy();
			}
		}

		return ItemStack.EMPTY;
	}
}
