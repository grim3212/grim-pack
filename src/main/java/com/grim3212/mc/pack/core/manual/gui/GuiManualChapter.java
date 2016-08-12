package com.grim3212.mc.pack.core.manual.gui;

import java.util.ArrayList;

import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualChapter;
import com.grim3212.mc.pack.core.manual.button.GuiButtonChangePage;
import com.grim3212.mc.pack.core.manual.button.GuiButtonHome;
import com.grim3212.mc.pack.core.manual.button.GuiButtonModSection;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

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

			FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
			boolean unicode = renderer.getUnicodeFlag();
			renderer.setUnicodeFlag(true);
			if (numPages != 1)
				renderer.drawString("(" + (this.getPage() + 1) + "/" + numPages + ")", this.x + 166, this.y + 216, 0, false);
			renderer.setUnicodeFlag(unicode);
		}
	}

	@Override
	protected void drawTitle() {
		boolean unicode = fontRendererObj.getUnicodeFlag();
		fontRendererObj.setUnicodeFlag(false);
		String title = part.getPartName();
		fontRendererObj.drawString(title, width / 2 - fontRendererObj.getStringWidth(title) / 2, this.y + 14, 0x0026FF, false);
		fontRendererObj.setUnicodeFlag(unicode);
	}

	@Override
	protected void drawInfo() {
		if (page == 0) {
			boolean unicode = fontRendererObj.getUnicodeFlag();
			fontRendererObj.setUnicodeFlag(true);
			fontRendererObj.drawSplitString(this.part.getPartInfo(), x + 15, y + 28, 162, 0);
			fontRendererObj.setUnicodeFlag(unicode);
		}
	}

	@Override
	protected void drawImage() {
		if (chapters.size() == 0) {
			GrimLog.error(part.getPartName(), "No subsections found!");
		}
	}

	@Override
	protected void updateButtons() {
		buttonList.clear();
		buttonList.add(this.changeForward = new GuiButtonChangePage(0, x + manualWidth - 20, y + manualHeight - 12, true));
		buttonList.add(this.changeBack = new GuiButtonChangePage(1, x + 2, y + manualHeight - 12, false));
		buttonList.add(this.goHome = new GuiButtonHome(2, width / 2 - 9 / 2, y + manualHeight - 11));

		if (page == 0) {
			changeForward.visible = chapters.size() > 12;
			changeForward.enabled = chapters.size() > 12;
		} else {
			changeForward.visible = (chapters.size() - 12 > (page * 14));
			changeForward.enabled = (chapters.size() - 12 > (page * 14));
		}

		if (page == 0) {
			for (int i = 0; i < chapters.size() && i < 12; i++) {
				buttonList.add(new GuiButtonModSection(i + 3, x + 15, y + (58 + i * 14), 10, chapters.get(i).getName()));
			}
		} else {
			for (int i = 0; i < 14; i++) {
				if ((12 + ((page - 1) * 14 + i)) < chapters.size()) {
					buttonList.add(new GuiButtonModSection(i + 3, x + 15, y + (30 + i * 14), 10, chapters.get(12 + ((page - 1) * 14 + i)).getName()));
				}
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0:
			page++;
			this.updateButtons();
			break;
		case 1:
			if (page == 0) {
				mc.displayGuiScreen(new GuiManualIndex(this.storedPage));
			} else {
				page--;
				this.updateButtons();
			}
			break;

		case 2:
			mc.displayGuiScreen(new GuiManualIndex(0));
			break;
		default:
			if (page == 0)
				mc.displayGuiScreen(new GuiManualPage(chapters.get(button.id - 3), this));
			else
				mc.displayGuiScreen(new GuiManualPage(chapters.get(12 + ((page - 1) * 14 + (button.id - 3))), this));
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
