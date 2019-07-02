package com.grim3212.mc.pack.decor.config;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.config.ConfigUtils;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.decor.GrimDecor;

import net.minecraft.block.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class DecorConfig extends GrimConfig {

	public static final String CONFIG_NAME = "decor";
	public static final String CONFIG_GENERAL_NAME = "decor.general";
	public static final String CONFIG_PARTS_NAME = "decor.subparts";

	public static List<BlockState> decorBlocks = Lists.newArrayList();

	public static ForgeConfigSpec.BooleanValue dyeFrames;
	public static ForgeConfigSpec.BooleanValue burnFrames;
	public static ForgeConfigSpec.BooleanValue dyeWallpaper;
	public static ForgeConfigSpec.BooleanValue copyDye;
	public static ForgeConfigSpec.BooleanValue burnWallpaper;
	public static ForgeConfigSpec.BooleanValue infiniteGrillFuel;

	// Sync to client
	public static ForgeConfigSpec.IntValue numWallpapers;
	public static ForgeConfigSpec.BooleanValue useAllBlocks;
	public static ForgeConfigSpec.ConfigValue<String[]> decorationBlocks;
	public static ForgeConfigSpec.BooleanValue flipBlocks;
	public static ForgeConfigSpec.BooleanValue consumeBlock;
	public static ForgeConfigSpec.IntValue smoothness;

	// Client only
	public static ForgeConfigSpec.DoubleValue widthWallpaper;
	public static ForgeConfigSpec.BooleanValue enableFirepitNet;

	// Subparts
	public static ForgeConfigSpec.BooleanValue subpartAlarm;
	public static ForgeConfigSpec.BooleanValue subpartCages;
	public static ForgeConfigSpec.BooleanValue subpartCalendar;
	public static ForgeConfigSpec.BooleanValue subpartColorizer;
	public static ForgeConfigSpec.BooleanValue subpartDecorations;
	public static ForgeConfigSpec.BooleanValue subpartFireplaces;
	public static ForgeConfigSpec.BooleanValue subpartFluro;
	public static ForgeConfigSpec.BooleanValue subpartFrames;
	public static ForgeConfigSpec.BooleanValue subpartFurniture;
	public static ForgeConfigSpec.BooleanValue subpartLampPosts;
	public static ForgeConfigSpec.BooleanValue subpartLanterns;
	public static ForgeConfigSpec.BooleanValue subpartLightBulbs;
	public static ForgeConfigSpec.BooleanValue subpartNeonSign;
	public static ForgeConfigSpec.BooleanValue subpartSlopes;
	public static ForgeConfigSpec.BooleanValue subpartWallClock;
	public static ForgeConfigSpec.BooleanValue subpartWallpaper;

	@Override
	public String name() {
		return CONFIG_NAME;
	}

	@Override
	public Optional<ForgeConfigSpec> initServer(Builder serverBuilder) {
		ConfigUtils.setCurrentPart(GrimDecor.partName);
		serverBuilder.comment(name());

		subpartAlarm = serverBuilder.comment("Enable alarm subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartAlarm", true);
		subpartCages = serverBuilder.comment("Enable cages subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartCages", true);
		subpartCalendar = serverBuilder.comment("Enable calendar subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartCalendar", true);
		subpartColorizer = serverBuilder.comment("Enable colorizer subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartColorizer", true);
		subpartDecorations = serverBuilder.comment("Enable decorations subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartDecorations", true);
		subpartFireplaces = serverBuilder.comment("Enable fireplaces subpart").comment("Requires Colorizer part to be active!").worldRestart().define(CONFIG_PARTS_NAME + ".subpartFireplaces", true);
		subpartFluro = serverBuilder.comment("Enable fluro subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartFluro", true);
		subpartFrames = serverBuilder.comment("Enable frames subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartFrames", true);
		subpartFurniture = serverBuilder.comment("Enable furniture subpart").comment("Requires Colorizer part to be active!").worldRestart().define(CONFIG_PARTS_NAME + ".subpartFurniture", true);
		subpartLampPosts = serverBuilder.comment("Enable lamp posts subpart").comment("Requires Colorizer part to be active!").worldRestart().define(CONFIG_PARTS_NAME + ".subpartLampPosts", true);
		subpartLanterns = serverBuilder.comment("Enable lanterns subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartLanterns", true);
		subpartLightBulbs = serverBuilder.comment("Enable lightbulbs subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartLightBulbs", true);
		subpartNeonSign = serverBuilder.comment("Enable neon sign subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartNeonSign", true);
		subpartSlopes = serverBuilder.comment("Enable slopes subpart").comment("Requires Colorizer part to be active!").worldRestart().define(CONFIG_PARTS_NAME + ".subpartSlopes", true);
		subpartWallClock = serverBuilder.comment("Enable wall clock subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartWallClock", true);
		subpartWallpaper = serverBuilder.comment("Enable wallpaper subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartWallpaper", true);

		dyeFrames = serverBuilder.comment("DyeFrames").define(CONFIG_GENERAL_NAME + ".dyeFrames", true);
		burnFrames = serverBuilder.comment("BurnFrames").define(CONFIG_GENERAL_NAME + ".burnFrames", true);
		dyeWallpaper = serverBuilder.comment("DyeWallpaper").define(CONFIG_GENERAL_NAME + ".dyeWallpaper", true);
		copyDye = serverBuilder.comment("CopyDye").define(CONFIG_GENERAL_NAME + ".copyDye", true);
		burnWallpaper = serverBuilder.comment("BurnWallpaper").define(CONFIG_GENERAL_NAME + ".burnWallpaper", true);
		numWallpapers = serverBuilder.comment("NumWallpapers").defineInRange(CONFIG_GENERAL_NAME + ".numWallpapers", 24, 1, 256);

		flipBlocks = serverBuilder.comment("Flip Blocks in Flat Item Frames").define(CONFIG_GENERAL_NAME + ".flipBlocks", true);
		useAllBlocks = serverBuilder.comment("UseAllBlocks").define(CONFIG_GENERAL_NAME + ".useAllBlocks", true);

		// decorationBlocks = config.get(CONFIG_GENERAL_NAME, "DecorationBlocks", new
		// String[] { "mossy_cobblestone", "diamond_ore" }).getStringList();

		// if (!useAllBlocks.get())
		// Don't waste time if we don't have to
		// ConfigUtils.loadBlocksOntoMap(decorationBlocks, decorBlocks);

		consumeBlock = serverBuilder.comment("Colorizers Consume Block").define(CONFIG_GENERAL_NAME + ".consumeBlock", false);
		smoothness = serverBuilder.comment("SlopeSmoothness").defineInRange(CONFIG_GENERAL_NAME + ".smoothness", 2, 1, 10);
		infiniteGrillFuel = serverBuilder.translation("grimpack.decor.cfg.InfiniteGrillFuel").define(CONFIG_GENERAL_NAME + ".infiniteGrillFuel", false);

		return Optional.of(serverBuilder.build());

	}

	@Override
	public Optional<ForgeConfigSpec> initClient(Builder clientBuilder) {
		clientBuilder.comment(name());

		widthWallpaper = clientBuilder.comment("WallpaperWidth").defineInRange(CONFIG_GENERAL_NAME + ".widthWallpaper", 1.0D, 0.1D, 5.0D);
		enableFirepitNet = clientBuilder.translation("grimpack.decor.cfg.EnableFirepitNet").define(CONFIG_GENERAL_NAME + ".enableFirepitNet", true);

		return Optional.of(clientBuilder.build());
	}
}
