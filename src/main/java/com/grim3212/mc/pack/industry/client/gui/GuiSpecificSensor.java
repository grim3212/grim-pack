package com.grim3212.mc.pack.industry.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.TooltipHelper;
import com.grim3212.mc.pack.core.client.gui.GuiButtonIcon;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.inventory.ContainerSpecificSensor;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.item.ItemPositionFinder;
import com.grim3212.mc.pack.industry.network.MessageSensorChangeMode;
import com.grim3212.mc.pack.industry.network.MessageSensorSetBox;
import com.grim3212.mc.pack.industry.network.MessageSensorSetEntity;
import com.grim3212.mc.pack.industry.network.MessageSensorSetItem;
import com.grim3212.mc.pack.industry.network.MessageSensorSetPlayer;
import com.grim3212.mc.pack.industry.network.MessageSensorSetPos;
import com.grim3212.mc.pack.industry.network.MessageSensorSetRender;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class GuiSpecificSensor extends GuiContainer {

	private GuiTextField entityName;
	private GuiTextField playerName;
	private GuiButton mode;
	private GuiButton setItem;
	private GuiButton setEntity;
	private GuiButton setPlayer;
	private GuiButton setPos;
	private GuiButton setSize;
	private GuiButton radiusAdd1;
	private GuiButton radiusSub1;
	private GuiButtonIcon reset;
	private GuiButtonSensorRender sensorRender;
	private GuiButtonIcon moreOptions;

	private GuiTextField minX;
	private GuiTextField minY;
	private GuiTextField minZ;
	private GuiTextField maxX;
	private GuiTextField maxY;
	private GuiTextField maxZ;

	private boolean onMainPage = true;
	private TileEntitySpecificSensor te;
	private static final ResourceLocation GUI_LOCATION = new ResourceLocation(GrimPack.modID, "textures/gui/specific_sensor.png");
	private boolean selectingItem = false;
	private boolean settingPos = false;
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
		buttonList.add(mode = new GuiButton(0, x + 171, y + 30, 75, 20, I18n.format("grimpack.industry.sensor." + te.getMode().name())));
		buttonList.add(setItem = new GuiButton(1, x + 171, y + 50, 75, 20, I18n.format("grimpack.industry.sensor.setItem")));
		buttonList.add(setEntity = new GuiButton(2, x + 171, y + 70, 75, 20, I18n.format("grimpack.industry.sensor.setEntity")));
		buttonList.add(setPlayer = new GuiButton(3, x + 171, y + 90, 75, 20, I18n.format("grimpack.industry.sensor.setPlayer")));
		buttonList.add(setPos = new GuiButton(4, x + 171, y + 110, 75, 20, I18n.format("grimpack.industry.sensor.setPos")));
		buttonList.add(sensorRender = new GuiButtonSensorRender(5, x + 205, y + 10, te.renderSensorPos()));
		buttonList.add(moreOptions = new GuiButtonIcon(6, x + 225, y + 10, 48, 0, this.te.getBlockType() == IndustryBlocks.specific_sensor ? I18n.format("grimpack.industry.sensor.upgradeOptions") : I18n.format("grimpack.industry.sensor.moreOptions")));
		if (this.te.getBlockType() == IndustryBlocks.specific_sensor) {
			this.moreOptions.enabled = false;
		}
		buttonList.add(setSize = new GuiButton(7, x + 171, y + 70, 75, 20, I18n.format("grimpack.industry.sensor.setSize")));
		this.setSize.visible = false;
		this.setSize.enabled = false;
		buttonList.add(radiusAdd1 = new GuiButton(8, x + 171, y + 90, 75, 20, I18n.format("grimpack.industry.sensor.radiusAdd1")));
		this.radiusAdd1.visible = false;
		this.radiusAdd1.enabled = false;
		buttonList.add(radiusSub1 = new GuiButton(9, x + 171, y + 110, 75, 20, I18n.format("grimpack.industry.sensor.radiusSub1")));
		this.radiusSub1.visible = false;
		this.radiusSub1.enabled = false;
		buttonList.add(reset = new GuiButtonIcon(10, x + 205, y + 10, 64, 0, I18n.format("grimpack.industry.sensor.reset")));
		this.reset.visible = false;
		this.reset.enabled = false;

		entityName = new GuiTextField(0, fontRenderer, x + 57, y + 73, 100, 15);
		entityName.setFocused(false);
		entityName.setMaxStringLength(64);
		entityName.setText(te.getSpecific().getEntityName());

		playerName = new GuiTextField(1, fontRenderer, x + 57, y + 93, 100, 15);
		playerName.setFocused(false);
		playerName.setText(te.getSpecific().getPlayerName());

		minX = new GuiTextField(5, fontRenderer, x + 9 + 14, y + 75, 34, 15);
		minX.setFocused(false);
		minX.setMaxStringLength(10);
		minX.setText(String.valueOf(te.getSenseBox().minX));
		minX.setVisible(false);
		minX.setEnabled(false);

		minY = new GuiTextField(5, fontRenderer, x + 59 + 14, y + 75, 34, 15);
		minY.setFocused(false);
		minY.setMaxStringLength(10);
		minY.setText(String.valueOf(te.getSenseBox().minY));
		minY.setVisible(false);
		minY.setEnabled(false);

		minZ = new GuiTextField(5, fontRenderer, x + 109 + 14, y + 75, 34, 15);
		minZ.setFocused(false);
		minZ.setMaxStringLength(10);
		minZ.setText(String.valueOf(te.getSenseBox().minZ));
		minZ.setVisible(false);
		minZ.setEnabled(false);

		maxX = new GuiTextField(5, fontRenderer, x + 9 + 14, y + 113, 34, 15);
		maxX.setFocused(false);
		maxX.setMaxStringLength(10);
		maxX.setText(String.valueOf(te.getSenseBox().maxX));
		maxX.setVisible(false);
		maxX.setEnabled(false);

		maxY = new GuiTextField(5, fontRenderer, x + 59 + 14, y + 113, 34, 15);
		maxY.setFocused(false);
		maxY.setMaxStringLength(10);
		maxY.setText(String.valueOf(te.getSenseBox().maxY));
		maxY.setVisible(false);
		maxY.setEnabled(false);

		maxZ = new GuiTextField(5, fontRenderer, x + 109 + 14, y + 113, 34, 15);
		maxZ.setFocused(false);
		maxZ.setMaxStringLength(10);
		maxZ.setText(String.valueOf(te.getSenseBox().maxZ));
		maxZ.setVisible(false);
		maxZ.setEnabled(false);
	}

	private boolean isStringFloat(String s) {
		try {
			Float.valueOf(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (this.onMainPage) {
			if (entityName.isFocused()) {
				entityName.textboxKeyTyped(typedChar, keyCode);
				if (!EntityList.isRegistered(new ResourceLocation(entityName.getText()))) {
					entityName.setTextColor(0xff0000);
				} else {
					entityName.setTextColor(0xffffff);
				}
			}
			if (playerName.isFocused()) {
				playerName.textboxKeyTyped(typedChar, keyCode);
			}
			if (!entityName.isFocused() && !playerName.isFocused())
				super.keyTyped(typedChar, keyCode);
		} else {
			if (minX.isFocused() && ((typedChar >= '0' && typedChar <= '9') || typedChar == '-' || typedChar == '.') || typedChar == '\b' || (keyCode == 203 || keyCode == 205)) {
				minX.textboxKeyTyped(typedChar, keyCode);
				if (!isStringFloat(minX.getText())) {
					minX.setTextColor(0xff0000);
				} else {
					minX.setTextColor(0xffffff);
				}
			}
			if (minY.isFocused() && ((typedChar >= '0' && typedChar <= '9') || typedChar == '-' || typedChar == '.') || typedChar == '\b' || (keyCode == 203 || keyCode == 205)) {
				minY.textboxKeyTyped(typedChar, keyCode);
				if (!isStringFloat(minY.getText())) {
					minY.setTextColor(0xff0000);
				} else {
					minY.setTextColor(0xffffff);
				}
			}
			if (minZ.isFocused() && ((typedChar >= '0' && typedChar <= '9') || typedChar == '-' || typedChar == '.') || typedChar == '\b' || (keyCode == 203 || keyCode == 205)) {
				minZ.textboxKeyTyped(typedChar, keyCode);
				if (!isStringFloat(minZ.getText())) {
					minZ.setTextColor(0xff0000);
				} else {
					minZ.setTextColor(0xffffff);
				}
			}
			if (maxX.isFocused() && ((typedChar >= '0' && typedChar <= '9') || typedChar == '-' || typedChar == '.') || typedChar == '\b' || (keyCode == 203 || keyCode == 205)) {
				maxX.textboxKeyTyped(typedChar, keyCode);
				if (!isStringFloat(maxX.getText())) {
					maxX.setTextColor(0xff0000);
				} else {
					maxX.setTextColor(0xffffff);
				}
			}
			if (maxY.isFocused() && ((typedChar >= '0' && typedChar <= '9') || typedChar == '-' || typedChar == '.') || typedChar == '\b' || (keyCode == 203 || keyCode == 205)) {
				maxY.textboxKeyTyped(typedChar, keyCode);
				if (!isStringFloat(maxY.getText())) {
					maxY.setTextColor(0xff0000);
				} else {
					maxY.setTextColor(0xffffff);
				}
			}
			if (maxZ.isFocused() && ((typedChar >= '0' && typedChar <= '9') || typedChar == '-' || typedChar == '.') || typedChar == '\b' || (keyCode == 203 || keyCode == 205)) {
				maxZ.textboxKeyTyped(typedChar, keyCode);
				if (!isStringFloat(maxZ.getText())) {
					maxZ.setTextColor(0xff0000);
				} else {
					maxZ.setTextColor(0xffffff);
				}
			}
			if (!minX.isFocused() && !minY.isFocused() && !minZ.isFocused() && !maxX.isFocused() && !maxY.isFocused() && !maxZ.isFocused())
				super.keyTyped(typedChar, keyCode);

			if (isStringFloat(minX.getText()) && isStringFloat(minY.getText()) && isStringFloat(minZ.getText()) && isStringFloat(maxX.getText()) && isStringFloat(maxY.getText()) && isStringFloat(maxZ.getText())) {
				this.setSize.enabled = true;
				this.radiusAdd1.enabled = true;
				this.radiusSub1.enabled = true;
			} else {
				this.setSize.enabled = false;
				this.radiusAdd1.enabled = false;
				this.radiusSub1.enabled = false;
			}
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (this.onMainPage) {
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
				}
				this.selectingItem = false;
			} else if (this.settingPos) {
				Slot slot = getSlotUnderMouse();
				if (slot != null) {
					ItemStack stack = slot.getStack();
					if (stack.getItem() == IndustryItems.position_finder) {
						BlockPos coords = ((ItemPositionFinder) stack.getItem()).getCoords(stack);
						if (coords != null) {
							te.setSensorPos(coords);
							PacketDispatcher.sendToServer(new MessageSensorSetPos(te.getPos(), coords));
						}
					}
				} else {
					super.mouseClicked(mouseX, mouseY, mouseButton);
					entityName.mouseClicked(mouseX, mouseY, mouseButton);
					playerName.mouseClicked(mouseX, mouseY, mouseButton);
				}
				this.settingPos = false;
			} else {
				super.mouseClicked(mouseX, mouseY, mouseButton);
				entityName.mouseClicked(mouseX, mouseY, mouseButton);
				playerName.mouseClicked(mouseX, mouseY, mouseButton);
			}
		} else {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			minX.mouseClicked(mouseX, mouseY, mouseButton);
			minY.mouseClicked(mouseX, mouseY, mouseButton);
			minZ.mouseClicked(mouseX, mouseY, mouseButton);
			maxX.mouseClicked(mouseX, mouseY, mouseButton);
			maxY.mouseClicked(mouseX, mouseY, mouseButton);
			maxZ.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();

		if (this.onMainPage) {
			entityName.updateCursorCounter();
			playerName.updateCursorCounter();
		} else {
			minX.updateCursorCounter();
			minY.updateCursorCounter();
			minZ.updateCursorCounter();
			maxX.updateCursorCounter();
			maxY.updateCursorCounter();
			maxZ.updateCursorCounter();
		}
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
			this.settingPos = !this.settingPos;
			break;
		case 5:
			boolean shouldRender = !te.renderSensorPos();
			sensorRender.setShouldRender(shouldRender);
			te.setRenderSensorPos(shouldRender);
			PacketDispatcher.sendToServer(new MessageSensorSetRender(te.getPos(), shouldRender));
			break;
		case 6:
			this.changePage();
			break;
		case 7:
			if (isStringFloat(minX.getText()) && isStringFloat(minY.getText()) && isStringFloat(minZ.getText()) && isStringFloat(maxX.getText()) && isStringFloat(maxY.getText()) && isStringFloat(maxZ.getText())) {
				float lMinX = Float.valueOf(this.minX.getText());
				float lMinY = Float.valueOf(this.minY.getText());
				float lMinZ = Float.valueOf(this.minZ.getText());
				float lMaxX = Float.valueOf(this.maxX.getText());
				float lMaxY = Float.valueOf(this.maxY.getText());
				float lMaxZ = Float.valueOf(this.maxZ.getText());

				double nMinX = checkSize(lMinX);
				double nMinY = checkSize(lMinY);
				double nMinZ = checkSize(lMinZ);
				double nMaxX = checkSize(lMaxX);
				double nMaxY = checkSize(lMaxY);
				double nMaxZ = checkSize(lMaxZ);

				this.minX.setText(String.valueOf(nMinX));
				this.minY.setText(String.valueOf(nMinY));
				this.minZ.setText(String.valueOf(nMinZ));
				this.maxX.setText(String.valueOf(nMaxX));
				this.maxY.setText(String.valueOf(nMaxY));
				this.maxZ.setText(String.valueOf(nMaxZ));

				te.setSenseBox(new AxisAlignedBB(nMinX, nMinY, nMinZ, nMaxX, nMaxY, nMaxZ));
				PacketDispatcher.sendToServer(new MessageSensorSetBox(te.getPos(), nMinX, nMinY, nMinZ, nMaxX, nMaxY, nMaxZ));
			}
			break;
		case 8:
			if (isStringFloat(minX.getText()) && isStringFloat(minY.getText()) && isStringFloat(minZ.getText()) && isStringFloat(maxX.getText()) && isStringFloat(maxY.getText()) && isStringFloat(maxZ.getText())) {
				float lMinX = Float.valueOf(this.minX.getText());
				float lMinY = Float.valueOf(this.minY.getText());
				float lMinZ = Float.valueOf(this.minZ.getText());
				float lMaxX = Float.valueOf(this.maxX.getText());
				float lMaxY = Float.valueOf(this.maxY.getText());
				float lMaxZ = Float.valueOf(this.maxZ.getText());

				double nMinX = checkSize(lMinX - 1);
				double nMinY = checkSize(lMinY - 1);
				double nMinZ = checkSize(lMinZ - 1);
				double nMaxX = checkSize(lMaxX + 1);
				double nMaxY = checkSize(lMaxY + 1);
				double nMaxZ = checkSize(lMaxZ + 1);

				this.minX.setText(String.valueOf(nMinX));
				this.minY.setText(String.valueOf(nMinY));
				this.minZ.setText(String.valueOf(nMinZ));
				this.maxX.setText(String.valueOf(nMaxX));
				this.maxY.setText(String.valueOf(nMaxY));
				this.maxZ.setText(String.valueOf(nMaxZ));

				te.setSenseBox(new AxisAlignedBB(nMinX, nMinY, nMinZ, nMaxX, nMaxY, nMaxZ));
				PacketDispatcher.sendToServer(new MessageSensorSetBox(te.getPos(), nMinX, nMinY, nMinZ, nMaxX, nMaxY, nMaxZ));
			}
			break;
		case 9:
			if (isStringFloat(minX.getText()) && isStringFloat(minY.getText()) && isStringFloat(minZ.getText()) && isStringFloat(maxX.getText()) && isStringFloat(maxY.getText()) && isStringFloat(maxZ.getText())) {
				float lMinX = Float.valueOf(this.minX.getText());
				float lMinY = Float.valueOf(this.minY.getText());
				float lMinZ = Float.valueOf(this.minZ.getText());
				float lMaxX = Float.valueOf(this.maxX.getText());
				float lMaxY = Float.valueOf(this.maxY.getText());
				float lMaxZ = Float.valueOf(this.maxZ.getText());

				double nMinX = checkSize(lMinX + 1);
				double nMinY = checkSize(lMinY + 1);
				double nMinZ = checkSize(lMinZ + 1);
				double nMaxX = checkSize(lMaxX - 1);
				double nMaxY = checkSize(lMaxY - 1);
				double nMaxZ = checkSize(lMaxZ - 1);

				this.minX.setText(String.valueOf(nMinX));
				this.minY.setText(String.valueOf(nMinY));
				this.minZ.setText(String.valueOf(nMinZ));
				this.maxX.setText(String.valueOf(nMaxX));
				this.maxY.setText(String.valueOf(nMaxY));
				this.maxZ.setText(String.valueOf(nMaxZ));

				te.setSenseBox(new AxisAlignedBB(nMinX, nMinY, nMinZ, nMaxX, nMaxY, nMaxZ));
				PacketDispatcher.sendToServer(new MessageSensorSetBox(te.getPos(), nMinX, nMinY, nMinZ, nMaxX, nMaxY, nMaxZ));
			}
			break;
		case 10:
			this.minX.setText("0.0");
			this.minX.setTextColor(0xffffff);
			this.minZ.setText("0.0");
			this.minZ.setTextColor(0xffffff);
			this.minZ.setText("0.0");
			this.minZ.setTextColor(0xffffff);
			this.maxX.setText("1.0");
			this.maxX.setTextColor(0xffffff);
			this.maxY.setText("1.0");
			this.maxY.setTextColor(0xffffff);
			this.maxZ.setText("1.0");
			this.maxZ.setTextColor(0xffffff);

			this.setSize.enabled = true;
			this.radiusAdd1.enabled = true;
			this.radiusSub1.enabled = true;

			te.setSenseBox(new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F));
			PacketDispatcher.sendToServer(new MessageSensorSetBox(te.getPos(), 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F));
			break;
		}
	}

	private double checkSize(double d) {
		if (d > 5) {
			return 5.0D;
		} else if (d < -5) {
			return -5.0D;
		}
		return d;
	}

	private void changePage() {
		// Page 1 Buttons
		this.mode.visible = !onMainPage;
		this.mode.enabled = !onMainPage;
		this.setItem.visible = !onMainPage;
		this.setItem.enabled = !onMainPage;
		this.setEntity.visible = !onMainPage;
		this.setEntity.enabled = !onMainPage;
		this.setPlayer.visible = !onMainPage;
		this.setPlayer.enabled = !onMainPage;
		this.setPos.visible = !onMainPage;
		this.setPos.enabled = !onMainPage;
		this.sensorRender.visible = !onMainPage;
		this.sensorRender.enabled = !onMainPage;

		// Page 1 Text Boxes
		this.entityName.setEnabled(!onMainPage);
		this.entityName.setVisible(!onMainPage);
		this.playerName.setEnabled(!onMainPage);
		this.playerName.setVisible(!onMainPage);

		// Page 2 Buttons
		this.setSize.visible = onMainPage;
		this.setSize.enabled = onMainPage;
		this.reset.visible = onMainPage;
		this.reset.enabled = onMainPage;
		this.radiusAdd1.visible = onMainPage;
		this.radiusAdd1.enabled = onMainPage;
		this.radiusSub1.visible = onMainPage;
		this.radiusSub1.enabled = onMainPage;

		// Page 2 Text Boxes
		this.minX.setEnabled(onMainPage);
		this.minX.setVisible(onMainPage);
		this.minY.setEnabled(onMainPage);
		this.minY.setVisible(onMainPage);
		this.minZ.setEnabled(onMainPage);
		this.minZ.setVisible(onMainPage);
		this.maxX.setEnabled(onMainPage);
		this.maxX.setVisible(onMainPage);
		this.maxY.setEnabled(onMainPage);
		this.maxY.setVisible(onMainPage);
		this.maxZ.setEnabled(onMainPage);
		this.maxZ.setVisible(onMainPage);

		this.onMainPage = !onMainPage;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.te.getDisplayName().getUnformattedText();
		drawCenteredString(fontRenderer, s, xSize / 2, 8, 0xffffff);

		if (this.onMainPage) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.70f, 0.70f, 0.75f);
			this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.entityName"), 19, 110, 0xffffff);
			this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.playerName"), 14, 138, 0xffffff);
			GlStateManager.popMatrix();

			if (te.isGoodPosition()) {
				this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.goodPos"), 11, 36, 0x00ff00);
			} else {

				if (!(te.getSensorPos().distanceSq(te.getPos()) > 80)) {
					this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.badPos"), 11, 36, 0xff0000);
				} else {
					this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.outOfRange"), 11, 36, 0xff0000);
				}
			}

			this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.mode"), 140, 36, 0xffffff);

			if (this.settingPos) {
				this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.settingPos"), 11, 117, 0xffffff);
			} else {
				this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.coords"), 11, 117, 0xffffff);
				this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.x"), 55, 117, 0xffffff);
				this.fontRenderer.drawString(String.valueOf(te.getSensorPos().getX()), 65, 117, 0xffffff);
				this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.y"), 95, 117, 0xffffff);
				this.fontRenderer.drawString(String.valueOf(te.getSensorPos().getY()), 105, 117, 0xffffff);
				this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.z"), 130, 117, 0xffffff);
				this.fontRenderer.drawString(String.valueOf(te.getSensorPos().getZ()), 140, 117, 0xffffff);
			}

			this.fontRenderer.drawString(this.selectingItem ? I18n.format("grimpack.industry.sensor.selecting") : this.te.getSpecific().getStack() == null ? I18n.format("grimpack.industry.sensor.noitem") : I18n.format("grimpack.industry.sensor.selected"), 11, 56, 0xffffff);
			GlStateManager.pushMatrix();
			RenderHelper.enableGUIStandardItemLighting();
			if (this.te.getSpecific().getStack() != null) {
				this.renderItem.renderItemAndEffectIntoGUI(this.te.getSpecific().getStack(), xSize / 2 - 35, 52);
			}
			RenderHelper.disableStandardItemLighting();
			GlStateManager.popMatrix();

			if (this.te.getSpecific().getStack() != null) {
				boolean hovered = mouseX >= this.guiLeft + 92 && mouseY >= this.guiTop + 50 && mouseX < this.guiLeft + 92 + 20 && mouseY < this.guiTop + 50 + 20;
				if (hovered) {
					TooltipHelper.renderToolTip(this.te.getSpecific().getStack(), mouseX - this.guiLeft, mouseY - this.guiTop);
				}
			}

		} else {
			this.drawCenteredString(fontRenderer, I18n.format("grimpack.industry.sensor.min"), 90, 61, 0xffffff);
			this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.x"), 11, 79, 0xffffff);
			this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.y"), 61, 79, 0xffffff);
			this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.z"), 111, 79, 0xffffff);
			this.drawCenteredString(fontRenderer, I18n.format("grimpack.industry.sensor.max"), 90, 100, 0xffffff);
			this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.x"), 11, 117, 0xffffff);
			this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.y"), 61, 117, 0xffffff);
			this.fontRenderer.drawString(I18n.format("grimpack.industry.sensor.z"), 111, 117, 0xffffff);
		}

		// Renders all tooltips if available
		for (GuiButton b : this.buttonList) {
			if (b.isMouseOver()) {
				b.drawButtonForegroundLayer(mouseX - this.guiLeft, mouseY - this.guiTop);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(GUI_LOCATION);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

		if (this.onMainPage) {
			entityName.drawTextBox();
			playerName.drawTextBox();
		} else {
			minX.drawTextBox();
			minY.drawTextBox();
			minZ.drawTextBox();
			maxX.drawTextBox();
			maxY.drawTextBox();
			maxZ.drawTextBox();
		}
	}

}
