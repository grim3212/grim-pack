package com.grim3212.mc.pack.decor.client.gui;

import com.grim3212.mc.pack.core.client.TooltipHelper;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;

public class GuiButtonNeon extends Button {

	private int texX;
	private int texY;
	private int hoverCount;
	private boolean changeHoverDir = false;

	public GuiButtonNeon(int x, int y, String buttonText, int texX, int texY, Button.IPressable onPress) {
		this(x, y, buttonText, texX, texY, -1, false, onPress);
	}

	public GuiButtonNeon(int x, int y, String buttonText, int texX, int texY, boolean changeHoverDir, Button.IPressable onPress) {
		this(x, y, buttonText, texX, texY, -1, changeHoverDir, onPress);
	}

	public GuiButtonNeon(int x, int y, String buttonText, int texX, int texY, int width, Button.IPressable onPress) {
		this(x, y, buttonText, texX, texY, width, false, onPress);
	}

	public GuiButtonNeon(int x, int y, String buttonText, int texX, int texY, int width, boolean changeHoverDir, Button.IPressable onPress) {
		super(x, y, 14, 14, buttonText, onPress);
		this.texX = texX;
		this.texY = texY;
		this.changeHoverDir = changeHoverDir;
		if (width != -1)
			this.width = width;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			Minecraft mc = Minecraft.getInstance();
			mc.getTextureManager().bindTexture(GuiEditNeonSign.NEON_SIGN_GUI_TEXTURE);
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int i = this.getYImage(this.isHovered);
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.blit(this.x, this.y, texX + (changeHoverDir ? 0 : width * (i - 1)), texY + (changeHoverDir ? height * (i - 1) : 0), this.width, this.height);
			this.renderBg(mc, mouseX, mouseY);
		}
	}

	@Override
	public void renderToolTip(int mouseX, int mouseY) {
		if (this.isMouseOver(mouseX, mouseY)) {
			this.hoverCount++;
		} else if (!this.isMouseOver(mouseX, mouseY) && this.hoverCount > 0) {
			this.hoverCount = 0;
		}

		if (this.hoverCount > 30)
			TooltipHelper.drawHoveringText(this.getMessage(), mouseX, mouseY, 120, Minecraft.getInstance().fontRenderer);
	}
}
