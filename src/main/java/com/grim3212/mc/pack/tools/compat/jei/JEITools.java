package com.grim3212.mc.pack.tools.compat.jei;

import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.tools.compat.jei.recipes.BackpackRecipeMaker;
import com.grim3212.mc.pack.tools.compat.jei.recipes.PelletBagRecipeMaker;
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
		if (CoreConfig.useTools) {
			// Backpack recipe
			registry.addRecipes(BackpackRecipeMaker.getBackpackRecipes(), VanillaRecipeCategoryUid.CRAFTING);
			// Pellet Bag recipe
			registry.addRecipes(PelletBagRecipeMaker.getPelletBagRecipes(), VanillaRecipeCategoryUid.CRAFTING);

			// portable workbench shiftclicking
			registry.getRecipeTransferRegistry().addRecipeTransferHandler(new PortableWorkbenchTransferInfo());

			// add our portable workbench to the list with the vanilla crafting
			registry.addRecipeCatalyst(new ItemStack(ToolsItems.portable_workbench, 1), VanillaRecipeCategoryUid.CRAFTING);
		}
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		if (CoreConfig.useTools)
			subtypeRegistry.useNbtForSubtypes(ToolsItems.backpack, ToolsItems.pellet_bag);
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
	}

}
