package com.grim3212.mc.pack.world.client.entity.model;

import com.grim3212.mc.pack.world.entity.EntityParaBuzzy;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelParaBuzzy extends ModelBase {

	public ModelRenderer Body1;
	public ModelRenderer Body2;
	public ModelRenderer Spike1;
	public ModelRenderer Spike2;
	public ModelRenderer Head;
	public ModelRenderer Shell1;
	public ModelRenderer Shell2;
	public ModelRenderer WingR;
	public ModelRenderer WingL;
	public ModelRenderer LegFL;
	public ModelRenderer LegFR;
	public ModelRenderer LegBL;
	public ModelRenderer LegBR;

	public ModelParaBuzzy() {
		Body1 = new ModelRenderer(this, 0, 0);
		Body1.addBox(-3F, -2F, -3F, 6, 4, 6, 1.0F);
		Body1.setRotationPoint(0.0F, 14.49F, 0.0F);
		Body2 = new ModelRenderer(this, 22, 0);
		Body2.addBox(-2F, 0.0F, -1.5F, 4, 1, 3, 1.0F);
		Body2.setRotationPoint(0.0F, 18F, 0.5F);
		Spike1 = new ModelRenderer(this, 37, 11);
		Spike1.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 1, -0.2F);
		Spike1.setRotationPoint(0.24F, 5.1F, -0.15F);
		Spike2 = new ModelRenderer(this, 37, 11);
		Spike2.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 1, -0.2F);
		Spike2.setRotationPoint(-0.24F, 5.1F, -0.2F);
		Head = new ModelRenderer(this, 0, 10);
		Head.addBox(-2.5F, -1.5F, -1.5F, 5, 3, 3, 0.0F);
		Head.setRotationPoint(0.0F, 19F, -2.5F);
		Shell1 = new ModelRenderer(this, 28, 0);
		Shell1.addBox(-4.5F, -1F, -4F, 9, 2, 8, 0.0F);
		Shell1.setRotationPoint(0.0F, 18.5F, 0.5F);
		Shell2 = new ModelRenderer(this, 0, 27);
		Shell2.addBox(-4.5F, -1F, -3F, 9, 2, 3, 0.0F);
		Shell2.setRotationPoint(0.0F, 16.5F, -1.6F);
		WingL = new ModelRenderer(this, 14, 16);
		WingL.addBox(0.0F, 0.0F, -3.5F, 7, 1, 7, 0.0F);
		WingL.setRotationPoint(4F, 14F, 0.0F);
		WingR = new ModelRenderer(this, 0, 16);
		WingR.addBox(-7F, 0.0F, -3.5F, 7, 1, 7, 0.0F);
		WingR.setRotationPoint(-4F, 14F, 0.0F);
		LegFL = new ModelRenderer(this, 52, 25);
		LegFL.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, -0.2F);
		LegFL.setRotationPoint(2.4F, 18F, -1.5F);
		LegFR = new ModelRenderer(this, 52, 25);
		LegFR.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, -0.2F);
		LegFR.setRotationPoint(-2.4F, 18F, -1.5F);
		LegBL = new ModelRenderer(this, 52, 25);
		LegBL.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, -0.2F);
		LegBL.setRotationPoint(2.4F, 18F, 2.2F);
		LegBR = new ModelRenderer(this, 52, 25);
		LegBR.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, -0.2F);
		LegBR.setRotationPoint(-2.4F, 18F, 2.2F);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

		Body1.render(scale);
		Body2.render(scale);
		Spike1.render(scale);
		Spike2.render(scale);
		Head.render(scale);
		Shell1.render(scale);
		Shell2.render(scale);
		WingL.render(scale);
		WingR.render(scale);
		LegFL.render(scale);
		LegFR.render(scale);
		LegBL.render(scale);
		LegBR.render(scale);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
		EntityParaBuzzy parabuzzy = (EntityParaBuzzy) entitylivingbaseIn;

		if (parabuzzy.isSitting()) {
			WingL.isHidden = true;
			WingR.isHidden = true;
		} else {
			WingL.isHidden = false;
			WingR.isHidden = false;
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		Spike2.rotateAngleY = ((float) Math.PI / 2F);
		Head.rotateAngleX = headPitch / 100F;
		Head.rotateAngleY = netHeadYaw / 650F;
		WingL.rotateAngleZ = MathHelper.cos(ageInTicks + (float) Math.PI);
		WingR.rotateAngleZ = MathHelper.cos(ageInTicks);
		LegFL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
		LegFL.rotateAngleZ = 0.2F;
		LegFR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
		LegFR.rotateAngleZ = -0.2F;
		LegBL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
		LegBL.rotateAngleZ = 0.2F;
		LegBR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
		LegBR.rotateAngleZ = -0.2F;
	}
}
