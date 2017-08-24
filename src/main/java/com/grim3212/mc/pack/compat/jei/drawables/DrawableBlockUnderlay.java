package com.grim3212.mc.pack.compat.jei.drawables;

import mezz.jei.Internal;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.ingredients.IngredientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DrawableBlockUnderlay implements IDrawableStatic {

	private final ResourceLocation resourceLocation;
	private final ItemStack stack;
	private final int textureWidth;
	private final int textureHeight;

	private final int u;
	private final int v;
	private final int width;
	private final int height;
	private final int offsetX;
	private final int offsetY;
	private final int paddingTop;
	private final int paddingBottom;
	private final int paddingLeft;
	private final int paddingRight;

	public DrawableBlockUnderlay(ResourceLocation resourceLocation, ItemStack stack, int u, int v, int width, int height, int offsetX, int offsetY, int paddingTop, int paddingBottom, int paddingLeft, int paddingRight, int textureWidth, int textureHeight) {
		this.resourceLocation = resourceLocation;
		this.stack = stack;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;

		this.u = u;
		this.v = v;
		this.width = width;
		this.height = height;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		this.paddingTop = paddingTop;
		this.paddingBottom = paddingBottom;
		this.paddingLeft = paddingLeft;
		this.paddingRight = paddingRight;
	}

	@Override
	public int getWidth() {
		return width + paddingLeft + paddingRight;
	}

	@Override
	public int getHeight() {
		return height + paddingTop + paddingBottom;
	}

	@Override
	public void draw(Minecraft minecraft, int xOffset, int yOffset) {
		draw(minecraft, xOffset, yOffset, 0, 0, 0, 0);
	}

	@Override
	public void draw(Minecraft minecraft, int xOffset, int yOffset, int maskTop, int maskBottom, int maskLeft, int maskRight) {
		int x = xOffset + this.paddingLeft + maskLeft;
		int y = yOffset + this.paddingTop + maskTop;
		int u = this.u + maskLeft;
		int v = this.v + maskTop;
		int width = this.width - maskRight - maskLeft;
		int height = this.height - maskBottom - maskTop;

		IngredientRegistry ingredientRegistry = Internal.getIngredientRegistry();
		IIngredientRenderer<ItemStack> ingredientRenderer = ingredientRegistry.getIngredientRenderer(stack);
		GlStateManager.enableDepth();
		ingredientRenderer.render(minecraft, x + offsetX, y + offsetY, stack);
		GlStateManager.enableAlpha();
		GlStateManager.disableDepth();

		minecraft.getTextureManager().bindTexture(this.resourceLocation);

		Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
	}
}
