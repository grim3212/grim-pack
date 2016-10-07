package com.grim3212.mc.pack.tools.compat.jei;

import com.grim3212.mc.pack.tools.items.ToolsItems;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEITools implements IModPlugin {

	@Override
	public void register(IModRegistry registry) {
		// portable workbench shiftclicking
		registry.getRecipeTransferRegistry().addRecipeTransferHandler(new PortableWorkbenchTransferInfo());

		// add our portable workbench to the list with the vanilla crafting
		registry.addRecipeCategoryCraftingItem(new ItemStack(ToolsItems.portable_workbench, 1), VanillaRecipeCategoryUid.CRAFTING);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
	}

}
