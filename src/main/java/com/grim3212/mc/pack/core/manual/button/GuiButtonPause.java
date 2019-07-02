package com.grim3212.mc.pack.core.manual.button;

import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;

public class GuiButtonPause extends Button {

	private boolean isPaused = false;

	public GuiButtonPause(int j, int k, IPressable pressable) {
		super(j, k, 9, 10, "", pressable);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getInstance().textureManager.bindTexture(GuiManualIndex.texture);
			int k = 202;
			int l = 0;

			if (flag || isPaused) {
				l += 10;
			}

			this.blit(this.x, this.y, k, l, 9, 10);
		}
	}

	public void setIsPaused(boolean pauseState) {
		this.isPaused = pauseState;
	}
}
