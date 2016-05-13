package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.entity.EntityRayw;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRayg extends Item {

	public ItemRayg() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if (playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(ToolsItems.energy_canister)) {
			playerIn.worldObj.playSoundAtEntity(playerIn, GrimTools.modID + ":raysh", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!worldIn.isRemote) {
				worldIn.spawnEntityInWorld(new EntityRayw(worldIn, playerIn));
			}
			if (!playerIn.capabilities.isCreativeMode)
				playerIn.inventory.addItemStackToInventory(new ItemStack(ToolsItems.empty_energy_canister));
		}

		return stack;
	}
}
