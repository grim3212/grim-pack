package com.grim3212.mc.pack.industry.client.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.Entity;

public class ModelLocker extends Model {
	public float doorAngle = 0.0F;
	public boolean renderHandle = false;
	public RendererModel lockerMain;
	public RendererModel lockerDoor;
	public RendererModel lockerHandle;
	public RendererModel lockerLock;
	public RendererModel lockerLeg1;
	public RendererModel lockerLeg2;
	public RendererModel lockerLeg3;
	public RendererModel lockerLeg4;
	public RendererModel[] lockerInt = new RendererModel[5];

	public ModelLocker() {
		this.lockerMain = new RendererModel(this, 0, 0).setTextureSize(128, 128);
		this.lockerDoor = new RendererModel(this, 32, 32).setTextureSize(128, 128);
		this.lockerHandle = new RendererModel(this, 48, 0).setTextureSize(128, 128);
		this.lockerLock = new RendererModel(this, 64, 0).setTextureSize(128, 128);
		this.lockerLeg1 = new RendererModel(this, 0, 0).setTextureSize(128, 128);
		this.lockerLeg2 = new RendererModel(this, 0, 0).setTextureSize(128, 128);
		this.lockerLeg3 = new RendererModel(this, 0, 0).setTextureSize(128, 128);
		this.lockerLeg4 = new RendererModel(this, 0, 0).setTextureSize(128, 128);
		this.lockerInt[0] = new RendererModel(this, 15, 79).setTextureSize(128, 128);
		this.lockerInt[1] = new RendererModel(this, 16, 64).setTextureSize(128, 128);
		this.lockerInt[2] = new RendererModel(this, 33, 64).setTextureSize(128, 128);
		this.lockerInt[3] = new RendererModel(this, 0, 95).setTextureSize(128, 128);
		this.lockerInt[4] = new RendererModel(this, 16, 95).setTextureSize(128, 128);

		this.lockerMain.addBox(0.0F, 3.0F, 0.0F, 16, 13, 16);

		this.lockerDoor.addBox(0.0F, 5.0F, 0.0F, 12, 9, 2, 0.0F);
		this.lockerDoor.setRotationPoint(2.0F, 0.0F, 15.0F);

		this.lockerHandle.addBox(9.0F, 9.0F, 2.0F, 1, 3, 1, 0.0F);
		this.lockerHandle.setRotationPoint(2.0F, 0.0F, 15.0F);

		this.lockerLock.addBox(9.0F, 6.0F, 1.0F, 3, 6, 1, 0.0F);
		this.lockerLock.setRotationPoint(2.0F, 0.0F, 15.1F);

		this.lockerLeg1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3);
		this.lockerLeg2.addBox(13.0F, 0.0F, 0.0F, 3, 3, 3);
		this.lockerLeg3.addBox(13.0F, 0.0F, 13.0F, 3, 3, 3);
		this.lockerLeg4.addBox(0.0F, 0.0F, 13.0F, 3, 3, 3);

		this.lockerInt[0].addBox(0.0F, 2.0F, 0.0F, 16, 13, 1);
		this.lockerInt[1].addBox(0.0F, 2.0F, 0.0F, 1, 13, 16);
		this.lockerInt[2].addBox(15.0F, 2.0F, 0.0F, 1, 13, 16);
		this.lockerInt[3].addBox(0.0F, 3.0F, 0.0F, 16, 1, 16);
		this.lockerInt[4].addBox(0.0F, 15.0F, 0.0F, 16, 1, 16);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		// super.render(entity, f, f1, f2, f3, f4, f5);
		// setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		callRender(f5);
	}

	public void renderModel(float f1) {
		callRender(f1);
	}

	public void callRender(float f1) {
		this.lockerDoor.rotateAngleY = (-(this.doorAngle / 90.0F));
		this.lockerHandle.rotateAngleY = (-(this.doorAngle / 90.0F));
		this.lockerLock.rotateAngleY = (-(this.doorAngle / 90.0F));
		this.lockerMain.render(f1);
		this.lockerDoor.render(f1);
		if (this.renderHandle)
			this.lockerHandle.render(f1);
		else
			this.lockerLock.render(f1);
		this.lockerLeg1.render(f1);
		this.lockerLeg2.render(f1);
		this.lockerLeg3.render(f1);
		this.lockerLeg4.render(f1);

		for (RendererModel model : this.lockerInt) {
			model.render(f1);
		}
	}
}