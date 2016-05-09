package com.grim3212.mc.core.manual.pages;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.core.manual.gui.GuiSubSectionPage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Page {

	private String pageName;
	private String modid;
	private String unlocalizedPageName = "";
	protected int relativeMouseX;
	protected int relativeMouseY;
	protected ItemStack tooltipItem;
	private boolean setupMethod = false;

	/**
	 * Basic Page constructor
	 * 
	 * @param pageName
	 *            Page unlocalized string
	 * @param setupMethod
	 *            True if you need to use some fields above after they have been
	 *            set. This includes modid, and unlocalizedPageName.
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

	public void setModid(String modid) {
		this.modid = modid;
	}

	public String getModid() {
		return modid;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getUnlocalizedPageName() {
		return unlocalizedPageName;
	}

	public void setLocalizedPageName(String unlocalizedPageName) {
		this.unlocalizedPageName = unlocalizedPageName;
	}

	public void drawScreen(GuiSubSectionPage gui, int mouseX, int mouseY) {
		this.drawTitle(gui);
		this.drawFooter(gui);
	}

	public void drawTitle(GuiSubSectionPage gui) {
		FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
		boolean unicode = renderer.getUnicodeFlag();
		renderer.setUnicodeFlag(false);
		String title = gui.getSubsection().getSubSectionName() + " - " + gui.getSubsection().getPages().get(gui.getPage()).getUnlocalizedPageName();
		renderer.drawString(title, gui.width / 2 - renderer.getStringWidth(title) / 2, gui.getY() + 14, 0x0026FF, false);
		renderer.setUnicodeFlag(unicode);
	}

	public void drawFooter(GuiSubSectionPage gui) {
		FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
		boolean unicode = renderer.getUnicodeFlag();
		renderer.setUnicodeFlag(true);
		if (gui.getSubsection().getPages().size() != 1)
			renderer.drawString("(" + (gui.getPage() + 1) + "/" + gui.getSubsection().getPages().size() + ")", gui.getX() + 166, gui.getY() + 216, 0, false);
		renderer.setUnicodeFlag(unicode);
	}

	public void renderItem(GuiSubSectionPage gui, ItemStack item, int x, int y) {
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

	public void renderItemCutWild(GuiSubSectionPage gui, ItemStack item, int x, int y) {
		if (item != null) {
			if (item.getItemDamage() == OreDictionary.WILDCARD_VALUE)
				item.setItemDamage(0);

			this.renderItem(gui, item, x, y);
		}
	}

	public void updateScreen() {
	}
}
