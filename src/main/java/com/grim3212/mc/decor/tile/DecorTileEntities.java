package com.grim3212.mc.decor.tile;

import com.grim3212.mc.core.part.IPartEntities;
import com.grim3212.mc.decor.client.tile.TileEntityCalendarRenderer;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DecorTileEntities implements IPartEntities {

	@Override
	public void initEntities() {
		GameRegistry.registerTileEntity(TileEntityCalendar.class, "calendar");
		GameRegistry.registerTileEntity(TileEntityWallClock.class, "wall_clock");
	}

	@Override
	public void renderEntities() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalendar.class, new TileEntityCalendarRenderer());
	}

}
