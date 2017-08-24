package com.grim3212.mc.pack.compat.jei.recipes.machines.mfurnace;

import com.grim3212.mc.pack.compat.jei.recipes.machines.MachineRecipeMaker;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

public abstract class ModernFurnaceRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

	protected static final int inputSlot = 0;
	protected static final int fuelSlot = 1;
	protected static final int outputSlot = 2;

	protected final IDrawableStatic staticFlame;
	protected final IDrawableAnimated animatedFlame;
	protected final IDrawableAnimated arrow;

	public ModernFurnaceRecipeCategory(IGuiHelper guiHelper) {
		staticFlame = guiHelper.createDrawable(MachineRecipeMaker.MACHINE_LOCATION, 103, 0, 14, 14);
		animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);

		IDrawableStatic arrowDrawable = guiHelper.createDrawable(MachineRecipeMaker.MACHINE_LOCATION, 82, 14, 23, 16);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
	}

}
