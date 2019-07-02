package com.grim3212.mc.pack.decor.client.tile;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.decor.block.BlockCalendar;
import com.grim3212.mc.pack.decor.tile.TileEntityCalendar;
import com.grim3212.mc.pack.decor.util.DateHandler;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TileEntityCalendarRenderer extends TileEntityRenderer<TileEntityCalendar> {

	@Override
	public void render(TileEntityCalendar tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
		GlStateManager.pushMatrix();

		float f1 = 0.6666667F;
		float f2 = tileEntityIn.getBlockState().get(BlockCalendar.FACING).getHorizontalAngle();

		GlStateManager.translatef((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
		GlStateManager.rotatef(-f2, 0.0F, 1.0F, 0.0F);
		GlStateManager.translatef(0.0F, -0.3125F, -0.4375F);

		float f3 = 0.015F * f1;
		GlStateManager.translatef(0.0F, 0.19F * f1, 0.01F * f1);
		GlStateManager.scalef(f3, -f3, f3);
		GL11.glNormal3f(0.0F, 0.0F, -1F * f3);

		GlStateManager.depthMask(false);

		String s = DateHandler.calculateDate(Minecraft.getInstance().world.getDayTime(), 1);
		String as[] = s.split(",");
		for (int k = 0; k < as.length; k++) {
			String s1 = as[k];
			this.getFontRenderer().drawString(s1, -this.getFontRenderer().getStringWidth(s1) / 2, k * 10 - as.length * 5, 0);
		}

		GlStateManager.depthMask(true);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}
}
