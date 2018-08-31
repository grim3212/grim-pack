package com.grim3212.mc.pack.compat.jei.recipes.machines.mfurnace;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.grim3212.mc.pack.compat.jei.recipes.machines.MachineRecipeMaker;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class MFurnaceFuelWrapper implements IRecipeWrapper {

	public static final float COOK_TIME = 100f;

	private final List<List<ItemStack>> inputs;
	private final String smeltCountString;
	private final String burnTimeString;
	private final IDrawableAnimated flame;

	public MFurnaceFuelWrapper(IGuiHelper guiHelper, Collection<ItemStack> input, int burnTime) {
		List<ItemStack> inputList = new ArrayList<>(input);
		this.inputs = Collections.singletonList(inputList);

		if (burnTime == 200) {
			this.smeltCountString = I18n.format("gui.jei.category.fuel.smeltCount.single");
		} else {
			NumberFormat numberInstance = NumberFormat.getNumberInstance();
			numberInstance.setMaximumFractionDigits(2);
			String smeltCount = numberInstance.format(burnTime / COOK_TIME);
			this.smeltCountString = I18n.format("gui.jei.category.fuel.smeltCount", smeltCount);
		}

		this.burnTimeString = I18n.format("grimpack.jei.category.fuel.burnTime", burnTime);

		IDrawableStatic flameDrawable = guiHelper.createDrawable(MachineRecipeMaker.MACHINE_LOCATION, 103, 0, 14, 14);
		this.flame = guiHelper.createAnimatedDrawable(flameDrawable, 100, IDrawableAnimated.StartDirection.TOP, true);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, inputs);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		flame.draw(minecraft, 1, 0);
		minecraft.fontRenderer.drawString(smeltCountString, 24, 8, Color.gray.getRGB());
		minecraft.fontRenderer.drawString(burnTimeString, 24, 18, Color.gray.getRGB());
	}
}