package com.grim3212.mc.pack.cuisine.event;

import com.grim3212.mc.pack.cuisine.item.CuisineItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DropEvent {

	@SubscribeEvent
	public void dropEvent(HarvestDropsEvent event) {
		if (event.getState().getBlock() == Blocks.CACTUS) {
			if (event.getWorld().getRandom().nextInt(3) == 0)
				event.getDrops().add(new ItemStack(CuisineItems.dragon_fruit, event.getWorld().getRandom().nextInt(2) + 1));
		}
	}
}
