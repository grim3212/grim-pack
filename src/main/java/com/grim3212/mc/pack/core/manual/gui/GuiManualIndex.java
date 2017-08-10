package com.grim3212.mc.pack.core.manual.gui;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.button.GuiButtonChangePage;
import com.grim3212.mc.pack.core.manual.button.GuiButtonHistory;
import com.grim3212.mc.pack.core.manual.button.GuiButtonHome;
import com.grim3212.mc.pack.core.manual.button.GuiButtonModSection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
	public void drawScreen(int i, int j, float f) {
		this.drawDefaultBackground();
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(x, y, 0, 0, manualWidth, manualHeight);

		this.drawTitle();
		this.drawInfo();
		this.drawImage();
		this.drawFooter();
		super.drawScreen(i, j, f);
	}

	protected void drawFooter() {
		float numPages = 1;

		if (ManualRegistry.getLoadedMods().size() > 12) {
			numPages = (float) (((ManualRegistry.getLoadedMods().size() - 12.0) / 14.0) + 1.0);
		}

		FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
		boolean unicode = renderer.getUnicodeFlag();
		renderer.setUnicodeFlag(true);
		if (numPages != 1)
			renderer.drawString("(" + (this.getPage() + 1) + "/" + (int) Math.ceil(numPages) + ")", this.x + 166, this.y + 216, 0, false);
		renderer.setUnicodeFlag(unicode);
	}

	protected void drawTitle() {
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(false);
		String title = I18n.format("grimpack.manual.title");
		fontRenderer.drawString(title, width / 2 - fontRenderer.getStringWidth(title) / 2, this.y + 14, 0x0026FF, false);
		fontRenderer.setUnicodeFlag(unicode);
	}

	protected void drawInfo() {
		if (page == 0) {
			boolean unicode = fontRenderer.getUnicodeFlag();
			fontRenderer.setUnicodeFlag(true);
			fontRenderer.drawSplitString(I18n.format("grimpack.manual.header"), x + 15, y + 28, 162, 0);
			fontRenderer.setUnicodeFlag(unicode);
		}
	}

	protected void drawImage() {
	}

	/**
	 * Updates all buttons on page including forward, back, and mod buttons
	 */
	public void updateButtons() {
		buttonList.clear();
		buttonList.add(changeForward = new GuiButtonChangePage(0, x + manualWidth - 20, y + manualHeight - 12, true));
		buttonList.add(changeBack = new GuiButtonChangePage(1, x + 2, y + manualHeight - 12, false));
		buttonList.add(goHome = new GuiButtonHome(2, width / 2 - 9 / 2, y + manualHeight - 11));

		if (page == 0) {
			changeForward.visible = ManualRegistry.getLoadedMods().size() > 12;
			changeForward.enabled = ManualRegistry.getLoadedMods().size() > 12;
			changeBack.visible = false;
			changeBack.enabled = false;
			goHome.visible = false;
			goHome.enabled = false;
		} else {
			changeForward.visible = (ManualRegistry.getLoadedMods().size() - 12 > (page * 14));
			changeForward.enabled = (ManualRegistry.getLoadedMods().size() - 12 > (page * 14));
			goHome.visible = true;
			goHome.enabled = true;
		}

		if (page == 0) {
			for (int i = 0; i < ManualRegistry.getLoadedMods().size() && i < 12; i++) {
				buttonList.add(new GuiButtonModSection(i + 3, x + 15, y + (58 + i * 14), 10, ManualRegistry.getLoadedMods().get(i).getPartName()));
			}
		} else {
			for (int i = 0; i < 14; i++) {
				if ((12 + ((page - 1) * 14 + i)) < ManualRegistry.getLoadedMods().size())
					buttonList.add(new GuiButtonModSection(i + 3, x + 15, y + (30 + i * 14), 10, ManualRegistry.getLoadedMods().get(12 + ((page - 1) * 14 + i)).getPartName()));
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
			page--;
			this.updateButtons();
			break;
		case 2:
			this.updateButtons();
			break;
		default:
			if (page == 0) {
				ManualPart part = ManualRegistry.getLoadedMods().get(button.id - 3);
				mc.displayGuiScreen(new GuiManualChapter(part, this.page));
			} else {
				ManualPart part = ManualRegistry.getLoadedMods().get(12 + ((page - 1) * 14 + (button.id - 3)));
				mc.displayGuiScreen(new GuiManualChapter(part, this.page));
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