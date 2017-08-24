package com.grim3212.mc.pack.compat.jei.recipes.chisel;

import com.grim3212.mc.pack.GrimPack;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ChiselRecipeCategory implements IRecipeCategory<ChiselRecipeWrapper> {

	public static final String UID = "grimpack.chisel";
	private final IDrawableStatic background;
	private final String localizedName;

	public ChiselRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation(GrimPack.modID, "textures/gui/jei_chisel.png");
		background = guiHelper.createDrawable(location, 0, 0, 140, 120);

		localizedName = I18n.format("grimpack.jei.chisel");
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public String getModName() {
		return GrimPack.modName;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ChiselRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 56, 11);
		guiItemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));

		int xOffset = 0;
		int yOffset = 0;
		// Skip out block
		for (int i = 1; i < ingredients.getOutputs(ItemStack.class).size(); i++) {
			guiItemStacks.init(i, false, 4 + xOffset, 58 + yOffset);
			xOffset += 19;
			if (xOffset > 130) {
				xOffset = 0;
				yOffset += 20;
			}
		}

		for (int i = 1; i < ingredients.getOutputs(ItemStack.class).size(); i++)
			guiItemStacks.set(i, ingredients.getOutputs(ItemStack.class).get(i));
	}

}
