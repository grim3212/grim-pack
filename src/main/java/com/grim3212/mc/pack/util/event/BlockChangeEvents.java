package com.grim3212.mc.pack.util.event;

import com.grim3212.mc.pack.decor.block.BlockDecorDoor;
import com.grim3212.mc.pack.util.config.UtilConfig;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockChangeEvents {

	@SubscribeEvent
	public void playerInteract(RightClickBlock event) {
		if (UtilConfig.doubleDoors) {
			if (!event.getEntityPlayer().isSneaking()) {
				IBlockState state = event.getWorld().getBlockState(event.getPos());
				if (state.getBlock() instanceof BlockDoor || state.getBlock() instanceof BlockDecorDoor) {
					DoubleDoor.activateDoubleDoor(event.getWorld(), event.getPos(), state, event.getEntityPlayer(), event.getHand(), event.getFace(), event.getHitVec());
				} else if (state.getBlock() instanceof BlockTrapDoor) {
					DoubleTrapDoor.activateDoubleTrap(event.getWorld(), event.getPos(), state, true, event.getEntityPlayer(), event.getHand(), event.getFace(), event.getHitVec());
				} else if (state.getBlock() instanceof BlockFenceGate) {
					DoubleFenceGate.updateFenceGate(event.getWorld(), event.getPos(), state, event.getEntityPlayer(), event.getHand(), event.getFace(), event.getHitVec());
				}
			}
		}
	}

	@SubscribeEvent
	public void createInfiniteLava(BlockEvent.CreateFluidSourceEvent event) {
		if (UtilConfig.infiniteLava) {
			if (event.getState().getMaterial() == Material.LAVA) {
				event.setResult(Result.ALLOW);
			}
		}
	}
}
