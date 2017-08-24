package com.grim3212.mc.pack.compat.jei.recipes.machines.mfurnace;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.compat.jei.recipes.machines.MachineRecipeMaker;
import com.grim3212.mc.pack.compat.jei.recipes.machines.MachineRecipeWrapper;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class MFurnaceSmeltingCategory extends ModernFurnaceRecipeCategory<MachineRecipeWrapper> {

	private final IDrawable background;
	private final String localizedName;
	public static final String UID = "grimpack.mfurnace_smelting";

	public MFurnaceSmeltingCategory(IGuiHelper guiHelper) {
		super(guiHelper);

		background = guiHelper.createDrawable(MachineRecipeMaker.MACHINE_LOCATION, 0, 26, 82, 54);
		localizedName = I18n.format("grimpack.jei.mfurnace_smelting");
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		animatedFlame.draw(minecraft, 1, 20);
		arrow.draw(minecraft, 24, 18);
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
	public void setRecipe(IRecipeLayout recipeLayout, MachineRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 0, 0);
		guiItemStacks.init(outputSlot, false, 60, 18);

		guiItemStacks.set(ingredients);
	}

}
