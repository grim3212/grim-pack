package com.grim3212.mc.pack.industry.client.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.industry.inventory.ContainerMachine;
import com.grim3212.mc.pack.industry.tile.TileEntityMachine;
import com.grim3212.mc.pack.industry.util.MachineRecipes.MachineType;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiMachine extends GuiContainer {

	private TileEntityMachine machineInventory;
	private final InventoryPlayer playerInventory;
	private ResourceLocation gui;

	public GuiMachine(InventoryPlayer playerInv, TileEntityMachine te) {
		super(new ContainerMachine(playerInv, te));
		this.machineInventory = te;
		this.playerInventory = playerInv;

		if (te.getMachineType() == MachineType.DERRICK) {
			gui = new ResourceLocation(GrimPack.modID, "textures/gui/gui_derrick.png");
		} else if (te.getMachineType() == MachineType.REFINERY) {
			gui = new ResourceLocation(GrimPack.modID, "textures/gui/gui_refinery.png");
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.machineInventory.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s, 50, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.gui != null)
			this.mc.renderEngine.bindTexture(this.gui);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
		int var7 = getWorkProgressScaled(24);
		this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 14, var7 + 1, 16);
	}

	private int getWorkProgressScaled(int pixels) {
		int i = this.machineInventory.getField(0);
		int j = this.machineInventory.getField(1);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
}
