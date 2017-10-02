package com.grim3212.mc.pack.decor.client.gui;

import com.grim3212.mc.pack.core.client.TooltipHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonNeon extends GuiButton {

	private int texX;
	private int texY;
	private int hoverCount;
	private boolean changeHoverDir = false;

	public GuiButtonNeon(int buttonId, int x, int y, String buttonText, int texX, int texY) {
		this(buttonId, x, y, buttonText, texX, texY, -1, false);
	}

	public GuiButtonNeon(int buttonId, int x, int y, String buttonText, int texX, int texY, boolean changeHoverDir) {
		this(buttonId, x, y, buttonText, texX, texY, -1, changeHoverDir);
	}

	public GuiButtonNeon(int buttonId, int x, int y, String buttonText, int texX, int texY, int width) {
		this(buttonId, x, y, buttonText, texX, texY, width, false);
	}

	public GuiButtonNeon(int buttonId, int x, int y, String buttonText, int texX, int texY, int width, boolean changeHoverDir) {
		super(buttonId, x, y, 14, 14, buttonText);
		this.texX = texX;
		this.texY = texY;
		this.changeHoverDir = changeHoverDir;
		if (width != -1)
			this.width = width;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			mc.getTextureManager().bindTexture(GuiEditNeonSign.NEON_SIGN_GUI_TEXTURE);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int i = this.getHoverState(this.hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.drawTexturedModalRect(this.x, this.y, texX + (changeHoverDir ? 0 : width * (i - 1)), texY + (changeHoverDir ? height * (i - 1) : 0), this.width, this.height);
			this.mouseDragged(mc, mouseX, mouseY);
		}
	}

	@Override
	public void drawButtonForegroundLayer(int mouseX, int mouseY) {
		if (this.isMouseOver()) {
			this.hoverCount++;
		} else if (!this.isMouseOver() && this.hoverCount > 0) {
			this.hoverCount = 0;
		}

		if (this.hoverCount > 30)
			TooltipHelper.drawHoveringText(this.displayString, mouseX, mouseY, 120, Minecraft.getMinecraft().fontRenderer);
	}
}
