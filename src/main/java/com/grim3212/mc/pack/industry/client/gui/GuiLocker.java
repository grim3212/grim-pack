package com.grim3212.mc.pack.industry.client.gui;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.industry.inventory.ContainerLocker;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiLocker extends GuiContainer {

	private static final ResourceLocation GUILocker = new ResourceLocation(GrimPack.modID, "textures/gui/gui_locker.png");
	private IInventory lockerInventory;
	private ContainerLocker containerLocker;
	private int rowID = 0;

	public GuiLocker(EntityPlayer player, IInventory lockerInventory) {
		super(new ContainerLocker(player, lockerInventory));
		this.containerLocker = ((ContainerLocker) this.inventorySlots);
		this.lockerInventory = lockerInventory;
		this.ySize = 204;
		this.xSize += 17;
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString(I18n.format(this.lockerInventory.getName()), 8, 6, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUILocker);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		GL11.glEnable(3042);
		drawTexturedModalRect(k + (this.xSize - 18), l + 20 + this.rowID, this.xSize, 0, 10, 5);
		GL11.glDisable(3042);
	}

	public void scrollInventory(boolean directionDown) {
		int prevRowID = this.rowID;

		if (directionDown) {
			if (this.rowID < 5)
				this.rowID += 1;
		} else if (this.rowID > 0)
			this.rowID -= 1;

		if (prevRowID != this.rowID) {
			this.containerLocker.setDisplayRow(this.rowID);
			this.mc.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0F, 1.0F);
		}
	}

	@Override
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
		super.handleMouseClick(slotIn, slotId, mouseButton, type);

		int i = Mouse.getEventDWheel();
		if (i != 0) {
			if (i > 0)
				scrollInventory(false);

			if (i < 0)
				scrollInventory(true);
		}
	}

	@Override
	public void mouseClicked(int x, int y, int button) throws IOException {
		super.mouseClicked(x, y, button);
		int modx = x - (this.width - this.xSize) / 2;
		int mody = y - (this.height - this.ySize) / 2;

		if ((modx >= 173) && (modx < 186) && (mody >= 7) && (mody < 20))
			scrollInventory(false);

		if ((modx >= 173) && (modx < 186) && (mody >= 30) && (mody < 43))
			scrollInventory(true);
	}
}