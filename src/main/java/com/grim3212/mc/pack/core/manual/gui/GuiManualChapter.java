package com.grim3212.mc.pack.core.manual.gui;

import java.util.ArrayList;

import com.grim3212.mc.pack.core.manual.ManualChapter;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.button.GuiButtonChangePage;
import com.grim3212.mc.pack.core.manual.button.GuiButtonHome;
import com.grim3212.mc.pack.core.manual.button.GuiButtonModSection;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
	public void init() {
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

			if (numPages != 1)
				font.drawString("(" + (this.getPage() + 1) + "/" + numPages + ")", (int) ((this.x + 166) / Page.SCALE_FACTOR), (int) ((this.y + 216) / Page.SCALE_FACTOR), 0);
		}
	}

	@Override
	protected void drawTitle() {
		String title = part.getPartName();
		font.drawString(title, width / 2 - font.getStringWidth(title) / 2, this.y + 14, 0x0026FF);
	}

	@Override
	protected void drawInfo() {
		if (page == 0) {
			font.drawSplitString(this.part.getPartInfo(), (int) ((x + 15) / Page.SCALE_FACTOR), (int) ((y + 25) / Page.SCALE_FACTOR), 210, 0);
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
		this.buttons.clear();
		this.children.clear();

		this.addButton(this.changeForward = new GuiButtonChangePage(x + manualWidth - 20, y + manualHeight - 12, true, b -> {
			page++;
			GuiManualChapter.this.updateButtons();
		}));

		this.addButton(this.changeBack = new GuiButtonChangePage(x + 2, y + manualHeight - 12, false, b -> {
			if (page == 0) {
				minecraft.displayGuiScreen(new GuiManualIndex(storedPage));
			} else {
				page--;
				GuiManualChapter.this.updateButtons();
			}
		}));
		this.addButton(this.goHome = new GuiButtonHome(width / 2 - 9 / 2, y + manualHeight - 11, b -> {
			minecraft.displayGuiScreen(new GuiManualIndex(0));
		}));

		if (page == 0) {
			changeForward.visible = chapters.size() > 12;
			changeForward.active = chapters.size() > 12;
		} else {
			changeForward.visible = (chapters.size() - 12 > (page * 14));
			changeForward.active = (chapters.size() - 12 > (page * 14));
		}

		if (page == 0) {
			for (int i = 0; i < chapters.size() && i < 12; i++) {
				this.addButton(new GuiButtonModSection(i, x + 15, y + (58 + i * 14), 10, chapters.get(i).getName(), b -> {
					minecraft.displayGuiScreen(new GuiManualPage(chapters.get(((GuiButtonModSection) b).getChapterId()), GuiManualChapter.this));
				}));
			}
		} else {
			for (int i = 0; i < 14; i++) {
				if ((12 + ((page - 1) * 14 + i)) < chapters.size()) {
					this.addButton(new GuiButtonModSection(12 + ((page - 1) * 14) + i, x + 15, y + (30 + i * 14), 10, chapters.get(12 + ((page - 1) * 14 + i)).getName(), b -> {
						minecraft.displayGuiScreen(new GuiManualPage(chapters.get(((GuiButtonModSection) b).getChapterId()), GuiManualChapter.this));
					}));
				}
			}
		}
	}

	@Override
	public void onClose() {
		activeManualPage = new GuiManualChapter(part, page, storedPage);
		this.minecraft.displayGuiScreen((Screen) null);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
