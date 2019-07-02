package com.grim3212.mc.pack.decor.client.tile;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.model.SignModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TileEntityNeonSignRenderer extends TileEntityRenderer<TileEntityNeonSign> {

	private static final ResourceLocation NEON_SIGN_TEXTURE = new ResourceLocation(GrimPack.modID, "textures/entity/decor/neon_sign.png");
	private static final ResourceLocation NEON_SIGN_CLEAR_TEXTURE = new ResourceLocation(GrimPack.modID, "textures/entity/decor/neon_sign_clear.png");
	private static final ResourceLocation NEON_SIGN_WHITE_TEXTURE = new ResourceLocation(GrimPack.modID, "textures/entity/decor/neon_sign_white.png");
	private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
	/** The ModelSign instance for use in this renderer */
	private final SignModel model = new SignModel();

	@Override
	public void render(TileEntityNeonSign tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
		BlockState iblockstate = tileEntityIn.getBlockState();
		GlStateManager.pushMatrix();

		if (iblockstate.getBlock() == DecorBlocks.neon_sign_standing) {
			GlStateManager.translatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			float f1 = (float) (iblockstate.get(StandingSignBlock.ROTATION) * 360) / 16.0F;
			GlStateManager.rotatef(-f1, 0.0F, 1.0F, 0.0F);
			this.model.getSignStick().showModel = true;
		} else {
			GlStateManager.translatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			GlStateManager.rotatef(-iblockstate.get(WallSignBlock.FACING).getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
			GlStateManager.translatef(0.0F, -0.3125F, -0.4375F);
			this.model.getSignStick().showModel = false;
		}

		if (destroyStage >= 0) {
			this.bindTexture(DESTROY_STAGES[destroyStage]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scalef(4.0F, 2.0F, 1.0F);
			GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		} else {
			if (tileEntityIn.mode == 0) {
				this.bindTexture(NEON_SIGN_TEXTURE);
			} else if (tileEntityIn.mode == 1) {
				this.bindTexture(NEON_SIGN_WHITE_TEXTURE);
			} else if (tileEntityIn.mode == 2) {
				this.bindTexture(NEON_SIGN_CLEAR_TEXTURE);
			} else {
				// Currently should never happen
				this.bindTexture(SIGN_TEXTURE);
			}
		}

		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();
		float f = 0.6666667F;
		GlStateManager.scalef(f, -f, -f);
		this.model.renderSign();
		GlStateManager.popMatrix();
		FontRenderer fontrenderer = this.getFontRenderer();
		float f3 = 0.010416667F;
		GlStateManager.translatef(0.0F, 0.33333334F, 0.046666667F);
		GlStateManager.scalef(f3, -f3, f3);
		GlStateManager.normal3f(0.0F, 0.0F, -f3);
		GlStateManager.depthMask(false);

		if (destroyStage < 0) {
			for (int j = 0; j < tileEntityIn.signText.length; ++j) {
				if (tileEntityIn.signText[j] != null) {
					ITextComponent itextcomponent = tileEntityIn.signText[j];
					List<ITextComponent> list = RenderComponentsUtil.splitText(itextcomponent, 90, fontrenderer, false, true);
					String s = list != null && !list.isEmpty() ? ((ITextComponent) list.get(0)).getFormattedText() : "";

					if (j == tileEntityIn.getEditLine()) {
						s = "> " + s + "\2470 <";
						renderLightText(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - tileEntityIn.signText.length * 5);
					} else {
						renderLightText(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - tileEntityIn.signText.length * 5);

						// Render on both sides for clear sign
						if (tileEntityIn.mode == 2 && iblockstate.getBlock() == DecorBlocks.neon_sign_standing) {
							GlStateManager.pushMatrix();
							GlStateManager.translatef(0.0F, 0.0F, -9F);
							GlStateManager.rotatef(180F, 0.0F, 1.0F, 0.0F);
							renderLightText(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - tileEntityIn.signText.length * 5);
							GlStateManager.popMatrix();
						}
					}
				}
			}
		}

		GlStateManager.depthMask(true);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();

		if (destroyStage >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}

	private void renderLightText(String s, int x, int y) {
		GlStateManager.pushLightingAttributes();
		GlStateManager.depthMask(true);
		this.getFontRenderer().drawString(s, x, y, 0);
		drawString(s, x, y, true);
		drawString(s, x, y, false);
		GlStateManager.popAttributes();
	}

	protected void drawString(String s, int x, int y, boolean flag) {
		FontRenderer font = this.getFontRenderer();

		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();

		if (flag) {
			GlStateManager.disableAlphaTest();
			GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
			int k = 61680;
			int l = k % 0x10000;
			int i1 = k / 0x10000;
			GlStateManager.texCoord2f((float) l / 1.0F, (float) i1 / 1.0F);
		} else {
			GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}

		font.drawString(s, x, y, 0xff000000);

		if (flag) {
			GlStateManager.enableAlphaTest();
		}

		font.drawString(s, x, y, 0xff000000);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}
}
