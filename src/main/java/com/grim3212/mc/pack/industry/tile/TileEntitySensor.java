package com.grim3212.mc.pack.industry.tile;

import java.util.List;

import com.grim3212.mc.pack.industry.block.BlockSensor;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntitySensor extends TileEntity implements ITickable {

	private int type;

	public TileEntitySensor() {
	}

	public TileEntitySensor(int type) {
		this.type = type;
	}

	@Override
	public void update() {
		IBlockState state = this.getWorld().getBlockState(getPos());

		Class<? extends Entity> triggerType = null;
		if (type == 0) {
			triggerType = Entity.class;
		} else if (type == 1) {
			triggerType = EntityLivingBase.class;
		} else if (type == 2) {
			triggerType = EntityPlayer.class;
		} else if (type == 3) {
			triggerType = EntityMob.class;
		}

		if (state.getBlock() == IndustryBlocks.wooden_sensor || state.getBlock() == IndustryBlocks.iron_sensor || state.getBlock() == IndustryBlocks.stone_sensor || state.getBlock() == IndustryBlocks.netherrack_sensor) {
			int side = state.getValue(BlockSensor.FACING).getIndex();
			int i1 = 0;
			int j1 = 0;
			int k1 = 0;
			if (side == 0) {
				j1 = -1;
				do {
					if (j1 < -15) {
						break;
					}
					IBlockState block = worldObj.getBlockState(pos.up(j1));
					if (isTraversable(block)) {
						j1++;
						break;
					}
					j1--;
				} while (true);
			} else if (side == 1) {
				j1 = 1;
				do {
					if (j1 > 15) {
						break;
					}
					IBlockState block = worldObj.getBlockState(pos.up(j1));
					if (isTraversable(block)) {
						j1--;
						break;
					}
					j1++;
				} while (true);
			} else if (side == 2) {
				k1 = -1;
				do {
					if (k1 < -15) {
						break;
					}
					IBlockState j2 = worldObj.getBlockState(pos.south(k1));
					if (isTraversable(j2)) {
						k1++;
						break;
					}
					k1--;
				} while (true);
			} else if (side == 3) {
				k1 = 1;
				do {
					if (k1 > 15) {
						break;
					}
					IBlockState k2 = worldObj.getBlockState(pos.south(k1));
					if (isTraversable(k2)) {
						k1--;
						break;
					}
					k1++;
				} while (true);
			} else if (side == 4) {
				i1 = -1;
				do {
					if (i1 < -15) {
						break;
					}
					IBlockState l2 = worldObj.getBlockState(pos.east(i1));
					if (isTraversable(l2)) {
						i1++;
						break;
					}
					i1--;
				} while (true);
			} else if (side == 5) {
				i1 = 1;
				do {
					if (i1 > 15) {
						break;
					}
					IBlockState i3 = worldObj.getBlockState(pos.east(i1));
					if (isTraversable(i3)) {
						i1--;
						break;
					}
					i1++;
				} while (true);
			}

			List<Entity> list = worldObj.getEntitiesWithinAABB(triggerType, state.getCollisionBoundingBox(worldObj, pos).offset(pos).addCoord(i1, j1, k1));

			if (list.isEmpty()) {
				worldObj.setBlockState(pos, state.withProperty(BlockSensor.ACTIVE, false));
			} else if (!list.isEmpty() && !state.getValue(BlockSensor.ACTIVE)) {
				worldObj.setBlockState(pos, state.withProperty(BlockSensor.ACTIVE, true));
			}
		}
	}

	public boolean isTraversable(IBlockState block) {
		if (block.getBlock() == Blocks.LAVA || block.getBlock() == Blocks.FLOWING_LAVA || block.getBlock() == Blocks.MOB_SPAWNER || (block.getBlock() != null && block.getBlock() == this.getBlockType())) {
			return true;
		}
		if (block != null && block.getMaterial() != Material.AIR && block.isOpaqueCube()) {
			return true;
		}
		return false;
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("type", type);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.type = compound.getInteger("type");
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
}
