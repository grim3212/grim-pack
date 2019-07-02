package com.grim3212.mc.pack.industry.client.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.Entity;

public class ModelExtruder extends Model {
	public RendererModel base;
	public RendererModel drill1;
	public RendererModel drill2;
	private float drillX;

	public ModelExtruder() {
		base = new RendererModel(this, 0, 0);
		base.setTextureSize(64, 64);
		base.mirror = true;
		base.addBox(-7F, -8F, -7F, 13, 15, 14);
		drill1 = new RendererModel(this, 0, 29);
		drill1.setTextureSize(64, 64);
		drill1.mirror = true;
		drill1.addBox(0.0F, -8F, 0.0F, 3, 12, 12);
		drill1.setRotationPoint(5F, 2.0F, -6F);
		drill2 = new RendererModel(this, 30, 41);
		drill2.mirror = true;
		drill2.setTextureSize(64, 64);
		drill2.addBox(0.0F, -8F, 0.0F, 8, 6, 6);
		drill2.setRotationPoint(3F, 5F, -3F);
		drillX = 3F;
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		drill2.rotationPointX = drillX + f4 * 2.5F;
		base.render(f5);
		drill1.render(f5);
		drill2.render(f5);
	}
}
