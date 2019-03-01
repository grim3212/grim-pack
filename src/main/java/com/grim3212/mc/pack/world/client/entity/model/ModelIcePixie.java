package com.grim3212.mc.pack.world.client.entity.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@OnlyIn(Dist.CLIENT)
public class ModelIcePixie extends ModelBiped {

	public ModelIcePixie(float f, float f1) {
		field_1279_h = false;
		field_1278_i = false;
		bipedEars = new ModelRenderer(this, 18, 0);
		bipedEars.addBox(-4F, 4F, -1F, 8, 6, 0, 0.0F);
		bipedEars.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		bipedBody = new ModelRenderer(this, 0, 0);
		bipedBody.addBox(-3F, 10F, -2F, 6, 8, 3, f);
		bipedBody.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		bipedRightArm = new ModelRenderer(this, 0, 11);
		bipedRightArm.addBox(0.0F, 10F, -2F, 2, 4, 2, f);
		bipedRightArm.setRotationPoint(0.0F, 1.0F + f1, 0.0F);
		bipedLeftArm = new ModelRenderer(this, 0, 11);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-2F, 10F, -2F, 2, 4, 2, f);
		bipedLeftArm.setRotationPoint(0.0F, 1.0F + f1, 0.0F);
		bipedRightLeg = new ModelRenderer(this, 0, 17);
		bipedRightLeg.mirror = true;
		bipedRightLeg.addBox(-1F, 6F, -2F, 3, 6, 3, f);
		bipedRightLeg.setRotationPoint(1.0F, 6F + f1, 0.0F);
		bipedLeftLeg = new ModelRenderer(this, 0, 17);
		bipedLeftLeg.addBox(-2F, 6F, -2F, 3, 6, 3, f);
		bipedLeftLeg.setRotationPoint(-1F, 6F + f1, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GlStateManager.pushMatrix();
		if (entity instanceof EntityLiving) {
			double d = 0.0D;
			double d2 = 1.5D;
			double d4 = 0.0D;
			double d6 = 0.5D + (0.5D * (double) 8) / 20D;
			GlStateManager.translate(d, d2, d4);
			GlStateManager.scale(d6, d6, d6);
			GlStateManager.translate(-d, -d2, -d4);
		}
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bipedBody.render(f5);
		bipedRightArm.render(f5);
		bipedLeftArm.render(f5);
		bipedRightLeg.render(f5);
		bipedLeftLeg.render(f5);

		GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		double d1 = 0.625D;
		GlStateManager.translate(0.0D, 0.45000000000000001D, 0.0D);
		GlStateManager.scale(d1, d1, d1);
		GlStateManager.translate(0.0D, 0.0D, 0.10000000000000001D);
		double d3 = -0.40000000000000002D;
		double d5 = 0.40000000000000002D;
		double d7 = -0.29999999999999999D;
		double d8 = 0.29999999999999999D;
		double d9 = -0.10000000000000001D;
		double d10 = 0.0D;
		float f6 = 0.28125F;
		float f7 = 0.40625F;
		float f8 = 0.0F;
		float f9 = 0.1875F;
		tessellator.getBuffer().pos(d5, d8, d9).tex(f7, f9).endVertex();
		tessellator.getBuffer().pos(d5, d7, d9).tex(f7, f8).endVertex();
		tessellator.getBuffer().pos(d3, d7, d9).tex(f6, f8).endVertex();
		tessellator.getBuffer().pos(d3, d8, d9).tex(f6, f9).endVertex();
		tessellator.getBuffer().pos(d5, d8, d10).tex(f7, f9).endVertex();
		tessellator.getBuffer().pos(d5, d7, d10).tex(f7, f8).endVertex();
		tessellator.getBuffer().pos(d3, d7, d10).tex(f6, f8).endVertex();
		tessellator.getBuffer().pos(d3, d8, d10).tex(f6, f9).endVertex();
		tessellator.draw();
		int i = 8;
		int j = 6;
		float f10 = (float) i / 10F;
		float f11 = (float) j / 10F;
		float f12 = (float) (d9 - d10);
		float f13 = 0.0078125F;
		float f14 = 0.015625F;

		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);

		for (int k = 0; k < i; k++) {
			float f15 = (float) k / (float) i;
			float f19 = f6 + (f7 - f6) * f15 + f13;
			float f23 = (f10 * f15 - 0.5F) + 0.1F;

			tessellator.getBuffer().pos(f23, d7, 0.0F + f12).tex(f19, f8).normal(-1F, 0.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f23, d7, 0.0D).tex(f19, f8).normal(-1F, 0.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f23, d8, 0.0D).tex(f19, f9).normal(-1F, 0.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f23, d8, 0.0F + f12).tex(f19, f9).normal(-1F, 0.0F, 0.0F).endVertex();
		}
		tessellator.draw();

		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		for (int l = 0; l < i; l++) {
			float f16 = (float) l / (float) i;
			float f20 = f6 + (f7 - f6) * f16 + f13;
			float f24 = (f10 * f16 - 0.5F) + 0.2F;

			tessellator.getBuffer().pos(f24, d8, 0.0F + f12).tex(f20, f9).normal(1F, 0.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f24, d8, 0.0D).tex(f20, f9).normal(1F, 0.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f24, d7, 0.0D).tex(f20, f8).normal(1F, 0.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(f24, d7, 0.0F + f12).tex(f20, f8).normal(1F, 0.0F, 0.0F).endVertex();
		}
		tessellator.draw();

		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		for (int i1 = 0; i1 < j; i1++) {
			float f17 = (float) i1 / (float) j;
			float f21 = f8 + (f9 - f8) * f17 + f14;
			float f25 = (f11 * f17 - 0.5F) + 0.2F;

			tessellator.getBuffer().pos(d3, f25, 0.0D).tex(f6, f21).normal(0F, 1.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(d5, f25, 0.0D).tex(f7, f21).normal(0F, 1.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(d5, f25, 0.0F + f12).tex(f7, f21).normal(0F, 1.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(d3, f25, 0.0F + f12).tex(f6, f21).normal(0F, 1.0F, 0.0F).endVertex();
		}
		tessellator.draw();

		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		for (int j1 = 0; j1 < j; j1++) {
			float f18 = (float) j1 / (float) j;
			float f22 = f8 + (f9 - f8) * f18 + f14;
			float f26 = (f11 * f18 - 0.5F) + 0.3F;

			tessellator.getBuffer().pos(d5, f26, 0.0D).tex(f7, f22).normal(0F, -1.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(d3, f26, 0.0D).tex(f6, f22).normal(0F, -1.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(d3, f26, 0.0F + f12).tex(f6, f22).normal(0F, -1.0F, 0.0F).endVertex();
			tessellator.getBuffer().pos(d5, f26, 0.0F + f12).tex(f7, f22).normal(0F, -1.0F, 0.0F).endVertex();
		}

		tessellator.draw();
		GlStateManager.popMatrix();
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 0.6F * limbSwingAmount * 0.5F;
		bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F;
		bipedRightArm.rotateAngleZ = 0.0F;
		bipedLeftArm.rotateAngleZ = 0.0F;
		bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
		bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 0.4F * limbSwingAmount;
		bipedRightLeg.rotateAngleY = netHeadYaw / 57.29578F;
		bipedLeftLeg.rotateAngleY = netHeadYaw / 57.29578F;
		if (isRiding) {
			bipedRightArm.rotateAngleX += -0.6283185F;
			bipedLeftArm.rotateAngleX += -0.6283185F;
			bipedRightLeg.rotateAngleX = -1.256637F;
			bipedLeftLeg.rotateAngleX = -1.256637F;
			bipedRightLeg.rotateAngleY = 0.3141593F;
			bipedLeftLeg.rotateAngleY = -0.3141593F;
		}
		if (!field_1279_h)
			;
		if (!field_1278_i)
			;
		bipedRightArm.rotateAngleY = 0.0F;
		bipedLeftArm.rotateAngleY = 0.0F;

		if (swingProgress > -9990F) {
			bipedBody.rotateAngleY = netHeadYaw / 57.29578F;
			bipedRightArm.rotationPointZ = MathHelper.sin(bipedBody.rotateAngleY) * 5F;
			bipedRightArm.rotationPointX = -MathHelper.cos(bipedBody.rotateAngleY) * 5F;
			bipedLeftArm.rotationPointZ = -MathHelper.sin(bipedBody.rotateAngleY) * 5F;
			bipedLeftArm.rotationPointX = MathHelper.cos(bipedBody.rotateAngleY) * 5F;
			bipedRightArm.rotateAngleY += bipedBody.rotateAngleY;
			bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY;
		}

		bipedBody.rotateAngleX = 0.0F;
		bipedRightLeg.rotationPointZ = 0.0F;
		bipedLeftLeg.rotationPointZ = 0.0F;
		bipedRightLeg.rotationPointY = 12F;
		bipedLeftLeg.rotationPointY = 12F;
		bipedEars.rotateAngleY = bipedBody.rotateAngleY;
		bipedEars.rotateAngleX = bipedBody.rotateAngleX;
		bipedEars.rotateAngleZ = bipedBody.rotateAngleZ;
	}

	public ModelRenderer bipedBody;
	public ModelRenderer bipedRightArm;
	public ModelRenderer bipedLeftArm;
	public ModelRenderer bipedRightLeg;
	public ModelRenderer bipedLeftLeg;
	public ModelRenderer bipedEars;
	public boolean field_1279_h;
	public boolean field_1278_i;
}
