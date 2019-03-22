package com.grim3212.mc.pack.core.manual.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.button.GuiButtonChangePage;
import com.grim3212.mc.pack.core.manual.button.GuiButtonHistory;
import com.grim3212.mc.pack.core.manual.button.GuiButtonHome;
import com.grim3212.mc.pack.core.manual.button.GuiButtonModSection;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiManualIndex extends GuiScreen {

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
	}

	public GuiManualIndex(int page) {
		this.page = page;
	}

	public GuiManualIndex(GuiManualIndex manual) {
		this.page = manual.page;
	}

	@Override
	public void initGui() {
		super.initGui();
		activeManualPage = this;

		x = (width - manualWidth) / 2;
		y = (height - manualHeight) / 2;

		this.updateButtons();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		GlStateManager.color4f(1F, 1F, 1F, 1F);
		mc.textureManager.bindTexture(texture);
		drawTexturedModalRect(x, y, 0, 0, manualWidth, manualHeight);

		this.drawTitle();

		GlStateManager.pushMatrix();
		GlStateManager.scalef(0.8f, 0.8f, 1.0f);
		GlStateManager.translatef(34f, 14f, 0f);
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
			fontRenderer.drawString("(" + (this.getPage() + 1) + "/" + (int) Math.ceil(numPages) + ")", this.x + 166, this.y + 216, 0);
	}

	protected void drawTitle() {
		String title = I18n.format("grimpack.manual.title");
		fontRenderer.drawString(title, width / 2 - fontRenderer.getStringWidth(title) / 2, this.y + 14, 0x0026FF);
	}

	protected void drawInfo() {
		if (page == 0) {
			fontRenderer.drawSplitString(I18n.format("grimpack.manual.header"), x + 15, y + 20, 210, 0);
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

		this.addButton(changeForward = new GuiButtonChangePage(0, x + manualWidth - 20, y + manualHeight - 12, true) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);

				page++;
				GuiManualIndex.this.updateButtons();
			}
		});
		this.addButton(changeBack = new GuiButtonChangePage(1, x + 2, y + manualHeight - 12, false) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);

				page--;
				GuiManualIndex.this.updateButtons();
			}
		});
		this.addButton(goHome = new GuiButtonHome(2, width / 2 - 9 / 2, y + manualHeight - 11) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);

				GuiManualIndex.this.updateButtons();
			}
		});

		if (page == 0) {
			changeForward.visible = ManualRegistry.getLoadedParts().size() > 12;
			changeForward.enabled = ManualRegistry.getLoadedParts().size() > 12;
			changeBack.visible = false;
			changeBack.enabled = false;
			goHome.visible = false;
			goHome.enabled = false;
		} else {
			changeForward.visible = (ManualRegistry.getLoadedParts().size() - 12 > (page * 14));
			changeForward.enabled = (ManualRegistry.getLoadedParts().size() - 12 > (page * 14));
			goHome.visible = true;
			goHome.enabled = true;
		}

		if (page == 0) {
			for (int i = 0; i < ManualRegistry.getLoadedParts().size() && i < 12; i++) {
				this.addButton(new GuiButtonModSection(i + 3, x + 15, y + (58 + i * 14), 10, ManualRegistry.getLoadedParts().get(i).getPartName()) {
					@Override
					public void onClick(double mouseX, double mouseY) {
						super.onClick(mouseX, mouseY);

						ManualPart part = ManualRegistry.getLoadedParts().get(this.id - 3);
						mc.displayGuiScreen(new GuiManualChapter(part, GuiManualIndex.this.page));
					}
				});
			}
		} else {
			for (int i = 0; i < 14; i++) {
				if ((12 + ((page - 1) * 14 + i)) < ManualRegistry.getLoadedParts().size())
					this.addButton(new GuiButtonModSection(i + 3, x + 15, y + (30 + i * 14), 10, ManualRegistry.getLoadedParts().get(12 + ((page - 1) * 14 + i)).getPartName()) {
						@Override
						public void onClick(double mouseX, double mouseY) {
							super.onClick(mouseX, mouseY);

							ManualPart part = ManualRegistry.getLoadedParts().get(12 + ((page - 1) * 14 + (this.id - 3)));
							mc.displayGuiScreen(new GuiManualChapter(part, GuiManualIndex.this.page));
						}
					});
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public void onGuiClosed() {
		activeManualPage = new GuiManualIndex(this.page);
	}
}