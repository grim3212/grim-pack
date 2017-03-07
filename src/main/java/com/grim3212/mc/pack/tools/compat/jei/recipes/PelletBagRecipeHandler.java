package com.grim3212.mc.pack.tools.compat.jei.recipes;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

public class PelletBagRecipeHandler implements IRecipeHandler<PelletBagRecipeWrapper> {

	@Override
	public Class<PelletBagRecipeWrapper> getRecipeClass() {
		return PelletBagRecipeWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid(PelletBagRecipeWrapper recipe) {
		return VanillaRecipeCategoryUid.CRAFTING;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(PelletBagRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(PelletBagRecipeWrapper recipe) {
		return true;
	}

}
