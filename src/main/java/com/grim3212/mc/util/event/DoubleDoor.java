package com.grim3212.mc.util.event;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DoubleDoor {

	public static void activateDoubleDoor(World world, BlockPos pos, IBlockState state) {
		int coordX = 0;
		int coordZ = 0;
		int coordOffset = isHingeLeft(BlockDoor.combineMetadata(world, pos)) ? -1 : 1;

		switch (((EnumFacing) state.getValue(BlockDoor.FACING)).getHorizontalIndex()) {
		case 0:
			coordX = pos.getX() - coordOffset;
			coordZ = pos.getZ();
			break;
		case 1:
			coordX = pos.getX();
			coordZ = pos.getZ() - coordOffset;
			break;
		case 2:
			coordX = pos.getX() + coordOffset;
			coordZ = pos.getZ();
			break;
		case 3:
			coordX = pos.getX();
			coordZ = pos.getZ() + coordOffset;
			break;
		}

		BlockPos neighborPos = new BlockPos(coordX, pos.getY(), coordZ);
		IBlockState neighborState = world.getBlockState(neighborPos);

		if (!(neighborState.getBlock() instanceof BlockDoor)) {
			System.out.println("oops1");
			return;
		}

		if ((Boolean) state.getValue(BlockDoor.OPEN) == BlockDoor.isOpen(world, neighborPos)) {
			System.out.println("oops");
			return;
		}

		world.setBlockState(neighborPos, neighborState.withProperty(BlockDoor.OPEN, (Boolean) state.getValue(BlockDoor.OPEN)), 2);
	}

	protected static boolean isHingeLeft(int combinedMeta) {
		return (combinedMeta & 16) != 0;
	}
}
