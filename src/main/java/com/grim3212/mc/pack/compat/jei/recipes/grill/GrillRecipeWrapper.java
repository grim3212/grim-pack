package com.grim3212.mc.pack.compat.jei.recipes.grill;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.decor.crafting.GrillRecipeFactory;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GrillRecipeWrapper implements IRecipeWrapper {

	private final List<List<ItemStack>> inputs;
	private final List<ItemStack> outputs;

	public GrillRecipeWrapper(List<ItemStack> inputs, ItemStack output) {
		this.inputs = Lists.newArrayList(Lists.newArrayList(inputs), Lists.newArrayList(inputs), Lists.newArrayList(inputs), Lists.newArrayList(inputs));
		this.outputs = Lists.newArrayList(output, new ItemStack(Items.COAL));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutputs(ItemStack.class, outputs);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		float experience = GrillRecipeFactory.getExperience(outputs.get(0));
		if (experience > 0) {
			String experienceString = Translator.translateToLocalFormatted("grimpack.jei.experience", experience);
			FontRenderer fontRenderer = minecraft.fontRenderer;
			int stringWidth = fontRenderer.getStringWidth(experienceString);
			fontRenderer.drawString(experienceString, ((36 - stringWidth) / 2) + 45, 35, Color.gray.getRGB());
		}
	}
}
