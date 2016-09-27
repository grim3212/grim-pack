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
	public static Page fusRoDah_page;
	public static Page time_page;
	public static Page grave_page;
	public static Page doors_page;
	public static Page lava_page;

	@Override
	public void initPages() {
		autoItem_page = new PageImageText("info", "autoitem.png");
		fusRoDah_page = new PageImageText("info", "fusrodah.png");
		time_page = new PageImageText("info", "time.png");
		grave_page = new PageImageText("info", "grave.png");
		doors_page = new PageImageText("info", "doors.png");
		lava_page = new PageImageText("info", "lava.png");
	}

	@Override
	public void registerChapters(ManualPart part) {
		if (UtilConfig.enableAutoReplace)
			ManualRegistry.addChapter("autoitem", part).addPages(autoItem_page);
		if (UtilConfig.enableFusRoDah)
			ManualRegistry.addChapter("fusrodah", part).addPages(fusRoDah_page);
		ManualRegistry.addChapter("time", part).addPages(time_page);
		if (UtilConfig.spawnGraves)
			ManualRegistry.addChapter("grave", part).addPages(grave_page);
		if (UtilConfig.doubleDoors)
			ManualRegistry.addChapter("doors", part).addPages(doors_page);
		if (UtilConfig.infiniteLava)
			ManualRegistry.addChapter("lava", part).addPages(lava_page);
	}

}
