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

	public static final String CONFIG_NAME = "decor";
	public static final String CONFIG_GENERAL_NAME = "decor.general";
	public static final String CONFIG_PARTS_NAME = "decor.subparts";
	public static final String CONFIG_GRILL_RECIPES_NAME = "decor.customgrillrecipes";

	public static HashMap<ItemStack, ItemStack> grillRecipes = Maps.newHashMap();

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

	// Subparts
	public static boolean subpartCages;
	public static boolean subpartCalendar;
	public static boolean subpartColorizer;
	public static boolean subpartDecorations;
	public static boolean subpartFireplaces;
	public static boolean subpartFlatItemFrame;
	public static boolean subpartFrames;
	public static boolean subpartFurniture;
	public static boolean subpartLampPosts;
	public static boolean subpartLanterns;
	public static boolean subpartLightBulbs;
	public static boolean subpartSlopes;
	public static boolean subpartWallClock;
	public static boolean subpartWallpaper;

	@Override
	public String name() {
		return CONFIG_NAME;
	}

	@Override
	public void syncSubparts() {
		config.addCustomCategoryComment(CONFIG_PARTS_NAME, "Subparts fireplaces, furniture, lamp posts, and slopes require Colorizer to also be active. If colorizer is disabled so are they.");

		subpartCages = config.get(CONFIG_PARTS_NAME, "Enable SubPart cages", true).setRequiresMcRestart(true).getBoolean();
		subpartCalendar = config.get(CONFIG_PARTS_NAME, "Enable SubPart calendar", true).setRequiresMcRestart(true).getBoolean();
		subpartColorizer = config.get(CONFIG_PARTS_NAME, "Enable SubPart colorizer", true).setRequiresMcRestart(true).getBoolean();
		subpartDecorations = config.get(CONFIG_PARTS_NAME, "Enable SubPart decorations", true).setRequiresMcRestart(true).getBoolean();
		subpartFireplaces = config.get(CONFIG_PARTS_NAME, "Enable SubPart fireplaces", true).setRequiresMcRestart(true).getBoolean();
		subpartFlatItemFrame = config.get(CONFIG_PARTS_NAME, "Enable SubPart flat item frame", true).setRequiresMcRestart(true).getBoolean();
		subpartFrames = config.get(CONFIG_PARTS_NAME, "Enable SubPart frames", true).setRequiresMcRestart(true).getBoolean();
		subpartFurniture = config.get(CONFIG_PARTS_NAME, "Enable SubPart furniture", true).setRequiresMcRestart(true).getBoolean();
		subpartLampPosts = config.get(CONFIG_PARTS_NAME, "Enable SubPart lamp posts", true).setRequiresMcRestart(true).getBoolean();
		subpartLanterns = config.get(CONFIG_PARTS_NAME, "Enable SubPart lanterns", true).setRequiresMcRestart(true).getBoolean();
		subpartLightBulbs = config.get(CONFIG_PARTS_NAME, "Enable SubPart lightbulbs", true).setRequiresMcRestart(true).getBoolean();
		subpartSlopes = config.get(CONFIG_PARTS_NAME, "Enable SubPart slopes", true).setRequiresMcRestart(true).getBoolean();
		subpartWallClock = config.get(CONFIG_PARTS_NAME, "Enable SubPart wall clock", true).setRequiresMcRestart(true).getBoolean();
		subpartWallpaper = config.get(CONFIG_PARTS_NAME, "Enable SubPart wallpaper", true).setRequiresMcRestart(true).getBoolean();
	}

	@Override
	public void syncConfig() {
		syncSubparts();

		if (subpartFrames) {
			dyeFrames = config.get(CONFIG_GENERAL_NAME, "DyeFrames", true).getBoolean();
			burnFrames = config.get(CONFIG_GENERAL_NAME, "BurnFrames", true).getBoolean();
		}

		if (subpartWallpaper) {
			dyeWallpaper = config.get(CONFIG_GENERAL_NAME, "DyeWallpaper", true).getBoolean();
			copyDye = config.get(CONFIG_GENERAL_NAME, "CopyDye", true).getBoolean();
			burnWallpaper = config.get(CONFIG_GENERAL_NAME, "BurnWallpaper", true).getBoolean();
			numWallpapers = config.get(CONFIG_GENERAL_NAME, "NumWallpapers", 24).getInt();
			widthWallpaper = (float) config.get(CONFIG_GENERAL_NAME, "WallpaperWidth", 1.0D).getDouble();
		}

		if (subpartFlatItemFrame) {
			flipBlocks = config.get(CONFIG_GENERAL_NAME, "Flip Blocks in Flat Item Frames", false).getBoolean();
		}

		if (subpartColorizer) {
			useAllBlocks = config.get(CONFIG_GENERAL_NAME, "UseAllBlocks", true).getBoolean();

			// TODO: Change this to use new configutils
			decorationBlocks = config.get(CONFIG_GENERAL_NAME, "DecorationBlocks", new String[] { "mossy_cobblestone", "diamond_ore" }).getStringList();
			consumeBlock = config.get(CONFIG_GENERAL_NAME, "Colorizers Consume Block", false).getBoolean();

			if (subpartSlopes) {
				smoothness = config.get(CONFIG_GENERAL_NAME, "SlopeSmoothness", 2).getInt();
			}

			if (subpartFireplaces) {
				infiniteGrillFuel = config.get(CONFIG_GENERAL_NAME, "grimpack.decor.cfg.InfiniteGrillFuel", false).getBoolean();
				enableFirepitNet = config.get(CONFIG_GENERAL_NAME, "grimpack.decor.cfg.EnableFirepitNet", true).getBoolean();
				config.get(CONFIG_GRILL_RECIPES_NAME, "grimpack.decor.cfg.recipes", new String[] { "porkchop>cooked_porkchop", "beef>cooked_beef", "chicken>cooked_chicken", "potato>baked_potato", "rabbit>cooked_rabbit", "mutton>cooked_mutton", "fish>cooked_fish", "grimpack:chocolate_bowl>grimpack:chocolate_bowl_hot" });
				config.addCustomCategoryComment(CONFIG_GRILL_RECIPES_NAME, "Use this to add new grill recipes. \nTo add a new recipe add a line then write out the [RawItemName] separated by a '>' then write out the [CookedItemName]. For mod items make sure to add the modID with a colon ':' and the then the item name. \nExample: grimpack:chocolate_ball>grimpack:chocolate_bar");

				if (!config.getCategory(CONFIG_GRILL_RECIPES_NAME).isEmpty()) {
					String[] recipes = config.getCategory(CONFIG_GRILL_RECIPES_NAME).get("grimpack.decor.cfg.recipes").getStringList();
					loadGrillRecipes(recipes);
				}
			}
		}

		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		if (subpartColorizer || subpartFrames || subpartFlatItemFrame || subpartWallpaper)
			list.add(new DummyCategoryElement("decorGeneralCfg", "grimpack.decor.cfg.general", new ConfigElement(GrimDecor.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements()));
		if (subpartColorizer && subpartFireplaces)
			list.add(new DummyCategoryElement("decorGrillRecipesCfg", "grimpack.decor.cfg.grillrecipes", new ConfigElement(GrimDecor.INSTANCE.getConfig().getCategory(CONFIG_GRILL_RECIPES_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("decorSubPartCfg", "grimpack.decor.cfg.subparts", new ConfigElement(GrimDecor.INSTANCE.getConfig().getCategory(CONFIG_PARTS_NAME)).getChildElements()));
		return list;
	}

	@Override
	public void readFromServer(PacketBuffer buffer) {
		subpartCages = buffer.readBoolean();
		subpartCalendar = buffer.readBoolean();
		subpartColorizer = buffer.readBoolean();
		subpartDecorations = buffer.readBoolean();
		subpartFireplaces = buffer.readBoolean();
		subpartFlatItemFrame = buffer.readBoolean();
		subpartFrames = buffer.readBoolean();
		subpartFurniture = buffer.readBoolean();
		subpartLampPosts = buffer.readBoolean();
		subpartLanterns = buffer.readBoolean();
		subpartLightBulbs = buffer.readBoolean();
		subpartSlopes = buffer.readBoolean();
		subpartWallClock = buffer.readBoolean();
		subpartWallpaper = buffer.readBoolean();

		if (subpartWallpaper)
			numWallpapers = buffer.readInt();

		if (subpartFlatItemFrame)
			flipBlocks = buffer.readBoolean();

		if (subpartColorizer) {
			useAllBlocks = buffer.readBoolean();
			consumeBlock = buffer.readBoolean();
			decorationBlocks = ByteBufUtils.readUTF8String(buffer).split(",");
		}
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		buffer.writeBoolean(subpartCages);
		buffer.writeBoolean(subpartCalendar);
		buffer.writeBoolean(subpartColorizer);
		buffer.writeBoolean(subpartDecorations);
		buffer.writeBoolean(subpartFireplaces);
		buffer.writeBoolean(subpartFlatItemFrame);
		buffer.writeBoolean(subpartFrames);
		buffer.writeBoolean(subpartFurniture);
		buffer.writeBoolean(subpartLampPosts);
		buffer.writeBoolean(subpartLanterns);
		buffer.writeBoolean(subpartLightBulbs);
		buffer.writeBoolean(subpartSlopes);
		buffer.writeBoolean(subpartWallClock);
		buffer.writeBoolean(subpartWallpaper);

		if (subpartWallpaper)
			buffer.writeInt(numWallpapers);

		if (subpartFlatItemFrame)
			buffer.writeBoolean(flipBlocks);

		if (subpartColorizer) {
			buffer.writeBoolean(useAllBlocks);
			buffer.writeBoolean(consumeBlock);
			StringBuilder builder = new StringBuilder();
			for (String block : decorationBlocks)
				builder.append(block + ",");
			buffer.writeString(builder.toString());
		}
	}

	public void loadGrillRecipes(String[] config) {
		DecorConfig.grillRecipes.clear();

		ConfigUtils.setCurrentPart(GrimDecor.partName);
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
