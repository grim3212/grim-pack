package com.grim3212.mc.pack.industry.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.GuiGrimContainer;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.industry.inventory.ContainerLocksmithWorkbench;
import com.grim3212.mc.pack.industry.network.MessageSetLock;

import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiLocksmithWorkbench extends GuiGrimContainer implements IContainerListener {

	private TextFieldWidget lockField;
	private final ContainerLocksmithWorkbench locksmith;
	private static final ResourceLocation GUILocksmith = new ResourceLocation(GrimPack.modID, "textures/gui/gui_locksmith.png");

	public GuiLocksmithWorkbench(PlayerInventory inventory, World world, BlockPos pos) {
		super(new ContainerLocksmithWorkbench(inventory, world, pos));
		this.locksmith = (ContainerLocksmithWorkbench) this.inventorySlots;
	}

	@Override
	public void initGui() {
		super.initGui();
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		Keyboard.enableRepeatEvents(true);
		this.lockField = new TextFieldWidget(0, this.fontRenderer, i + 67, j + 39, 42, 12);
		this.lockField.setTextColor(-1);
		this.lockField.setDisabledTextColour(-1);
		this.lockField.setEnableBackgroundDrawing(false);
		this.lockField.setMaxStringLength(10);
		this.inventorySlots.removeListener(this);
		this.inventorySlots.addListener(this);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
		this.inventorySlots.removeListener(this);
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		if (this.lockField.textboxKeyTyped(typedChar, keyCode)) {
			this.changeLock();
		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}

	private void changeLock() {
		String s = this.lockField.getText();
		this.locksmith.updateLock(s);
		PacketDispatcher.sendToServer(new MessageSetLock(s));
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.lockField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString(I18n.format("container.locksmith_workbench"), 38, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.locksmith_workbench.combo"), 59, 17, 7368816);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

	}

	@Override
	public void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUILocksmith);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();
		this.lockField.drawTextBox();
	}

	@Override
	public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
		if (slotInd == 0) {

			if (!stack.isEmpty()) {
				if (NBTHelper.hasTag(stack, "Lock")) {
					String s = NBTHelper.getString(stack, "Lock");
					if (!StringUtils.isBlank(s)) {
						this.lockField.setText(s);
					}
				}

				this.changeLock();
			} else {
				// No-op; Dont Change the current lock
			}
		}
	}

	@Override
	public void sendAllWindowProperties(Container containerIn, IInventory inventory) {
	}

	@Override
	public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {
		this.sendSlotContents(containerToSend, 0, containerToSend.getSlot(0).getStack());
	}

	@Override
	public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) {
	}
}