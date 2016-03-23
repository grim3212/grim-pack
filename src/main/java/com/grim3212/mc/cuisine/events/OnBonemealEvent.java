package com.grim3212.mc.cuisine.events;

import com.grim3212.mc.cuisine.block.BlockCocoaSapling;
import com.grim3212.mc.cuisine.block.CuisineBlocks;

import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OnBonemealEvent {

	@SubscribeEvent
	public void onUseBonemeal(BonemealEvent event) {
		if (event.block == CuisineBlocks.cocoa_tree_sapling.getDefaultState()) {
			if (!event.world.isRemote) {
				((BlockCocoaSapling) CuisineBlocks.cocoa_tree_sapling).generateTree(event.world, event.pos, event.block, event.world.rand);
				event.setResult(Result.ALLOW);
			}
		}
	}
}
