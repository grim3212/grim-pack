package com.grim3212.mc.pack.util.event;

import com.grim3212.mc.pack.util.config.UtilConfig;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockChangeEvents {

	@SubscribeEvent
	public void playerInteract(RightClickBlock event) {
		if (UtilConfig.doubleDoors) {
			IBlockState state = event.getWorld().getBlockState(event.getPos());
			if (state.getBlock() instanceof BlockDoor) {
				BlockPos blockpos1 = state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER ? event.getPos() : event.getPos().down();
				IBlockState iblockstate1 = event.getPos().equals(blockpos1) ? state : event.getWorld().getBlockState(blockpos1);
				state = iblockstate1.cycleProperty(BlockDoor.OPEN);
				DoubleDoor.activateDoubleDoor(event.getWorld(), blockpos1, state);
			} else if (state.getBlock() instanceof BlockTrapDoor) {
				DoubleTrapDoor.activateDoubleTrap(event.getWorld(), event.getPos(), state, true);
			} else if (state.getBlock() instanceof BlockFenceGate) {
				DoubleFenceGate.updateFenceGate(event.getWorld(), event.getPos(), state);
			}
		}
	}

	@SubscribeEvent
	public void neighborNotifyEvent(BlockEvent.NeighborNotifyEvent event) {
		if (UtilConfig.infiniteLava) {
			if (event.getState().getMaterial() == Material.LAVA) {

				int adjacent = checkAdjacentBlock(event.getWorld(), event.getPos());

				if (adjacent >= 2) {
					event.getWorld().setBlockState(event.getPos(), Blocks.FLOWING_LAVA.getDefaultState(), 11);
				}

				for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
					BlockPos newPos = event.getPos().offset(enumfacing);

					if (event.getWorld().getBlockState(newPos).getMaterial() == Material.LAVA) {
						int adjacent2 = checkAdjacentBlock(event.getWorld(), newPos);

						if (adjacent2 >= 2) {
							event.getWorld().setBlockState(newPos, Blocks.FLOWING_LAVA.getDefaultState(), 11);
						}
					}
				}
			}
		}
	}

	private int checkAdjacentBlock(World worldIn, BlockPos pos) {
		int adjacentBlocks = 0;
		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			BlockPos newPos = pos.offset(enumfacing);

			if (this.getLevel(worldIn, newPos) == 0) {
				++adjacentBlocks;
			}
		}
		return adjacentBlocks;
	}

	private int getLevel(IBlockAccess worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getMaterial() == Material.LAVA ? worldIn.getBlockState(pos).getValue(BlockLiquid.LEVEL) : -1;
	}
}
