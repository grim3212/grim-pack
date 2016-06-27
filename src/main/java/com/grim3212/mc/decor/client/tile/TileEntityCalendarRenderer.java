package com.grim3212.mc.decor.client.tile;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.decor.tile.TileEntityCalendar;
import com.grim3212.mc.decor.util.DateHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityCalendarRenderer extends TileEntitySpecialRenderer<TileEntityCalendar> {

	@Override
	public void renderTileEntityAt(TileEntityCalendar te, double x, double y, double z, float partialTicks, int destroyStage) {
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
		GlStateManager.translate(0.0F, 0.19F * f1, 0.01F * f1);
		GlStateManager.scale(f3, -f3, f3);
		GL11.glNormal3f(0.0F, 0.0F, -1F * f3);

		GlStateManager.depthMask(false);
		
		String s = DateHandler.calculateDate(Minecraft.getMinecraft().theWorld.getWorldTime(), 1);
		String as[] = s.split(",");
		for (int k = 0; k < as.length; k++) {
			String s1 = as[k];
			this.getFontRenderer().drawString(s1, -this.getFontRenderer().getStringWidth(s1) / 2, k * 10 - as.length * 5, 0);
		}

		GlStateManager.depthMask(true);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}
}
