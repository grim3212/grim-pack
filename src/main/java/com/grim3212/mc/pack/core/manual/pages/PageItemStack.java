package com.grim3212.mc.pack.core.manual.pages;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.TooltipHelper;
import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class PageItemStack extends Page {

	private ResourceLocation stackOverlay = new ResourceLocation(GrimPack.modID, "textures/gui/stack_overlay.png");

	private List<ItemStack> displayStacks;

	private int stackShown = 0;
	private int update = 0;
	private int updateTime = 1;

	public PageItemStack(String pageName, ItemStack stack) {
		this(pageName, 1, stack);
	}

	public PageItemStack(String pageName, int updateTime, ItemStack... stacks) {
		this(pageName, updateTime, Lists.newArrayList(stacks));
	}

	public PageItemStack(String pageName, int updateTime, List<ItemStack> stacks) {
		super(pageName, false);

		this.displayStacks = stacks;
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
		render.bindTexture(stackOverlay);

		GL11.glColor4f(1F, 1F, 1F, 1F);
		((GuiScreen) gui).drawTexturedModalRect(gui.getX() + 21, gui.getY() + 120, 21, 120, 147, 85);

		tooltipItem = ItemStack.EMPTY;

		ItemStack outstack = displayStacks.get(stackShown);

		int xOffset = 72;
		int yOffset = 142;
		this.renderItem(gui, outstack, gui.getX() + xOffset, gui.getY() + yOffset);

		FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
		renderer.drawString(outstack.getDisplayName(), (gui.getManualWidth() / 2 - renderer.getStringWidth(outstack.getDisplayName()) / 2) + gui.getX(), gui.getY() + 210, Color.BLACK.getRGB(), false);

		if (!tooltipItem.isEmpty()) {
			TooltipHelper.renderToolTip(tooltipItem, mouseX, mouseY);
		}
	}

	@Override
	public void renderItem(GuiManualPage gui, ItemStack item, int x, int y) {
		RenderItem render = Minecraft.getMinecraft().getRenderItem();

		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();

		GlStateManager.translate(-x * 2f, -y * 2f, 0);
		GlStateManager.scale(3F, 3F, 0.75F);

		GlStateManager.enableRescaleNormal();
		GlStateManager.enableDepth();

		render.renderItemAndEffectIntoGUI(item, x, y);
		render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, item, x, y, (String) null);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();

		if (relativeMouseX >= x && relativeMouseY >= y && relativeMouseX <= x + (16 * 3) && relativeMouseY <= y + (16 * 3)) {
			this.tooltipItem = item;
		}

		GlStateManager.disableLighting();
	}

	@Override
	public void updateScreen() {
		if (update % this.updateTime == 0) {
			stackShown++;

			if (stackShown == displayStacks.size())
				stackShown = 0;
		}
		++update;
	}

	@Override
	public JsonObject deconstruct() {
		JsonObject json = super.deconstruct();

		JsonArray displayStacks = new JsonArray();
		for (ItemStack stack : this.displayStacks) {
			displayStacks.add(this.deconstructItem(stack));
		}

		json.add("displayStacks", displayStacks);

		return json;
	}
}
