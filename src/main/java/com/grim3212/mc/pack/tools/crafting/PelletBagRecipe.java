package com.grim3212.mc.pack.tools.crafting;

import com.grim3212.mc.pack.tools.items.ItemPelletBag;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

public class PelletBagRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		ItemStack bag = ItemStack.EMPTY;
		ItemStack dye = ItemStack.EMPTY;

		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack tmp = inv.getStackInSlot(i);

			if (!tmp.isEmpty()) {
				if (tmp.getItem() == Items.WATER_BUCKET || OreDictionary.containsMatch(false, OreDictionary.getOres("dye"), new ItemStack(tmp.getItem(), 1, OreDictionary.WILDCARD_VALUE))) {
					if (!dye.isEmpty()) {
						return false;
					}

					dye = tmp;
				} else {
					if (tmp.getItem() != ToolsItems.pellet_bag) {
						return false;
					}

					if (!bag.isEmpty()) {
						return false;
					}

					bag = tmp;
				}
			}
		}

		if (!bag.isEmpty() && !dye.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack dye = ItemStack.EMPTY;
		ItemStack bag = ItemStack.EMPTY;

		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack tmp = inv.getStackInSlot(i);

			if (!tmp.isEmpty()) {
				if (OreDictionary.containsMatch(false, OreDictionary.getOres("dye"), new ItemStack(tmp.getItem(), 1, OreDictionary.WILDCARD_VALUE)) || tmp.getItem() == Items.WATER_BUCKET) {
					dye = tmp;
				} else if (tmp.getItem() == ToolsItems.pellet_bag) {
					bag = tmp.copy();
				}
			}
		}

		if (bag.isEmpty()) {
			return bag;
		} else {
			if (dye.getItem() == Items.WATER_BUCKET) {
				return ItemPelletBag.setColor(bag, -1);
			} else {
				String[] dyes = { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };
				for (int i = 0; i < dyes.length; i++) {
					if (OreDictionary.containsMatch(false, OreDictionary.getOres("dye" + dyes[i]), dye)) {
						return ItemPelletBag.setColor(bag, i);
					}
				}
			}

			return bag;
		}
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting invCrafting) {
		NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(invCrafting.getSizeInventory(), ItemStack.EMPTY);

		for (int i = 0; i < nonnulllist.size(); ++i) {
			ItemStack itemstack = invCrafting.getStackInSlot(i);
			nonnulllist.set(i, ForgeHooks.getContainerItem(itemstack));
		}

		return nonnulllist;
	}

}