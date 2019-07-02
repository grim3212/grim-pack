package com.grim3212.mc.pack.decor.client;

import com.google.common.collect.ImmutableList;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;
import com.grim3212.mc.pack.core.manual.pages.PageImageText;
import com.grim3212.mc.pack.core.manual.pages.PageInfo;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.init.DecorNames;

public class ManualDecor implements IManualPart {

	public static ManualDecor INSTANCE = new ManualDecor();

	public static Page fancyPack_page;
	public static Page moss_page;
	public static Page fancyStone_page;
	public static Page road_page;
	public static Page calendar_page;
	public static Page clock_page;
	public static Page wallpaperInfo_page;
	public static Page wallpaper_page;
	public static Page framesInfo_page;
	public static Page frames_page;
	public static Page cage_page;
	public static Page lantern_page;
	public static Page crafts_page;
	public static Page firing_page;
	public static Page lights_page;
	public static Page colorizer_page;
	public static Page brush_page;
	public static Page table_page;
	public static Page chair_page;
	public static Page stool_page;
	public static Page counter_page;
	public static Page fence_page;
	public static Page fenceGate_page;
	public static Page wall_page;
	public static Page burningWood_page;
	public static Page fireplace_page;
	public static Page chimney_page;
	public static Page stove_page;
	public static Page firepit_page;
	public static Page firering_page;
	public static Page grill_page;
	public static Page lamps_page;
	public static Page doors_page;
	public static Page slopeInfo_page;
	public static Page corner_page;
	public static Page slope_page;
	public static Page slopedAngle_page;
	public static Page slantedCorner_page;
	public static Page obliqueSlope_page;
	public static Page slopedIntersection_page;
	public static Page pyramid_page;
	public static Page fullPyramid_page;
	public static Page slopedPost_page;
	public static Page stairs_page;
	public static Page pruners_page;
	public static Page pillar_page;
	public static Page decorTrapDoor_page;
	public static Page alarm_page;
	public static Page neonSign_page;
	public static Page fluro_page;
	public static Page illuminationTube_page;

	@Override
	public void initPages() {
		if (DecorConfig.subpartFluro.get()) {
			fluro_page = new PageCrafting("fluro", ImmutableList.of(RecipeHelper.getRecipePath(DecorNames.BLACK_FLURO), RecipeHelper.getRecipePath(DecorNames.BLUE_FLURO), RecipeHelper.getRecipePath(DecorNames.BROWN_FLURO), RecipeHelper.getRecipePath(DecorNames.CYAN_FLURO), RecipeHelper.getRecipePath(DecorNames.GRAY_FLURO), RecipeHelper.getRecipePath(DecorNames.GREEN_FLURO), RecipeHelper.getRecipePath(DecorNames.LIGHT_BLUE_FLURO), RecipeHelper.getRecipePath(DecorNames.LIGHT_GRAY_FLURO),
					RecipeHelper.getRecipePath(DecorNames.LIME_FLURO), RecipeHelper.getRecipePath(DecorNames.MAGENTA_FLURO), RecipeHelper.getRecipePath(DecorNames.ORANGE_FLURO), RecipeHelper.getRecipePath(DecorNames.PINK_FLURO), RecipeHelper.getRecipePath(DecorNames.PURPLE_FLURO), RecipeHelper.getRecipePath(DecorNames.RED_FLURO), RecipeHelper.getRecipePath(DecorNames.WHITE_FLURO), RecipeHelper.getRecipePath(DecorNames.YELLOW_FLURO)), 25);
			illuminationTube_page = new PageCrafting("illumination_tube", ImmutableList.of(RecipeHelper.getRecipePath(GrimDecor.partId, "illumination_tube_glowstone"), RecipeHelper.getRecipePath(GrimDecor.partId, "illumination_tube_gunpowder")), 25);
		}

		if (DecorConfig.subpartDecorations.get()) {
			fancyPack_page = new PageInfo("fancy");
			moss_page = new PageCrafting("moss", RecipeHelper.getRecipePath(GrimDecor.partId, "mossy_cobblestone"));
			fancyStone_page = new PageCrafting("stone", ImmutableList.of(RecipeHelper.getRecipePath(DecorNames.FANCY_STONE), RecipeHelper.getRecipePath(GrimDecor.partId, "stone")), 25);
			road_page = new PageFurnace("road", RecipeHelper.getRecipePath(DecorNames.ROAD));
			crafts_page = new PageCrafting("crafts", ImmutableList.of(RecipeHelper.getRecipePath(DecorNames.CRAFT_BONE), RecipeHelper.getRecipePath(DecorNames.UNFIRED_CRAFT), RecipeHelper.getRecipePath(DecorNames.UNFIRED_POT)), 25);
			firing_page = new PageFurnace("firing", ImmutableList.of(RecipeHelper.getRecipePath(DecorNames.CRAFT_CLAY), RecipeHelper.getRecipePath(DecorNames.POT)), 20);
		}

		if (DecorConfig.subpartAlarm.get())
			alarm_page = new PageCrafting("alarm", RecipeHelper.getRecipePath(DecorNames.ALARM));

		if (DecorConfig.subpartCalendar.get())
			calendar_page = new PageCrafting("calendar", RecipeHelper.getRecipePath(DecorNames.CALENDAR));

		if (DecorConfig.subpartWallClock.get())
			clock_page = new PageCrafting("clock", ImmutableList.of(RecipeHelper.getRecipePath(DecorNames.WALL_CLOCK), RecipeHelper.getRecipePath(GrimDecor.partId, "clock"), RecipeHelper.getRecipePath(GrimDecor.partId, "wall_clock_alt")), 20);

		if (DecorConfig.subpartWallpaper.get()) {
			wallpaperInfo_page = new PageInfo("wallpaperinfo");
			wallpaper_page = new PageCrafting("wallpaper", RecipeHelper.getRecipePath(DecorNames.WALLPAPER));
		}

		if (DecorConfig.subpartFrames.get()) {
			framesInfo_page = new PageImageText("frameinfo", "frames_info_page.png", 0.85F);
			frames_page = new PageCrafting("frames", ImmutableList.of(RecipeHelper.getRecipePath(DecorNames.FRAME_IRON), RecipeHelper.getRecipePath(DecorNames.FRAME_WOOD)), 25);
		}

		if (DecorConfig.subpartCages.get())
			cage_page = new PageCrafting("cage", ImmutableList.of(RecipeHelper.getRecipePath(DecorNames.CAGE), RecipeHelper.getRecipePath(DecorNames.CHAIN)), 20).appendImageUrl("cage.png");

		if (DecorConfig.subpartLanterns.get())
			lantern_page = new PageCrafting("lantern", ImmutableList.of(RecipeHelper.getRecipePath(DecorNames.BONE_LANTERN), RecipeHelper.getRecipePath(DecorNames.IRON_LANTERN), RecipeHelper.getRecipePath(DecorNames.PAPER_LANTERN)), 20);

		if (DecorConfig.subpartLightBulbs.get())
			lights_page = new PageCrafting("lights", ImmutableList.of(RecipeHelper.getRecipePath(DecorNames.LIGHT_BULB), RecipeHelper.getRecipePath(DecorNames.GLASS_SHARD)), 25);

		if (DecorConfig.subpartNeonSign.get())
			neonSign_page = new PageCrafting("neon_sign", RecipeHelper.getRecipePath(DecorNames.NEON_SIGN)).appendImageUrl("neon_signs.png");

		if (DecorConfig.subpartColorizer.get()) {
			colorizer_page = new PageCrafting("colorizer", ImmutableList.of(RecipeHelper.getRecipePath(DecorNames.HARDENED_WOOD), RecipeHelper.getRecipePath(DecorNames.COLORIZER), RecipeHelper.getRecipePath(DecorNames.COLORIZER_LIGHT)), 20);
			brush_page = new PageCrafting("brush", RecipeHelper.getRecipePath(DecorNames.BRUSH));

			if (DecorConfig.subpartFurniture.get()) {
				table_page = new PageCrafting("table", RecipeHelper.getRecipePath(DecorNames.TABLE));
				chair_page = new PageCrafting("chair", RecipeHelper.getRecipePath(DecorNames.CHAIR)).appendImageUrl("furniture.png");
				stool_page = new PageCrafting("stool", RecipeHelper.getRecipePath(DecorNames.STOOL)).appendImageUrl("stools.png");
				counter_page = new PageCrafting("counter", RecipeHelper.getRecipePath(DecorNames.COUNTER));
				fence_page = new PageCrafting("fence", RecipeHelper.getRecipePath(DecorNames.FENCE));
				fenceGate_page = new PageCrafting("fencegate", RecipeHelper.getRecipePath(DecorNames.FENCE_GATE));
				wall_page = new PageCrafting("wall", RecipeHelper.getRecipePath(DecorNames.WALL));
				doors_page = new PageCrafting("doors", RecipeHelper.getRecipePath(DecorNames.DOOR));
				decorTrapDoor_page = new PageCrafting("trap_door", RecipeHelper.getRecipePath(DecorNames.TRAP_DOOR));
			}

			if (DecorConfig.subpartFireplaces.get()) {
				burningWood_page = new PageCrafting("burning_wood", RecipeHelper.getRecipePath(DecorNames.BURNING_WOOD));
				fireplace_page = new PageCrafting("fireplace", RecipeHelper.getRecipePath(DecorNames.FIREPLACE)).appendImageUrl("fireplaces.png");
				chimney_page = new PageCrafting("chimney", RecipeHelper.getRecipePath(DecorNames.CHIMNEY));
				stove_page = new PageCrafting("stove", RecipeHelper.getRecipePath(DecorNames.STOVE));
				firepit_page = new PageCrafting("firepit", RecipeHelper.getRecipePath(DecorNames.FIREPIT));
				firering_page = new PageCrafting("firering", RecipeHelper.getRecipePath(DecorNames.FIRERING));
				grill_page = new PageCrafting("grill", RecipeHelper.getRecipePath(DecorNames.GRILL)).appendImageUrl("grills.png");
			}

			if (DecorConfig.subpartLampPosts.get())
				lamps_page = new PageCrafting("recipes", RecipeHelper.getRecipePath(DecorNames.LAMP_ITEM));

			if (DecorConfig.subpartSlopes.get()) {
				slopeInfo_page = new PageInfo("slope_info");
				corner_page = new PageCrafting("corner", RecipeHelper.getRecipePath(DecorNames.CORNER));
				slope_page = new PageCrafting("slope", RecipeHelper.getRecipePath(DecorNames.SLOPE));
				slopedAngle_page = new PageCrafting("sloped_angle", RecipeHelper.getRecipePath(DecorNames.SLOPED_ANGLE));
				slantedCorner_page = new PageCrafting("slanted_corner", RecipeHelper.getRecipePath(DecorNames.SLANTED_CORNER));
				obliqueSlope_page = new PageCrafting("oblique_slope", RecipeHelper.getRecipePath(DecorNames.OBLIQUE_SLOPE));
				slopedIntersection_page = new PageCrafting("sloped_intersection", RecipeHelper.getRecipePath(DecorNames.SLOPED_INTERSECTION));
				pyramid_page = new PageCrafting("pyramid", RecipeHelper.getRecipePath(DecorNames.PYRAMID));
				fullPyramid_page = new PageCrafting("full_pyramid", RecipeHelper.getRecipePath(DecorNames.FULL_PYRAMID));
				slopedPost_page = new PageCrafting("sloped_post", RecipeHelper.getRecipePath(DecorNames.SLOPED_POST));
				stairs_page = new PageCrafting("stairs", RecipeHelper.getRecipePath(DecorNames.STAIRS));
				pruners_page = new PageCrafting("pruners", RecipeHelper.getRecipePath(DecorNames.PRUNERS));
				pillar_page = new PageCrafting("pillar", RecipeHelper.getRecipePath(DecorNames.PILLAR));
			}
		}
	}

	@Override
	public void registerChapters(ManualPart part) {
		if (DecorConfig.subpartDecorations.get())
			ManualRegistry.addChapter("intro", part).addPages(fancyPack_page, moss_page, fancyStone_page, road_page);

		if (DecorConfig.subpartCalendar.get() || DecorConfig.subpartWallClock.get() || DecorConfig.subpartWallpaper.get() || DecorConfig.subpartFrames.get() || DecorConfig.subpartNeonSign.get())
			ManualRegistry.addChapter("hanging", part).addPages(calendar_page, clock_page, wallpaperInfo_page, wallpaper_page, framesInfo_page, frames_page, neonSign_page);

		if (DecorConfig.subpartCages.get() || DecorConfig.subpartLanterns.get() || DecorConfig.subpartDecorations.get() || DecorConfig.subpartLightBulbs.get() || DecorConfig.subpartFluro.get())
			ManualRegistry.addChapter("deco", part).addPages(cage_page, lantern_page, crafts_page, firing_page, lights_page, fluro_page, illuminationTube_page);

		if (DecorConfig.subpartAlarm.get())
			ManualRegistry.addChapter("alarm", part).addPages(alarm_page);

		if (DecorConfig.subpartColorizer.get()) {
			if (DecorConfig.subpartFurniture.get()) {
				ManualRegistry.addChapter("furniture", part).addPages(colorizer_page, brush_page, table_page, chair_page, stool_page, counter_page, doors_page, decorTrapDoor_page);
				ManualRegistry.addChapter("other", part).addPages(fence_page, fenceGate_page, wall_page);
			}

			if (DecorConfig.subpartFireplaces.get()) {
				ManualRegistry.addChapter("fires", part).addPages(burningWood_page, fireplace_page, chimney_page, stove_page, firepit_page, firering_page);
				ManualRegistry.addChapter("grill", part).addPages(grill_page);
			}

			if (DecorConfig.subpartLampPosts.get())
				ManualRegistry.addChapter("lamps", part).addPages(lamps_page);

			if (DecorConfig.subpartSlopes.get())
				ManualRegistry.addChapter("superslopes", part).addPages(slopeInfo_page, slope_page, slopedAngle_page, slantedCorner_page, corner_page, obliqueSlope_page, slopedIntersection_page, stairs_page, pyramid_page, fullPyramid_page, slopedPost_page, pillar_page, pruners_page).appendImageUrl("slope.png");
		}
	}

}
