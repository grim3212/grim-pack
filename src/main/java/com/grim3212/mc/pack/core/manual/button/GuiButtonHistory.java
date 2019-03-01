package com.grim3212.mc.pack.core.manual.button;

import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonHistory extends GuiButton {

	/*
	 * Is the arrow pointing backward
	 */
	private boolean back;

	public GuiButtonHistory(int id, int j, int k, boolean back) {
		super(id, j, k, 18, 11, "");
		this.back = back;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		super.render(mouseX, mouseY, partialTicks);

		if (this.visible) {
			boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getInstance().textureManager.bindTexture(GuiManualIndex.texture);
			int k = back ? 211 : 229;
			int l = 20;

			if (flag) {
				l += 11;
			}

			this.drawTexturedModalRect(this.x, this.y, k, l, 18, 11);
		}
	}

}
