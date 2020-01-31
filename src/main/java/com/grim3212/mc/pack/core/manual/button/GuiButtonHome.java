package com.grim3212.mc.pack.core.manual.button;

import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;

public class GuiButtonHome extends Button {

	public GuiButtonHome(int j, int k, IPressable pressable) {
		super(j, k, 9, 10, "", pressable);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			RenderSystem.color4f(1f, 1f, 1f, 1f);
			Minecraft.getInstance().textureManager.bindTexture(GuiManualIndex.texture);
			RenderSystem.color4f(1f, 1f, 1f, 1f);
			int k = 247;
			int l = 0;

			if (flag) {
				l += 10;
			}

			this.blit(this.x, this.y, k, l, 9, 10);
		}
	}
}