package com.grim3212.mc.pack.industry.tile;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.block.BlockFan;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.tools.entity.EntityBlockPushPull;

import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityFan extends TileEntity implements ITickable {

	private int range;
	private FanMode mode;
	private FanMode oldMode;
	private Random rand;

	public TileEntityFan() {
		this.range = 1;
		this.mode = FanMode.BLOW;
		this.oldMode = FanMode.BLOW;
		this.rand = new Random();
	}

	@Override
	public void update() {
		BlockState state = world.getBlockState(pos);

		if (mode != FanMode.OFF) {

			Direction facing = state.getValue(BlockFan.FACING);

			int i1 = 0;
			int j1 = 0;
			int k1 = 0;
			int io = 0;
			int jo = 0;
			int ko = 0;

			switch (facing) {
			case UP:
				jo = 1;
				j1 = 1;
				do {
					if (j1 > range) {
						j1--;
						break;
					}
					if (!getIsPassable(world.getBlockState(pos.up(j1)))) {
						j1--;
						break;
					}
					j1++;
				} while (true);
				break;
			case DOWN:
				jo = -1;
				j1 = -1;
				do {
					if (j1 < -range) {
						j1++;
						break;
					}
					if (!getIsPassable(world.getBlockState(pos.up(j1)))) {
						j1++;
						break;
					}
					j1--;
				} while (true);
				break;
			case NORTH:
				ko = -1;
				k1 = -1;
				do {
					if (k1 < -range) {
						k1++;
						break;
					}
					if (!getIsPassable(world.getBlockState(pos.south(k1)))) {
						k1++;
						break;
					}
					k1--;
				} while (true);
				break;
			case SOUTH:
				ko = 1;
				k1 = 1;
				do {
					if (k1 > range) {
						k1--;
						break;
					}
					if (!getIsPassable(world.getBlockState(pos.south(k1)))) {
						k1--;
						break;
					}
					k1++;
				} while (true);
				break;
			case WEST:
				io = -1;
				i1 = -1;
				do {
					if (i1 < -range) {
						i1++;
						break;
					}
					if (!getIsPassable(world.getBlockState(pos.east(i1)))) {
						i1++;
						break;
					}
					i1--;
				} while (true);
				break;
			case EAST:
				io = 1;
				i1 = 1;
				do {
					if (i1 > range) {
						i1--;
						break;
					}
					if (!getIsPassable(world.getBlockState(pos.east(i1)))) {
						i1--;
						break;
					}
					i1++;
				} while (true);
				break;
			}
			if (mode == FanMode.SUCK) {
				io *= -1;
				jo *= -1;
				ko *= -1;
			}

			List<Entity> list = world.getEntitiesWithinAABB(Entity.class, state.getCollisionBoundingBox(world, pos).offset(pos).expand(i1, j1, k1));
			Iterator<Entity> iterator = list.iterator();
			do {
				if (!iterator.hasNext()) {
					break;
				}
				Entity entity = iterator.next();
				if (!(entity instanceof FallingBlockEntity) && !(entity instanceof EntityBlockPushPull)) {
					double d = (entity.getDistance((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D)) - 1;
					d = 1 - (d / range);
					double multiplier = range;
					multiplier = multiplier > 8 ? 8 : multiplier;
					double power = 0.070000000000000007D * multiplier;
					power = power * d;

					if (facing == Direction.UP && mode == FanMode.BLOW) {
						entity.fallDistance = 0F;
					}
					entity.addVelocity(power * io, power * jo, power * ko);
				}
			} while (true);

			if (world.isRemote) {
				if (IndustryConfig.showFanParticles) {
					BlockPos particlePos = pos.offset(facing);

					if (mode == FanMode.BLOW) {
						for (int ii = 1; ii <= range / 4 + 1; ii++) {
							GrimIndustry.proxy.airParticles(world, particlePos.getX() + rand.nextDouble(), particlePos.getY() + rand.nextDouble(), particlePos.getZ() + rand.nextDouble(), io, jo, ko, this);
						}
					} else if (mode == FanMode.SUCK) {
						for (int ii = 1; ii <= range / 4 + 1; ii++) {
							GrimIndustry.proxy.airParticles(world, particlePos.getX() + rand.nextDouble() + (i1 * rand.nextDouble()), particlePos.getY() + rand.nextDouble() + (j1 * rand.nextDouble()), particlePos.getZ() + rand.nextDouble() + (k1 * rand.nextDouble()), io, jo, ko, this);
						}
					}
				}
			}
		}
	}

	public boolean getIsPassable(BlockState state) {
		if (state.getMaterial() == Material.GLASS || state.getMaterial() == Material.WATER || state.getMaterial() == Material.LAVA || state.getMaterial() == Material.ICE || state.getBlock() == Blocks.MOB_SPAWNER) {
			return false;
		}
		if (state != null && state != Material.AIR && state.isOpaqueCube()) {
			return false;
		}
		return true;
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return writeToNBT(new CompoundNBT());
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		readFromNBT(tag);
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT compound) {
		super.writeToNBT(compound);
		compound.setInteger("Range", range);
		compound.setInteger("Mode", mode.ordinal());
		compound.setInteger("OldMode", oldMode.ordinal());
		return compound;
	}

	@Override
	public void readFromNBT(CompoundNBT compound) {
		super.readFromNBT(compound);
		this.range = compound.getInteger("Range");
		this.mode = FanMode.VALUES[compound.getInteger("Mode")];
		this.oldMode = FanMode.VALUES[compound.getInteger("OldMode")];
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbtTagCompound = new CompoundNBT();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SUpdateTileEntityPacket(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public FanMode getMode() {
		return mode;
	}

	public void setMode(FanMode mode) {
		this.mode = mode;
	}

	public FanMode getOldMode() {
		return oldMode;
	}

	public void setOldMode(FanMode oldMode) {
		this.oldMode = oldMode;
	}

	public enum FanMode implements IStringSerializable {
		BLOW, SUCK, OFF;

		public static FanMode[] VALUES = FanMode.values();

		public FanMode getNext() {
			// Check if the next value is the last value
			// If so go back to the first value
			if ((this.ordinal() + 2) == VALUES.length)
				return VALUES[0];

			return VALUES[(this.ordinal() + 1) % VALUES.length];
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}
	}
}
