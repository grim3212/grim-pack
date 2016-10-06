package com.grim3212.mc.pack.industry.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.industry.inventory.ContainerSpecificSensor;
import com.grim3212.mc.pack.industry.network.MessageSensorChangeMode;
import com.grim3212.mc.pack.industry.network.MessageSensorSetEntity;
import com.grim3212.mc.pack.industry.network.MessageSensorSetItem;
import com.grim3212.mc.pack.industry.network.MessageSensorSetPlayer;
import com.grim3212.mc.pack.industry.network.MessageSensorSetPos;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiSpecificSensor extends GuiContainer {

	private GuiTextField posX;
	private GuiTextField posY;
	private GuiTextField posZ;
	private GuiTextField entityName;
	private GuiTextField playerName;
	private TileEntitySpecificSensor te;
	private static final ResourceLocation GUI_LOCATION = new ResourceLocation(GrimPack.modID, "textures/gui/specific_sensor.png");
	private boolean selectingItem = false;
	private RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

	public GuiSpecificSensor(InventoryPlayer playerInv, TileEntitySpecificSensor te, BlockPos pos) {
		super(new ContainerSpecificSensor(pos, playerInv));
		this.xSize = 256;
		this.ySize = 220;
		this.te = te;
	}

	@Override
	public void initGui() {
		super.initGui();

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		Keyboard.enableRepeatEvents(true);

		buttonList.clear();
		buttonList.add(new GuiButton(0, x + 171, y + 30, 75, 20, I18n.format("grimpack.industry.sensor." + te.getMode().name())));
		buttonList.add(new GuiButton(1, x + 171, y + 50, 75, 20, I18n.format("grimpack.industry.sensor.setItem")));
		buttonList.add(new GuiButton(2, x + 171, y + 70, 75, 20, I18n.format("grimpack.industry.sensor.setEntity")));
		buttonList.add(new GuiButton(3, x + 171, y + 90, 75, 20, I18n.format("grimpack.industry.sensor.setPlayer")));
		buttonList.add(new GuiButton(4, x + 171, y + 110, 75, 20, I18n.format("grimpack.industry.sensor.setPos")));

		entityName = new GuiTextField(0, fontRendererObj, x + 57, y + 73, 100, 15);
		entityName.setFocused(false);
		entityName.setMaxStringLength(64);
		entityName.setText(te.getSpecific().getEntityName());

		playerName = new GuiTextField(1, fontRendererObj, x + 57, y + 93, 100, 15);
		playerName.setFocused(false);
		playerName.setText(te.getSpecific().getPlayerName());

		posX = new GuiTextField(2, fontRendererObj, x + 9 + 14, y + 113, 34, 15);
		posX.setFocused(false);
		posX.setMaxStringLength(10);
		posX.setTextColor(0xffffff);
		posX.setText(te.getSensorPos() == null ? String.valueOf(te.getPos().getX()) : String.valueOf(te.getSensorPos().getX()));

		posY = new GuiTextField(3, fontRendererObj, x + 59 + 14, y + 113, 34, 15);
		posY.setFocused(false);
		posY.setMaxStringLength(10);
		posY.setText(te.getSensorPos() == null ? String.valueOf(te.getPos().getY()) : String.valueOf(te.getSensorPos().getY()));

		posZ = new GuiTextField(4, fontRendererObj, x + 109 + 14, y + 113, 34, 15);
		posZ.setFocused(false);
		posZ.setMaxStringLength(10);
		posZ.setText(te.getSensorPos() == null ? String.valueOf(te.getPos().getZ()) : String.valueOf(te.getSensorPos().getZ()));
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (entityName.isFocused()) {
			entityName.textboxKeyTyped(typedChar, keyCode);
			if (!EntityList.isStringValidEntityName(entityName.getText())) {
				entityName.setTextColor(0xff0000);
			} else {
				entityName.setTextColor(0xffffff);
			}
		}
		if (playerName.isFocused()) {
			playerName.textboxKeyTyped(typedChar, keyCode);
		}
		if (posX.isFocused() && ((typedChar >= '0' && typedChar <= '9') || typedChar == '-') || typedChar == '\b') {
			posX.textboxKeyTyped(typedChar, keyCode);
		}
		if (posY.isFocused() && ((typedChar >= '0' && typedChar <= '9') || typedChar == '-') || typedChar == '\b') {
			posY.textboxKeyTyped(typedChar, keyCode);
		}
		if (posZ.isFocused() && ((typedChar >= '0' && typedChar <= '9') || typedChar == '-') || typedChar == '\b') {
			posZ.textboxKeyTyped(typedChar, keyCode);
		}
		if (!entityName.isFocused() && !playerName.isFocused() && !posX.isFocused() && !posY.isFocused() && !posZ.isFocused())
			super.keyTyped(typedChar, keyCode);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (this.selectingItem) {
			Slot slot = getSlotUnderMouse();
			if (slot != null) {
				ItemStack stack = slot.getStack();
				te.getSpecific().setItemStackSpecific(stack);
				PacketDispatcher.sendToServer(new MessageSensorSetItem(te.getPos(), stack));
			} else {
				super.mouseClicked(mouseX, mouseY, mouseButton);
				entityName.mouseClicked(mouseX, mouseY, mouseButton);
				playerName.mouseClicked(mouseX, mouseY, mouseButton);
				posX.mouseClicked(mouseX, mouseY, mouseButton);
				posY.mouseClicked(mouseX, mouseY, mouseButton);
				posZ.mouseClicked(mouseX, mouseY, mouseButton);
			}
			this.selectingItem = false;
		} else {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			entityName.mouseClicked(mouseX, mouseY, mouseButton);
			playerName.mouseClicked(mouseX, mouseY, mouseButton);
			posX.mouseClicked(mouseX, mouseY, mouseButton);
			posY.mouseClicked(mouseX, mouseY, mouseButton);
			posZ.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();

		entityName.updateCursorCounter();
		playerName.updateCursorCounter();
		posX.updateCursorCounter();
		posY.updateCursorCounter();
		posZ.updateCursorCounter();
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case 0:
			te.setMode(te.getMode().getNext());
			button.displayString = I18n.format("grimpack.industry.sensor." + te.getMode().name());
			PacketDispatcher.sendToServer(new MessageSensorChangeMode(te.getPos(), te.getMode()));
			break;
		case 1:
			this.selectingItem = !this.selectingItem;
			break;
		case 2:
			te.getSpecific().setEntitySpecific(this.entityName.getText());
			PacketDispatcher.sendToServer(new MessageSensorSetEntity(te.getPos(), this.entityName.getText()));
			break;
		case 3:
			te.getSpecific().setPlayerSpecific(this.playerName.getText());
			PacketDispatcher.sendToServer(new MessageSensorSetPlayer(te.getPos(), this.playerName.getText()));
			break;
		case 4:
			BlockPos sensePos = new BlockPos(Integer.valueOf(this.posX.getText()), Integer.valueOf(this.posY.getText()), Integer.valueOf(this.posZ.getText()));
			te.setSensorPos(sensePos);
			PacketDispatcher.sendToServer(new MessageSensorSetPos(te.getPos(), sensePos));
			break;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.te.getDisplayName().getUnformattedText();
		drawCenteredString(fontRendererObj, s, xSize / 2, 8, 0xffffff);

		GlStateManager.pushMatrix();
		GlStateManager.scale(0.70f, 0.70f, 0.75f);
		this.fontRendererObj.drawString(I18n.format("grimpack.industry.sensor.entityName"), 19, 110, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("grimpack.industry.sensor.playerName"), 14, 138, 0xffffff);
		GlStateManager.popMatrix();

		if (te.isGoodPosition()) {
			this.fontRendererObj.drawString(I18n.format("grimpack.industry.sensor.goodPos"), 11, 36, 0x00ff00);
		} else {
			this.fontRendererObj.drawString(I18n.format("grimpack.industry.sensor.badPos"), 11, 36, 0xff0000);
		}

		this.fontRendererObj.drawString(I18n.format("grimpack.industry.sensor.mode"), 140, 36, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("grimpack.industry.sensor.x"), 11, 117, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("grimpack.industry.sensor.y"), 61, 117, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("grimpack.industry.sensor.z"), 111, 117, 0xffffff);

		this.fontRendererObj.drawString(this.selectingItem ? I18n.format("grimpack.industry.sensor.selecting") : this.te.getSpecific().getStack() == null ? I18n.format("grimpack.industry.sensor.noitem") : I18n.format("grimpack.industry.sensor.selected"), 11, 56, 0xffffff);
		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		if (this.te.getSpecific().getStack() != null) {
			this.renderItem.renderItemAndEffectIntoGUI(this.te.getSpecific().getStack(), xSize / 2 - 35, 52);
		}
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUI_LOCATION);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

		entityName.drawTextBox();
		playerName.drawTextBox();
		posX.drawTextBox();
		posY.drawTextBox();
		posZ.drawTextBox();
	}

}
