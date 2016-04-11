package com.grim3212.mc.decor.client.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.grim3212.mc.core.util.NBTHelper;
import com.grim3212.mc.decor.GrimDecor;
import com.grim3212.mc.decor.block.DecorBlocks;
import com.grim3212.mc.decor.block.container.ContainerGrill;
import com.grim3212.mc.decor.tile.TileEntityGrill;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@SuppressWarnings("deprecation")
public class GuiGrill extends GuiContainer {

	private TileEntityGrill grillInventory;
	private RenderItem renderItems = Minecraft.getMinecraft().getRenderItem();
	private static ResourceLocation GrillGUI = new ResourceLocation(GrimDecor.modID, "textures/gui/grill.png");

	public GuiGrill(InventoryPlayer par1InventoryPlayer, TileEntityGrill par2TileEntityGrill) {
		super(new ContainerGrill(par1InventoryPlayer, par2TileEntityGrill));
		this.grillInventory = par2TileEntityGrill;
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(this.grillInventory.getName(), this.xSize / 2 - this.fontRendererObj.getStringWidth(grillInventory.getName()) / 2, 6, 4210752);
		this.fontRendererObj.drawString("Tier " + grillInventory.getTier(), 73, 14, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
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

		GL11.glPushMatrix();
		GL11.glMatrixMode(5889);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		ScaledResolution var7 = new ScaledResolution(this.mc);
		GL11.glViewport((var7.getScaledWidth() - 320) / 2 * var7.getScaleFactor(), (var7.getScaledHeight() - 240) / 2 * var7.getScaleFactor(), 320 * var7.getScaleFactor(), 240 * var7.getScaleFactor());
		GLU.gluPerspective(90.0F, 1.333333F, 9.0F, 80.0F);
		GL11.glMatrixMode(5888);
		GL11.glLoadIdentity();
		RenderHelper.enableStandardItemLighting();
		GL11.glTranslatef(0.0F, 5.0F, -16.0F);
		GL11.glScalef(10.0F, 10.0F, 2.5F);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
		GL11.glEnable(32826);

		if (this.grillInventory != null) {
			ItemStack grill = new ItemStack(DecorBlocks.grill);
			NBTHelper.setInteger(grill, "blockID", grillInventory.getBlockID());
			NBTHelper.setInteger(grill, "blockMeta", grillInventory.getBlockMeta());
			renderItems.renderItem(grill, TransformType.NONE);
		}

		GL11.glDisable(32826);
		RenderHelper.disableStandardItemLighting();
		GL11.glMatrixMode(5889);
		GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glPopMatrix();
		GL11.glMatrixMode(5888);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}