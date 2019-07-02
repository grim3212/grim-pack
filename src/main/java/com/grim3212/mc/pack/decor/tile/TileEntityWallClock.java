package com.grim3212.mc.pack.decor.tile;

import com.grim3212.mc.pack.core.tile.TileEntityGrim;
import com.grim3212.mc.pack.decor.block.BlockWallClock;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.MathHelper;

public class TileEntityWallClock extends TileEntityGrim implements ITickableTileEntity {

	private int time = 0;

	public TileEntityWallClock() {
		super(DecorTileEntities.WALL_CLOCK);
	}

	public int getTime() {
		return time;
	}

	private double field_94239_h;
	private double field_94240_i;

	@Override
	public void tick() {
		double d0 = 0.0D;

		if (getWorld() != null) {
			float f = getWorld().getCelestialAngle(1.0F);
			d0 = (double) f;

			if (!getWorld().getDimension().isSurfaceWorld()) {
				d0 = Math.random();
			}
		}

		double d1;

		for (d1 = d0 - field_94239_h; d1 < -0.5D; ++d1) {
			;
		}

		while (d1 >= 0.5D) {
			--d1;
		}

		d1 = MathHelper.clamp(d1, -1.0D, 1.0D);
		field_94240_i += d1 * 0.1D;
		field_94240_i *= 0.8D;
		field_94239_h += field_94240_i;

		int i;
		int numFrames = 64;
		for (i = (int) ((this.field_94239_h + 1.0D) * (double) numFrames) % numFrames; i < 0; i = (i + numFrames) % numFrames) {
			;
		}
		if (i != time) {
			time = i;
			BlockState state = this.getBlockState();
			this.getWorld().setBlockState(getPos(), state.with(BlockWallClock.TIME, getTime()), 2);
		}
	}
}