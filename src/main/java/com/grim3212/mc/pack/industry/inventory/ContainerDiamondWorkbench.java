package com.grim3212.mc.pack.industry.inventory;

import java.util.Iterator;

import com.grim3212.mc.pack.industry.config.IndustryConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerDiamondWorkbench extends ContainerWorkbench {

	private World worldObj;

	public ContainerDiamondWorkbench(InventoryPlayer invPlayer, World world, BlockPos pos) {
		super(invPlayer, world, pos);
		this.worldObj = world;
	}

	@Override
	public void onCraftMatrixChanged(IInventory iinventory) {
		ItemStack found = CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj);

		if (IndustryConfig.useWhitelist) {
			Iterator<ItemStack> itr = IndustryConfig.workbenchUpgradeList.iterator();

			while (itr.hasNext()) {
				ItemStack stack = itr.next();

				if (ItemStack.areItemsEqual(found, stack)) {
					this.craftResult.setInventorySlotContents(0, found);
					break;
				}
			}
		} else {
			Iterator<ItemStack> itr = IndustryConfig.workbenchUpgradeList.iterator();

			boolean blacklisted = false;

			while (itr.hasNext()) {
				ItemStack stack = itr.next();

				if (ItemStack.areItemsEqual(found, stack)) {
					blacklisted = true;
					break;
				}
			}

			if (!blacklisted) {
				this.craftResult.setInventorySlotContents(0, found);
			}
		}

		if (craftResult.getStackInSlot(0) != null) {
			if (IndustryConfig.useWorkbenchUpgrades)
				if (craftResult.getStackInSlot(0).stackSize >= 32) {
					craftResult.getStackInSlot(0).stackSize = 127;
				} else {
					craftResult.getStackInSlot(0).stackSize *= 4;
				}
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return true;
	}
}
