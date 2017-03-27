package com.grim3212.mc.pack.industry.client.gui;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.industry.inventory.ContainerItemTower;
import com.grim3212.mc.pack.industry.inventory.InventoryItemTower;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiItemTower extends GuiContainer {

	private InventoryItemTower itemTowers;
	private ContainerItemTower containerItemTower;
	private int rowID = 0;
	private static final ResourceLocation GUITower = new ResourceLocation(GrimPack.modID, "textures/gui/gui_tower.png");

	public GuiItemTower(EntityPlayer player, IInventory itemTowers) {
		super(new ContainerItemTower(player, itemTowers));
		this.containerItemTower = ((ContainerItemTower) this.inventorySlots);
		this.itemTowers = ((InventoryItemTower) itemTowers);
		this.ySize = 132;
		this.xSize += 17;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(I18n.format(this.itemTowers.getName()) + I18n.format("container.item_tower.row", (this.rowID + 1)) + this.itemTowers.getSizeInventory() / 9, 8, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUITower);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

	public void scrollInventory(boolean directionDown) {
		int prevRowID = this.rowID;

		if (directionDown) {
			if (this.rowID < this.itemTowers.getSizeInventory() / 9 - 1)
				this.rowID += 1;
			else {
				this.rowID = 0;
			}

			this.itemTowers.setAnimation(1);
		} else {
			if (this.rowID > 0)
				this.rowID -= 1;
			else {
				this.rowID = (this.itemTowers.getSizeInventory() / 9 - 1);
			}

			this.itemTowers.setAnimation(-1);
		}

		if (prevRowID != this.rowID) {
			this.containerItemTower.setDisplayRow(this.rowID);
			this.mc.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0F, 1.0F);
		}
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();

		int i = Mouse.getEventDWheel();
		if (i != 0) {
			if (i > 0)
				scrollInventory(true);

			if (i < 0)
				scrollInventory(false);
		}
	}

	@Override
	public void mouseClicked(int x, int y, int button) throws IOException {
		super.mouseClicked(x, y, button);
		int modx = x - (this.width - this.xSize) / 2;
		int mody = y - (this.height - this.ySize) / 2;

		if ((modx >= 173) && (modx < 186) && (mody >= 13) && (mody < 26))
			scrollInventory(true);

		if ((modx >= 173) && (modx < 186) && (mody >= 26) && (mody < 39))
			scrollInventory(false);
	}
}