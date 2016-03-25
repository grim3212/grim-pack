package com.grim3212.mc.decor.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;

public class TileEntityWallClock extends TileEntity implements ITickable {

	private int time = 0;

	public TileEntityWallClock() {
	}

	public int getTime() {
		return time;
	}

	private double field_94239_h;
	private double field_94240_i;

	@Override
	public void update() {
		Minecraft minecraft = Minecraft.getMinecraft();
		double d0 = 0.0D;

		if (minecraft.theWorld != null && minecraft.thePlayer != null) {
			float f = minecraft.theWorld.getCelestialAngle(1.0F);
			d0 = (double) f;

			if (!minecraft.theWorld.provider.isSurfaceWorld()) {
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

		d1 = MathHelper.clamp_double(d1, -1.0D, 1.0D);
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
			getWorld().markBlockForUpdate(getPos());
		}
	}
}