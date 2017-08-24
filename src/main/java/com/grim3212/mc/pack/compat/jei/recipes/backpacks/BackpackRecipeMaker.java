package com.grim3212.mc.pack.compat.jei.recipes.backpacks;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.tools.items.ItemBackpack;

public class BackpackRecipeMaker {
	
	public static List<BackpackRecipeWrapper> getBackpackRecipes() {
		List<BackpackRecipeWrapper> recipes = new ArrayList<BackpackRecipeWrapper>();
		for (int color : ItemBackpack.colorMeta) {
			BackpackRecipeWrapper recipe = new BackpackRecipeWrapper(color);
			recipes.add(recipe);
		}
		
		return recipes;
	}

}
