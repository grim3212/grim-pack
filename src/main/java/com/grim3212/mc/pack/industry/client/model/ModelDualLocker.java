package com.grim3212.mc.pack.industry.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDualLocker extends ModelBase {
	public float doorAngle = 0.0F;
	public boolean renderHandle = false;
	public ModelRenderer lockerLower;
	public ModelRenderer lockerUpper;
	public ModelRenderer lockerDoor;
	public ModelRenderer lockerHandle;
	public ModelRenderer lockerLock;
	public ModelRenderer lockerLeg1;
	public ModelRenderer lockerLeg2;
	public ModelRenderer lockerLeg3;
	public ModelRenderer lockerLeg4;
	public ModelRenderer lockerShelf;
	public ModelRenderer[] lockerInt = new ModelRenderer[5];

	public ModelDualLocker() {
		this.lockerLower = new ModelRenderer(this, 64, 0).setTextureSize(128, 128);
		this.lockerUpper = new ModelRenderer(this, 64, 32).setTextureSize(128, 128);
		this.lockerDoor = new ModelRenderer(this, 0, 32).setTextureSize(128, 128);
		this.lockerHandle = new ModelRenderer(this, 48, 0).setTextureSize(128, 128);
		this.lockerLock = new ModelRenderer(this, 64, 0).setTextureSize(128, 128);
		this.lockerLeg1 = new ModelRenderer(this, 0, 0).setTextureSize(128, 128);
		this.lockerLeg2 = new ModelRenderer(this, 0, 0).setTextureSize(128, 128);
		this.lockerLeg3 = new ModelRenderer(this, 0, 0).setTextureSize(128, 128);
		this.lockerLeg4 = new ModelRenderer(this, 0, 0).setTextureSize(128, 128);
		this.lockerShelf = new ModelRenderer(this, 48, 112).setTextureSize(128, 128);
		this.lockerInt[0] = new ModelRenderer(this, 79, 79).setTextureSize(128, 128);
		this.lockerInt[1] = new ModelRenderer(this, 80, 64).setTextureSize(128, 128);
		this.lockerInt[2] = new ModelRenderer(this, 97, 64).setTextureSize(128, 128);
		this.lockerInt[3] = new ModelRenderer(this, 0, 95).setTextureSize(128, 128);
		this.lockerInt[4] = new ModelRenderer(this, 16, 95).setTextureSize(128, 128);

		this.lockerLower.addBox(0.0F, 3.0F, 0.0F, 16, 13, 16);
		this.lockerUpper.addBox(0.0F, 16.0F, 0.0F, 16, 16, 16);

		this.lockerDoor.addBox(0.0F, 5.0F, 0.0F, 12, 25, 2, 0.0F);
		this.lockerDoor.setRotationPoint(2.0F, 0.0F, 15.0F);

		this.lockerHandle.addBox(9.0F, 15.0F, 2.0F, 1, 4, 1, 0.0F);
		this.lockerHandle.setRotationPoint(2.0F, 0.0F, 15.0F);

		this.lockerLock.addBox(9.0F, 12.0F, 1.0F, 3, 6, 1, 0.0F);
		this.lockerLock.setRotationPoint(2.0F, 0.0F, 15.1F);

		this.lockerLeg1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3);
		this.lockerLeg2.addBox(13.0F, 0.0F, 0.0F, 3, 3, 3);
		this.lockerLeg3.addBox(13.0F, 0.0F, 13.0F, 3, 3, 3);
		this.lockerLeg4.addBox(0.0F, 0.0F, 13.0F, 3, 3, 3);

		this.lockerShelf.addBox(1.0F, 15.5F, 1.0F, 14, 1, 15);

		this.lockerInt[0].addBox(0.0F, 2.0F, 0.0F, 16, 29, 1);
		this.lockerInt[1].addBox(0.0F, 2.0F, 0.0F, 1, 29, 16);
		this.lockerInt[2].addBox(15.0F, 2.0F, 0.0F, 1, 29, 16);
		this.lockerInt[3].addBox(0.0F, 3.0F, 0.0F, 16, 1, 16);
		this.lockerInt[4].addBox(0.0F, 31.0F, 0.0F, 16, 1, 16);
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
		this.lockerDoor.rotateAngleY = (-(this.doorAngle / 90.0F));
		this.lockerHandle.rotateAngleY = (-(this.doorAngle / 90.0F));
		this.lockerLock.rotateAngleY = (-(this.doorAngle / 90.0F));
		this.lockerUpper.render(f1);
		this.lockerLower.render(f1);
		this.lockerDoor.render(f1);
		if (this.renderHandle)
			this.lockerHandle.render(f1);
		else
			this.lockerLock.render(f1);
		this.lockerLeg1.render(f1);
		this.lockerLeg2.render(f1);
		this.lockerLeg3.render(f1);
		this.lockerLeg4.render(f1);
		this.lockerShelf.render(f1);

		for (ModelRenderer model : this.lockerInt) {
			model.render(f1);
		}
	}
}