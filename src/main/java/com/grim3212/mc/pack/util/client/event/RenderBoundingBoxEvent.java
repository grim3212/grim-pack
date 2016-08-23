package com.grim3212.mc.pack.util.client.event;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.util.config.UtilConfig;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderBoundingBoxEvent {

	@SubscribeEvent
	public void showBoundingBox(DrawBlockHighlightEvent event) {
		if (UtilConfig.showCollisionBoxes) {
			if (event.getTarget().typeOfHit == RayTraceResult.Type.BLOCK) {
				Minecraft mc = Minecraft.getMinecraft();
				EntityPlayer player = mc.thePlayer;

				if (player.isSneaking()) {
					event.setCanceled(true);

					World world = mc.theWorld;

					GlStateManager.pushMatrix();
					GlStateManager.pushAttrib();

					GlStateManager.enableBlend();
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
					GlStateManager.glLineWidth(2F);
					GlStateManager.disableTexture2D();
					GlStateManager.depthMask(false);
					GlStateManager.disableDepth();

					BlockPos blockpos = event.getTarget().getBlockPos();
					IBlockState iblockstate = world.getBlockState(blockpos);

					if (iblockstate.getMaterial() != Material.AIR && world.getWorldBorder().contains(blockpos)) {
						double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) event.getPartialTicks();
						double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) event.getPartialTicks();
						double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) event.getPartialTicks();

						drawSelectionBoundingBox(iblockstate.getSelectedBoundingBox(world, blockpos).expandXyz(0.0020000000949949026D).offset(-d0, -d1, -d2), 0.5F, 0.5F, 0.3F, 0.4F);

						List<AxisAlignedBB> collisionBoxes = Lists.<AxisAlignedBB> newArrayList();
						iblockstate.addCollisionBoxToList(world, blockpos, iblockstate.getSelectedBoundingBox(world, blockpos).expandXyz(3.0d), collisionBoxes, player);

						for (int i = 0; i < collisionBoxes.size(); i++)
							drawSelectionBoundingBox(collisionBoxes.get(i).expandXyz(0.0020000000949949026D).offset(-d0, -d1, -d2), 0.0F, 0.0F, 0.2F, 0.4F);
					}

					GlStateManager.enableDepth();
					GlStateManager.depthMask(true);
					GlStateManager.enableTexture2D();
					GlStateManager.disableBlend();

					GlStateManager.popAttrib();
					GlStateManager.popMatrix();
				}
			}
		}
	}

	private static void drawSelectionBoundingBox(AxisAlignedBB box, float red, float green, float blue, float alpha) {
		drawBoundingBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);
	}

	private static void drawBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		drawBoundingBox(vertexbuffer, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
		tessellator.draw();
	}

	private static void drawBoundingBox(VertexBuffer buffer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
		buffer.pos(minX, minY, minZ).color(red, green, blue, 0.0F).endVertex();
		buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(minX, maxY, maxZ).color(red, green, blue, 0.0F).endVertex();
		buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(maxX, maxY, maxZ).color(red, green, blue, 0.0F).endVertex();
		buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(maxX, maxY, minZ).color(red, green, blue, 0.0F).endVertex();
		buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
		buffer.pos(maxX, minY, minZ).color(red, green, blue, 0.0F).endVertex();
	}
}
