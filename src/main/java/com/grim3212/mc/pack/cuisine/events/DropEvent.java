package com.grim3212.mc.pack.cuisine.events;

import com.grim3212.mc.pack.cuisine.item.CuisineItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DropEvent {

	@SubscribeEvent
	public void dropEvent(HarvestDropsEvent event) {
		if (event.getState().getBlock() == Blocks.CACTUS) {
			if (event.getWorld().rand.nextInt(3) == 0)
				event.getDrops().add(new ItemStack(CuisineItems.dragon_fruit, event.getWorld().rand.nextInt(2) + 1));
		}
	}

}
