package com.grim3212.mc.pack.core.manual.gui;

import java.util.ArrayList;

import com.grim3212.mc.pack.core.manual.ManualChapter;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.button.GuiButtonChangePage;
import com.grim3212.mc.pack.core.manual.button.GuiButtonHome;
import com.grim3212.mc.pack.core.manual.button.GuiButtonModSection;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class GuiManualChapter extends GuiManualIndex {

	private ManualPart part;
	private int page = 0;
	private int storedPage = 0;

	private ArrayList<ManualChapter> chapters = new ArrayList<ManualChapter>();

	public GuiManualChapter(ManualPart part, int storedPage) {
		this.part = part;
		this.storedPage = storedPage;
	}

	public GuiManualChapter(ManualPart part, int page, int storedPage) {
		this.part = part;
		this.page = page;
		this.storedPage = storedPage;
	}

	public GuiManualChapter(GuiManualChapter chapter) {
		this.part = chapter.part;
		this.page = chapter.page;
		this.storedPage = chapter.storedPage;
	}

	@Override
	public void initGui() {
		activeManualPage = this;

		x = (width - manualWidth) / 2;
		y = (height - manualHeight) / 2;

		chapters.clear();
		chapters.addAll(part.getChapters());

		this.updateButtons();
	}

	@Override
	protected void drawFooter() {
		if (chapters.size() != 0) {
			int numPages = 1;

			if (chapters.size() > 12) {
				numPages = 2;
				if (chapters.size() > 26) {
					numPages = ((chapters.size() - 12) / 14) + 2;
				}
			}

			FontRenderer renderer = Minecraft.getInstance().fontRenderer;
			boolean unicode = renderer.getBidiFlag();
			renderer.setBidiFlag(true);
			if (numPages != 1)
				renderer.drawString("(" + (this.getPage() + 1) + "/" + numPages + ")", this.x + 166, this.y + 216, 0);
			renderer.setBidiFlag(unicode);
		}
	}

	@Override
	protected void drawTitle() {
		boolean unicode = fontRenderer.getBidiFlag();
		fontRenderer.setBidiFlag(false);
		String title = part.getPartName();
		fontRenderer.drawString(title, width / 2 - fontRenderer.getStringWidth(title) / 2, this.y + 14, 0x0026FF);
		fontRenderer.setBidiFlag(unicode);
	}

	@Override
	protected void drawInfo() {
		if (page == 0) {
			boolean unicode = fontRenderer.getBidiFlag();
			fontRenderer.setBidiFlag(true);
			fontRenderer.drawSplitString(this.part.getPartInfo(), x + 15, y + 28, 162, 0);
			fontRenderer.setBidiFlag(unicode);
		}
	}

	@Override
	protected void drawImage() {
		if (chapters.size() == 0) {
			GrimLog.error(part.getPartName(), "No subsections found!");
		}
	}

	@Override
	public void updateButtons() {
		buttons.clear();
		buttons.add(this.changeForward = new GuiButtonChangePage(0, x + manualWidth - 20, y + manualHeight - 12, true) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);

				page++;
				GuiManualChapter.this.updateButtons();
			}
		});
		buttons.add(this.changeBack = new GuiButtonChangePage(1, x + 2, y + manualHeight - 12, false) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);

				if (page == 0) {
					mc.displayGuiScreen(new GuiManualIndex(storedPage));
				} else {
					page--;
					GuiManualChapter.this.updateButtons();
				}
			}
		});
		buttons.add(this.goHome = new GuiButtonHome(2, width / 2 - 9 / 2, y + manualHeight - 11) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);

				mc.displayGuiScreen(new GuiManualIndex(0));
			}
		});

		if (page == 0) {
			changeForward.visible = chapters.size() > 12;
			changeForward.enabled = chapters.size() > 12;
		} else {
			changeForward.visible = (chapters.size() - 12 > (page * 14));
			changeForward.enabled = (chapters.size() - 12 > (page * 14));
		}

		if (page == 0) {
			for (int i = 0; i < chapters.size() && i < 12; i++) {
				buttons.add(new GuiButtonModSection(i + 3, x + 15, y + (58 + i * 14), 10, chapters.get(i).getName()) {
					@Override
					public void onClick(double mouseX, double mouseY) {
						super.onClick(mouseX, mouseY);

						mc.displayGuiScreen(new GuiManualPage(chapters.get(this.id - 3), GuiManualChapter.this));
					}
				});
			}
		} else {
			for (int i = 0; i < 14; i++) {
				if ((12 + ((page - 1) * 14 + i)) < chapters.size()) {
					buttons.add(new GuiButtonModSection(i + 3, x + 15, y + (30 + i * 14), 10, chapters.get(12 + ((page - 1) * 14 + i)).getName()) {
						@Override
						public void onClick(double mouseX, double mouseY) {
							super.onClick(mouseX, mouseY);

							mc.displayGuiScreen(new GuiManualPage(chapters.get(12 + ((page - 1) * 14 + (this.id - 3))), GuiManualChapter.this));
						}
					});
				}
			}
		}
	}

	@Override
	public void onGuiClosed() {
		activeManualPage = new GuiManualChapter(part, page, storedPage);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
