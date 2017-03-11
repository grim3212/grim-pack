package com.grim3212.mc.pack.tools.crafting;

import com.grim3212.mc.pack.tools.items.ItemBackpack;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

public class BackpackRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		ItemStack backpack = ItemStack.EMPTY;
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
					if (tmp.getItem() != ToolsItems.backpack) {
						return false;
					}

					if (!backpack.isEmpty()) {
						return false;
					}

					backpack = tmp;
				}
			}
		}

		if (!backpack.isEmpty() && !dye.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack dye = ItemStack.EMPTY;
		ItemStack backpack = ItemStack.EMPTY;

		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack tmp = inv.getStackInSlot(i);

			if (!tmp.isEmpty()) {
				if (OreDictionary.containsMatch(false, OreDictionary.getOres("dye"), new ItemStack(tmp.getItem(), 1, OreDictionary.WILDCARD_VALUE)) || tmp.getItem() == Items.WATER_BUCKET) {
					dye = tmp;
				} else if (tmp.getItem() == ToolsItems.backpack) {
					backpack = tmp.copy();
				}
			}
		}

		if (backpack.isEmpty()) {
			return backpack;
		} else {
			if (dye.getItem() == Items.WATER_BUCKET) {
				return ItemBackpack.setColor(backpack, -1);
			} else {
				String[] dyes = { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };
				for (int i = 0; i < dyes.length; i++) {
					if (OreDictionary.containsMatch(false, OreDictionary.getOres("dye" + dyes[i]), dye)) {
						return ItemBackpack.setColor(backpack, i);
					}
				}
			}

			return backpack;
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