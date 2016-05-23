package com.grim3212.mc.pack.decor.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.inventory.ContainerGrill;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGrill extends GuiContainer {

	private TileEntityGrill grillInventory;
	private IInventory playerInv;
	private RenderItem renderItems = Minecraft.getMinecraft().getRenderItem();
	private static ResourceLocation GrillGUI = new ResourceLocation(GrimPack.modID, "textures/gui/grill.png");

	public GuiGrill(InventoryPlayer inventoryPlayer, TileEntityGrill grillTE) {
		super(new ContainerGrill(inventoryPlayer, grillTE));
		this.playerInv = inventoryPlayer;
		this.grillInventory = grillTE;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRendererObj.drawString(this.grillInventory.getName(), this.xSize / 2 - this.fontRendererObj.getStringWidth(grillInventory.getName()) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.grill.tier") + " " + grillInventory.getTier(), 73, 14, 4210752);
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GrillGUI);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

		if (this.grillInventory.isGrillBurning()) {
			drawTexturedModalRect(x + 141, y + 24, 176, 2, 4, 8);
		}

		int metersize = (int) (this.grillInventory.grillCoal / 4000.0F * 16.0F);
		if (this.grillInventory.grillCoal != 0.0F)
			drawTexturedModalRect(x + 135, y + 34, 176, 10, metersize + 1, 2);

		this.zLevel = 199.0F;

		metersize = (int) (this.grillInventory.cookTimes[0] / this.grillInventory.getTierTime() * 16.0F);
		if (this.grillInventory.cookTimes[0] != 0)
			drawTexturedModalRect(x + 71, y + 24, 176, 0, metersize + 1, 2);

		metersize = (int) (this.grillInventory.cookTimes[1] / this.grillInventory.getTierTime() * 16.0F);
		if (this.grillInventory.cookTimes[1] != 0)
			drawTexturedModalRect(x + 89, y + 24, 176, 0, metersize + 1, 2);

		metersize = (int) (this.grillInventory.cookTimes[2] / this.grillInventory.getTierTime() * 16.0F);
		if (this.grillInventory.cookTimes[2] != 0)
			drawTexturedModalRect(x + 71, y + 60, 176, 0, metersize + 1, 2);

		metersize = (int) (this.grillInventory.cookTimes[3] / this.grillInventory.getTierTime() * 16.0F);
		if (this.grillInventory.cookTimes[3] != 0)
			drawTexturedModalRect(x + 89, y + 60, 176, 0, metersize + 1, 2);

		this.zLevel = 0.0F;

		GlStateManager.pushMatrix();
		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		ScaledResolution var7 = new ScaledResolution(this.mc);
		GlStateManager.viewport((var7.getScaledWidth() - 320) / 2 * var7.getScaleFactor(), (var7.getScaledHeight() - 240) / 2 * var7.getScaleFactor(), 320 * var7.getScaleFactor(), 240 * var7.getScaleFactor());
		GLU.gluPerspective(90.0F, 1.333333F, 9.0F, 80.0F);
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		GlStateManager.loadIdentity();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.translate(0.0F, 5.0F, -16.0F);
		GlStateManager.scale(10.0F, 10.0F, 2.5F);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.enableRescaleNormal();

		if (this.grillInventory != null) {
			ItemStack grill = new ItemStack(DecorBlocks.grill);
			NBTHelper.setInteger(grill, "blockID", grillInventory.getBlockID());
			NBTHelper.setInteger(grill, "blockMeta", grillInventory.getBlockMeta());
			renderItems.renderItem(grill, TransformType.NONE);
		}

		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
}