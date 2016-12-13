package com.grim3212.mc.pack.core.manual.pages;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.TooltipHelper;
import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.core.util.RecipeHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class PageCrafting extends Page {

	private ResourceLocation craftingOverlay = new ResourceLocation(GrimPack.modID, "textures/gui/crafting_overlay.png");

	private int recipeShown = 0;
	private int update = 0;
	private int updateTime = 1;
	private List<IRecipe> outputRecipes = new ArrayList<IRecipe>();
	private boolean isArray = false;
	private boolean isShapeless = false;

	public PageCrafting(String pageName, IRecipe output) {
		super(pageName, false);
		this.outputRecipes.add(output);
	}

	public PageCrafting(String pageName, List<IRecipe> outputs, int updateTime) {
		super(pageName, false);
		for (int i = 0; i < outputs.size(); i++) {
			if (outputs.get(i) != null)
				this.outputRecipes.add(outputs.get(i));
		}
		this.updateTime = updateTime;
		this.isArray = outputs.size() > 1;
	}

	public PageCrafting(String pageName, ItemStack output) {
		super(pageName, false);
		this.outputRecipes.add(RecipeHelper.getQuickIRecipeForItemStack(output));
	}

	public boolean isArray() {
		return isArray;
	}

	@Override
	public void addButtons(GuiManualPage gui, List<GuiButton> buttonList) {
	}

	@Override
	public void drawScreen(GuiManualPage gui, int mouseX, int mouseY) {
		super.drawScreen(gui, mouseX, mouseY);

		relativeMouseX = mouseX;
		relativeMouseY = mouseY;

		int x = gui.getX() + 15;
		int y = gui.getY() + 28;
		PageInfo.drawText(x, y, this.getInfo());

		TextureManager render = Minecraft.getMinecraft().renderEngine;
		render.bindTexture(craftingOverlay);

		GL11.glColor4f(1F, 1F, 1F, 1F);
		((GuiScreen) gui).drawTexturedModalRect(gui.getX() + 21, gui.getY() + 120, 21, 120, 147, 85);

		this.renderRecipe(gui, outputRecipes);

		if (!tooltipItem.isEmpty()) {
			TooltipHelper.renderToolTip(tooltipItem, mouseX, mouseY);
		}

		tooltipItem = ItemStack.EMPTY;
	}

	public void renderRecipe(GuiManualPage gui, List<IRecipe> output) {
		this.drawIngredientList(gui, output.get(recipeShown));

		if (isShapeless) {
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			TextureManager render = Minecraft.getMinecraft().renderEngine;
			render.bindTexture(craftingOverlay);

			((GuiScreen) gui).drawTexturedModalRect(gui.getX() + 133, gui.getY() + 144, 0, 27, 36, 36);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}

		ItemStack outstack = output.get(recipeShown).getRecipeOutput();
		if (isShapeless)
			NBTHelper.setString(outstack, "customTooltip", I18n.format("grimpack.manual.shapeless"));
		this.renderItem(gui, outstack, gui.getX() + 143, gui.getY() + 154);

		FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;
		renderer.drawString(outstack.getDisplayName(), (gui.getManualWidth() / 2 - renderer.getStringWidth(outstack.getDisplayName()) / 2) + gui.getX(), gui.getY() + 210, Color.BLACK.getRGB(), false);
	}

	@SuppressWarnings("unchecked")
	public void drawOreDictionaryItem(GuiManualPage gui, Object item, int x, int y) {
		if (item instanceof ItemStack) {
			this.renderItemCutWild(gui, (ItemStack) item, x - 1, y - 1);
		} else if (item instanceof List<?>) {
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			TextureManager render = Minecraft.getMinecraft().renderEngine;
			render.bindTexture(craftingOverlay);

			((GuiScreen) gui).drawTexturedModalRect(x - 6, y - 6, 0, 0, 26, 26);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();

			this.renderItemCutWild(gui, NBTHelper.setStringItemStack(((List<ItemStack>) item).get(0), "customTooltip", I18n.format("grimpack.manual.oredictionary") + " : " + RecipeHelper.getOreDict((List<ItemStack>) item)), x - 1, y - 1);
		}
	}

	@Override
	public void updateScreen() {
		if (update % this.updateTime == 0) {
			recipeShown++;

			if (recipeShown == outputRecipes.size())
				recipeShown = 0;
		}
		++update;
	}

	public void drawIngredientList(GuiManualPage gui, IRecipe recipe) {
		// vanilla recipe classes
		if (recipe instanceof ShapedRecipes) {
			ShapedRecipes shaped = (ShapedRecipes) recipe;

			for (int y = 0; y < shaped.recipeHeight; y++) {
				for (int x = 0; x < shaped.recipeWidth; x++) {
					drawOreDictionaryItem(gui, shaped.recipeItems[x + y * shaped.recipeWidth], gui.getX() + x * 29 + 30 - x * 2, gui.getY() + y * 27 + 128);
				}
			}

			isShapeless = false;
		} else if (recipe instanceof ShapelessRecipes) {
			ShapelessRecipes shapeless = (ShapelessRecipes) recipe;

			for (int i = 0; i < shapeless.recipeItems.size(); i++) {
				if (i < 3)
					drawOreDictionaryItem(gui, shapeless.recipeItems.get(i), gui.getX() + i * 29 + 30 - i * 2, gui.getY() + 128);
				else if (i < 6)
					drawOreDictionaryItem(gui, shapeless.recipeItems.get(i), gui.getX() + (i - 3) * 29 + 30 - (i - 3) * 2, gui.getY() + 155);
				else
					drawOreDictionaryItem(gui, shapeless.recipeItems.get(i), gui.getX() + (i - 6) * 29 + 30 - (i - 6) * 2, gui.getY() + 182);
			}

			isShapeless = true;
		}
		// ore dictionary classes
		else if (recipe instanceof ShapedOreRecipe) {
			ShapedOreRecipe shapedOre = (ShapedOreRecipe) recipe;
			int width = ReflectionHelper.<Integer, ShapedOreRecipe>getPrivateValue(ShapedOreRecipe.class, shapedOre, 4);
			int height = ReflectionHelper.<Integer, ShapedOreRecipe>getPrivateValue(ShapedOreRecipe.class, shapedOre, 5);

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					Object item = shapedOre.getInput()[x + y * width];
					if (item != null) {
						drawOreDictionaryItem(gui, item, gui.getX() + x * 29 + 30 - x * 2, gui.getY() + y * 27 + 128);
					}
				}
			}

			isShapeless = false;
		} else if (recipe instanceof ShapelessOreRecipe) {
			ShapelessOreRecipe shapelessOre = (ShapelessOreRecipe) recipe;

			for (int i = 0; i < shapelessOre.getInput().size(); i++) {
				if (i < 3)
					drawOreDictionaryItem(gui, shapelessOre.getInput().get(i), gui.getX() + i * 29 + 30 - i * 2, gui.getY() + 128);
				else if (i < 6)
					drawOreDictionaryItem(gui, shapelessOre.getInput().get(i), gui.getX() + (i - 3) * 29 + 30 - (i - 3) * 2, gui.getY() + 155);
				else
					drawOreDictionaryItem(gui, shapelessOre.getInput().get(i), gui.getX() + (i - 6) * 29 + 30 - (i - 6) * 2, gui.getY() + 182);
			}

			isShapeless = true;
		}
	}

}