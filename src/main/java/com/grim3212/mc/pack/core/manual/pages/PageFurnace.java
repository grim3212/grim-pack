package com.grim3212.mc.pack.core.manual.pages;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.TooltipHelper;
import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class PageFurnace extends Page {

	private ResourceLocation furnaceOverlay = new ResourceLocation(GrimPack.modID, "textures/gui/furnaceOverlay.png");

	private int recipeShown = 0;
	private int update = 0;
	private int updateTime = 1;
	private NonNullList<ItemStack> inputs = NonNullList.create();
	private boolean isArray = false;

	public PageFurnace(String pageName, ItemStack input) {
		super(pageName, false);
		this.inputs.add(input);
	}

	public PageFurnace(String pageName, ItemStack[] input, int updateTime) {
		super(pageName, false);
		for (int i = 0; i < input.length; i++)
			this.inputs.add(input[i]);
		this.updateTime = updateTime;
		this.isArray = true;
	}

	public boolean isArray() {
		return this.isArray;
	}

	@Override
	public void drawScreen(GuiManualPage gui, int mouseX, int mouseY) {
		super.drawScreen(gui, mouseX, mouseY);

		relativeMouseX = mouseX;
		relativeMouseY = mouseY;

		int x = gui.getX() + 15;
		int y = gui.getY() + 28;
		PageInfo.drawText(x, y, this.getInfo());

		TextureManager render = Minecraft.getMinecraft().renderEngine;
		render.bindTexture(furnaceOverlay);

		GL11.glColor4f(1F, 1F, 1F, 1F);
		((GuiScreen) gui).drawTexturedModalRect(gui.getX(), gui.getY(), 0, 0, gui.getManualWidth(), gui.getManualHeight());

		this.renderItem(gui, inputs.get(recipeShown), gui.getX() + 49, gui.getY() + 145);

		ItemStack output = FurnaceRecipes.instance().getSmeltingResult(inputs.get(recipeShown));
		this.renderItem(gui, output, gui.getX() + 122, gui.getY() + 143);

		FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
		renderer.drawString(output.getDisplayName(), (gui.getManualWidth() / 2 - renderer.getStringWidth(output.getDisplayName()) / 2) + gui.getX(), gui.getY() + 210, Color.BLACK.getRGB(), false);

		if (!tooltipItem.isEmpty()) {
			TooltipHelper.renderToolTip(tooltipItem, mouseX, mouseY);
		}

		tooltipItem = ItemStack.EMPTY;
	}

	@Override
	public void updateScreen() {
		if (update % this.updateTime == 0) {
			recipeShown++;

			if (recipeShown == inputs.size())
				recipeShown = 0;
		}
		++update;
	}
}