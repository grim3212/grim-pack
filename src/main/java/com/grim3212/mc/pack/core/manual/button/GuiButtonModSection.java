package com.grim3212.mc.pack.core.manual.button;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;

public class GuiButtonModSection extends Button {

	private final int chapterId;
	
	public GuiButtonModSection(int chapterId, int x, int y, int height, String s, IPressable pressable) {
		super(x, y, Minecraft.getInstance().fontRenderer.getStringWidth(s) + 10, height, s, pressable);
		this.chapterId = chapterId;
	}
	
	public int getChapterId() {
		return chapterId;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

		FontRenderer font = Minecraft.getInstance().fontRenderer;

		font.drawString("* " + this.getMessage(), x, y, flag ? 0xb3b3b3 : 0x000000);
	}
}
