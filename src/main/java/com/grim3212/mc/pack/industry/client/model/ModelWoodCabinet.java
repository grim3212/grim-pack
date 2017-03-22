package com.grim3212.mc.pack.industry.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWoodCabinet extends ModelBase {
	public boolean glassDoor = false;
	public boolean renderHandle = false;
	public float doorAngle = 0.0F;
	public ModelRenderer cabinetMain;
	public ModelRenderer[] cabinetInt = new ModelRenderer[5];
	public ModelRenderer cabinetDoor1;
	public ModelRenderer cabinetDoor2;
	public ModelRenderer cabinetHandle1;
	public ModelRenderer cabinetLock;
	public ModelRenderer cabinetHandle2;

	public ModelWoodCabinet(boolean useGlass) {
		this.glassDoor = useGlass;
		init();
	}

	public ModelWoodCabinet() {
		init();
	}

	public void init() {
		this.cabinetMain = new ModelRenderer(this, 0, 0).setTextureSize(64, 128);
		this.cabinetInt[0] = new ModelRenderer(this, 0, 65).setTextureSize(64, 128);
		this.cabinetInt[1] = new ModelRenderer(this, 0, 48).setTextureSize(64, 128);
		this.cabinetInt[2] = new ModelRenderer(this, 31, 96).setTextureSize(64, 128);
		this.cabinetInt[3] = new ModelRenderer(this, 2, 49).setTextureSize(64, 128);
		this.cabinetInt[4] = new ModelRenderer(this, -14, 49).setTextureSize(64, 128);
		this.cabinetDoor1 = new ModelRenderer(this, this.glassDoor ? 16 : 0, 32).setTextureSize(64, 128);
		this.cabinetDoor2 = new ModelRenderer(this, this.glassDoor ? 16 : 0, 32).setTextureSize(64, 128);
		this.cabinetHandle1 = new ModelRenderer(this, 0, 0).setTextureSize(64, 128);
		this.cabinetLock = new ModelRenderer(this, 48, 0).setTextureSize(64, 128);
		this.cabinetHandle2 = new ModelRenderer(this, 0, 0).setTextureSize(64, 128);

		this.cabinetMain.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16);

		this.cabinetInt[0].addBox(0.0F, 0.0F, 0.0F, 1, 16, 16);
		this.cabinetInt[1].addBox(0.0F, 0.0F, 0.0F, 16, 16, 1);
		this.cabinetInt[2].addBox(15.0F, 0.0F, 0.0F, 1, 16, 16);
		this.cabinetInt[3].addBox(0.0F, 15.0F, 0.0F, 16, 1, 16);
		this.cabinetInt[4].addBox(0.0F, 0.0F, 0.0F, 16, 1, 16);

		this.cabinetDoor1.addBox(0.0F, 2.0F, 0.0F, 6, 12, 2, 0.0F);
		this.cabinetDoor1.setRotationPoint(2.0F, 0.0F, 15.0F);

		this.cabinetDoor2.addBox(-6.0F, 2.0F, 0.0F, 6, 12, 2, 0.0F);
		this.cabinetDoor2.setRotationPoint(14.0F, 0.0F, 15.0F);

		this.cabinetHandle1.addBox(4.0F, 7.0F, 2.0F, 1, 2, 1, 0.0F);
		this.cabinetHandle1.setRotationPoint(2.0F, 0.0F, 15.0F);

		this.cabinetLock.addBox(4.5F, 5.0F, 1.0F, 3, 6, 1, 0.0F);
		this.cabinetLock.setRotationPoint(2.0F, 0.0F, 15.1F);

		this.cabinetHandle2.addBox(-5.0F, 7.0F, 2.0F, 1, 2, 1, 0.0F);
		this.cabinetHandle2.setRotationPoint(14.0F, 0.0F, 15.0F);
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
		this.cabinetDoor1.rotateAngleY = (this.doorAngle / 90.0F * -1.25F);
		this.cabinetDoor2.rotateAngleY = (this.doorAngle / 90.0F * 1.25F);
		this.cabinetHandle1.rotateAngleY = (this.doorAngle / 90.0F * -1.25F);
		this.cabinetHandle2.rotateAngleY = (this.doorAngle / 90.0F * 1.25F);
		this.cabinetLock.rotateAngleY = (this.doorAngle / 90.0F * -1.25F);
		this.cabinetMain.render(f1);
		this.cabinetInt[0].render(f1);
		this.cabinetInt[1].render(f1);
		this.cabinetInt[2].render(f1);
		this.cabinetInt[3].render(f1);
		this.cabinetInt[4].render(f1);
		this.cabinetDoor1.render(f1);
		this.cabinetDoor2.render(f1);

		if (this.renderHandle) {
			this.cabinetHandle1.render(f1);
			this.cabinetHandle2.render(f1);
		} else {
			this.cabinetLock.render(f1);
		}
	}
}