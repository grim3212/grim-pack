package com.grim3212.mc.pack.decor.client;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;
import com.grim3212.mc.pack.core.manual.pages.PageImageText;
import com.grim3212.mc.pack.core.manual.pages.PageInfo;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.init.DecorRecipes;
import com.grim3212.mc.pack.decor.item.DecorItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

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
	public static Page flatItemFrame_page;
	public static Page pillar_page;
	public static Page decorTrapDoor_page;
	public static Page alarm_page;
	public static Page neonSign_page;
	public static Page fluro_page;
	public static Page illuminationTube_page;

	@Override
	public void initPages() {
		DecorRecipes.initRecipes();

		if (DecorConfig.subpartFluro.get()) {
			fluro_page = new PageCrafting("fluro", RecipeHelper.getAllPaths("fluro_"), 25);
			illuminationTube_page = new PageCrafting("illumination_tube", RecipeHelper.getAllPaths("illumination_tube_"), 25);
		}

		if (DecorConfig.subpartDecorations.get()) {
			fancyPack_page = new PageInfo("fancy");
			moss_page = new PageCrafting("moss", DecorRecipes.mossy);
			fancyStone_page = new PageCrafting("stone", DecorRecipes.stone, 25);
			road_page = new PageFurnace("road", new ItemStack(Blocks.GRAVEL));
			crafts_page = new PageCrafting("crafts", DecorRecipes.crafts, 25);
			firing_page = new PageFurnace("firing", new ItemStack[] { new ItemStack(DecorItems.unfired_craft), new ItemStack(DecorItems.unfired_pot) }, 20);
		}

		if (DecorConfig.subpartAlarm.get())
			alarm_page = new PageCrafting("alarm", new ItemStack(DecorBlocks.alarm));

		if (DecorConfig.subpartFlatItemFrame.get())
			flatItemFrame_page = new PageCrafting("flat_item_frame", new ItemStack(DecorItems.flat_item_frame)).appendImageUrl("flatitemframe.png");

		if (DecorConfig.subpartCalendar.get())
			calendar_page = new PageCrafting("calendar", new ItemStack(DecorBlocks.calendar));

		if (DecorConfig.subpartWallClock.get())
			clock_page = new PageCrafting("clock", DecorRecipes.clocks, 20);

		if (DecorConfig.subpartWallpaper.get()) {
			wallpaperInfo_page = new PageInfo("wallpaperinfo");
			wallpaper_page = new PageCrafting("wallpaper", new ItemStack(DecorItems.wallpaper));
		}

		if (DecorConfig.subpartFrames.get()) {
			framesInfo_page = new PageImageText("frameinfo", "frames_info_page.png", 0.85F);
			frames_page = new PageCrafting("frames", DecorRecipes.frames, 25);
		}

		if (DecorConfig.subpartCages.get())
			cage_page = new PageCrafting("cage", DecorRecipes.chains, 20).appendImageUrl("cage.png");

		if (DecorConfig.subpartLanterns.get())
			lantern_page = new PageCrafting("lantern", RecipeHelper.getAllPaths("lantern"), 20);

		if (DecorConfig.subpartLightBulbs.get())
			lights_page = new PageCrafting("lights", DecorRecipes.lights, 25);

		if (DecorConfig.subpartNeonSign.get())
			neonSign_page = new PageCrafting("neon_sign", new ItemStack(DecorItems.neon_sign)).appendImageUrl("neon_signs.png");

		if (DecorConfig.subpartColorizer.get()) {
			colorizer_page = new PageCrafting("colorizer", DecorRecipes.colorizers, 20);
			brush_page = new PageCrafting("brush", new ItemStack(DecorItems.brush));

			if (DecorConfig.subpartFurniture.get()) {
				table_page = new PageCrafting("table", new ItemStack(DecorBlocks.table));
				chair_page = new PageCrafting("chair", new ItemStack(DecorBlocks.chair)).appendImageUrl("furniture.png");
				stool_page = new PageCrafting("stool", new ItemStack(DecorBlocks.stool)).appendImageUrl("stools.png");
				counter_page = new PageCrafting("counter", new ItemStack(DecorBlocks.counter));
				fence_page = new PageCrafting("fence", new ItemStack(DecorBlocks.fence));
				fenceGate_page = new PageCrafting("fencegate", new ItemStack(DecorBlocks.fence_gate));
				wall_page = new PageCrafting("wall", new ItemStack(DecorBlocks.wall));
				doors_page = new PageCrafting("doors", DecorRecipes.decor_door);
				decorTrapDoor_page = new PageCrafting("trap_door", new ItemStack(DecorBlocks.decor_trap_door));
			}

			if (DecorConfig.subpartFireplaces.get()) {
				burningWood_page = new PageCrafting("burning_wood", new ItemStack(DecorBlocks.burning_wood));
				fireplace_page = new PageCrafting("fireplace", new ItemStack(DecorBlocks.fireplace)).appendImageUrl("fireplaces.png");
				chimney_page = new PageCrafting("chimney", new ItemStack(DecorBlocks.chimney));
				stove_page = new PageCrafting("stove", new ItemStack(DecorBlocks.stove));
				firepit_page = new PageCrafting("firepit", new ItemStack(DecorBlocks.firepit));
				firering_page = new PageCrafting("firering", new ItemStack(DecorBlocks.firering));
				grill_page = new PageCrafting("grill", new ItemStack(DecorBlocks.grill)).appendImageUrl("grills.png");
			}

			if (DecorConfig.subpartLampPosts.get())
				lamps_page = new PageCrafting("recipes", new ItemStack(DecorItems.lamp_item));

			if (DecorConfig.subpartSlopes.get()) {
				slopeInfo_page = new PageInfo("slope_info");
				corner_page = new PageCrafting("corner", new ItemStack(DecorBlocks.corner));
				slope_page = new PageCrafting("slope", new ItemStack(DecorBlocks.slope));
				slopedAngle_page = new PageCrafting("sloped_angle", new ItemStack(DecorBlocks.sloped_angle));
				slantedCorner_page = new PageCrafting("slanted_corner", new ItemStack(DecorBlocks.slanted_corner));
				obliqueSlope_page = new PageCrafting("oblique_slope", new ItemStack(DecorBlocks.oblique_slope));
				slopedIntersection_page = new PageCrafting("sloped_intersection", new ItemStack(DecorBlocks.sloped_intersection));
				pyramid_page = new PageCrafting("pyramid", new ItemStack(DecorBlocks.pyramid));
				fullPyramid_page = new PageCrafting("full_pyramid", new ItemStack(DecorBlocks.full_pyramid));
				slopedPost_page = new PageCrafting("sloped_post", new ItemStack(DecorBlocks.sloped_post));
				stairs_page = new PageCrafting("stairs", new ItemStack(DecorBlocks.decor_stairs));
				pruners_page = new PageCrafting("pruners", new ItemStack(DecorItems.pruners));
				pillar_page = new PageCrafting("pillar", new ItemStack(DecorBlocks.pillar));
			}
		}
	}

	@Override
	public void registerChapters(ManualPart part) {
		if (DecorConfig.subpartDecorations.get())
			ManualRegistry.addChapter("intro", part).addPages(fancyPack_page, moss_page, fancyStone_page, road_page);

		if (DecorConfig.subpartCalendar.get() || DecorConfig.subpartWallClock.get() || DecorConfig.subpartWallpaper.get() || DecorConfig.subpartFrames.get() || DecorConfig.subpartFlatItemFrame.get() || DecorConfig.subpartNeonSign.get())
			ManualRegistry.addChapter("hanging", part).addPages(calendar_page, clock_page, wallpaperInfo_page, wallpaper_page, framesInfo_page, frames_page, flatItemFrame_page, neonSign_page);

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
