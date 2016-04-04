package com.grim3212.mc.decor.config;

import com.grim3212.mc.core.config.GrimConfig;

import net.minecraftforge.common.config.Configuration;

public class DecorConfig extends GrimConfig {

	public static boolean dyeFrames;
	public static boolean burnFrames;
	public static boolean dyeWallpaper;
	public static boolean copyDye;
	public static boolean burnWallpaper;
	public static float widthWallpaper;
	public static int numWallpapers;

	@Override
	public void syncConfig() {
		dyeFrames = config.get(Configuration.CATEGORY_GENERAL, "DyeFrames", true).getBoolean();
		burnFrames = config.get(Configuration.CATEGORY_GENERAL, "BurnFrames", true).getBoolean();
		dyeWallpaper = config.get(Configuration.CATEGORY_GENERAL, "DyeWallpaper", true).getBoolean();
		copyDye = config.get(Configuration.CATEGORY_GENERAL, "CopyDye", true).getBoolean();
		burnWallpaper = config.get(Configuration.CATEGORY_GENERAL, "BurnWallpaper", true).getBoolean();
		widthWallpaper = (float) config.get(Configuration.CATEGORY_GENERAL, "WallpaperWidth", 1.0D).getDouble();
		numWallpapers = config.get(Configuration.CATEGORY_GENERAL, "NumWallpapers", 24).getInt();


		super.syncConfig();
	}
}
