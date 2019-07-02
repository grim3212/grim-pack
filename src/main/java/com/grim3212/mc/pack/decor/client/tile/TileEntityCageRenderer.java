package com.grim3212.mc.pack.decor.client.tile;

import com.grim3212.mc.pack.decor.tile.TileEntityCage;
import com.grim3212.mc.pack.decor.util.CageSpawnerLogic;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TileEntityCageRenderer extends TileEntityRenderer<TileEntityCage> {

	@Override
	public void render(TileEntityCage tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
		renderMob(tileEntityIn.getSpawnerLogic(), x, y, z, partialTicks);
		GlStateManager.popMatrix();
	}

	public static void renderMob(CageSpawnerLogic cageSpawnerLogic, double posX, double posY, double posZ, float partialTicks) {
		Entity entity = cageSpawnerLogic.getEntity();

		if (entity != null) {
			float f = 0.53125F;
			float f1 = Math.max(entity.getWidth(), entity.getHeight());

			if ((double) f1 > 1.0D) {
				f /= f1;
			}

			GlStateManager.translatef(0.0F, 0.4F, 0.0F);
			GlStateManager.rotatef((float) (cageSpawnerLogic.getPrevMobRotation() + (cageSpawnerLogic.getMobRotation() - cageSpawnerLogic.getPrevMobRotation()) * (double) partialTicks) * 10.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translatef(0.0F, -0.2F, 0.0F);
			GlStateManager.scalef(f, f, f);
			entity.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
			Minecraft.getInstance().getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
		}
	}
}
