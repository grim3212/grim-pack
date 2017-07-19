package com.grim3212.mc.pack.core.manual.button;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

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
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		super.drawButton(mc, mouseX, mouseY, partialTicks);

		if (this.visible) {
			boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(GuiManualIndex.texture);
			int k = back ? 211 : 229;
			int l = 20;

			if (flag) {
				l += 11;
			}

			this.drawTexturedModalRect(this.x, this.y, k, l, 18, 11);
		}
	}

}
