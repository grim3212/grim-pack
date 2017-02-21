package com.grim3212.mc.pack.core.manual.pages;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualChapter;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;
import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;
import com.grim3212.mc.pack.core.network.MessageUpdateManual;
import com.grim3212.mc.pack.core.network.PacketDispatcher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Page {

	private String pageName;
	private String title;
	private ManualChapter chapter;
	protected int relativeMouseX;
	protected int relativeMouseY;
	protected ItemStack tooltipItem = ItemStack.EMPTY;
	private boolean setupMethod = false;
	private GuiManualPage link;

	/**
	 * Basic Page constructor
	 * 
	 * @param unlocalizedName
	 *            Page unlocalized string
	 * @param setupMethod
	 *            True if you need to use some fields above after they have been
	 *            set. This includes partId, and unlocalizedPageName.
	 */
	public Page(String pageName, boolean setupMethod) {
		this.pageName = pageName;
		this.setupMethod = setupMethod;
	}

	public boolean setupMethod() {
		return setupMethod;
	}

	/**
	 * Called if setupMethod is true... Used for initialization of fields
	 */
	public void setup() {
	}

	public void setLink(GuiManualPage link) {
		this.link = link;
	}

	public GuiManualPage getLink() {
		return link;
	}

	public void setChapter(ManualChapter chapter) {
		this.chapter = chapter;
	}

	public String getInfo() {
		return I18n.format(chapter.getUnlocalizedName() + ".page." + getPageName());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPageName() {
		return pageName;
	}

	public void drawScreen(GuiManualPage gui, int mouseX, int mouseY) {
		this.drawTitle(gui);
		this.drawFooter(gui);
	}

	public void drawTitle(GuiManualPage gui) {
		FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
		boolean unicode = renderer.getUnicodeFlag();
		renderer.setUnicodeFlag(false);
		String title = gui.getChapter().getName() + " - " + this.getTitle();
		renderer.drawString(title, gui.width / 2 - renderer.getStringWidth(title) / 2, gui.getY() + 14, 0x0026FF, false);
		renderer.setUnicodeFlag(unicode);
	}

	public void drawFooter(GuiManualPage gui) {
		FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
		boolean unicode = renderer.getUnicodeFlag();
		renderer.setUnicodeFlag(true);
		if (gui.getChapter().getPages().size() != 1)
			renderer.drawString("(" + (gui.getPage() + 1) + "/" + gui.getChapter().getPages().size() + ")", gui.getX() + 166, gui.getY() + 216, 0, false);
		renderer.setUnicodeFlag(unicode);
	}

	public void renderItem(GuiManualPage gui, ItemStack item, int x, int y) {
		RenderItem render = Minecraft.getMinecraft().getRenderItem();

		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.scale(1F, 1F, 0.75F);

		GlStateManager.enableRescaleNormal();
		GlStateManager.enableDepth();

		render.renderItemAndEffectIntoGUI(item, x, y);
		render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRendererObj, item, x, y, (String) null);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();

		if (relativeMouseX >= x && relativeMouseY >= y && relativeMouseX <= x + 16 && relativeMouseY <= y + 16) {
			this.tooltipItem = item;
		}

		GlStateManager.disableLighting();
	}

	public void renderItemCutWild(GuiManualPage gui, ItemStack item, int x, int y) {
		if (!item.isEmpty()) {
			if (item.getItemDamage() == OreDictionary.WILDCARD_VALUE)
				item.setItemDamage(0);

			this.renderItem(gui, item, x, y);
		}
	}

	public void updateScreen() {
	}

	public void addButtons(GuiManualPage gui, List<GuiButton> buttonList) {

	}

	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0) {
			if (!tooltipItem.isEmpty()) {
				if (tooltipItem.getItem() instanceof IManualItem) {
					Page page = ((IManualItem) tooltipItem.getItem()).getPage(tooltipItem);
					System.out.println(page.pageName);
					System.out.println(page.getLink().getChapter());
					System.out.println(page.getLink().getChapter().getPartId());
					Minecraft.getMinecraft().displayGuiScreen(page.getLink().copySelf());
				}
			}
		}
	}

	private void updatePage(@Nullable Page page) {
	}
}
