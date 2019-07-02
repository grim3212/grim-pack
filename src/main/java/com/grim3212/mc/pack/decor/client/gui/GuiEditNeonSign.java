package com.grim3212.mc.pack.decor.client.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.network.MessageNeonChangeMode;
import com.grim3212.mc.pack.decor.network.MessageNeonUpdate;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

@OnlyIn(Dist.CLIENT)
public class GuiEditNeonSign extends Screen {
	/** Reference to the sign object. */
	private final TileEntityNeonSign tileSign;
	/** Counts the number of screen updates. */
	private int updateCounter;
	/** The index of the line that is being edited. */
	private int editLine;

	private final int bgWidth = 176;
	private final int bgHeight = 208;
	private TextInputUtil textInputUtil;

	public static final ResourceLocation NEON_SIGN_GUI_TEXTURE = new ResourceLocation(GrimPack.modID, "textures/gui/gui_neon_sign.png");

	public GuiEditNeonSign(TileEntityNeonSign teSign) {
		super(new TranslationTextComponent("sign.edit"));
		this.tileSign = teSign;
	}

	@Override
	public void init() {
		int x = (width - bgWidth) / 2;
		int y = (height - bgHeight) / 2;

		this.buttons.clear();
		this.children.clear();

		this.textInputUtil = new TextInputUtil(this.minecraft, () -> {
			return this.tileSign.getText(this.editLine).getString();
		}, (s) -> {
			this.tileSign.setText(this.editLine, new StringTextComponent(s));
		}, 90);

		this.minecraft.keyboardListener.enableRepeatEvents(true);
		this.addButton(new Button(x + (bgWidth - 154) / 2, y + 179, 154, 20, I18n.format("gui.done"), btn -> {
			this.onClose();
		}));

		for (int l = 0; l < 11; l++) {
			final int id = l + 1;
			this.addButton(new GuiButtonNeon(x + 11 + 14 * l, y + 136, "", 176, l * 14, btn -> {
				addSignText(id);
			}));
		}

		for (int i1 = 11; i1 < 16; i1++) {
			final int id = i1 + 1;
			this.addButton(new GuiButtonNeon(x + 11 + 14 * (i1 - 11), y + 150, "", 176, i1 * 14, btn -> {
				addSignText(id);
			}));
		}

		this.addButton(new GuiButtonNeon(x + 11 + 70, y + 150, I18n.format("grimpack.decor.neon.bold"), 204, 0, btn -> {
			addSignText(17);
		}));
		this.addButton(new GuiButtonNeon(x + 11 + 84, y + 150, I18n.format("grimpack.decor.neon.italic"), 204, 14, btn -> {
			addSignText(18);
		}));
		this.addButton(new GuiButtonNeon(x + 11 + 98, y + 150, I18n.format("grimpack.decor.neon.underline"), 204, 28, btn -> {
			addSignText(19);
		}));
		this.addButton(new GuiButtonNeon(x + 11 + 112, y + 150, I18n.format("grimpack.decor.neon.strikethrough"), 204, 42, btn -> {
			addSignText(20);
		}));
		this.addButton(new GuiButtonNeon(x + 11 + 126, y + 150, I18n.format("grimpack.decor.neon.random"), 204, 56, btn -> {
			addSignText(21);
		}));
		this.addButton(new GuiButtonNeon(x + 11 + 140, y + 150, I18n.format("grimpack.decor.neon.reset"), 204, 70, btn -> {
			addSignText(22);
		}));
		this.addButton(new GuiButtonNeon(x + 11, y + 164, "", 0, 208, 51, true, btn -> {
			GuiEditNeonSign.this.tileSign.mode = 0;
			PacketDispatcher.send(PacketDistributor.SERVER.noArg(), new MessageNeonChangeMode(0, GuiEditNeonSign.this.tileSign.getPos()));
		}));
		this.addButton(new GuiButtonNeon(x + 11 + 51, y + 164, "", 51, 208, 52, true, btn -> {
			GuiEditNeonSign.this.tileSign.mode = 1;
			PacketDispatcher.send(PacketDistributor.SERVER.noArg(), new MessageNeonChangeMode(1, GuiEditNeonSign.this.tileSign.getPos()));
		}));
		this.addButton(new GuiButtonNeon(x + 11 + 103, y + 164, "", 103, 208, 51, true, btn -> {
			GuiEditNeonSign.this.tileSign.mode = 2;
			PacketDispatcher.send(PacketDistributor.SERVER.noArg(), new MessageNeonChangeMode(2, GuiEditNeonSign.this.tileSign.getPos()));
		}));
	}

	private void addSignText(int id) {
		String s = this.tileSign.signText[this.editLine].getString();
		s = s + "§" + getColorCode(id);
		this.tileSign.signText[this.editLine] = new StringTextComponent(s);
		this.textInputUtil.func_216899_b();
	}

	@Override
	public void removed() {
		this.minecraft.keyboardListener.enableRepeatEvents(false);

		// Update lines on server side
		PacketDispatcher.send(PacketDistributor.SERVER.noArg(), new MessageNeonUpdate(this.tileSign.getPos(), this.tileSign.signText));
	}

	@Override
	public void onClose() {
		this.tileSign.markDirty();
		this.minecraft.displayGuiScreen((Screen) null);
	}

	@Override
	public void tick() {
		++this.updateCounter;
	}

	private String getColorCode(int nbr) {
		switch (nbr) {
		case 11:
			return "A";
		case 12:
			return "B";
		case 13:
			return "C";
		case 14:
			return "D";
		case 15:
			return "E";
		case 16:
			return "F";
		case 17:
			return "l";
		case 18:
			return "o";
		case 19:
			return "n";
		case 20:
			return "m";
		case 21:
			return "k";
		case 22:
			return "r";
		}
		return Integer.valueOf(nbr - 1).toString();
	}

	@Override
	public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
		this.textInputUtil.func_216894_a(p_charTyped_1_);
		return true;
	}

	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		if (p_keyPressed_1_ == 265) {
			this.editLine = this.editLine - 1 & 3;
			this.textInputUtil.func_216899_b();
			return true;
		} else if (p_keyPressed_1_ != 264 && p_keyPressed_1_ != 257 && p_keyPressed_1_ != 335) {
			return this.textInputUtil.func_216897_a(p_keyPressed_1_) ? true : super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
		} else {
			this.editLine = this.editLine + 1 & 3;
			this.textInputUtil.func_216899_b();
			return true;
		}
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();

		// Draw GUI
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.textureManager.bindTexture(NEON_SIGN_GUI_TEXTURE);
		int j = (width - bgWidth) / 2;
		int k = (height - bgHeight) / 2;
		blit(j, k, 0, 0, bgWidth, bgHeight);

		this.drawCenteredString(this.font, I18n.format("sign.edit"), this.width / 2, k + 5, 16777215);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) (this.width / 2), (float) k - 50F, 50.0F);
		float f = 93.75F;
		GlStateManager.scalef(-f, -f, -f);
		GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
		BlockState iblockstate = this.tileSign.getBlockState();

		float f1;
		if (iblockstate.getBlock() == DecorBlocks.neon_sign_standing) {
			f1 = (float) (iblockstate.get(StandingSignBlock.ROTATION) * 360) / 16.0F;
		} else {
			f1 = iblockstate.get(WallSignBlock.FACING).getHorizontalAngle();
		}
		GlStateManager.rotatef(f1, 0.0F, 1.0F, 0.0F);
		GlStateManager.translatef(0.0F, -1.0625F, 0.0F);

		this.tileSign.fromScreen(this.editLine, this.textInputUtil.func_216896_c(), this.textInputUtil.func_216898_d(), this.updateCounter / 6 % 2 == 0);
		TileEntityRendererDispatcher.instance.render(this.tileSign, -0.5D, -0.75D, -0.5D, 0.0F);
		this.tileSign.reset();
		GlStateManager.popMatrix();
		super.render(mouseX, mouseY, partialTicks);
	}
}