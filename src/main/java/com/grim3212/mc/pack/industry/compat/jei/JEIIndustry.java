package com.grim3212.mc.pack.industry.compat.jei;

import javax.annotation.Nonnull;

import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.item.IndustryItems;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEIIndustry implements IModPlugin {

	@Override
	public void register(@Nonnull IModRegistry registry) {
		// crafting table shiftclicking
		registry.getRecipeTransferRegistry().addRecipeTransferHandler(new IronWorkbenchTransferInfo());
		registry.getRecipeTransferRegistry().addRecipeTransferHandler(new DiamondWorkbenchTransferInfo());

		// add our crafting table to the list with the vanilla crafting
		// table
		registry.addRecipeCategoryCraftingItem(new ItemStack(IndustryBlocks.iron_workbench, 1), VanillaRecipeCategoryUid.CRAFTING);
		registry.addRecipeCategoryCraftingItem(new ItemStack(IndustryBlocks.diamond_workbench, 1), VanillaRecipeCategoryUid.CRAFTING);
		registry.addRecipeCategoryCraftingItem(new ItemStack(IndustryItems.portable_iron_workbench, 1), VanillaRecipeCategoryUid.CRAFTING);
		registry.addRecipeCategoryCraftingItem(new ItemStack(IndustryItems.portable_diamond_workbench, 1), VanillaRecipeCategoryUid.CRAFTING);
	}

	@Override
	public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
	}
}