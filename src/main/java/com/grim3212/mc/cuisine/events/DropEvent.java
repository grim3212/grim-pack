package com.grim3212.mc.cuisine.events;

import com.grim3212.mc.cuisine.item.CuisineItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DropEvent {

	@SubscribeEvent
	public void dropEvent(HarvestDropsEvent event) {
		if (event.state.getBlock() == Blocks.cactus) {
			if (event.world.rand.nextInt(3) == 0)
				event.drops.add(new ItemStack(CuisineItems.dragon_fruit, event.world.rand.nextInt(2) + 1));
		}
	}

}
