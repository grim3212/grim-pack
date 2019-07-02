package com.grim3212.mc.pack.core.client.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.TooltipHelper;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

public class GuiButtonIcon extends Button {

	private final ResourceLocation texture = new ResourceLocation(GrimPack.modID + ":" + "textures/gui/icons.png");

	private int iconX;
	private int iconY;
	private String tooltip = "";

	public GuiButtonIcon(int x, int y, int iconX, int iconY, IPressable pressable) {
		super(x, y, 20, 20, "", pressable);
		this.iconX = iconX;
		this.iconY = iconY;
	}

	public GuiButtonIcon(int x, int y, int iconX, int iconY, String tooltip, IPressable pressable) {
		this(x, y, iconX, iconY, pressable);
		this.tooltip = tooltip;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		super.render(mouseX, mouseY, partialTicks);

		if (this.visible) {
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getInstance().getTextureManager().bindTexture(texture);

			this.blit(this.x + 2, this.y + 2, iconX, iconY, 16, 16);
		}
	}

	@Override
	public void renderToolTip(int mouseX, int mouseY) {
		if (!tooltip.isEmpty())
			TooltipHelper.drawHoveringText(tooltip, mouseX, mouseY, 120, Minecraft.getInstance().fontRenderer);
	}

}
