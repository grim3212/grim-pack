package com.grim3212.mc.pack.compat.jei.recipes.machines;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import com.grim3212.mc.pack.industry.util.MachineRecipes;
import com.grim3212.mc.pack.industry.util.MachineRecipes.MachineType;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class MachineRecipeWrapper implements IRecipeWrapper {

	private final List<List<ItemStack>> inputs;
	private final ItemStack output;
	private final MachineType type;

	public MachineRecipeWrapper(List<ItemStack> inputs, ItemStack output, MachineType type) {
		this.inputs = Collections.singletonList(inputs);
		this.output = output;
		this.type = type;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, output);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		float experience = MachineRecipes.INSTANCE.getSmeltingExperience(output, type);
		if (experience > 0) {
			String experienceString = Translator.translateToLocalFormatted("grimpack.jei.experience", experience);
			FontRenderer fontRenderer = minecraft.fontRenderer;
			int stringWidth = fontRenderer.getStringWidth(experienceString);
			if (type != MachineType.MODERN_FURNACE)
				fontRenderer.drawString(experienceString, ((36 - stringWidth) / 2) + 20, 26, Color.gray.getRGB());
			else
				fontRenderer.drawString(experienceString, recipeWidth - stringWidth, 0, Color.gray.getRGB());
		}
	}

}
