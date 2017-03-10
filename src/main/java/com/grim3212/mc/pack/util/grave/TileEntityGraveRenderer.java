package com.grim3212.mc.pack.util.grave;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityGraveRenderer extends TileEntitySpecialRenderer<TileEntityGrave> {

	@Override
	public void renderTileEntityAt(TileEntityGrave te, double x, double y, double z, float partialTicks, int destroyStage) {
		GlStateManager.pushMatrix();

		float f1 = 0.6666667F;
		int i = te.getBlockMetadata();
		float f2 = 0.0F;
		if (i == 2) {
			f2 = 180F;
		}
		if (i == 4) {
			f2 = 90F;
		}
		if (i == 5) {
			f2 = -90F;
		}

		GlStateManager.translate((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
		GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.0F, -0.3125F, -0.4375F);

		float f3 = 0.015F * f1;
		GlStateManager.translate(0.0F, 0.50F * f1, 0.1F * f1);
		GlStateManager.scale(f3, -f3, f3);
		GL11.glNormal3f(0.0F, 0.0F, -1F * f3);

		GlStateManager.depthMask(false);

		for (int k = 0; k < te.signText.length; k++) {
			if (te.signText[k] != null) {
				ITextComponent itextcomponent = te.signText[k];
				List<ITextComponent> list = GuiUtilRenderComponents.splitText(itextcomponent, 90, this.getFontRenderer(), false, true);
				String s = list != null && !list.isEmpty() ? ((ITextComponent) list.get(0)).getFormattedText() : "";
				this.getFontRenderer().drawString(s, -this.getFontRenderer().getStringWidth(s) / 2, k * 10 - te.signText.length * 5, 0);
			}
		}

		GlStateManager.depthMask(true);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}
}
