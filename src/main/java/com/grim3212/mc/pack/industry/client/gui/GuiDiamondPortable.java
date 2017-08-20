package com.grim3212.mc.pack.industry.client.gui;

import com.grim3212.mc.pack.core.client.gui.GuiGrimContainer;
import com.grim3212.mc.pack.industry.inventory.ContainerDiamondWorkbench;
import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiDiamondPortable extends GuiGrimContainer {

	private static final ResourceLocation resourceLocation = new ResourceLocation("textures/gui/container/crafting_table.png");
	private IInventory playerInv;
	private ItemStack portableStack;

	public GuiDiamondPortable(EntityPlayer player, World world, BlockPos pos, ItemStack stack) {
		super(new ContainerDiamondWorkbench(player, world, pos, true));
		this.playerInv = player.inventory;

		if (stack.getItem() == IndustryItems.portable_diamond_workbench) {
			this.portableStack = stack;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if (this.portableStack.hasDisplayName())
			fontRenderer.drawString(this.portableStack.getDisplayName(), 8, 6, 4210752);
		else
			fontRenderer.drawString(I18n.format("container.portable_diamond_workbench"), 8, 6, 4210752);
		fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
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
