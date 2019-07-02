package com.grim3212.mc.pack.decor.client.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.GrimContainerScreen;
import com.grim3212.mc.pack.decor.inventory.ContainerCage;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiCage extends GrimContainerScreen<ContainerCage> {

	private static final ResourceLocation GUI_LOCATION = new ResourceLocation(GrimPack.modID, "textures/gui/cage.png");

	public GuiCage(ContainerCage cage, PlayerInventory playerInv, ITextComponent name) {
		super(cage, playerInv, name);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.getTitle().getFormattedText();
		this.font.drawString(s, this.xSize / 2 - this.font.getStringWidth(s) / 2, 6, 4210752);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.textureManager.bindTexture(GUI_LOCATION);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.blit(x, y, 0, 0, this.xSize, this.ySize);
	}
}
