package com.grim3212.mc.pack.industry.client.gui;

import com.grim3212.mc.pack.industry.inventory.ContainerDiamondWorkbench;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCraftingDiamond extends GuiContainer {

	private static final ResourceLocation resourceLocation = new ResourceLocation("textures/gui/container/crafting_table.png");
	private IInventory playerInv;

	public GuiCraftingDiamond(EntityPlayer inventoryplayer, World world, BlockPos pos) {
		super(new ContainerDiamondWorkbench(inventoryplayer.inventory, world, pos, false));
		this.playerInv = inventoryplayer.inventory;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRendererObj.drawString(I18n.format("container.crafting_diamond"), 28, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
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
