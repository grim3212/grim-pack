package com.grim3212.mc.pack.industry.client.pages;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.TooltipHelper;
import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageInfo;
import com.grim3212.mc.pack.industry.util.MachineRecipes;
import com.grim3212.mc.pack.industry.util.MachineRecipes.MachineType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class PageMachine extends Page {

	private ResourceLocation mFurnaceOverlay = new ResourceLocation(GrimPack.modID, "textures/gui/modern_furnace_overlay.png");
	private ResourceLocation derrickOverlay = new ResourceLocation(GrimPack.modID, "textures/gui/derrick_overlay.png");
	private ResourceLocation refineryOverlay = new ResourceLocation(GrimPack.modID, "textures/gui/refinery_overlay.png");

	private int recipeShown = 0;
	private int update = 0;
	private int updateTime = 1;
	private NonNullList<ItemStack> inputs = NonNullList.create();
	private boolean isArray = false;
	private MachineType type;

	public PageMachine(String pageName, ItemStack input, MachineType type) {
		super(pageName, false);
		this.inputs.add(input);
		this.type = type;
	}

	public PageMachine(String pageName, ItemStack[] input, int updateTime, MachineType type) {
		super(pageName, false);
		for (int i = 0; i < input.length; i++)
			this.inputs.add(input[i]);
		this.updateTime = updateTime;
		this.isArray = true;
		this.type = type;
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
		if (type == MachineType.MODERN_FURNACE)
			render.bindTexture(mFurnaceOverlay);
		else if (type == MachineType.DERRICK)
			render.bindTexture(derrickOverlay);
		else if (type == MachineType.REFINERY)
			render.bindTexture(refineryOverlay);

		GL11.glColor4f(1F, 1F, 1F, 1F);
		((GuiScreen) gui).drawTexturedModalRect(gui.getX(), gui.getY(), 0, 0, gui.getManualWidth(), gui.getManualHeight());

		tooltipItem = ItemStack.EMPTY;

		this.renderItem(gui, inputs.get(recipeShown), gui.getX() + 49, gui.getY() + 145);

		ItemStack output = MachineRecipes.INSTANCE.getResult(inputs.get(recipeShown), this.type);
		this.renderItem(gui, output, gui.getX() + 122, gui.getY() + 143);

		FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
		renderer.drawString(output.getDisplayName(), (gui.getManualWidth() / 2 - renderer.getStringWidth(output.getDisplayName()) / 2) + gui.getX(), gui.getY() + 210, Color.BLACK.getRGB(), false);

		if (!tooltipItem.isEmpty()) {
			TooltipHelper.renderToolTip(tooltipItem, mouseX, mouseY);
		}
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