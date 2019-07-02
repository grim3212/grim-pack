package com.grim3212.mc.pack.decor.client.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.decor.network.MessageSaveAlarm;
import com.grim3212.mc.pack.decor.tile.TileEntityAlarm;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

@OnlyIn(Dist.CLIENT)
public final class GuiAlarm extends Screen {

	private static final ResourceLocation LOCATION = new ResourceLocation(GrimPack.modID, "textures/gui/alarm_gui.png");
	private TileEntityAlarm alarm;
	private PlayerEntity player;
	private int alarmType = 13;
	private String alertText = "";

	public GuiAlarm(TileEntityAlarm alarm, PlayerEntity player) {
		super(new TranslationTextComponent("gui.alarm.title"));
		this.alarm = alarm;
		this.player = player;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void init() {
		showPage();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		this.minecraft.getTextureManager().bindTexture(LOCATION);

		int posX = (this.width - 256) / 2;
		blit(posX, 5, 0, 0, 256, 230);

		drawCenteredString(font, I18n.format("gui.alarm.title"), width / 2, 10, 0xFF1010);
		int textBorder = 5;
		font.drawSplitString(I18n.format("gui.alarm.fulltext"), posX + textBorder, 30, 256 - textBorder * 2, 0xFFFFFF);
		drawCenteredString(font, I18n.format(alertText), width / 2, 150, 0x00FF00);
		super.render(mouseX, mouseY, partialTicks);
	}

	private void showPage() {
		buttons.clear();
		children.clear();

		this.addButton(new Button(this.width / 2 - 25, 210, 50, 20, I18n.format("gui.done"), btn -> {
			GuiAlarm.this.alarm.alarmType = alarmType;

			PacketDispatcher.send(PacketDistributor.SERVER.noArg(), new MessageSaveAlarm(alarmType, alarm.getPos()));

			this.onClose();
		}));

		alarmType = alarm.alarmType;
		if (alarmType < 26) {
			char ch = (char) (65 + alarmType);
			if (alarmType != 13)
				this.addButton(new Button(this.width / 2 - 50, 100, 100, 20, I18n.format("gui.alarm.name", ch), btn -> {
					changeAlarm(btn);
				}));
			else
				this.addButton(new Button(this.width / 2 - 50, 100, 100, 20, I18n.format("gui.alarm.carlock"), btn -> {
					changeAlarm(btn);
				}));
		} else {
			alertText = "gui.alert.desc";
			this.addButton(new Button(this.width / 2 - 50, 100, 100, 20, I18n.format("gui.alert.name"), btn -> {
				changeAlarm(btn);
			}));
		}
		this.addButton(new Button(this.width / 2 - 25, 120, 50, 20, I18n.format("gui.alarm.test"), btn -> {
			// test the alarm button
			if (alarmType < 26) {
				player.playSound(TileEntityAlarm.getSound(alarmType), 1f, 1f);
				alertText = "";
			} else {
				alertText = "gui.alert.test";
			}
		}));
	}

	private void changeAlarm(Button btn) {
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
				btn.setMessage(I18n.format("gui.alarm.name", ch));
			else
				btn.setMessage(I18n.format("gui.alarm.carlock"));
			alertText = "";
		} else {
			btn.setMessage(I18n.format("gui.alert.name"));
			alertText = "gui.alert.desc";
		}
	}

	@Override
	public void onClose() {
		this.alarm.markDirty();
		this.minecraft.displayGuiScreen((Screen) null);
	}
}
