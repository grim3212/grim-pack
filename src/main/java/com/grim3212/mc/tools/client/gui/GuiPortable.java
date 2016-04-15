package com.grim3212.mc.tools.client.gui;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.tools.inventory.ContainerCustomWorkbench;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiPortable extends GuiContainer {
	private static final ResourceLocation resourceLocation = new ResourceLocation("textures/gui/container/crafting_table.png");

	public GuiPortable(EntityPlayer inventoryplayer, World world, BlockPos pos) {
		super(new ContainerCustomWorkbench(inventoryplayer.inventory, world, pos));
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRendererObj.drawString("Portable Workbench", 8, 6, 4210752);
		fontRendererObj.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(resourceLocation);
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
	}
}
