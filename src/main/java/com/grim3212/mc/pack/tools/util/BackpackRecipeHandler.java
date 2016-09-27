package com.grim3212.mc.pack.tools.util;

import com.grim3212.mc.pack.tools.items.ItemBackpack;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BackpackRecipeHandler implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inventoryCrafting, World world) {
		boolean colorFound = false;
		boolean backpackFound = false;
		for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
			if (inventoryCrafting.getStackInSlot(i) != null) {
				for (int j = 0; j < OreDictionary.getOres("dye").size(); j++) {
					if (inventoryCrafting.getStackInSlot(i).getItem() == OreDictionary.getOres("dye").get(j).getItem() || inventoryCrafting.getStackInSlot(i).getItem() == Items.WATER_BUCKET) {
						if (colorFound) {
							return false;
						} else {
							colorFound = true;
						}
					} else if (inventoryCrafting.getStackInSlot(i).getItem() == ToolsItems.backpack) {
						if (backpackFound) {
							return false;
						} else {
							backpackFound = true;
						}
					}
				}
			}
		}
		if (colorFound && backpackFound) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
		int color = -1;
		ItemStack itemStack = null;
		for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
			if (inventoryCrafting.getStackInSlot(i) != null) {
				for (int j = 0; j < OreDictionary.getOres("dye").size(); j++) {
					if (inventoryCrafting.getStackInSlot(i).getItem() == OreDictionary.getOres("dye").get(j).getItem()) {
						color = MathHelper.clamp_int(inventoryCrafting.getStackInSlot(i).getItemDamage(), 0, 15);
					} else if (inventoryCrafting.getStackInSlot(i).getItem() == Items.WATER_BUCKET) {
						color = -1;
					} else if (inventoryCrafting.getStackInSlot(i).getItem() == ToolsItems.backpack) {
						itemStack = inventoryCrafting.getStackInSlot(i);
					} else {
						return null;
					}
				}
			}

		}
		ItemStack tmpStack = null;
		if (itemStack != null) {
			tmpStack = itemStack.copy();
			if (itemStack.getItem() == ToolsItems.backpack) {
				ItemBackpack.setColor(tmpStack, color);
			}
		} else {
			return null;
		}
		return tmpStack;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting invCrafting) {
		ItemStack[] aitemstack = new ItemStack[invCrafting.getSizeInventory()];

		for (int i = 0; i < aitemstack.length; ++i) {
			ItemStack itemstack = invCrafting.getStackInSlot(i);
			aitemstack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
		}

		return aitemstack;
	}

}