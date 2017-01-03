package com.grim3212.mc.pack.tools.client.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.tools.inventory.ContainerPelletBag;
import com.grim3212.mc.pack.tools.inventory.PelletBagInventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPelletBag extends GuiContainer {

	private static final ResourceLocation resourceLocation = new ResourceLocation(GrimPack.modID, "textures/gui/pellet_bag_gui.png");
	private IInventory playerInv;
	private PelletBagInventory inventory;

	public GuiPelletBag(PelletBagInventory inventory, InventoryPlayer inventoryPlayer) {
		super(new ContainerPelletBag(inventory, inventoryPlayer));
		this.inventory = inventory;
		this.playerInv = inventoryPlayer;
		this.ySize = 168;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(this.inventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 94, 4210752);
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
