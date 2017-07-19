package com.grim3212.mc.pack.decor.entity;

import com.grim3212.mc.pack.core.util.Utils;

public class DecorEntities {

	public static void initEntities() {
		Utils.registerEntity(EntityFrame.class, "Frames", 250, 2147483647, false);
		Utils.registerEntity(EntityWallpaper.class, "Wallpaper", 250, 2147483647, false);
		Utils.registerEntity(EntityFlatItemFrame.class, "FlatItemFrame", 250, 2147483647, false);
	}

}
