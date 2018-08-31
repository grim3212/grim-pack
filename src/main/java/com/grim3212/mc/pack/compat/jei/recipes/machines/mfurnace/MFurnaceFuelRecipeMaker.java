package com.grim3212.mc.pack.compat.jei.recipes.machines.mfurnace;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.grim3212.mc.pack.industry.tile.TileEntityMFurnace;
import com.grim3212.mc.pack.industry.util.MachineRecipes;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class MFurnaceFuelRecipeMaker {

	private MFurnaceFuelRecipeMaker() {
	}

	public static List<MFurnaceFuelWrapper> getFuelRecipes(IJeiHelpers helpers) {
		IGuiHelper guiHelper = helpers.getGuiHelper();
		IStackHelper stackHelper = helpers.getStackHelper();

		List<Pair<ItemStack, Integer>> mFurnaceFuels = MachineRecipes.INSTANCE.getModernFurnaceFuel();
		List<MFurnaceFuelWrapper> fuelRecipes = Lists.newArrayList();
		Set<String> oreDictNames = Sets.newHashSet();

		for (Pair<ItemStack, Integer> fuel : mFurnaceFuels) {
			ItemStack fuelStack = fuel.getLeft();

			if (fuelStack == ItemStack.EMPTY || fuelStack == null) {
				continue;
			}

			int[] oreIDs = OreDictionary.getOreIDs(fuelStack);
			if (oreIDs.length > 0) {
				for (int oreID : oreIDs) {
					String name = OreDictionary.getOreName(oreID);
					if (oreDictNames.contains(name)) {
						continue;
					}

					oreDictNames.add(name);
					List<ItemStack> oreDictFuels = OreDictionary.getOres(name);
					List<ItemStack> oreDictFuelsSet = stackHelper.getAllSubtypes(oreDictFuels);
					removeNoBurnTime(oreDictFuelsSet);
					if (oreDictFuels.isEmpty()) {
						continue;
					}
					int burnTime = getBurnTime(oreDictFuels.get(0));

					fuelRecipes.add(new MFurnaceFuelWrapper(guiHelper, oreDictFuelsSet, burnTime));
				}
			} else {
				List<ItemStack> fuels = stackHelper.getSubtypes(fuelStack);
				fuels = removeNoBurnTime(fuels);

				if (fuels.isEmpty()) {
					continue;
				}

				int burnTime = getBurnTime(fuels.get(0));
				fuelRecipes.add(new MFurnaceFuelWrapper(guiHelper, fuels, burnTime));
			}
		}
		return fuelRecipes;
	}

	private static List<ItemStack> removeNoBurnTime(List<ItemStack> itemStacks) {
		List<ItemStack> newItemStacks = new ArrayList<ItemStack>();

		for (ItemStack stack : itemStacks) {
			if (getBurnTime(stack) != 0) {
				newItemStacks.add(stack.copy());
			}
		}

		return newItemStacks;
	}

	private static int getBurnTime(ItemStack itemStack) {
		return TileEntityMFurnace.getItemBurnTime(itemStack);
	}
}