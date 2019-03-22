package com.grim3212.mc.pack.core.manual.pages;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualChapter;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.core.util.generator.Generator;
import com.grim3212.mc.pack.core.util.generator.GeneratorUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextComponent;

public abstract class Page {

	private String pageName;
	private String title;
	private ManualChapter chapter;
	private List<String> imageUrls = Lists.newArrayList();
	protected int relativeMouseX;
	protected int relativeMouseY;
	protected ItemStack tooltipItem = ItemStack.EMPTY;
	private boolean setupMethod = false;
	private GuiManualPage link;
	private String extraInfo = "";

	/**
	 * Basic Page constructor
	 * 
	 * @param unlocalizedName Page unlocalized string
	 * @param setupMethod     True if you need to use some fields above after they
	 *                        have been set. This includes partId, and
	 *                        unlocalizedPageName.
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

	public String getLinkString() {
		return ManualRegistry.getStringFromPage(this);
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

	public Page setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
		return this;
	}

	public String getExtraInfo() {
		return I18n.format(extraInfo);
	}

	public String getPageName() {
		return pageName;
	}

	public void drawScreen(GuiManualPage gui, int mouseX, int mouseY) {
		this.drawTitle(gui);
		
		GlStateManager.pushMatrix();
		GlStateManager.scalef(0.8f, 0.8f, 1.0f);
		this.drawFooter(gui);
		GlStateManager.popMatrix();
	}

	public void drawTitle(GuiManualPage gui) {
		FontRenderer renderer = Minecraft.getInstance().fontRenderer;
		String title = gui.getChapter().getName() + " - " + this.getTitle();
		renderer.drawString(title, gui.width / 2 - renderer.getStringWidth(title) / 2, gui.getY() + 14, 0x0026FF);
	}

	public void drawFooter(GuiManualPage gui) {
		FontRenderer renderer = Minecraft.getInstance().fontRenderer;
		if (gui.getChapter().getPages().size() != 1)
			renderer.drawString("(" + (gui.getPage() + 1) + "/" + gui.getChapter().getPages().size() + ")", gui.getX() + 230, gui.getY() + 272, 0);
	}

	public void renderItem(GuiManualPage gui, ItemStack item, int x, int y) {
		ItemRenderer render = Minecraft.getInstance().getItemRenderer();

		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();

		GlStateManager.scalef(1F, 1F, 0.75F);

		GlStateManager.enableRescaleNormal();
		GlStateManager.enableDepthTest();

		render.renderItemAndEffectIntoGUI(item, x, y);
		render.renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, item, x, y, (String) null);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();

		if (relativeMouseX >= x && relativeMouseY >= y && relativeMouseX <= x + 16 && relativeMouseY <= y + 16) {
			this.tooltipItem = item;
		}

		GlStateManager.disableLighting();
	}

	public void renderItemCutWild(GuiManualPage gui, ItemStack item, int x, int y) {
		if (!item.isEmpty()) {
			this.renderItem(gui, item, x, y);
		}
	}

	public void updateScreen() {
	}

	public void addButtons(GuiManualPage gui) {

	}

	public void handleMouseClick(double mouseX, double mouseY, int mouseButton) {
		if (mouseButton == 0) {
			if (!tooltipItem.isEmpty()) {
				if (tooltipItem.getItem() instanceof IManualItem) {
					Page page = ((IManualItem) tooltipItem.getItem()).getPage(tooltipItem);

					// Reset tooltip for this page...
					// Caused hours of headaches!!
					// It was so simple
					tooltipItem = ItemStack.EMPTY;
					Minecraft.getInstance().displayGuiScreen(page.getLink().copySelf());
				}
			}
		}
	}

	/**
	 * When documentation is being generated how will the page break down into Json
	 * As well as what images will be saved etc...
	 * 
	 * @return
	 */
	public JsonObject deconstruct() {
		JsonObject json = new JsonObject();
		json.addProperty("id", this.getPageName());
		json.addProperty("name", this.getTitle());
		if (this.getExtraInfo().isEmpty())
			json.addProperty("info", GeneratorUtil.changeFormatCodes(this.getInfo()));
		else
			json.addProperty("info", this.getExtraInfo());

		// Add in Image Urls
		if (this.getImageUrls() != null && this.getImageUrls().size() > 0) {
			JsonArray urls = new JsonArray();
			for (String s : this.getImageUrls())
				urls.add(s);

			json.add("imageUrls", urls);
		}

		return json;
	}

	/**
	 * Used only for documenting.
	 * 
	 * The URL can be full 'https://i.imgur.com/oiqrWcM.png' Or relative to domain
	 * 'assets/images/coolImg.png'
	 * 
	 * @param urls
	 */
	public Page addImageUrl(String urls) {
		this.imageUrls.add(urls);
		return this;
	}

	public Page addImageUrls(List<String> urls) {
		this.imageUrls.addAll(urls);
		return this;
	}

	public Page appendImageUrl(String name) {
		return this.addImageUrl("/assets/grimpack/images/" + name);
	}

	public Page appendImageUrls(List<String> names) {
		for (String name : names)
			this.appendImageUrl(name);

		return this;
	}

	protected List<String> getImageUrls() {
		return this.imageUrls;
	}

	protected JsonObject deconstructItem(ItemStack stack) {
		return this.deconstructItem(stack, -1, -1);
	}

	protected JsonObject deconstructItem(ItemStack stack, int locX, int locY) {
		JsonObject item = new JsonObject();
		item.addProperty("id", stack.getItem().getRegistryName().toString());
		item.addProperty("unloc", stack.getTranslationKey());
		item.addProperty("name", GeneratorUtil.nameToHtml(stack.getDisplayName().getFormattedText()));
		item.addProperty("amount", stack.getCount());

		if (locX != -1 && locY != -1) {
			item.addProperty("posX", locX);
			item.addProperty("posY", locY);
		}

		if (stack.hasTag()) {
			JsonObject nbt = (JsonObject) new JsonParser().parse(stack.serializeNBT().toString());
			item.add("nbt", nbt);
		}

		// Get tooltip info
		List<ITextComponent> tts = stack.getTooltip(null, ITooltipFlag.TooltipFlags.NORMAL);
		if (!tts.isEmpty() && tts.size() > 1) {
			JsonArray tooltips = new JsonArray();

			for (ITextComponent s : tts) {
				// Find reset characters and replace with just text
				tooltips.add(GeneratorUtil.nameToHtml(s.getFormattedText()));
			}

			item.add("tooltip", tooltips);
		}

		// See if the item should have a link
		if (stack.getItem() instanceof IManualItem) {
			Page page = ((IManualItem) stack.getItem()).getPage(stack);

			if (page != null) {
				item.addProperty("link", page.getLinkString());
				item.addProperty("part", page.getLink().getChapter().getPartId());
				item.addProperty("chapter", page.getLink().getChapter().getChapterId());
			}

		}

		// Add as an exportable image
		Generator.exports.add(stack.copy());

		return item;
	}

	@Nullable
	protected JsonObject deconstructItem(Ingredient item, int locX, int locY) {
		if (item == Ingredient.EMPTY) {
			JsonObject empty = new JsonObject();
			empty.addProperty("id", "empty");
			return empty;
		}

		/*
		 * if (item instanceof OreIngredient) { String oreName =
		 * RecipeHelper.getOreDict(item.getMatchingStacks()); JsonObject itemObj =
		 * this.deconstructItem(item.getMatchingStacks()[0], locX, locY);
		 * itemObj.addProperty("oreName", oreName); return itemObj; } else {
		 */
		ItemStack[] stacks = item.getMatchingStacks();
		if (stacks.length > 0) {
			return this.deconstructItem(stacks[0], locX, locY);
		} else {
			GrimLog.error(Generator.GENERATOR_NAME, "Ingredient did not have any matching stacks");
			return null;
		}
		// }
	}
}
