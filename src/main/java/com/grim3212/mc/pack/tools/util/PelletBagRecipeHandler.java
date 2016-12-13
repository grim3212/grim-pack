package com.grim3212.mc.pack.tools.util;

import com.grim3212.mc.pack.tools.items.ItemPelletBag;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

public class PelletBagRecipeHandler implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inventoryCrafting, World world) {
		boolean colorFound = false;
		boolean backpackFound = false;
		for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
			if (!inventoryCrafting.getStackInSlot(i).isEmpty()) {
				for (int j = 0; j < OreDictionary.getOres("dye").size(); j++) {
					if (inventoryCrafting.getStackInSlot(i).getItem() == OreDictionary.getOres("dye").get(j).getItem() || inventoryCrafting.getStackInSlot(i).getItem() == Items.WATER_BUCKET) {
						if (colorFound) {
							return false;
						} else {
							colorFound = true;
						}
					} else if (inventoryCrafting.getStackInSlot(i).getItem() == ToolsItems.pellet_bag) {
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
		ItemStack itemStack = ItemStack.EMPTY;
		for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
			if (!inventoryCrafting.getStackInSlot(i).isEmpty()) {
				for (int j = 0; j < OreDictionary.getOres("dye").size(); j++) {
					if (inventoryCrafting.getStackInSlot(i).getItem() == OreDictionary.getOres("dye").get(j).getItem()) {
						color = MathHelper.clamp(inventoryCrafting.getStackInSlot(i).getItemDamage(), 0, 15);
					} else if (inventoryCrafting.getStackInSlot(i).getItem() == Items.WATER_BUCKET) {
						color = -1;
					} else if (inventoryCrafting.getStackInSlot(i).getItem() == ToolsItems.pellet_bag) {
						itemStack = inventoryCrafting.getStackInSlot(i);
					} else {
						return ItemStack.EMPTY;
					}
				}
			}

		}
		ItemStack tmpStack = ItemStack.EMPTY;
		if (!itemStack.isEmpty()) {
			tmpStack = itemStack.copy();
			if (itemStack.getItem() == ToolsItems.pellet_bag) {
				ItemPelletBag.setColor(tmpStack, color);
			}
		} else {
			return ItemStack.EMPTY;
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
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting invCrafting) {
		NonNullList<ItemStack> itemsLeft = NonNullList.create();

		// TODO: Might need some work
		for (int i = 0; i < invCrafting.getSizeInventory(); ++i) {
			ItemStack itemstack = invCrafting.getStackInSlot(i);
			itemsLeft.add(ForgeHooks.getContainerItem(itemstack));
		}

		return itemsLeft;
	}

}