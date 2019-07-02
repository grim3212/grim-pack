package com.grim3212.mc.pack.core.manual.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.button.GuiButtonChangePage;
import com.grim3212.mc.pack.core.manual.button.GuiButtonHistory;
import com.grim3212.mc.pack.core.manual.button.GuiButtonHome;
import com.grim3212.mc.pack.core.manual.button.GuiButtonModSection;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiManualIndex extends Screen {

	public static GuiManualIndex activeManualPage = new GuiManualIndex();

	public static final ResourceLocation texture = new ResourceLocation(GrimPack.modID + ":" + "textures/gui/gui_manual.png");

	protected int manualWidth = 192;
	protected int manualHeight = 236;

	protected int x;
	protected int y;

	private int page = 0;

	protected GuiButtonChangePage changeForward;
	protected GuiButtonChangePage changeBack;
	protected GuiButtonHome goHome;
	protected GuiButtonHistory historyBackButton;

	public GuiManualIndex() {
		super(new TranslationTextComponent("grimpack.manual.header"));
	}

	public GuiManualIndex(int page) {
		this();
		this.page = page;
	}

	public GuiManualIndex(GuiManualIndex manual) {
		this();
		this.page = manual.page;
	}

	@Override
	public void init() {
		super.init();
		activeManualPage = this;

		x = (width - manualWidth) / 2;
		y = (height - manualHeight) / 2;

		this.updateButtons();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		GlStateManager.color4f(1F, 1F, 1F, 1F);
		minecraft.textureManager.bindTexture(texture);
		blit(x, y, 0, 0, manualWidth, manualHeight);

		this.drawTitle();

		GlStateManager.pushMatrix();
		GlStateManager.scalef(Page.SCALE_FACTOR, Page.SCALE_FACTOR, 1.0f);
		this.drawInfo();
		this.drawImage();
		this.drawFooter();
		GlStateManager.popMatrix();

		super.render(mouseX, mouseY, partialTicks);
	}

	protected void drawFooter() {
		float numPages = 1;

		if (ManualRegistry.getLoadedParts().size() > 12) {
			numPages = (float) (((ManualRegistry.getLoadedParts().size() - 12.0) / 14.0) + 1.0);
		}

		if (numPages != 1)
			font.drawString("(" + (this.getPage() + 1) + "/" + (int) Math.ceil(numPages) + ")", (int) ((this.x + 166) / Page.SCALE_FACTOR), (int) ((this.y + 216) / Page.SCALE_FACTOR), 0);
	}

	protected void drawTitle() {
		String title = I18n.format("grimpack.manual.title");
		font.drawString(title, width / 2 - font.getStringWidth(title) / 2, this.y + 14, 0x0026FF);
	}

	protected void drawInfo() {
		if (page == 0) {
			font.drawSplitString(I18n.format("grimpack.manual.header"), (int) ((x + 15) / Page.SCALE_FACTOR), (int) ((y + 25) / Page.SCALE_FACTOR), 210, 0);
		}
	}

	protected void drawImage() {
	}

	/**
	 * Updates all buttons on page including forward, back, and mod buttons
	 */
	public void updateButtons() {
		this.buttons.clear();
		this.children.clear();

		this.addButton(changeForward = new GuiButtonChangePage(x + manualWidth - 20, y + manualHeight - 12, true, b -> {
			page++;
			GuiManualIndex.this.updateButtons();
		}));
		this.addButton(changeBack = new GuiButtonChangePage(x + 2, y + manualHeight - 12, false, b -> {
			page--;
			GuiManualIndex.this.updateButtons();
		}));
		this.addButton(goHome = new GuiButtonHome(width / 2 - 9 / 2, y + manualHeight - 11, b -> {
			GuiManualIndex.this.updateButtons();
		}));

		if (page == 0) {
			changeForward.visible = ManualRegistry.getLoadedParts().size() > 12;
			changeForward.active = ManualRegistry.getLoadedParts().size() > 12;
			changeBack.visible = false;
			changeBack.active = false;
			goHome.visible = false;
			goHome.active = false;
		} else {
			changeForward.visible = (ManualRegistry.getLoadedParts().size() - 12 > (page * 14));
			changeForward.active = (ManualRegistry.getLoadedParts().size() - 12 > (page * 14));
			goHome.visible = true;
			goHome.active = true;
		}

		if (page == 0) {
			for (int i = 0; i < ManualRegistry.getLoadedParts().size() && i < 12; i++) {
				this.addButton(new GuiButtonModSection(i, x + 15, y + (58 + i * 14), 10, ManualRegistry.getLoadedParts().get(i).getPartName(), b -> {
					ManualPart part = ManualRegistry.getLoadedParts().get(((GuiButtonModSection) b).getChapterId());
					minecraft.displayGuiScreen(new GuiManualChapter(part, GuiManualIndex.this.page));
				}));
			}
		} else {
			for (int i = 0; i < 14; i++) {
				if ((12 + ((page - 1) * 14 + i)) < ManualRegistry.getLoadedParts().size())
					this.addButton(new GuiButtonModSection(12 + ((page - 1) * 14) + i, x + 15, y + (30 + i * 14), 10, ManualRegistry.getLoadedParts().get(12 + ((page - 1) * 14 + i)).getPartName(), b -> {
						ManualPart part = ManualRegistry.getLoadedParts().get(((GuiButtonModSection) b).getChapterId());
						minecraft.displayGuiScreen(new GuiManualChapter(part, GuiManualIndex.this.page));
					}));
			}
		}
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public void onClose() {
		super.onClose();
		activeManualPage = new GuiManualIndex(this.page);
	}
}