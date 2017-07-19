package com.grim3212.mc.pack.industry.client.gui;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.industry.inventory.ContainerGoldSafe;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGoldSafe extends GuiContainer {

	private IInventory playerInventory;
	private IInventory safeInventory;
	private int inventoryRows = 0;

	private static final ResourceLocation GUIGoldSafe = new ResourceLocation("textures/gui/container/generic_54.png");

	public GuiGoldSafe(EntityPlayer player, IInventory safeInventory) {
		super(new ContainerGoldSafe(player, safeInventory));
		this.playerInventory = player.inventory;
		this.safeInventory = safeInventory;
		this.allowUserInput = false;
		short short1 = 222;
		int i = short1 - 108;
		this.inventoryRows = (safeInventory.getSizeInventory() / 9);
		this.ySize = (i + this.inventoryRows * 18);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString(I18n.format(this.safeInventory.getName()), 8, 6, 4210752);
		this.fontRenderer.drawString(I18n.format(this.playerInventory.getName()), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUIGoldSafe);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
		drawTexturedModalRect(k, l + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
	}
}