package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.tools.entity.EntitySlingpellet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSlingshot extends Item {

	public ItemSlingshot() {
		this.maxStackSize = 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode && !player.inventory.hasItem(ToolsItems.sling_pellet)) {
			return itemstack;
		} else {
			player.inventory.consumeInventoryItem(ToolsItems.sling_pellet);
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote) {
				world.spawnEntityInWorld(new EntitySlingpellet(world, player));
			}

			return itemstack;
		}
	}
}
