package com.grim3212.mc.pack.industry.client.tile;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.industry.client.model.ModelDualLocker;
import com.grim3212.mc.pack.industry.client.model.ModelLocker;
import com.grim3212.mc.pack.industry.tile.TileEntityLocker;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@OnlyIn(Dist.CLIENT)
public class TileEntityLockerRenderer extends TileEntitySpecialRenderer<TileEntityLocker> {

	private ModelLocker model;
	private ModelDualLocker dualmodel;
	private static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(GrimPack.modID, "textures/models/locker.png");

	public TileEntityLockerRenderer() {
		this.model = new ModelLocker();
		this.dualmodel = new ModelDualLocker();
	}

	@Override
	public void render(TileEntityLocker te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if ((te != null) && (te.isUpperLocker()))
			return;

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);

		int i = 0;

		if (te != null && te.getWorld() != null) {
			i = te.getBlockMetadata();
		}

		if (i == 3) {
			GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(-1.0F, 0.0F, 0.0F);
		} else if (i == 2) {
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(-1.0F, 0.0F, -1.0F);
		} else if (i == 1) {
			GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.0F, 0.0F, -1.0F);
		}

		TileEntity upperlocker = te != null ? te.getUpperLocker() : null;

		if ((upperlocker != null) && ((upperlocker instanceof TileEntityLocker))) {
			if (destroyStage >= 0) {
				this.bindTexture(DESTROY_STAGES[destroyStage]);
				GlStateManager.matrixMode(5890);
				GlStateManager.pushMatrix();
				GlStateManager.scale(8.0F, 8.0F, 1.0F);
				GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
				GlStateManager.matrixMode(5888);
			} else {
				this.bindTexture(RESOURCE_LOCATION);
			}

			GlStateManager.pushMatrix();
			int rotation = 0;
			if (te != null)
				rotation = te.rotation;

			this.dualmodel.doorAngle = rotation;
			if (te != null)
				this.dualmodel.renderHandle = !te.isLocked();
			else
				this.dualmodel.renderHandle = true;
			this.dualmodel.renderModel(0.0625F);
		} else {

			if (destroyStage >= 0) {
				this.bindTexture(DESTROY_STAGES[destroyStage]);
				GlStateManager.matrixMode(5890);
				GlStateManager.pushMatrix();
				GlStateManager.scale(8.0F, 8.0F, 1.0F);
				GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
				GlStateManager.matrixMode(5888);
			} else {
				this.bindTexture(RESOURCE_LOCATION);
			}

			GlStateManager.pushMatrix();
			int rotation = 0;
			if (te != null)
				rotation = te.rotation;

			this.model.doorAngle = rotation;
			if (te != null)
				this.model.renderHandle = !te.isLocked();
			else
				this.model.renderHandle = true;
			this.model.renderModel(0.0625F);
		}
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();

		if (destroyStage >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}
}