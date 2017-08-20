package com.grim3212.mc.pack.decor.client.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.GuiGrimContainer;
import com.grim3212.mc.pack.decor.inventory.ContainerCage;
import com.grim3212.mc.pack.decor.tile.TileEntityCage;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCage extends GuiGrimContainer {

	private final InventoryPlayer playerInv;
	private final TileEntityCage cage;
	private static final ResourceLocation GUI_LOCATION = new ResourceLocation(GrimPack.modID, "textures/gui/cage.png");

	public GuiCage(InventoryPlayer playerInv, TileEntityCage cage) {
		super(new ContainerCage(cage, playerInv));
		this.playerInv = playerInv;
		this.cage = cage;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = cage.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUI_LOCATION);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
}
