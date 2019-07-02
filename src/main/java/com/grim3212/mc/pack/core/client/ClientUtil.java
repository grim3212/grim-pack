package com.grim3212.mc.pack.core.client;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientUtil {

	public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, double zLevel) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		tessellator.getBuffer().pos(x + 0, y + height, zLevel).tex((float) (textureX + 0) * f, (float) (textureY + height) * f1).endVertex();
		tessellator.getBuffer().pos(x + width, y + height, zLevel).tex((float) (textureX + width) * f, (float) (textureY + height) * f1).endVertex();
		tessellator.getBuffer().pos(x + width, y + 0, zLevel).tex((float) (textureX + width) * f, (float) (textureY + 0) * f1).endVertex();
		tessellator.getBuffer().pos(x + 0, y + 0, zLevel).tex((float) (textureX + 0) * f, (float) (textureY + 0) * f1).endVertex();
		tessellator.draw();
	}

	public static RayTraceResult getMouseOver() {
		Minecraft mc = Minecraft.getInstance();
		Entity entity = mc.getRenderViewEntity();
		float partialTicks = mc.getRenderPartialTicks();
		World world = mc.world;

		if (entity != null) {
			if (world != null) {
				double d0 = (double) mc.playerController.getBlockReachDistance();
				RayTraceResult objectMouseOver = entity.func_213324_a(d0, partialTicks, false);
				Vec3d vec3d = entity.getEyePosition(partialTicks);
				boolean flag = false;
				double d1 = d0;
				if (mc.playerController.extendedReach()) {
					d1 = 6.0D;
					d0 = d1;
				} else {
					if (d0 > 3.0D) {
						flag = true;
					}
				}

				d1 = d1 * d1;
				if (objectMouseOver != null) {
					d1 = objectMouseOver.getHitVec().squareDistanceTo(vec3d);
				}

				Vec3d vec3d1 = entity.getLook(1.0F);
				Vec3d vec3d2 = vec3d.add(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0);
				AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(vec3d1.scale(d0)).grow(1.0D, 1.0D, 1.0D);
				EntityRayTraceResult entityraytraceresult = ProjectileHelper.func_221273_a(entity, vec3d, vec3d2, axisalignedbb, (p_215312_0_) -> {
					return !p_215312_0_.isSpectator();
				}, d1);
				if (entityraytraceresult != null) {
					Vec3d vec3d3 = entityraytraceresult.getHitVec();
					double d2 = vec3d.squareDistanceTo(vec3d3);
					if (flag && d2 > 9.0D) {
						return new BlockRayTraceResult(vec3d3, Direction.getFacingFromVector(vec3d1.x, vec3d1.y, vec3d1.z), new BlockPos(vec3d3), false);
					} else if (d2 < d1 || objectMouseOver == null) {
						return entityraytraceresult;
					}
				}
				return objectMouseOver;
			}
		}
		return null;
	}

	public static void renderBlock(BlockState block, float x, float y, float z, float rotate, float scale) {
		Minecraft mc = Minecraft.getInstance();
		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();
		GlStateManager.rotatef(-30.0F, 0.0F, 1.0F, 0.0F);
		net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translatef(x, y, 50.0F + z);
		GlStateManager.rotatef(20.0F, 1.0F, 0.0F, 0.0F);
		scale *= 50;
		GlStateManager.scalef(scale, -scale, -scale);
		GlStateManager.translatef(0.5F, 0.5F, 0.5F);
		GlStateManager.rotatef(rotate, 0.0F, 1.0F, 0.0F);
		GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
		mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
		mc.getBlockRendererDispatcher().renderBlockBrightness(block, 1.0F);
		GlStateManager.popMatrix();
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
	}
}
