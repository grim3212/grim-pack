package com.grim3212.mc.pack.tools.client.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.GuiGrimContainer;
import com.grim3212.mc.pack.tools.inventory.BackpackInventory;
import com.grim3212.mc.pack.tools.inventory.ContainerBackpack;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@OnlyIn(Dist.CLIENT)
public class GuiBackpack extends GuiGrimContainer {

	private static final ResourceLocation resourceLocation = new ResourceLocation(GrimPack.modID, "textures/gui/backpack_gui.png");
	private IInventory playerInv;
	private BackpackInventory backpackInv;

	public GuiBackpack(BackpackInventory backpackInventory, InventoryPlayer inventoryPlayer) {
		super(new ContainerBackpack(backpackInventory, inventoryPlayer));
		this.backpackInv = backpackInventory;
		this.playerInv = inventoryPlayer;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString(this.backpackInv.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 110, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(resourceLocation);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
	}
}