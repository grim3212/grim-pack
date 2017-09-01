package com.grim3212.mc.pack.decor.client.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.decor.network.MessageSaveAlarm;
import com.grim3212.mc.pack.decor.tile.TileEntityAlarm;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class GuiAlarm extends GuiScreen {

	private static final ResourceLocation LOCATION = new ResourceLocation(GrimPack.modID, "textures/gui/alarm_gui.png");
	private TileEntityAlarm alarm;
	private EntityPlayer player;
	private int alarmType = 13;
	private String alertText = "";

	public GuiAlarm(TileEntityAlarm alarm, EntityPlayer player) {
		this.alarm = alarm;
		this.player = player;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui() {
		showPage();
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		this.mc.getTextureManager().bindTexture(LOCATION);

		int posX = (this.width - 256) / 2;
		drawTexturedModalRect(posX, 5, 0, 0, 256, 230);

		drawCenteredString(fontRenderer, I18n.format("gui.alarm.title"), width / 2, 10, 0xFF1010);
		int textBorder = 5;
		fontRenderer.drawSplitString(I18n.format("gui.alarm.fulltext"), posX + textBorder, 30, 256 - textBorder * 2, 0xFFFFFF);
		drawCenteredString(fontRenderer, I18n.format(alertText), width / 2, 150, 0x00FF00);

		super.drawScreen(i, j, f);
	}

	private void showPage() {
		buttonList.clear();
		buttonList.add(new GuiButton(0, this.width / 2 - 25, 210, 50, 20, I18n.format("gui.done")));

		alarmType = alarm.alarmType;
		if (alarmType < 26) {
			char ch = (char) (65 + alarmType);
			if (alarmType != 13)
				buttonList.add(new GuiButton(1, this.width / 2 - 50, 100, 100, 20, I18n.format("gui.alarm.name", ch)));
			else
				buttonList.add(new GuiButton(1, this.width / 2 - 50, 100, 100, 20, I18n.format("gui.alarm.carlock")));
		} else {
			alertText = "gui.alert.desc";
			buttonList.add(new GuiButton(1, this.width / 2 - 50, 100, 100, 20, I18n.format("gui.alert.name")));
		}
		buttonList.add(new GuiButton(2, this.width / 2 - 25, 120, 50, 20, I18n.format("gui.alarm.test")));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if (!guibutton.enabled) {
			return;
		}

		// SET alarm button
		if (guibutton.id == 0) {
			this.alarm.alarmType = alarmType;
			PacketDispatcher.sendToServer(new MessageSaveAlarm(alarmType, alarm.getPos()));

			mc.displayGuiScreen((GuiScreen) null);
		} else if (guibutton.id == 1) {
			// change alarm type button
			alarmType++;
			if (alarmType > 13 && alarmType < 26) {
				alarmType = 26;
			} else if (alarmType > 26) {
				alarmType = 0;
			}
			if (alarmType < 26) {
				char ch = (char) (65 + alarmType);
				if (alarmType != 13)
					guibutton.displayString = I18n.format("gui.alarm.name", ch);
				else
					guibutton.displayString = I18n.format("gui.alarm.carlock");
				alertText = "";
			} else {
				guibutton.displayString = I18n.format("gui.alert.name");
				alertText = "gui.alert.desc";
			}

		} else if (guibutton.id == 2) {
			// test the alarm button
			if (alarmType < 26) {
				player.playSound(TileEntityAlarm.getSound(alarmType), 1f, 1f);
				alertText = "";
			} else {
				alertText = "gui.alert.test";
			}
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		if (i == 1) { // escape and dont save
			mc.currentScreen = null;
			mc.setIngameFocus();
		}
	}
}
