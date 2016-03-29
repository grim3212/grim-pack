package com.grim3212.mc.decor.tile;

import com.grim3212.mc.core.part.IPartTileEntities;
import com.grim3212.mc.decor.client.tile.TileEntityCalendarRenderer;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DecorTileEntities implements IPartTileEntities {

	@Override
	public void initTileEntities() {
		GameRegistry.registerTileEntity(TileEntityCalendar.class, "calendar");
		GameRegistry.registerTileEntity(TileEntityWallClock.class, "wall_clock");
	}

	@Override
	public void renderTileEntities() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalendar.class, new TileEntityCalendarRenderer());
	}

}
