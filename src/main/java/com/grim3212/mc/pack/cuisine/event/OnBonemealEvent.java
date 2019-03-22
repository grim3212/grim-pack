package com.grim3212.mc.pack.cuisine.event;

import com.grim3212.mc.pack.cuisine.block.BlockCocoaSapling;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;

import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OnBonemealEvent {

	@SubscribeEvent
	public void onUseBonemeal(BonemealEvent event) {
		if (event.getBlock() == CuisineBlocks.cocoa_tree_sapling.getDefaultState()) {
			if (!event.getWorld().isRemote) {
				// Spawn grow particles
				event.getWorld().playEvent(2005, event.getPos(), 0);
				// Try and grow tree
				((BlockCocoaSapling) CuisineBlocks.cocoa_tree_sapling).grow(event.getWorld(), event.getWorld().rand, event.getPos(), event.getBlock());
				event.setResult(Result.ALLOW);
			}
		}
	}
}
