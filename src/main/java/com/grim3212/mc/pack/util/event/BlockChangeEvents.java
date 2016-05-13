package com.grim3212.mc.pack.util.event;

import com.grim3212.mc.pack.util.config.UtilConfig;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockChangeEvents {

	@SubscribeEvent
	public void playerInteract(PlayerInteractEvent event) {
		if (UtilConfig.doubleDoors) {
			if (event.action == Action.RIGHT_CLICK_BLOCK) {
				IBlockState state = event.world.getBlockState(event.pos);
				if (state.getBlock() instanceof BlockDoor) {
					BlockPos blockpos1 = state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER ? event.pos : event.pos.down();
					IBlockState iblockstate1 = event.pos.equals(blockpos1) ? state : event.world.getBlockState(blockpos1);
					state = iblockstate1.cycleProperty(BlockDoor.OPEN);
					DoubleDoor.activateDoubleDoor(event.world, blockpos1, state);
				} else if (state.getBlock() instanceof BlockTrapDoor) {
					DoubleTrapDoor.activateDoubleTrap(event.world, event.pos, state, true);
				} else if (state.getBlock() instanceof BlockFenceGate) {
					System.out.println("fencegate");
					DoubleFenceGate.updateFenceGate(event.world, event.pos, state);
				}
			}
		}
	}

	@SubscribeEvent
	public void neighborNotifyEvent(BlockEvent.NeighborNotifyEvent event) {
		if (UtilConfig.infiniteLava) {
			if (event.state.getBlock() == Blocks.flowing_lava) {

				int adjacent = checkAdjacentBlock(event.world, event.pos);

				System.out.println(event.world.getBlockState(event.pos));
				System.out.println(adjacent);

				if (adjacent >= 2) {
					event.world.setBlockState(event.pos, Blocks.lava.getDefaultState());
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
		return worldIn.getBlockState(pos).getBlock().getMaterial() == Material.lava ? ((Integer) worldIn.getBlockState(pos).getValue(BlockLiquid.LEVEL)).intValue() : -1;
	}
}
