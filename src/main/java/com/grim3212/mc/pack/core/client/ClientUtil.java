package com.grim3212.mc.pack.core.client;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
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
		Entity entity = Minecraft.getInstance().getRenderViewEntity();
		Entity pointedEntity = null;
		float partialTicks = Minecraft.getInstance().getRenderPartialTicks();

		if (entity != null) {
			if (Minecraft.getInstance().world != null) {
				double d0 = (double) Minecraft.getInstance().playerController.getBlockReachDistance();
				RayTraceResult objectMouseOver = entity.rayTrace(d0, partialTicks, RayTraceFluidMode.ALWAYS);
				Vec3d vec3d = entity.getEyePosition(partialTicks);
				boolean flag = false;
				double d1 = d0;

				if (Minecraft.getInstance().playerController.extendedReach()) {
					d1 = 6.0D;
					d0 = d1;
				} else {
					if (d0 > 3.0D) {
						flag = true;
					}
				}

				if (objectMouseOver != null) {
					d1 = objectMouseOver.hitVec.distanceTo(vec3d);
				}

				Vec3d vec3d1 = entity.getLook(partialTicks);
				Vec3d vec3d2 = vec3d.add(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0);
				pointedEntity = null;
				Vec3d vec3d3 = null;
				List<Entity> list = Minecraft.getInstance().world.getEntitiesInAABBexcluding(entity, entity.getBoundingBox().expand(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0).expand(1.0D, 1.0D, 1.0D), EntitySelectors.NOT_SPECTATING.and(new Predicate<Entity>() {
					@Override
					public boolean test(@Nullable Entity input) {
						return input != null;
					}
				}));
				double d2 = d1;

				for (int j = 0; j < list.size(); ++j) {
					Entity entity1 = (Entity) list.get(j);

					double collision = (double) entity1.getCollisionBorderSize();
					if (entity1 instanceof EntityItem)
						collision = 0.2D;

					AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow(collision);
					RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);

					if (axisalignedbb.contains(vec3d)) {
						if (d2 >= 0.0D) {
							pointedEntity = entity1;
							vec3d3 = raytraceresult == null ? vec3d : raytraceresult.hitVec;
							d2 = 0.0D;
						}
					} else if (raytraceresult != null) {
						double d3 = vec3d.distanceTo(raytraceresult.hitVec);

						if (d3 < d2 || d2 == 0.0D) {
							if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity.canRiderInteract()) {
								if (d2 == 0.0D) {
									pointedEntity = entity1;
									vec3d3 = raytraceresult.hitVec;
								}
							} else {
								pointedEntity = entity1;
								vec3d3 = raytraceresult.hitVec;
								d2 = d3;
							}
						}
					}
				}

				if (pointedEntity != null && flag && vec3d.distanceTo(vec3d3) > 3.0D) {
					pointedEntity = null;
					objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, (EnumFacing) null, new BlockPos(vec3d3));
				}

				if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
					objectMouseOver = new RayTraceResult(pointedEntity, vec3d3);
				}
				return objectMouseOver;
			}
		}
		return null;
	}

	public static void renderBlock(IBlockState block, float x, float y, float z, float rotate, float scale) {
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
		mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		mc.getBlockRendererDispatcher().renderBlockBrightness(block, 1.0F);
		GlStateManager.popMatrix();
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
	}
}
