package com.grim3212.mc.pack.decor.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.network.MessageNeonChangeMode;
import com.grim3212.mc.pack.decor.network.MessageNeonUpdate;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@OnlyIn(Dist.CLIENT)
public class GuiEditNeonSign extends GuiScreen {
	/** Reference to the sign object. */
	private final TileEntityNeonSign tileSign;
	/** Counts the number of screen updates. */
	private int updateCounter;
	/** The index of the line that is being edited. */
	private int editLine;
	/** "Done" button for the GUI. */
	private GuiButton doneBtn;

	private final int bgWidth = 176;
	private final int bgHeight = 208;

	public static final ResourceLocation NEON_SIGN_GUI_TEXTURE = new ResourceLocation(GrimPack.modID, "textures/gui/gui_neon_sign.png");

	public GuiEditNeonSign(TileEntityNeonSign teSign) {
		this.tileSign = teSign;
	}

	@Override
	public void initGui() {
		int x = (width - bgWidth) / 2;
		int y = (height - bgHeight) / 2;

		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		this.doneBtn = this.addButton(new GuiButton(0, x + (bgWidth - 154) / 2, y + 179, 154, 20, I18n.format("gui.done")));

		for (int l = 0; l < 11; l++) {
			this.addButton(new GuiButtonNeon(l + 1, x + 11 + 14 * l, y + 136, "", 176, l * 14));
		}

		for (int i1 = 11; i1 < 16; i1++) {
			this.addButton(new GuiButtonNeon(i1 + 1, x + 11 + 14 * (i1 - 11), y + 150, "", 176, i1 * 14));
		}

		this.addButton(new GuiButtonNeon(17, x + 11 + 70, y + 150, I18n.format("grimpack.decor.neon.bold"), 204, 0));
		this.addButton(new GuiButtonNeon(18, x + 11 + 84, y + 150, I18n.format("grimpack.decor.neon.italic"), 204, 14));
		this.addButton(new GuiButtonNeon(19, x + 11 + 98, y + 150, I18n.format("grimpack.decor.neon.underline"), 204, 28));
		this.addButton(new GuiButtonNeon(20, x + 11 + 112, y + 150, I18n.format("grimpack.decor.neon.strikethrough"), 204, 42));
		this.addButton(new GuiButtonNeon(21, x + 11 + 126, y + 150, I18n.format("grimpack.decor.neon.random"), 204, 56));
		this.addButton(new GuiButtonNeon(22, x + 11 + 140, y + 150, I18n.format("grimpack.decor.neon.reset"), 204, 70));
		this.addButton(new GuiButtonNeon(23, x + 11, y + 164, "", 0, 208, 51, true));
		this.addButton(new GuiButtonNeon(24, x + 11 + 51, y + 164, "", 51, 208, 52, true));
		this.addButton(new GuiButtonNeon(25, x + 11 + 103, y + 164, "", 103, 208, 51, true));
		this.tileSign.setEditable(false);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);

		// Update lines on server side
		PacketDispatcher.sendToServer(new MessageNeonUpdate(this.tileSign.getPos(), this.tileSign.signText));

		this.tileSign.setEditable(true);
	}

	@Override
	public void updateScreen() {
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
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {

			switch (button.id) {
			case 0:
				this.tileSign.markDirty();
				this.mc.displayGuiScreen((GuiScreen) null);
				break;
			case 23:
				this.tileSign.mode = 0;
				PacketDispatcher.sendToServer(new MessageNeonChangeMode(0, this.tileSign.getPos()));
				break;
			case 24:
				this.tileSign.mode = 1;
				PacketDispatcher.sendToServer(new MessageNeonChangeMode(1, this.tileSign.getPos()));
				break;
			case 25:
				this.tileSign.mode = 2;
				PacketDispatcher.sendToServer(new MessageNeonChangeMode(2, this.tileSign.getPos()));
				break;
			default:
				String s = this.tileSign.signText[this.editLine].getUnformattedText();
				s = s + "§" + getColorCode(button.id);
				this.tileSign.signText[this.editLine] = new TextComponentString(s);
				break;
			}
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 200) {
			this.editLine = this.editLine - 1 & 3;
		}

		if (keyCode == 208 || keyCode == 28 || keyCode == 156) {
			this.editLine = this.editLine + 1 & 3;
		}

		String s = this.tileSign.signText[this.editLine].getUnformattedText();

		if (keyCode == 14 && !s.isEmpty()) {
			s = s.substring(0, s.length() - 1);
		}

		if (ChatAllowedCharacters.isAllowedCharacter(typedChar) && this.fontRenderer.getStringWidth(s + typedChar) <= 90) {
			s = s + typedChar;
		}

		this.tileSign.signText[this.editLine] = new TextComponentString(s);

		if (keyCode == 1) {
			this.actionPerformed(this.doneBtn);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		// Draw GUI
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(NEON_SIGN_GUI_TEXTURE);
		int j = (width - bgWidth) / 2;
		int k = (height - bgHeight) / 2;
		drawTexturedModalRect(j, k, 0, 0, bgWidth, bgHeight);

		this.drawCenteredString(this.fontRenderer, I18n.format("sign.edit"), this.width / 2, k + 5, 16777215);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) (this.width / 2), (float) k - 50F, 50.0F);
		float f = 93.75F;
		GlStateManager.scale(-f, -f, -f);
		GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
		Block block = this.tileSign.getBlockType();

		if (block == DecorBlocks.neon_sign_standing) {
			float f1 = (float) (this.tileSign.getBlockMetadata() * 360) / 16.0F;
			GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.0F, -1.0625F, 0.0F);
		} else {
			int i = this.tileSign.getBlockMetadata();
			float f2 = 0.0F;

			if (i == 2) {
				f2 = 180.0F;
			}

			if (i == 4) {
				f2 = 90.0F;
			}

			if (i == 5) {
				f2 = -90.0F;
			}

			GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.0F, -1.0625F, 0.0F);
		}

		if (this.updateCounter / 6 % 2 == 0) {
			this.tileSign.lineBeingEdited = this.editLine;
		}

		TileEntityRendererDispatcher.instance.render(this.tileSign, -0.5D, -0.75D, -0.5D, 0.0F);
		this.tileSign.lineBeingEdited = -1;
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);

		// Renders all tooltips if available
		for (GuiButton b : this.buttonList) {
			b.drawButtonForegroundLayer(mouseX, mouseY);
		}
	}
}