package com.grim3212.mc.pack.util.client.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.util.grave.ContainerGrave;
import com.grim3212.mc.pack.util.grave.TileEntityGrave;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiGrave extends GuiContainer {

	private TileEntityGrave grave;
	private final InventoryPlayer playerInventory;
	private final ResourceLocation GUI = new ResourceLocation(GrimPack.modID, "textures/gui/grave_gui.png");

	public GuiGrave(InventoryPlayer playerInv, TileEntityGrave te) {
		super(new ContainerGrave(te, playerInv));
		this.grave = te;
		this.playerInventory = playerInv;
		this.ySize = 222;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.grave.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s, 8, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.GUI != null)
			this.mc.renderEngine.bindTexture(this.GUI);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
	}

}
