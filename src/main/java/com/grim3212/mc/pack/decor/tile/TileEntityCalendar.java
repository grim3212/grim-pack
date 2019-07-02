package com.grim3212.mc.pack.decor.tile;

import com.grim3212.mc.pack.core.tile.TileEntityGrim;

import net.minecraft.nbt.CompoundNBT;

public class TileEntityCalendar extends TileEntityGrim {

	public TileEntityCalendar() {
		super(DecorTileEntities.CALENDAR);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		return super.write(compound);
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
	}
}
