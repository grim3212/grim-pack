package com.grim3212.mc.pack.industry.client.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.Entity;

public class ModelWarehouseCrate extends Model {
	public boolean renderHandle = false;
	public float doorAngle = 0.0F;
	public RendererModel[] crateMain = new RendererModel[5];
	public RendererModel[] crateBars = new RendererModel[13];
	public RendererModel crateLid;
	public RendererModel crateLock;

	public ModelWarehouseCrate() {
		init();
	}

	public void init() {
		this.crateMain[0] = new RendererModel(this, 0, 0).setTextureSize(64, 128);
		this.crateMain[1] = new RendererModel(this, 0, 0).setTextureSize(64, 128);
		this.crateMain[2] = new RendererModel(this, 0, 28).setTextureSize(64, 128);
		this.crateMain[3] = new RendererModel(this, 0, 28).setTextureSize(64, 128);
		this.crateMain[4] = new RendererModel(this, 0, 43).setTextureSize(64, 128);

		this.crateBars[0] = new RendererModel(this, 30, 0).setTextureSize(64, 128);
		this.crateBars[1] = new RendererModel(this, 30, 0).setTextureSize(64, 128);
		this.crateBars[2] = new RendererModel(this, 30, 0).setTextureSize(64, 128);
		this.crateBars[3] = new RendererModel(this, 30, 0).setTextureSize(64, 128);
		this.crateBars[4] = new RendererModel(this, 30, 17).setTextureSize(64, 128);
		this.crateBars[5] = new RendererModel(this, 30, 17).setTextureSize(64, 128);
		this.crateBars[6] = new RendererModel(this, 30, 21).setTextureSize(64, 128);
		this.crateBars[7] = new RendererModel(this, 30, 21).setTextureSize(64, 128);
		this.crateBars[8] = new RendererModel(this, 0, 56).setTextureSize(64, 128);
		this.crateBars[9] = new RendererModel(this, 0, 56).setTextureSize(64, 128);
		this.crateBars[10] = new RendererModel(this, 6, 56).setTextureSize(64, 128);
		this.crateBars[11] = new RendererModel(this, 6, 56).setTextureSize(64, 128);
		this.crateBars[12] = new RendererModel(this, 6, 56).setTextureSize(64, 128);

		this.crateLid = new RendererModel(this, 0, 76).setTextureSize(64, 128);
		this.crateLock = new RendererModel(this, 56, 0).setTextureSize(64, 128);

		this.crateMain[0].addBox(1.0F, 1.0F, 1.0F, 1, 14, 14);
		this.crateMain[1].addBox(14.0F, 1.0F, 1.0F, 1, 14, 14);
		this.crateMain[2].addBox(2.0F, 1.0F, 1.0F, 12, 14, 1);
		this.crateMain[3].addBox(2.0F, 1.0F, 14.0F, 12, 14, 1);
		this.crateMain[4].addBox(2.0F, 1.0F, 2.0F, 12, 1, 12);

		this.crateBars[0].addBox(0.0F, 0.01F, 0.0F, 2, 15, 2);
		this.crateBars[1].addBox(14.0F, 0.01F, 14.0F, 2, 15, 2);
		this.crateBars[2].addBox(0.0F, 0.01F, 14.0F, 2, 15, 2);
		this.crateBars[3].addBox(14.0F, 0.01F, 0.0F, 2, 15, 2);
		this.crateBars[4].addBox(2.0F, 0.0F, 0.0F, 12, 2, 2);
		this.crateBars[5].addBox(2.0F, 0.0F, 14.0F, 12, 2, 2);
		this.crateBars[6].addBox(0.0F, 0.0F, 2.0F, 2, 2, 12);
		this.crateBars[7].addBox(14.0F, 0.0F, 2.0F, 2, 2, 12);

		this.crateBars[8].addBox(0.0F, 0.0F, 0.0F, 2, 18, 1);
		this.crateBars[8].setRotationPoint(1.5F, 2.25F, 0.01F);
		this.crateBars[8].rotateAngleZ = ((float) Math.toRadians(-45.0D));

		this.crateBars[9].addBox(0.0F, 0.0F, 0.0F, 2, 18, 1);
		this.crateBars[9].setRotationPoint(13.0F, 0.85F, 14.99F);
		this.crateBars[9].rotateAngleZ = ((float) Math.toRadians(45.0D));

		this.crateBars[10].addBox(0.0F, 0.0F, 0.0F, 1, 18, 2);
		this.crateBars[10].setRotationPoint(0.01F, 2.25F, 1.5F);
		this.crateBars[10].rotateAngleX = ((float) Math.toRadians(45.0D));

		this.crateBars[11].addBox(0.0F, 0.0F, 0.0F, 1, 18, 2);
		this.crateBars[11].setRotationPoint(14.99F, 0.85F, 13.0F);
		this.crateBars[11].rotateAngleX = ((float) Math.toRadians(-45.0D));

		this.crateBars[12].addBox(0.0F, 0.0F, 0.0F, 1, 18, 2);
		this.crateBars[12].setRotationPoint(1.0F, 0.01F, 2.0F);
		this.crateBars[12].rotateAngleX = ((float) Math.toRadians(135.0D));
		this.crateBars[12].rotateAngleZ = ((float) Math.toRadians(90.0D));

		this.crateLid.addBox(0.01F, 0.0F, 0.01F, 16, 2, 16);
		this.crateLid.setRotationPoint(0.0F, 14.0F, 0.0F);

		this.crateLock.addBox(0.0F, -4.0F, 15.05F, 3, 6, 1);
		this.crateLock.setRotationPoint(6.5F, 14.0F, 0.0F);
		this.crateLock.mirror = true;
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
		this.crateLid.rotateAngleX = ((float) Math.toRadians(this.doorAngle / -3.0F));
		this.crateLock.rotateAngleX = ((float) Math.toRadians(this.doorAngle / -3.0F));

		this.crateMain[0].render(f1);
		this.crateMain[1].render(f1);
		this.crateMain[2].render(f1);
		this.crateMain[3].render(f1);
		this.crateMain[4].render(f1);

		this.crateBars[0].render(f1);
		this.crateBars[1].render(f1);
		this.crateBars[2].render(f1);
		this.crateBars[3].render(f1);
		this.crateBars[4].render(f1);
		this.crateBars[5].render(f1);
		this.crateBars[6].render(f1);
		this.crateBars[7].render(f1);
		this.crateBars[8].render(f1);
		this.crateBars[9].render(f1);
		this.crateBars[10].render(f1);
		this.crateBars[11].render(f1);
		this.crateBars[12].render(f1);

		this.crateLid.render(f1);
		if (!this.renderHandle)
			this.crateLock.render(f1);
	}
}