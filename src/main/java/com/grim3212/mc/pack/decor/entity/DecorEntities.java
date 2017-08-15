package com.grim3212.mc.pack.decor.entity;

import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.decor.config.DecorConfig;

public class DecorEntities {

	public static void initEntities() {
		if (DecorConfig.subpartFrames)
			Utils.registerEntity(EntityFrame.class, "Frames", 250, 2147483647, false);

		if (DecorConfig.subpartWallpaper)
			Utils.registerEntity(EntityWallpaper.class, "Wallpaper", 250, 2147483647, false);

		if (DecorConfig.subpartFlatItemFrame)
			Utils.registerEntity(EntityFlatItemFrame.class, "FlatItemFrame", 250, 2147483647, false);
	}

}
