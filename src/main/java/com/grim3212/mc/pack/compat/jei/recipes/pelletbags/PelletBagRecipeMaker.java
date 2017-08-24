package com.grim3212.mc.pack.compat.jei.recipes.pelletbags;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.tools.items.ItemBackpack;

public class PelletBagRecipeMaker {

	public static List<PelletBagRecipeWrapper> getPelletBagRecipes() {
		List<PelletBagRecipeWrapper> recipes = new ArrayList<PelletBagRecipeWrapper>();
		for (int color : ItemBackpack.colorMeta) {
			PelletBagRecipeWrapper recipe = new PelletBagRecipeWrapper(color);
			recipes.add(recipe);
		}

		return recipes;
	}

}
