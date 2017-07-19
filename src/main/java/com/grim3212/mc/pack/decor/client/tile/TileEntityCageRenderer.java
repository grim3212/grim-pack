package com.grim3212.mc.pack.decor.client.tile;

import com.grim3212.mc.pack.decor.tile.TileEntityCage;
import com.grim3212.mc.pack.decor.util.CageSpawnerLogic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityCageRenderer extends TileEntitySpecialRenderer<TileEntityCage> {

	@Override
	public void render(TileEntityCage te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y, (float) z + 0.5F);
		renderMob(te.getSpawnerLogic(), x, y, z, partialTicks);
		GlStateManager.popMatrix();
	}

	public static void renderMob(CageSpawnerLogic cageSpawnerLogic, double posX, double posY, double posZ, float partialTicks) {
		Entity entity = cageSpawnerLogic.getEntity();

		if (entity != null) {
			float f = 0.53125F;
			float f1 = Math.max(entity.width, entity.height);

			if ((double) f1 > 1.0D) {
				f /= f1;
			}

			GlStateManager.translate(0.0F, 0.4F, 0.0F);
			GlStateManager.rotate((float) (cageSpawnerLogic.getPrevMobRotation() + (cageSpawnerLogic.getMobRotation() - cageSpawnerLogic.getPrevMobRotation()) * (double) partialTicks) * 10.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.0F, -0.2F, 0.0F);
			GlStateManager.scale(f, f, f);
			entity.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
			Minecraft.getMinecraft().getRenderManager().doRenderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
		}
	}
}
