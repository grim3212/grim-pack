package com.grim3212.mc.pack.util.event;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DoubleTrapDoor {

	private static final NeighbourBlocks[] _neighbour_blocks = { new NeighbourBlocks(new RelBlockCoord(0, -1), new RelBlockCoord(1, 0), new RelBlockCoord(-1, 0)), new NeighbourBlocks(new RelBlockCoord(0, 1), new RelBlockCoord(-1, 0), new RelBlockCoord(1, 0)), new NeighbourBlocks(new RelBlockCoord(-1, 0), new RelBlockCoord(0, -1), new RelBlockCoord(0, 1)), new NeighbourBlocks(new RelBlockCoord(1, 0), new RelBlockCoord(0, 1), new RelBlockCoord(0, -1)) };

	public static void activateDoubleTrap(World world, BlockPos pos, IBlockState state, boolean include_left_right, EntityPlayer entityPlayer, EnumHand enumHand, EnumFacing facing, Vec3d vec3d) {
		NeighbourBlocks neighbour_blocks = _neighbour_blocks[getMetaForFacing(state.getValue(BlockTrapDoor.FACING))];

		int coord_x = pos.getX() + neighbour_blocks.counterpart.x;
		int coord_z = pos.getZ() + neighbour_blocks.counterpart.z;
		BlockPos neighborPos = new BlockPos(coord_x, pos.getY(), coord_z);
		IBlockState neighborState = world.getBlockState(neighborPos);

		if (isValidNeighbor(world, pos, neighborPos)) {
			activateDoubleTrap(world, neighborPos, state, false, entityPlayer, enumHand, facing, vec3d);
			neighborState.getBlock().onBlockActivated(world, neighborPos, neighborState, entityPlayer, enumHand, facing, (float) vec3d.x, (float) vec3d.y, (float) vec3d.z);
		}

		if (include_left_right) {
			int left_x = pos.getX() + neighbour_blocks.left.x;
			int left_z = pos.getZ() + neighbour_blocks.left.z;
			BlockPos leftPos = new BlockPos(left_x, pos.getY(), left_z);

			int right_x = pos.getX() + neighbour_blocks.right.x;
			int right_z = pos.getZ() + neighbour_blocks.right.z;
			BlockPos rightPos = new BlockPos(right_x, pos.getY(), right_z);

			if ((!isValidNeighbor(world, pos, leftPos)) && (isValidNeighbor(world, pos, rightPos))) {
				left_x = right_x + neighbour_blocks.right.x;
				left_z = right_z + neighbour_blocks.right.z;
				leftPos = new BlockPos(left_x, pos.getY(), left_z);
			} else if ((!isValidNeighbor(world, pos, rightPos)) && (isValidNeighbor(world, pos, leftPos))) {
				right_x = left_x + neighbour_blocks.left.x;
				right_z = left_z + neighbour_blocks.left.z;
				rightPos = new BlockPos(right_x, pos.getY(), right_z);
			}

			IBlockState leftState = world.getBlockState(leftPos);
			if (isValidNeighbor(world, pos, leftPos)) {
				activateDoubleTrap(world, leftPos, state, false, entityPlayer, enumHand, facing, vec3d);
				leftState.getBlock().onBlockActivated(world, leftPos, leftState, entityPlayer, enumHand, facing, (float) vec3d.x, (float) vec3d.y, (float) vec3d.z);
			}

			IBlockState rightState = world.getBlockState(rightPos);
			if (isValidNeighbor(world, pos, rightPos)) {
				activateDoubleTrap(world, rightPos, state, false, entityPlayer, enumHand, facing, vec3d);
				rightState.getBlock().onBlockActivated(world, rightPos, rightState, entityPlayer, enumHand, facing, (float) vec3d.x, (float) vec3d.y, (float) vec3d.z);
			}
		}
	}

	public static void setDoubleTrap(World world, BlockPos pos, IBlockState state, boolean include_left_right) {
		NeighbourBlocks neighbour_blocks = _neighbour_blocks[getMetaForFacing(state.getValue(BlockTrapDoor.FACING))];

		int coord_x = pos.getX() + neighbour_blocks.counterpart.x;
		int coord_z = pos.getZ() + neighbour_blocks.counterpart.z;
		BlockPos neighborPos = new BlockPos(coord_x, pos.getY(), coord_z);
		IBlockState neighborState = world.getBlockState(neighborPos);

		if (isValidNeighbor(world, pos, neighborPos)) {
			setDoubleTrap(world, neighborPos, state, false);
			world.setBlockState(neighborPos, neighborState.withProperty(BlockTrapDoor.OPEN, state.getValue(BlockTrapDoor.OPEN)));
		}

		if (include_left_right) {
			int left_x = pos.getX() + neighbour_blocks.left.x;
			int left_z = pos.getZ() + neighbour_blocks.left.z;
			BlockPos leftPos = new BlockPos(left_x, pos.getY(), left_z);

			int right_x = pos.getX() + neighbour_blocks.right.x;
			int right_z = pos.getZ() + neighbour_blocks.right.z;
			BlockPos rightPos = new BlockPos(right_x, pos.getY(), right_z);

			if ((!isValidNeighbor(world, pos, leftPos)) && (isValidNeighbor(world, pos, rightPos))) {
				left_x = right_x + neighbour_blocks.right.x;
				left_z = right_z + neighbour_blocks.right.z;
				leftPos = new BlockPos(left_x, pos.getY(), left_z);
			} else if ((!isValidNeighbor(world, pos, rightPos)) && (isValidNeighbor(world, pos, leftPos))) {
				right_x = left_x + neighbour_blocks.left.x;
				right_z = left_z + neighbour_blocks.left.z;
				rightPos = new BlockPos(right_x, pos.getY(), right_z);
			}

			IBlockState leftState = world.getBlockState(leftPos);
			if (isValidNeighbor(world, pos, leftPos)) {
				setDoubleTrap(world, leftPos, state, false);
				world.setBlockState(leftPos, leftState.withProperty(BlockTrapDoor.OPEN, state.getValue(BlockTrapDoor.OPEN)));
			}

			IBlockState rightState = world.getBlockState(rightPos);
			if (isValidNeighbor(world, pos, rightPos)) {
				setDoubleTrap(world, rightPos, state, false);
				world.setBlockState(rightPos, rightState.withProperty(BlockTrapDoor.OPEN, state.getValue(BlockTrapDoor.OPEN)));
			}
		}
	}

	protected static int getMetaForFacing(EnumFacing facing) {
		switch (facing) {
		case NORTH:
			return 0;
		case SOUTH:
			return 1;
		case WEST:
			return 2;
		case EAST:
		default:
			return 3;
		}
	}

	private static boolean isValidNeighbor(World world, BlockPos source, BlockPos neighbor) {
		IBlockState source_state = world.getBlockState(source);
		IBlockState source_neighbor = world.getBlockState(neighbor);

		if ((source_neighbor.getBlock() == source_state.getBlock()) && (source_state.getValue(BlockTrapDoor.HALF) == source_neighbor.getValue(BlockTrapDoor.HALF)) && source_state.getValue(BlockTrapDoor.OPEN) == source_neighbor.getValue(BlockTrapDoor.OPEN)) {
			return true;
		}

		return false;
	}

	private static class NeighbourBlocks {
		public final RelBlockCoord counterpart;
		public final RelBlockCoord left;
		public final RelBlockCoord right;

		public NeighbourBlocks(RelBlockCoord cp, RelBlockCoord l, RelBlockCoord r) {
			this.counterpart = cp;
			this.left = l;
			this.right = r;
		}
	}

	private static class RelBlockCoord {
		public final int x;
		public final int z;

		public RelBlockCoord(int new_x, int new_z) {
			this.x = new_x;
			this.z = new_z;
		}
	}

}
