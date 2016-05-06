package com.grim3212.mc.tools.client.gui;

import com.grim3212.mc.tools.inventory.ContainerCustomWorkbench;
import com.grim3212.mc.tools.items.ToolsItems;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiPortable extends GuiContainer {
	private static final ResourceLocation resourceLocation = new ResourceLocation("textures/gui/container/crafting_table.png");
	private IInventory playerInv;
	private ItemStack portableStack;

	public GuiPortable(InventoryPlayer inventoryplayer, World world, BlockPos pos, ItemStack stack) {
		super(new ContainerCustomWorkbench(inventoryplayer, world, pos));
		this.playerInv = inventoryplayer;

		if (stack.getItem() == ToolsItems.portable_workbench) {
			this.portableStack = stack.copy();
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if (this.portableStack.hasDisplayName())
			fontRendererObj.drawString(this.portableStack.getDisplayName(), 8, 6, 4210752);
		else
			fontRendererObj.drawString(StatCollector.translateToLocal("container.portable_workbench"), 8, 6, 4210752);
		fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
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
