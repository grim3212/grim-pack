package com.grim3212.mc.pack.industry.client.gui;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.industry.network.MessageSaveFan;
import com.grim3212.mc.pack.industry.tile.TileEntityFan;
import com.grim3212.mc.pack.industry.tile.TileEntityFan.FanMode;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;

public class GuiFan extends Screen {

	private final Minecraft mc;
	private final TileEntityFan tileentity;
	private FanMode mode;
	private int range;

	public GuiFan(TileEntityFan tileentity) {
		this.tileentity = tileentity;
		this.mode = this.tileentity.getMode() == FanMode.OFF ? this.tileentity.getOldMode() : this.tileentity.getMode();
		this.range = this.tileentity.getRange();
		this.mc = Minecraft.getMinecraft();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(new Button(0, width / 2 - 80, height / 4 + 120, 70, 20, I18n.format("grimpack.gui.ok")));
		buttonList.add(new Button(1, width / 2 + 10, height / 4 + 120, 70, 20, I18n.format("grimpack.gui.cancel")));
		buttonList.add(new Button(2, width / 2 - 50, height / 4 + 10, 100, 20, I18n.format("grimpack.industry.fan." + this.mode.name())));
		buttonList.add(new Button(3, width / 2 + 20, height / 4 + 65, 40, 20, I18n.format("grimpack.industry.fan.addOne")));
		buttonList.add(new Button(4, width / 2 + 65, height / 4 + 65, 40, 20, I18n.format("grimpack.industry.fan.addFive")));
		buttonList.add(new Button(5, width / 2 + 110, height / 4 + 65, 40, 20, I18n.format("grimpack.gui.max")));
		buttonList.add(new Button(6, width / 2 - 60, height / 4 + 65, 40, 20, I18n.format("grimpack.industry.fan.minusOne")));
		buttonList.add(new Button(7, width / 2 - 105, height / 4 + 65, 40, 20, I18n.format("grimpack.industry.fan.minusFive")));
		buttonList.add(new Button(8, width / 2 - 150, height / 4 + 65, 40, 20, I18n.format("grimpack.gui.min")));
	}

	@Override
	protected void actionPerformed(Button button) throws IOException {
		switch (button.id) {
		case 0:
			if (this.tileentity.getMode() != FanMode.OFF)
				this.tileentity.setMode(mode);
			this.tileentity.setOldMode(mode);
			this.tileentity.setRange(range);
			tileentity.getWorld().markBlockRangeForRenderUpdate(tileentity.getPos(), tileentity.getPos());
			PacketDispatcher.sendToServer(new MessageSaveFan(range, mode, tileentity.getPos()));

			mc.displayGuiScreen((Screen) null);
			mc.setIngameFocus();
			break;
		case 1:
			mc.displayGuiScreen((Screen) null);
			mc.setIngameFocus();
			break;
		case 2:
			this.mode = this.mode.getNext();
			button.displayString = I18n.format("grimpack.industry.fan." + this.mode.name());
			break;
		case 3:
			this.range += 1;
			break;
		case 4:
			this.range += 5;
			break;
		case 5:
			this.range = 32;
			break;
		case 6:
			this.range -= 1;
			break;
		case 7:
			this.range -= 5;
			break;
		case 8:
			this.range = 1;
			break;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawStrings();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private void drawStrings() {
		drawCenteredString(this.fontRenderer, I18n.format("grimpack.industry.fan.mode"), width / 2, height / 4 - 10, 0xffffff);
		drawCenteredString(this.fontRenderer, I18n.format("grimpack.industry.fan.range"), width / 2, height / 4 + 45, 0xffffff);

		if (range > 32)
			range = 32;
		if (range < 1)
			range = 1;

		drawCenteredString(this.fontRenderer, "" + range, width / 2, height / 4 + 70, 0xffffff);
	}
}
