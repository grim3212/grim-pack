package com.grim3212.mc.pack.util.client;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageImageText;
import com.grim3212.mc.pack.util.config.UtilConfig;

public class ManualUtil implements IManualPart {

	public static ManualUtil INSTANCE = new ManualUtil();

	public static Page autoItem_page;
	public static Page autoTorch_page;
	public static Page fusRoDah_page;
	public static Page time_page;
	public static Page grave_page;
	public static Page doors_page;
	public static Page lava_page;

	@Override
	public void initPages() {
		if (UtilConfig.subpartAutoItemReplacer)
			autoItem_page = new PageImageText("info", "autoitem.png");

		if (UtilConfig.subpartAutoTorch)
			autoTorch_page = new PageImageText("info", "autotorch.png");

		if (UtilConfig.subpartFusRoDah)
			fusRoDah_page = new PageImageText("info", "fusrodah.png");

		if (UtilConfig.subpartTime)
			time_page = new PageImageText("info", "time.png");

		if (UtilConfig.subpartGraves)
			grave_page = new PageImageText("info", "grave.png");

		if (UtilConfig.subpartDoubleDoors)
			doors_page = new PageImageText("info", "doors.png");

		if (UtilConfig.subpartInfiniteLava)
			lava_page = new PageImageText("info", "lava.png");
	}

	@Override
	public void registerChapters(ManualPart part) {
		if (UtilConfig.subpartAutoItemReplacer)
			ManualRegistry.addChapter("autoitem", part).addPages(autoItem_page);
		if (UtilConfig.subpartAutoTorch)
			ManualRegistry.addChapter("autotorch", part).addPages(autoTorch_page);
		if (UtilConfig.subpartFusRoDah)
			ManualRegistry.addChapter("fusrodah", part).addPages(fusRoDah_page);
		if (UtilConfig.subpartTime)
			ManualRegistry.addChapter("time", part).addPages(time_page);
		if (UtilConfig.subpartGraves)
			ManualRegistry.addChapter("grave", part).addPages(grave_page);
		if (UtilConfig.subpartDoubleDoors)
			ManualRegistry.addChapter("doors", part).addPages(doors_page);
		if (UtilConfig.subpartInfiniteLava)
			ManualRegistry.addChapter("lava", part).addPages(lava_page);
	}
}