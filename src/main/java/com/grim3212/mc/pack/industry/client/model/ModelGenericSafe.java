package com.grim3212.mc.pack.industry.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGenericSafe extends ModelBase {
	public float doorAngle = 0.0F;
	public boolean renderHandle = false;
	public ModelRenderer safeMain;
	public ModelRenderer safeDoor;
	public ModelRenderer safeHandle;
	public ModelRenderer safeLock;
	public ModelRenderer safeLeg1;
	public ModelRenderer safeLeg2;
	public ModelRenderer safeLeg3;
	public ModelRenderer safeLeg4;
	public ModelRenderer[] safeInt = new ModelRenderer[5];

	public ModelGenericSafe() {
		this.safeMain = new ModelRenderer(this, 0, 0).setTextureSize(64, 128);
		this.safeDoor = new ModelRenderer(this, 0, 32).setTextureSize(64, 128);
		this.safeHandle = new ModelRenderer(this, 48, 0).setTextureSize(64, 128);
		this.safeLock = new ModelRenderer(this, 0, 48).setTextureSize(64, 128);
		this.safeLeg1 = new ModelRenderer(this, 0, 0).setTextureSize(64, 128);
		this.safeLeg2 = new ModelRenderer(this, 0, 0).setTextureSize(64, 128);
		this.safeLeg3 = new ModelRenderer(this, 0, 0).setTextureSize(64, 128);
		this.safeLeg4 = new ModelRenderer(this, 0, 0).setTextureSize(64, 128);
		this.safeInt[0] = new ModelRenderer(this, 15, 63).setTextureSize(64, 128);
		this.safeInt[1] = new ModelRenderer(this, 16, 48).setTextureSize(64, 128);
		this.safeInt[2] = new ModelRenderer(this, 33, 48).setTextureSize(64, 128);
		this.safeInt[3] = new ModelRenderer(this, 0, 80).setTextureSize(64, 128);
		this.safeInt[4] = new ModelRenderer(this, 16, 80).setTextureSize(64, 128);

		this.safeMain.addBox(0.0F, 3.0F, 0.0F, 16, 13, 16);

		this.safeDoor.addBox(0.0F, 6.0F, 0.0F, 10, 7, 2, 0.0F);
		this.safeDoor.setRotationPoint(3.0F, 0.0F, 15.0F);

		this.safeHandle.addBox(7.0F, 8.0F, 2.0F, 1, 3, 1, 0.0F);
		this.safeHandle.setRotationPoint(3.0F, 0.0F, 15.0F);

		this.safeLock.addBox(6.0F, 6.0F, 1.0F, 3, 6, 1, 0.0F);
		this.safeLock.setRotationPoint(3.0F, 0.0F, 15.1F);

		this.safeLeg1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3);
		this.safeLeg2.addBox(13.0F, 0.0F, 0.0F, 3, 3, 3);
		this.safeLeg3.addBox(13.0F, 0.0F, 13.0F, 3, 3, 3);
		this.safeLeg4.addBox(0.0F, 0.0F, 13.0F, 3, 3, 3);

		this.safeInt[0].addBox(0.0F, 2.0F, 0.0F, 16, 13, 1);
		this.safeInt[1].addBox(0.0F, 2.0F, 0.0F, 1, 13, 16);
		this.safeInt[2].addBox(15.0F, 2.0F, 0.0F, 1, 13, 16);
		this.safeInt[3].addBox(0.0F, 2.0F, 0.0F, 16, 1, 16);
		this.safeInt[4].addBox(0.0F, 15.0F, 0.0F, 16, 1, 16);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		callRender(f5);
	}

	public void renderModel(float f1) {
		callRender(f1);
	}

	public void callRender(float f1) {
		this.safeDoor.rotateAngleY = (-(this.doorAngle / 90.0F));
		this.safeHandle.rotateAngleY = (-(this.doorAngle / 90.0F));
		this.safeLock.rotateAngleY = (-(this.doorAngle / 90.0F));
		this.safeMain.render(f1);
		this.safeDoor.render(f1);
		if (this.renderHandle)
			this.safeHandle.render(f1);
		else
			this.safeLock.render(f1);
		this.safeLeg1.render(f1);
		this.safeLeg2.render(f1);
		this.safeLeg3.render(f1);
		this.safeLeg4.render(f1);

		for (ModelRenderer model : this.safeInt) {
			model.render(f1);
		}
	}
}