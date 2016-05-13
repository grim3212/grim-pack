package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.entity.EntityAdvRayw;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAdvRayg extends Item {

	public ItemAdvRayg() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if (playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(ToolsItems.advanced_energy_canister)) {
			worldIn.playSoundAtEntity(playerIn, GrimTools.modID + ":raysh", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!worldIn.isRemote) {
				worldIn.spawnEntityInWorld(new EntityAdvRayw(worldIn, playerIn));
			}
			if (!playerIn.capabilities.isCreativeMode)
				playerIn.inventory.addItemStackToInventory(new ItemStack(ToolsItems.advanced_empty_energy_canister));
		}

		return stack;
	}
}
