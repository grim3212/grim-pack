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

	@Override
	public void initPages() {
		fancyPack_page = new PageInfo("fancy");
		flatItemFrame_page = new PageCrafting("flat_item_frame", new ItemStack(DecorItems.flat_item_frame));
		moss_page = new PageCrafting("moss", DecorBlocks.mossy);
		fancyStone_page = new PageCrafting("stone", DecorBlocks.stone, 25);
		road_page = new PageFurnace("road", new ItemStack(Blocks.GRAVEL));
		calendar_page = new PageCrafting("calendar", new ItemStack(DecorBlocks.calendar));
		clock_page = new PageCrafting("clock", DecorBlocks.clocks, 20);
		wallpaperInfo_page = new PageInfo("wallpaperinfo");
		wallpaper_page = new PageCrafting("wallpaper", new ItemStack(DecorItems.wallpaper));
		framesInfo_page = new PageImageText("frameinfo", "frames_info_page.png", 0.85F);
		frames_page = new PageCrafting("frames", DecorItems.frames, 25);
		cage_page = new PageCrafting("cage", DecorBlocks.chains, 20);
		lantern_page = new PageCrafting("lantern", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.lantern)), 20);
		crafts_page = new PageCrafting("crafts", DecorBlocks.crafts, 25);
		firing_page = new PageFurnace("firing", new ItemStack[] { new ItemStack(DecorItems.unfired_craft), new ItemStack(DecorItems.unfired_pot) }, 20);
		lights_page = new PageCrafting("lights", DecorBlocks.lights, 25);
		colorizer_page = new PageCrafting("colorizer", DecorBlocks.colorizers, 20);
		brush_page = new PageCrafting("brush", new ItemStack(DecorItems.brush));
		table_page = new PageCrafting("table", new ItemStack(DecorBlocks.table));
		chair_page = new PageCrafting("chair", new ItemStack(DecorBlocks.chair));
		stool_page = new PageCrafting("stool", new ItemStack(DecorBlocks.stool));
		counter_page = new PageCrafting("counter", new ItemStack(DecorBlocks.counter));
		fence_page = new PageCrafting("fence", new ItemStack(DecorBlocks.fence));
		fenceGate_page = new PageCrafting("fencegate", new ItemStack(DecorBlocks.fence_gate));
		wall_page = new PageCrafting("wall", new ItemStack(DecorBlocks.wall));
		burningWood_page = new PageCrafting("burning_wood", new ItemStack(DecorBlocks.burning_wood));
		fireplace_page = new PageCrafting("fireplace", new ItemStack(DecorBlocks.fireplace));
		chimney_page = new PageCrafting("chimney", new ItemStack(DecorBlocks.chimney));
		stove_page = new PageCrafting("stove", new ItemStack(DecorBlocks.stove));
		firepit_page = new PageCrafting("firepit", new ItemStack(DecorBlocks.firepit));
		firering_page = new PageCrafting("firering", new ItemStack(DecorBlocks.firering));
		grill_page = new PageCrafting("grill", new ItemStack(DecorBlocks.grill));
		lamps_page = new PageCrafting("recipes", new ItemStack(DecorItems.lamp_item));
		doors_page = new PageCrafting("doors", new ItemStack(DecorItems.decor_door_item));
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
	}

	@Override
	public void registerChapters(ManualPart part) {
		ManualRegistry.addChapter("intro", part).addPages(fancyPack_page, moss_page, fancyStone_page, road_page);
		ManualRegistry.addChapter("hanging", part).addPages(calendar_page, clock_page, wallpaperInfo_page, wallpaper_page, framesInfo_page, frames_page, flatItemFrame_page);
		ManualRegistry.addChapter("deco", part).addPages(cage_page, lantern_page, crafts_page, firing_page, lights_page);
		ManualRegistry.addChapter("furniture", part).addPages(colorizer_page, brush_page, table_page, chair_page, stool_page, counter_page, doors_page);
		ManualRegistry.addChapter("other", part).addPages(fence_page, fenceGate_page, wall_page);
		ManualRegistry.addChapter("fires", part).addPages(burningWood_page, fireplace_page, chimney_page, stove_page, firepit_page, firering_page);
		ManualRegistry.addChapter("grill", part).addPages(grill_page);
		ManualRegistry.addChapter("lamps", part).addPages(lamps_page);
		ManualRegistry.addChapter("superslopes", part).addPages(slopeInfo_page, slope_page, slopedAngle_page, slantedCorner_page, corner_page, obliqueSlope_page, slopedIntersection_page, stairs_page, pyramid_page, fullPyramid_page, slopedPost_page, pruners_page);
	}

}
