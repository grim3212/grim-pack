package com.grim3212.mc.pack.industry.client.tile;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.industry.client.model.ModelWoodCabinet;
import com.grim3212.mc.pack.industry.tile.TileEntityGlassCabinet;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityGlassCabinetRenderer extends TileEntitySpecialRenderer<TileEntityGlassCabinet> {

	private ModelWoodCabinet model;
	private static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(GrimPack.modID, "textures/models/cabinet.png");

	public TileEntityGlassCabinetRenderer() {
		this.model = new ModelWoodCabinet(true);
	}

	@Override
	public void renderTileEntityAt(TileEntityGlassCabinet te, double x, double y, double z, float partialTicks, int destroyStage) {
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

		this.bindTexture(RESOURCE_LOCATION);
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
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}