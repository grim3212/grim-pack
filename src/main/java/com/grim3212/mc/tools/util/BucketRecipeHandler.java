package com.grim3212.mc.tools.util;

import com.grim3212.mc.core.util.NBTHelper;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BucketRecipeHandler implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting invCrafting, World worldIn) {
		boolean foundWheat0 = false;
		boolean foundWheat1 = false;
		boolean foundWheat2 = false;
		boolean foundSugar = false;
		boolean foundEgg = false;
		boolean foundMilkBuckets = false;

		for (int i = 0; i < invCrafting.getSizeInventory(); i++) {
			if (invCrafting.getStackInSlot(i) == null || invCrafting.getStackInSlot(i).getItem() == null || invCrafting.getStackInSlot(i).stackSize == 0) {
				return false;
			}
		}

		if (isMilkBucket(invCrafting.getStackInSlot(2)) && isMilkBucket(invCrafting.getStackInSlot(1)) && isMilkBucket(invCrafting.getStackInSlot(0))) {
			foundMilkBuckets = true;
		}

		for (int i = 0; i < OreDictionary.getOres("egg").size(); i++) {
			if (OreDictionary.getOres("egg").get(i).getItem() == invCrafting.getStackInSlot(4).getItem()) {
				foundEgg = true;
			}
		}

		if (invCrafting.getStackInSlot(5).isItemEqual(new ItemStack(Items.sugar)) && invCrafting.getStackInSlot(3).isItemEqual(new ItemStack(Items.sugar))) {
			foundSugar = true;
		}

		for (int i = 0; i < OreDictionary.getOres("cropWheat").size(); i++) {
			Item oreItem = OreDictionary.getOres("cropWheat").get(i).getItem();

			if (oreItem == invCrafting.getStackInSlot(8).getItem()) {
				foundWheat0 = true;

			}

			if (oreItem == invCrafting.getStackInSlot(7).getItem()) {
				foundWheat1 = true;
			}

			if (oreItem == invCrafting.getStackInSlot(6).getItem()) {
				foundWheat2 = true;
			}
		}

		return foundWheat0 && foundWheat1 && foundWheat2 && foundSugar && foundMilkBuckets && foundEgg;
	}

	private boolean isMilkBucket(ItemStack stack) {
		if (stack != null) {
			if (stack.getItem() == Items.milk_bucket) {
				return true;
			} else if (stack.hasTagCompound()) {
				if (NBTHelper.getString(stack, "FluidName").equals("milk")) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting invCrafting) {
		return new ItemStack(Items.cake);
	}

	@Override
	public int getRecipeSize() {
		return 9;
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
