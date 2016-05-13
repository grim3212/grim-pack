package com.grim3212.mc.pack.industry.tile;

import java.util.List;

import com.grim3212.mc.pack.industry.block.BlockSensor;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
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

			int side = ((EnumFacing) state.getValue(BlockSensor.FACING)).getIndex();
			int i1 = 0;
			int j1 = 0;
			int k1 = 0;
			if (side == 0) {
				j1 = -1;
				do {
					if (j1 < -15) {
						break;
					}
					Block block = worldObj.getBlockState(pos.up(j1)).getBlock();
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
					Block block = worldObj.getBlockState(pos.up(j1)).getBlock();
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
					Block j2 = worldObj.getBlockState(pos.south(k1)).getBlock();
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
					Block k2 = worldObj.getBlockState(pos.south(k1)).getBlock();
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
					Block l2 = worldObj.getBlockState(pos.east(i1)).getBlock();
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
					Block i3 = worldObj.getBlockState(pos.east(i1)).getBlock();
					if (isTraversable(i3)) {
						i1--;
						break;
					}
					i1++;
				} while (true);
			}

			List<Entity> list = worldObj.getEntitiesWithinAABB(triggerType, this.getBlockType().getCollisionBoundingBox(worldObj, pos, state).addCoord(i1, j1, k1));

			if (list.isEmpty()) {
				worldObj.setBlockState(pos, state.withProperty(BlockSensor.ACTIVE, false));
			} else if (!list.isEmpty() && !(Boolean) state.getValue(BlockSensor.ACTIVE)) {
				worldObj.setBlockState(pos, state.withProperty(BlockSensor.ACTIVE, true));
			}
		}
	}

	public boolean isTraversable(Block block) {
		if (block == Blocks.lava || block == Blocks.flowing_lava || block == Blocks.mob_spawner || (block != null && block == this.getBlockType())) {
			return true;
		}
		if (block != null && block.getMaterial() != Material.air && block.isOpaqueCube()) {
			return true;
		}
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("type", type);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.type = compound.getInteger("type");
	}

	@Override
	public Packet<INetHandlerPlayClient> getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new S35PacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
}
