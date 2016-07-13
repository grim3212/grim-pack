package com.grim3212.mc.pack.decor.entity;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.part.IPartEntities;
import com.grim3212.mc.pack.core.util.Utils;

import net.minecraftforge.fml.common.registry.EntityRegistry;

public class DecorEntities implements IPartEntities {

	@Override
	public void initEntities() {
		EntityRegistry.registerModEntity(EntityFrame.class, "Frames", Utils.entityID++, GrimPack.INSTANCE, 250, 2147483647, false);
		EntityRegistry.registerModEntity(EntityWallpaper.class, "Wallpaper", Utils.entityID++, GrimPack.INSTANCE, 250, 2147483647, false);
	}

}
