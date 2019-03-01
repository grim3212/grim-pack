package com.grim3212.mc.pack.decor.util;

import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.tile.TileEntityCage;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CageSpawnerLogic {

	private final TileEntityCage cage;
	/** The delay to spawn. */
	private int spawnDelay = 400;
	/** The rotation of the mob inside the mob spawner */
	private double mobRotation;
	/** the previous rotation of the mob inside the mob spawner */
	private double prevMobRotation;
	private int activatingRangeFromPlayer = 16;

	public CageSpawnerLogic(TileEntityCage cage) {
		this.cage = cage;
	}

	public void broadcastEvent(int id) {
		cage.getWorld().addBlockEvent(cage.getPos(), DecorBlocks.cage, id, 0);
	}

	public World getSpawnerWorld() {
		return cage.getWorld();
	}

	public BlockPos getSpawnerPosition() {
		return cage.getPos();
	}

	public Entity getEntity() {
		return cage.getPokeballEntity();
	}

	/**
	 * Returns true if there's a player close enough to this mob spawner to
	 * activate it.
	 */
	private boolean isActivated() {
		BlockPos blockpos = this.getSpawnerPosition();
		return this.getSpawnerWorld().isAnyPlayerWithinRangeAt((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D, (double) blockpos.getZ() + 0.5D, (double) this.activatingRangeFromPlayer);
	}

	public void updateSpawner() {
		if (!this.isActivated()) {
			this.prevMobRotation = this.mobRotation;
		} else {
			if (this.getEntity() != null)
				this.getEntity().onUpdate();

			if (this.getSpawnerWorld().isRemote) {
				this.prevMobRotation = this.mobRotation;
				this.mobRotation = (this.mobRotation + (double) (1000.0F / ((float) this.spawnDelay + 200.0F))) % 360.0D;
			}
		}

	}

	@OnlyIn(Dist.CLIENT)
	public double getMobRotation() {
		return this.mobRotation;
	}

	@OnlyIn(Dist.CLIENT)
	public double getPrevMobRotation() {
		return this.prevMobRotation;
	}
}
