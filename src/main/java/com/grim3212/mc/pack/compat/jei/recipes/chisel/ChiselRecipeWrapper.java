package com.grim3212.mc.pack.compat.jei.recipes.chisel;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.compat.jei.JEIGrimPack;
import com.grim3212.mc.pack.core.client.ClientUtil;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ChiselRecipeWrapper implements IRecipeWrapper {

	private final Block inBlock;
	private final int inMeta;
	private final Block outBlock;
	private final int outMeta;
	private final NonNullList<ItemStack> outItems = NonNullList.create();
	private final Rectangle inBlockRect;
	private final Rectangle outBlockRect;
	private final String noticeString;
	private final boolean isEstimate;

	public ChiselRecipeWrapper(Block inBlock, int inMeta, Block outBlock, int outMeta, NonNullList<ItemStack> outItems, boolean isEstimate) {
		this.inBlock = inBlock;
		this.inMeta = inMeta;
		this.outBlock = outBlock;
		this.outMeta = outMeta;
		this.isEstimate = isEstimate;

		this.inBlockRect = new Rectangle(5, 1, 40, 40);
		this.outBlockRect = new Rectangle(95, 1, 40, 40);
		this.noticeString = I18n.format("grimpack.jei.chisel.notice");

		this.outItems.add(new ItemStack(outBlock, 1, outMeta));
		// Combine same items into one stack
		combineItems(outItems);
	}

	private void combineItems(NonNullList<ItemStack> outItems) {
		for (int i = 0; i < outItems.size(); i++) {
			ItemStack stack = outItems.get(i).copy();

			boolean foundSame = false;
			for (int j = 0; j < this.outItems.size(); j++) {
				if (this.outItems.get(j).isItemEqual(stack)) {
					this.outItems.get(j).grow(1);
					foundSame = true;
					break;
				}
			}

			if (!foundSame) {
				this.outItems.add(stack);
			}
		}

	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, Lists.newArrayList(Lists.newArrayList(new ItemStack(ToolsItems.iron_chisel), new ItemStack(ToolsItems.diamond_chisel)), Lists.newArrayList(new ItemStack(inBlock, 1, inMeta))));
		ingredients.setOutputs(ItemStack.class, outItems);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		IBlockState inState = inBlock.getStateFromMeta(inMeta);
		IBlockState outState = outBlock.getStateFromMeta(outMeta);

		ClientUtil.renderBlock(inState, 22, 34, -10, 20f, 0.4f);
		ClientUtil.renderBlock(outState, 112, 34, -10, 20f, 0.4f);

		// Only if getDrops was used
		if (this.isEstimate) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5f, 0.5f, 0);
			minecraft.fontRenderer.drawString(noticeString, 10, 224, Color.BLACK.getRGB());
			GlStateManager.popMatrix();
		}
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		if (isHovered(inBlockRect, mouseX, mouseY)) {
			String s = inBlock.getLocalizedName();
			if (!s.isEmpty()) {
				return Lists.newArrayList(s);
			}
		} else if (isHovered(outBlockRect, mouseX, mouseY)) {
			String s = outBlock.getLocalizedName();
			if (!s.isEmpty()) {
				return Lists.newArrayList(s);
			}
		}

		return IRecipeWrapper.super.getTooltipStrings(mouseX, mouseY);
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		if (isHovered(inBlockRect, mouseX, mouseY)) {
			return click(mouseButton, new ItemStack(inBlock, 1, inMeta));
		} else if (isHovered(outBlockRect, mouseX, mouseY)) {
			return click(mouseButton, new ItemStack(outBlock, 1, outMeta));
		}

		return IRecipeWrapper.super.handleClick(minecraft, mouseX, mouseY, mouseButton);
	}

	private boolean click(int mouseButton, ItemStack stack) {
		IJeiRuntime runtime = JEIGrimPack.getJeiRuntime();
		IIngredientRegistry ingredientRegistry = JEIGrimPack.getIngredientRegistry();

		if (runtime != null && ingredientRegistry != null) {

			if (ingredientRegistry.getIngredientHelper(ItemStack.class).isValidIngredient(stack)) {
				if (mouseButton == 0) {
					runtime.getRecipesGui().show(runtime.getRecipeRegistry().createFocus(IFocus.Mode.OUTPUT, stack));
					return true;
				} else if (mouseButton == 1) {
					runtime.getRecipesGui().show(runtime.getRecipeRegistry().createFocus(IFocus.Mode.INPUT, stack));
					return true;
				}
			}
		}
		return false;
	}

	private boolean isHovered(Rectangle rect, int x, int y) {
		if (rect.contains(x, y)) {
			return true;
		}
		return false;
	}
}
