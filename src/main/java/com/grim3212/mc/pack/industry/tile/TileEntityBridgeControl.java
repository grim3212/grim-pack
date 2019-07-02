package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.industry.block.BlockBridge;
import com.grim3212.mc.pack.industry.block.BlockBridgeControl;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.util.EnumBridgeType;

import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityBridgeControl extends TileEntityBridge implements ITickable {

	private EnumBridgeType type;
	private boolean removed = false;
	private int length = 0;
	private int checkTimer = 0;

	public TileEntityBridgeControl() {
		this.type = null;
	}

	public TileEntityBridgeControl(EnumBridgeType type) {
		this.type = type;
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT compound) {
		CompoundNBT tag = super.writeToNBT(compound);
		tag.setString("Type", this.type.name());
		tag.setInteger("Length", this.length);

		return tag;
	}

	@Override
	public void readFromNBT(CompoundNBT compound) {
		super.readFromNBT(compound);
		this.type = EnumBridgeType.valueOf(compound.getString("Type"));
		this.length = compound.getInteger("Length");
	}

	@Override
	public void update() {
		if (type == null) {
			return;
		}

		BlockState state = this.world.getBlockState(this.pos);
		if (state.getValue(BlockBridgeControl.ACTIVE)) {
			createBridge(state);
			removed = false;
		} else {
			if (!removed) {
				deleteBridge(state);
			}
		}
	}

	public void createBridge(BlockState state) {
		if (length < IndustryConfig.bridgeMaxLength) {
			tryBuild(state);
		}

		// Don't try and fill gaps every time
		// Shouldn't be gaps except in creative
		if (checkTimer == 1000) {
			checkTimer = 0;
			fillGaps(state);
		} else {
			checkTimer++;
		}
	}

	private void fillGaps(BlockState state) {
		for (int i = 1; i <= length; i++) {
			BlockPos newPos = pos.offset(state.getValue(DirectionalBlock.FACING), i);

			if (world.getBlockState(newPos) == IndustryBlocks.bridge.getDefaultState().withProperty(BlockBridge.TYPE, this.getType())) {
				continue;
			}

			if (world.isAirBlock(newPos)) {
				world.setBlockState(newPos, IndustryBlocks.bridge.getDefaultState().withProperty(BlockBridge.TYPE, this.getType()));

				TileEntity te = world.getTileEntity(newPos);
				if (te instanceof TileEntityBridge) {
					((TileEntityBridge) te).setBlockState(getBlockState());

					if (te instanceof TileEntityBridgeGravity) {
						((TileEntityBridgeGravity) te).setGravFacing(state.getValue(DirectionalBlock.FACING));
					}
				}
			} else {
				length = i;
				break;
			}
		}
	}

	private void tryBuild(BlockState state) {
		BlockPos newPos = pos.offset(state.getValue(DirectionalBlock.FACING), length + 1);

		if (BlockBridge.canLaserBreak(world, newPos, world.getBlockState(newPos).getBlock()) || world.getBlockState(newPos) == IndustryBlocks.bridge.getDefaultState().withProperty(BlockBridge.TYPE, this.getType())) {

			if (world.getBlockState(newPos) != IndustryBlocks.bridge.getDefaultState().withProperty(BlockBridge.TYPE, this.getType())) {
				world.setBlockState(newPos, IndustryBlocks.bridge.getDefaultState().withProperty(BlockBridge.TYPE, this.getType()));

				TileEntity te = world.getTileEntity(newPos);
				if (te instanceof TileEntityBridge) {
					((TileEntityBridge) te).setBlockState(getBlockState());
				}

			} else if (this.getType() == EnumBridgeType.GRAVITY) {
				TileEntity te = world.getTileEntity(newPos);

				if (te instanceof TileEntityBridgeGravity) {
					if (((TileEntityBridgeGravity) te).getGravFacing() != state.getValue(DirectionalBlock.FACING)) {
						// Stop going if we meet a different gravity bridge
						return;
					}
				}
			}

			if (this.getType() == EnumBridgeType.GRAVITY) {
				TileEntity te = world.getTileEntity(newPos);
				if (te instanceof TileEntityBridgeGravity) {
					((TileEntityBridgeGravity) te).setGravFacing(state.getValue(DirectionalBlock.FACING));
				}
			}

			// Offset value for next try
			length++;
		}
	}

	public void deleteBridge(BlockState state) {
		if (length == 0) {
			removed = true;
			return;
		}

		for (int i = 1; i <= length; i++) {
			BlockPos newPos = pos.offset(state.getValue(DirectionalBlock.FACING), i);
			if (world.getBlockState(newPos) == IndustryBlocks.bridge.getDefaultState().withProperty(BlockBridge.TYPE, this.getType()) || world.isAirBlock(newPos)) {
				world.setBlockToAir(newPos);
			}
		}

		length = 0;
	}

	public EnumBridgeType getType() {
		return type;
	}

	public void setType(EnumBridgeType type) {
		this.type = type;
	}
}
