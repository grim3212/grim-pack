package com.grim3212.mc.pack.compat.jei.recipes.machines.mfurnace;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.compat.jei.drawables.DrawableBlockUnderlay;
import com.grim3212.mc.pack.compat.jei.recipes.machines.MachineRecipeMaker;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class MFurnaceFuelCategory extends ModernFurnaceRecipeCategory<MFurnaceFuelWrapper> {

	private final IDrawableStatic background;
	private final IDrawableStatic icon;
	private final String localizedName;
	public static final String UID = "grimpack.mfurnace_fuel";

	public MFurnaceFuelCategory(IGuiHelper guiHelper) {
		super(guiHelper);

		background = guiHelper.drawableBuilder(MachineRecipeMaker.MACHINE_LOCATION, 0, 46, 18, 34).addPadding(0, 5, 0, 80).build();
		icon = new DrawableBlockUnderlay(MachineRecipeMaker.MACHINE_LOCATION, new ItemStack(IndustryBlocks.modern_furnace), 117, 0, 13, 13, -1, -1, 0, 0, 0, 0, 256, 256);
		localizedName = I18n.format("grimpack.jei.mfurnace_fuel");
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
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
	public void setRecipe(IRecipeLayout recipeLayout, MFurnaceFuelWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(fuelSlot, true, 0, 16);
		guiItemStacks.set(ingredients);
	}

}
