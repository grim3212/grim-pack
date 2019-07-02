package com.grim3212.mc.pack.industry.client.tile;

import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;
import com.grim3212.mc.pack.util.client.event.RenderBoundingBoxEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntitySpecificSensorRenderer extends TileEntityRenderer<TileEntitySpecificSensor> {

	@Override
	public void render(TileEntitySpecificSensor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te.renderSensorPos() && te.isGoodPosition()) {
			GlStateManager.pushMatrix();
			GlStateManager.pushAttrib();

			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.glLineWidth(5.0F);
			GlStateManager.disableTexture2D();
			GlStateManager.depthMask(false);

			if (te.getSensorPos() != null && te.getWorld().getWorldBorder().contains(te.getSensorPos())) {
				Minecraft mc = Minecraft.getMinecraft();
				PlayerEntity player = mc.player;

				double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
				double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
				double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;

				RenderBoundingBoxEvent.drawSelectionBoundingBox(te.getSenseBox().offset(te.getSensorPos()).offset(-d0, -d1, -d2).grow(0.0020000000949949026D), 1.0f, 1.0f, 1.0f, 1.0f);

			}
			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();

			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
	}
}