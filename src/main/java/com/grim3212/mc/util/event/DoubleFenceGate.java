package com.grim3212.mc.util.event;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DoubleFenceGate {

	private static boolean isFenceGate(World world, BlockPos pos, IBlockState state) {
		if (world.getBlockState(pos).getBlock() == state.getBlock()) {
			if ((EnumFacing) world.getBlockState(pos).getValue(BlockFenceGate.FACING) == (EnumFacing) state.getValue(BlockFenceGate.FACING)) {
				return true;
			}
		}
		return false;
	}

	public static void updateFenceGate(World world, BlockPos pos, IBlockState state) {

		state = state.withProperty(BlockFenceGate.OPEN, !state.getValue(BlockFenceGate.OPEN));

		// Get all fence gates above the block
		int upBlocks = 1;
		while (isFenceGate(world, pos.up(upBlocks), state)) {
			world.setBlockState(pos.up(upBlocks), state);
			upBlocks++;
		}

		// Get all fence gates below the block
		int downBlocks = 1;
		while (isFenceGate(world, pos.down(downBlocks), state)) {
			world.setBlockState(pos.down(downBlocks), state);
			downBlocks++;
		}

		// Play sound :)
		// world.playAuxSFXAtEntity((EntityPlayer) null, ((Boolean)
		// state.getValue(BlockFenceGate.OPEN)) ? 1003 : 1006, pos, 0);
	}
}
