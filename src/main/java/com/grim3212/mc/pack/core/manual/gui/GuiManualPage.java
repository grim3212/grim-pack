package com.grim3212.mc.pack.core.manual.gui;

import com.grim3212.mc.pack.core.manual.ManualChapter;
import com.grim3212.mc.pack.core.manual.button.GuiButtonChangePage;
import com.grim3212.mc.pack.core.manual.button.GuiButtonHome;
import com.grim3212.mc.pack.core.manual.button.GuiButtonPause;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;

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
		this.buttons.clear();
		this.children.clear();
		
		if (chapter.getPages().size() > page + 1) {
			this.addButton(changeForward = new GuiButtonChangePage(0, x + manualWidth - 20, y + manualHeight - 12, true) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);

					page++;
					GuiManualPage.this.updateButtons();
					isPaused = false;
					pauseButton.setIsPaused(false);
				}
			});
		}
		this.addButton(changeBack = new GuiButtonChangePage(1, x + 2, y + manualHeight - 12, false) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);

				if (page == 0) {
					mc.displayGuiScreen(chapterGui);
				} else {
					page--;
					GuiManualPage.this.updateButtons();
					isPaused = false;
					pauseButton.setIsPaused(false);
				}
			}
		});
		this.addButton(goHome = new GuiButtonHome(2, width / 2 - 9 / 2, y + manualHeight - 11) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);

				mc.displayGuiScreen(new GuiManualIndex(0));
			}
		});
		this.addButton(pauseButton = new GuiButtonPause(3, 0, 0) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);

				if (!isPaused) {
					isPaused = true;
					pauseButton.setIsPaused(true);
				} else {
					isPaused = false;
					pauseButton.setIsPaused(false);
				}
			}
		});

		pauseButton.visible = false;
		pauseButton.enabled = false;

		if (chapter.getPages().get(this.page) instanceof PageCrafting) {
			pauseButton.x = this.getX() + 112;
			pauseButton.y = this.getY() + 165;
			PageCrafting page = (PageCrafting) chapter.getPages().get(this.page);
			pauseButton.visible = page.isArray();
			pauseButton.enabled = page.isArray();
		} else if (chapter.getPages().get(this.page) instanceof PageFurnace) {
			pauseButton.x = this.getX() + 85;
			pauseButton.y = this.getY() + 154;
			PageFurnace page = (PageFurnace) chapter.getPages().get(this.page);
			pauseButton.visible = page.isArray();
			pauseButton.enabled = page.isArray();
		}

		chapter.getPages().get(this.page).addButtons(this);
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
	public void tick() {
		if (!isPaused) {
			Page page = chapter.getPages().get(this.page);
			page.updateScreen();
		}
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		super.render(mouseX, mouseY, partialTicks);

		Page page = chapter.getPages().get(this.page);
		page.drawScreen(this, mouseX, mouseY);
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
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		chapter.getPages().get(this.page).handleMouseClick(mouseX, mouseY, mouseButton);
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}