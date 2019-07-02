package com.grim3212.mc.pack.decor.client.gui;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.inventory.ContainerGrill;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;
import com.grim3212.mc.pack.decor.util.DecorUtil;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class GuiGrill extends ContainerScreen<ContainerGrill> {

	private TileEntityGrill grill;
	private ItemRenderer renderItems = Minecraft.getInstance().getItemRenderer();
	private static ResourceLocation GrillGUI = new ResourceLocation(GrimPack.modID, "textures/gui/grill.png");

	public GuiGrill(ContainerGrill container, PlayerInventory inventoryPlayer, ITextComponent name) {
		super(container, inventoryPlayer, name);

		TileEntity te = this.minecraft.world.getTileEntity(this.container.getPos());

		if (te instanceof TileEntityGrill) {
			this.grill = (TileEntityGrill) te;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.font.drawString(this.getTitle().getFormattedText(), this.xSize / 2 - this.font.getStringWidth(this.getTitle().getFormattedText()) / 2, 6, 4210752);
		this.font.drawString(I18n.format("container.grill.tier") + " " + this.container.getTier(), 73, 14, 4210752);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.textureManager.bindTexture(GrillGUI);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		blit(x, y, 0, 0, this.xSize, this.ySize);

		if (this.grill != null && this.grill.isGrillBurning()) {
			blit(x + 141, y + 24, 176, 2, 4, 8);
		}

		int metersize = (int) (this.container.getGrillCoal() / 4000.0F * 16.0F);
		if (this.container.getGrillCoal() != 0.0F)
			blit(x + 135, y + 34, 176, 10, metersize + 1, 2);

		this.blitOffset = 199;

		metersize = (int) (this.container.getCookTime(1) / TileEntityGrill.getTierTime(this.container.getTier()) * 16.0F);
		if (this.container.getCookTime(1) != 0)
			blit(x + 71, y + 24, 176, 0, metersize + 1, 2);

		metersize = (int) (this.container.getCookTime(2) / TileEntityGrill.getTierTime(this.container.getTier()) * 16.0F);
		if (this.container.getCookTime(2) != 0)
			blit(x + 89, y + 24, 176, 0, metersize + 1, 2);

		metersize = (int) (this.container.getCookTime(3) / TileEntityGrill.getTierTime(this.container.getTier()) * 16.0F);
		if (this.container.getCookTime(3) != 0)
			blit(x + 71, y + 60, 176, 0, metersize + 1, 2);

		metersize = (int) (this.container.getCookTime(4) / TileEntityGrill.getTierTime(this.container.getTier()) * 16.0F);
		if (this.container.getCookTime(4) != 0)
			blit(x + 89, y + 60, 176, 0, metersize + 1, 2);

		this.blitOffset = 0;

		GlStateManager.pushMatrix();
		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();

		GlStateManager.viewport((this.minecraft.mainWindow.getScaledWidth() - 320) / 2 * (int) this.minecraft.mainWindow.getGuiScaleFactor(), (this.minecraft.mainWindow.getScaledHeight() - 240) / 2 * (int) this.minecraft.mainWindow.getGuiScaleFactor(), 320 * (int) this.minecraft.mainWindow.getGuiScaleFactor(), 240 * (int) this.minecraft.mainWindow.getGuiScaleFactor());
		// GLU.gluPerspective(90.0F, 1.333333F, 9.0F, 80.0F);
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		GlStateManager.loadIdentity();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.translatef(0.0F, 5.0F, -16.0F);
		GlStateManager.scalef(10.0F, 10.0F, 2.5F);
		GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.enableRescaleNormal();

		if (this.grill != null) {
			renderItems.renderItem(DecorUtil.createFurnitureWithState(DecorBlocks.grill, grill.getStoredBlockState()), TransformType.FIXED);
		}

		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.viewport(0, 0, this.minecraft.mainWindow.getWidth(), this.minecraft.mainWindow.getHeight());
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}