package com.grim3212.mc.pack.tools.compat.jei.recipes;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

public class BackpackRecipeHandler implements IRecipeHandler<BackpackRecipeWrapper> {

	@Override
	public Class<BackpackRecipeWrapper> getRecipeClass() {
		return BackpackRecipeWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid(BackpackRecipeWrapper recipe) {
		return VanillaRecipeCategoryUid.CRAFTING;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(BackpackRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(BackpackRecipeWrapper recipe) {
		return true;
	}

}
