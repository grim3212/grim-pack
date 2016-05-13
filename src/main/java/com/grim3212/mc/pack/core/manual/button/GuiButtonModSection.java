package com.grim3212.mc.pack.core.manual.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.FMLClientHandler;

public class GuiButtonModSection extends GuiButton {

	public GuiButtonModSection(int id, int j, int k, int m, String s) {
		super(id, j, k, FMLClientHandler.instance().getClient().fontRendererObj.getStringWidth(s) + 2, m, s);
	}

	@Override
	public void drawButton(Minecraft mc, int x, int y) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		boolean flag = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;

		boolean unicode = mc.fontRendererObj.getUnicodeFlag();
		mc.fontRendererObj.setUnicodeFlag(true);
		mc.fontRendererObj.drawString("\u00a7l* " + displayString, xPosition, yPosition, flag ? 0xb3b3b3 : 0x000000);
		mc.fontRendererObj.setUnicodeFlag(unicode);
	}
}
