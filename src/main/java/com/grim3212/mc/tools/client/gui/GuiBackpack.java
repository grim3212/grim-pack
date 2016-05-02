package com.grim3212.mc.tools.client.gui;

import com.grim3212.mc.tools.GrimTools;
import com.grim3212.mc.tools.inventory.BackpackInventory;
import com.grim3212.mc.tools.inventory.ContainerBackpack;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBackpack extends GuiContainer {

	private static final ResourceLocation resourceLocation = new ResourceLocation(GrimTools.modID, "textures/gui/backpack_gui.png");

	public GuiBackpack(BackpackInventory backpackInventory, InventoryPlayer inventoryPlayer) {
		super(new ContainerBackpack(backpackInventory, inventoryPlayer));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRendererObj.drawString(I18n.format("container.backpack", new Object[0]), 8, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 110, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(resourceLocation);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
	}

	public void onGuiClosed() {
		super.onGuiClosed();
		inventorySlots.getSlot(0).onSlotChanged();
	}
}