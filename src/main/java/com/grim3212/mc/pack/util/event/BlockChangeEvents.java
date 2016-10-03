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
			if (!event.getEntityPlayer().isSneaking()) {
				IBlockState state = event.getWorld().getBlockState(event.getPos());
				if (state.getBlock() instanceof BlockDoor) {
					DoubleDoor.activateDoubleDoor(event.getWorld(), event.getPos(), state, event.getEntityPlayer(), event.getHand(), event.getItemStack(), event.getFace(), event.getHitVec());
				} else if (state.getBlock() instanceof BlockTrapDoor) {
					DoubleTrapDoor.activateDoubleTrap(event.getWorld(), event.getPos(), state, true, event.getEntityPlayer(), event.getHand(), event.getItemStack(), event.getFace(), event.getHitVec());
				} else if (state.getBlock() instanceof BlockFenceGate) {
					DoubleFenceGate.updateFenceGate(event.getWorld(), event.getPos(), state, event.getEntityPlayer(), event.getHand(), event.getItemStack(), event.getFace(), event.getHitVec());
				}
			}
		}
	}

	@SubscribeEvent
	public void neighborNotifyEvent(BlockEvent.NeighborNotifyEvent event) {
		if (UtilConfig.infiniteLava) {
			if (event.getState().getMaterial() == Material.LAVA) {

				int adjacent = checkAdjacentBlock(event.getWorld(), event.getPos());

				if (adjacent >= 2) {
<<<<<<< HEAD
					event.getWorld().setBlockState(event.getPos(), Blocks.FLOWING_LAVA.getDefaultState(), 11);
=======
					event.getWorld().setBlockState(event.getPos(), Blocks.FLOWING_LAVA.getDefaultState());
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
				}

				for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
					BlockPos newPos = event.getPos().offset(enumfacing);

					if (event.getWorld().getBlockState(newPos).getMaterial() == Material.LAVA) {
						int adjacent2 = checkAdjacentBlock(event.getWorld(), newPos);

						if (adjacent2 >= 2) {
<<<<<<< HEAD
							event.getWorld().setBlockState(newPos, Blocks.FLOWING_LAVA.getDefaultState(), 11);
=======
							event.getWorld().setBlockState(newPos, Blocks.FLOWING_LAVA.getDefaultState());
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
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
