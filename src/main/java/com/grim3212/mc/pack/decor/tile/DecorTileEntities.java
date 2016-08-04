package com.grim3212.mc.pack.decor.tile;

import com.grim3212.mc.pack.core.part.IPartEntities.IPartTileEntities;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class DecorTileEntities implements IPartTileEntities {

	@Override
	public void initTileEntities() {
		GameRegistry.registerTileEntity(TileEntityCalendar.class, "calendar");
		GameRegistry.registerTileEntity(TileEntityWallClock.class, "wall_clock");
		GameRegistry.registerTileEntity(TileEntityGrill.class, "grill");
		GameRegistry.registerTileEntity(TileEntityColorizer.class, "colorizer");
	}
}
