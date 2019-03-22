package com.grim3212.mc.pack.core.manual.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonModSection extends GuiButton {

	public GuiButtonModSection(int id, int x, int y, int height, String s) {
		super(id, x, y, Minecraft.getInstance().fontRenderer.getStringWidth(s) + 10, height, s);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

		FontRenderer font = Minecraft.getInstance().fontRenderer;

		font.drawString("* " + displayString, x, y, flag ? 0xb3b3b3 : 0x000000);
	}
}
