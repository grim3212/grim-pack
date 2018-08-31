package com.grim3212.mc.pack.compat.jei.recipes.machines;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.industry.util.MachineRecipes.MachineType;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class MachineRecipeCategory implements IRecipeCategory<MachineRecipeWrapper> {

	public static final String REFINERY_UID = "grimpack.refinery";
	public static final String DERRICK_UID = "grimpack.derrick";
	private final IDrawableStatic background;
	protected final IDrawableAnimated animatedArrow;
	private final String localizedName;
	private final MachineType type;

	private final int inputSlot = 0;
	private final int outputSlot = 1;

	public MachineRecipeCategory(IGuiHelper guiHelper, MachineType type) {
		this.type = type;
		
		background = guiHelper.drawableBuilder(MachineRecipeMaker.MACHINE_LOCATION, 0, 0, 82, 26).addPadding(0, 5, 0, 0).build();
		IDrawableStatic staticArrow = guiHelper.createDrawable(MachineRecipeMaker.MACHINE_LOCATION, 82, 14, 23, 16);
		animatedArrow = guiHelper.createAnimatedDrawable(staticArrow, 200, IDrawableAnimated.StartDirection.LEFT, false);

		if (type == MachineType.REFINERY)
			localizedName = I18n.format("grimpack.jei.refinery");
		else
			localizedName = I18n.format("grimpack.jei.derrick");
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		animatedArrow.draw(minecraft, 24, 5);
	}

	@Override
	public String getUid() {
		if (type == MachineType.REFINERY)
			return REFINERY_UID;
		else
			return DERRICK_UID;
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
	public void setRecipe(IRecipeLayout recipeLayout, MachineRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 0, 4);
		guiItemStacks.init(outputSlot, false, 60, 4);

		guiItemStacks.set(ingredients);
	}

}
