package com.grim3212.mc.pack.core.manual.gui;

import java.io.IOException;

import com.grim3212.mc.pack.core.manual.ManualChapter;
import com.grim3212.mc.pack.core.manual.button.GuiButtonChangePage;
import com.grim3212.mc.pack.core.manual.button.GuiButtonHome;
import com.grim3212.mc.pack.core.manual.button.GuiButtonPause;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;

import net.minecraft.client.gui.GuiButton;

public class GuiManualPage extends GuiManualIndex {

	private int page = 0;
	private ManualChapter chapter;
	private GuiManualChapter chapterGui;
	private GuiButtonPause pauseButton;
	private boolean isPaused = false;

	public GuiManualPage(ManualChapter chapter, GuiManualChapter chapterGui) {
		this.chapter = chapter;
		this.chapterGui = chapterGui;
	}

	public GuiManualPage(ManualChapter chapter, GuiManualChapter chapterGui, int page) {
		this.chapter = chapter;
		this.chapterGui = chapterGui;
		this.page = page;
	}

	public GuiManualPage copySelf() {
		return new GuiManualPage(this.chapter, new GuiManualChapter(this.chapterGui), this.page);
	}

	public GuiManualPage copy(GuiManualPage page) {
		return new GuiManualPage(page.chapter, new GuiManualChapter(page.chapterGui), page.page);
	}

	public int getManualWidth() {
		return this.manualWidth;
	}

	public int getManualHeight() {
		return this.manualHeight;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.setX((width - manualWidth) / 2);
		this.setY((height - manualHeight) / 2);

		this.updateButtons();
	}

	public void updateButtons() {
		buttonList.clear();
		buttonList.add(changeForward = new GuiButtonChangePage(0, x + manualWidth - 20, y + manualHeight - 12, true));
		buttonList.add(changeBack = new GuiButtonChangePage(1, x + 2, y + manualHeight - 12, false));
		buttonList.add(goHome = new GuiButtonHome(2, width / 2 - 9 / 2, y + manualHeight - 11));
		buttonList.add(pauseButton = new GuiButtonPause(3, 0, 0));

		changeForward.visible = chapter.getPages().size() > page + 1;
		changeForward.enabled = chapter.getPages().size() > page + 1;

		pauseButton.visible = false;
		pauseButton.enabled = false;

		if (chapter.getPages().get(this.page) instanceof PageCrafting) {
			pauseButton.xPosition = this.getX() + 112;
			pauseButton.yPosition = this.getY() + 165;
			PageCrafting page = (PageCrafting) chapter.getPages().get(this.page);
			pauseButton.visible = page.isArray();
			pauseButton.enabled = page.isArray();
		} else if (chapter.getPages().get(this.page) instanceof PageFurnace) {
			pauseButton.xPosition = this.getX() + 85;
			pauseButton.yPosition = this.getY() + 154;
			PageFurnace page = (PageFurnace) chapter.getPages().get(this.page);
			pauseButton.visible = page.isArray();
			pauseButton.enabled = page.isArray();
		}

		chapter.getPages().get(this.page).addButtons(this, buttonList);
	}

	@Override
	protected void drawFooter() {
	}

	@Override
	protected void drawTitle() {
	}

	@Override
	protected void drawInfo() {
	}

	@Override
	protected void drawImage() {
	}

	@Override
	public void updateScreen() {
		if (!isPaused) {
			Page page = chapter.getPages().get(this.page);
			page.updateScreen();
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);

		Page page = chapter.getPages().get(this.page);
		page.drawScreen(this, i, j);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0:
			page++;
			this.updateButtons();
			isPaused = false;
			pauseButton.setIsPaused(false);
			break;
		case 1:
			if (page == 0) {
				mc.displayGuiScreen(chapterGui);
			} else {
				page--;
				this.updateButtons();
				isPaused = false;
				pauseButton.setIsPaused(false);
			}
			break;
		case 2:
			mc.displayGuiScreen(new GuiManualIndex(0));
			break;
		case 3:
			if (!isPaused) {
				isPaused = true;
				pauseButton.setIsPaused(true);
			} else {
				isPaused = false;
				pauseButton.setIsPaused(false);
			}
			break;
		default:
			break;
		}
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public ManualChapter getChapter() {
		return chapter;
	}

	public void setSubsection(ManualChapter chapter) {
		this.chapter = chapter;
	}

	@Override
	public void onGuiClosed() {
		activeManualPage = this;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		Page page = chapter.getPages().get(this.page);
		page.handleMouseClick(mouseX, mouseY, mouseButton);
	}
}