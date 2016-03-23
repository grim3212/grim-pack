package com.grim3212.mc.core.manual.gui;

import java.util.ArrayList;

import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.ModSubSection;
import com.grim3212.mc.core.manual.button.GuiButtonChangePage;
import com.grim3212.mc.core.manual.button.GuiButtonModSection;
import com.grim3212.mc.core.util.GrimLog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiSubSection extends GuiManualIndex {

	private ModSection mod;
	private int page = 0;
	private int storedPage = 0;

	private ArrayList<ModSubSection> subsections = new ArrayList<ModSubSection>();

	public GuiSubSection(ModSection mod, int storedPage) {
		this.mod = mod;
		this.storedPage = storedPage;
	}

	public GuiSubSection(ModSection mod, int page, int storedPage) {
		this.mod = mod;
		this.page = page;
		this.storedPage = storedPage;
	}

	@Override
	public void initGui() {
		activeManualPage = this;

		x = (width - manualWidth) / 2;
		y = (height - manualHeight) / 2;

		subsections.clear();
		subsections.addAll(mod.getPages());

		this.updateButtons();
	}

	@Override
	protected void drawFooter() {
		if (subsections.size() != 0) {
			int numPages = 1;

			if (subsections.size() > 12) {
				numPages = 2;
				if (subsections.size() > 26) {
					numPages = ((subsections.size() - 12) / 14) + 2;
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
		String title = mod.getModName();
		fontRendererObj.drawString(title, width / 2 - fontRendererObj.getStringWidth(title) / 2, this.y + 14, 0x0026FF, false);
		fontRendererObj.setUnicodeFlag(unicode);
	}

	@Override
	protected void drawInfo() {
		if (page == 0) {
			boolean unicode = fontRendererObj.getUnicodeFlag();
			fontRendererObj.setUnicodeFlag(true);
			fontRendererObj.drawSplitString(this.mod.getModSectionInfo(), x + 15, y + 28, 162, 0);
			fontRendererObj.setUnicodeFlag(unicode);
		}
	}

	@Override
	protected void drawImage() {
		if (subsections.size() == 0) {
			GrimLog.error(mod.getModName(), "No subsections found!");
		}
	}

	@Override
	protected void updateButtons() {
		buttonList.clear();
		buttonList.add(this.changeForward = new GuiButtonChangePage(0, x + manualWidth - 20, y + manualHeight - 12, true));
		buttonList.add(this.changeBack = new GuiButtonChangePage(1, x + 2, y + manualHeight - 12, false));

		if (page == 0) {
			changeForward.visible = subsections.size() > 12;
			changeForward.enabled = subsections.size() > 12;
		} else {
			changeForward.visible = (subsections.size() - 12 > (page * 14));
			changeForward.enabled = (subsections.size() - 12 > (page * 14));
		}

		if (page == 0) {
			for (int i = 0; i < subsections.size() && i < 12; i++) {
				buttonList.add(new GuiButtonModSection(i + 2, x + 15, y + (58 + i * 14), 10, subsections.get(i).getSubSectionName()));
			}
		} else {
			for (int i = 0; i < 14; i++) {
				if ((12 + ((page - 1) * 14 + i)) < subsections.size()) {
					buttonList.add(new GuiButtonModSection(i + 2, x + 15, y + (30 + i * 14), 10, subsections.get(12 + ((page - 1) * 14 + i)).getSubSectionName()));
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
		default:
			if (page == 0)
				mc.displayGuiScreen(new GuiSubSectionPage(subsections.get(button.id - 2), this));
			else
				mc.displayGuiScreen(new GuiSubSectionPage(subsections.get(12 + ((page - 1) * 14 + (button.id - 2))), this));
		}
	}

	@Override
	public void onGuiClosed() {
		activeManualPage = new GuiSubSection(mod, page, storedPage);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
