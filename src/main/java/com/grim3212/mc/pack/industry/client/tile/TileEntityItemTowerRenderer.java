package com.grim3212.mc.pack.industry.client.tile;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.industry.client.model.ModelItemTower;
import com.grim3212.mc.pack.industry.tile.TileEntityItemTower;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityItemTowerRenderer extends TileEntitySpecialRenderer<TileEntityItemTower> {

	public ModelItemTower ITEM_MODEL;
	private static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(GrimPack.modID, "textures/models/tower.png");

	public TileEntityItemTowerRenderer() {
		ITEM_MODEL = new ModelItemTower();
	}

	@Override
	public void render(TileEntityItemTower te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
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

		if (te != null && te.getWorld() != null) {
			GlStateManager.pushMatrix();

			te.model.topBlock = (te.getWorld().getTileEntity(te.getPos().up()) instanceof TileEntityItemTower);
			te.model.bottomBlock = (te.getWorld().getTileEntity(te.getPos().down()) instanceof TileEntityItemTower);
			te.model.renderModel(0.0625F);

			te.model.topBlock = false;
			te.model.bottomBlock = false;

			GlStateManager.popMatrix();
		} else if (te == null) {
			GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(-1.0F, 0.0F, 0.0F);
			ITEM_MODEL.renderModel(0.0625F);
		}

		GlStateManager.popMatrix();

		if (destroyStage >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}
}