package com.grim3212.mc.pack.world.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
<<<<<<< HEAD
=======
import net.minecraft.client.renderer.GlStateManager;
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelTreasureMob extends ModelBase {

	public ModelTreasureMob() {
		box1 = new ModelRenderer(this, 0, 17);
		box0 = new ModelRenderer(this, 0, 0);
		box1.addBox(0.0F, 0.0F, 0.0F, 11, 4, 11);
		box1.setRotationPoint(-6F, 13F, -7.5F);
		box0.addBox(0.0F, 0.0F, 0.0F, 11, 6, 11);
		box0.setRotationPoint(-6F, 16F, -6F);
	}

<<<<<<< HEAD
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		setRotationAngles(par2, par3, par4, par5, par6, par7);
		box1.render(par7);
		box0.render(par7);
=======
	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180f, 0.0f, 1.0F, 0.0F);
		setRotationAngles(par2, par3, par4, par5, par6, par7);
		box1.render(par7);
		box0.render(par7);
		GlStateManager.popMatrix();
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
		box1.rotateAngleX = 0.5934119F;
	}

	public ModelRenderer box1;
	public ModelRenderer box0;
}
