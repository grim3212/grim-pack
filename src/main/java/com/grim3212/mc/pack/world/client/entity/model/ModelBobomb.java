package com.grim3212.mc.pack.world.client.entity.model;

import com.grim3212.mc.pack.world.entity.EntityBobomb;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBobomb extends ModelBase {

	public ModelRenderer Body;
	public ModelRenderer Key[];
	public ModelRenderer Top;
	public ModelRenderer Fuse;
	public ModelRenderer LegR;
	public ModelRenderer LegL;
	public ModelRenderer FootR;
	public ModelRenderer FootL;

	public ModelBobomb() {
		Key = new ModelRenderer[2];
		Body = new ModelRenderer(this, 1, 14);
		Body.addBox(-4.5F, -4.5F, -4.5F, 9, 9, 9);
		Body.setRotationPoint(0.0F, 15F, 0.0F);
		Key[0] = new ModelRenderer(this, 0, 15);
		Key[0].addBox(-1F, -1F, 0.0F, 2, 2, 3, -0.3F);
		Key[0].setRotationPoint(0.0F, 15F, 4.2F);
		Key[1] = new ModelRenderer(this, 48, 16);
		Key[1].addBox(-0.3F, -5F, 0.0F, 2, 10, 6, -0.3F);
		Key[1].setRotationPoint(0.0F, 15F, 5.2F);
		Top = new ModelRenderer(this, 24, 0);
		Top.addBox(-2.5F, 0.0F, -2.5F, 5, 2, 5);
		Top.setRotationPoint(0.0F, 9F, 0.0F);
		Fuse = new ModelRenderer(this, 0, 0);
		Fuse.addBox(-2F, -5F, 0.0F, 7, 7, 1, -0.5F);
		Fuse.setRotationPoint(-0.63F, 7.5F, -0.5F);
		LegR = new ModelRenderer(this, 0, 8);
		LegR.addBox(-1F, 0.0F, -1F, 2, 5, 2, -0.3F);
		LegR.setRotationPoint(-2.3F, 17F, 0.0F);
		LegL = new ModelRenderer(this, 0, 8);
		LegL.addBox(-1F, 0.0F, -1F, 2, 5, 2, -0.3F);
		LegL.setRotationPoint(2.3F, 17F, 0.0F);
		FootR = new ModelRenderer(this, 44, 0);
		FootR.addBox(-2F, 4F, -4F, 4, 2, 6, -0.2F);
		FootR.setRotationPoint(-2.3F, 17.5F, 0.0F);
		FootL = new ModelRenderer(this, 44, 0);
		FootL.addBox(-2F, 4F, -4F, 4, 2, 6, -0.2F);
		FootL.setRotationPoint(2.3F, 17.5F, 0.0F);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.65F, 0.65F, 0.65F);
		GlStateManager.translate(0f, 0.80f, 0d);

		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		Body.render(scale);
		Top.render(scale);
		Fuse.render(scale);
		LegR.render(scale);
		LegL.render(scale);
		FootR.render(scale);
		FootL.render(scale);
		Key[0].render(scale);
		Key[1].render(scale);

		GlStateManager.popMatrix();
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		LegL.rotateAngleX = MathHelper.cos(limbSwing * 1.9662F + (float) Math.PI) * 1.0F * limbSwingAmount;
		LegR.rotateAngleX = MathHelper.cos(limbSwing * 1.9662F) * 1.0F * limbSwingAmount;
		FootL.rotateAngleX = LegL.rotateAngleX;
		FootR.rotateAngleX = LegR.rotateAngleX;

		if (entityIn instanceof EntityBobomb) {
			Key[0].rotateAngleZ = ((EntityBobomb) entityIn).rotation;
			Key[1].rotateAngleZ = ((EntityBobomb) entityIn).rotation;
		}
	}
}
