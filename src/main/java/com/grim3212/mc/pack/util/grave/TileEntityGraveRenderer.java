package com.grim3212.mc.pack.util.grave;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityGraveRenderer extends TileEntitySpecialRenderer<TileEntityGrave> {

	@Override
	public void render(TileEntityGrave te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.pushMatrix();

		IBlockState state = te.getWorld().getBlockState(te.getPos());

		if (state.getBlock() instanceof BlockGrave) {
			EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockHorizontal.FACING);
			float rot = 0.0F;

			if (facing == EnumFacing.NORTH) {
				rot = 180F;
			} else if (facing == EnumFacing.WEST) {
				rot = 90F;
			} else if (facing == EnumFacing.EAST) {
				rot = -90F;
			}

			float f1 = 0.6666667F;
			GlStateManager.translate((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
			GlStateManager.rotate(-rot, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.0F, -0.3125F, -0.4375F);

			float scale = 0.015F * f1;
			GlStateManager.translate(0.0F, 0.50F * f1, 0.1F * f1);
			GlStateManager.scale(scale, -scale, scale);
			GL11.glNormal3f(0.0F, 0.0F, -1F * scale);

			GlStateManager.depthMask(false);

			for (int k = 0; k < te.signText.length; k++) {
				if (te.signText[k] != null) {
					ITextComponent itextcomponent = te.signText[k];
					List<ITextComponent> list = GuiUtilRenderComponents.splitText(itextcomponent, 90, this.getFontRenderer(), false, true);
					String s = list != null && !list.isEmpty() ? ((ITextComponent) list.get(0)).getFormattedText() : "";
					this.getFontRenderer().drawString(s, -this.getFontRenderer().getStringWidth(s) / 2, k * 10 - te.signText.length * 5, 0);
				}
			}

			GlStateManager.depthMask(true);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}

		GlStateManager.popMatrix();
	}
}
