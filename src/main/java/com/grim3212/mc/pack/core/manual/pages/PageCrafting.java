package com.grim3212.mc.pack.core.manual.pages;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.TooltipHelper;
import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;
import com.grim3212.mc.pack.core.util.GrimLog;
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
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class PageCrafting extends Page {

	private ResourceLocation craftingOverlay = new ResourceLocation(GrimPack.modID, "textures/gui/crafting_overlay.png");

	private int recipeShown = 0;
	private int update = 0;
	private int updateTime = 1;
	private final List<ResourceLocation> outputRecipes;
	private boolean isArray = false;
	private boolean isShapeless = false;

	public PageCrafting(String pageName, ResourceLocation output) {
		super(pageName, false);
		this.outputRecipes = ImmutableList.of(output);
	}

	public PageCrafting(String pageName, List<ResourceLocation> outputs, int updateTime) {
		super(pageName, false);
		this.outputRecipes = outputs;
		this.updateTime = updateTime;
		this.isArray = outputRecipes.size() > 1;
	}

	public PageCrafting(String pageName, ItemStack output) {
		super(pageName, false);
		this.outputRecipes = ImmutableList.of(output.getItem().getRegistryName());
	}

	public PageCrafting(String pageName, int updateTime, ItemStack... outputs) {
		super(pageName, false);
		ImmutableList.Builder<ResourceLocation> b = ImmutableList.builder();
		for (ItemStack stack : outputs)
			b.add(stack.getItem().getRegistryName());
		this.outputRecipes = b.build();

		this.updateTime = updateTime;
		this.isArray = outputs.length > 1;
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

		tooltipItem = ItemStack.EMPTY;

		this.renderRecipe(gui, outputRecipes);

		if (!tooltipItem.isEmpty()) {
			TooltipHelper.renderToolTip(tooltipItem, mouseX, mouseY);
		}
	}

	public void renderRecipe(GuiManualPage gui, List<ResourceLocation> output) {
		ResourceLocation loc = output.get(recipeShown);

		IRecipe recipe = ForgeRegistries.RECIPES.getValue(loc);

		if (recipe != null) {
			ItemStack outstack = recipe.getRecipeOutput();

			if (outstack != ItemStack.EMPTY && outstack != null) {
				this.drawIngredientList(gui, recipe);

				if (isShapeless) {
					GlStateManager.pushMatrix();
					GlStateManager.enableBlend();
					TextureManager render = Minecraft.getMinecraft().renderEngine;
					render.bindTexture(craftingOverlay);

					((GuiScreen) gui).drawTexturedModalRect(gui.getX() + 133, gui.getY() + 144, 0, 27, 36, 36);
					GlStateManager.disableBlend();
					GlStateManager.popMatrix();
				}

				if (isShapeless)
					NBTHelper.setString(outstack, "customTooltip", I18n.format("grimpack.manual.shapeless"));
				this.renderItem(gui, outstack, gui.getX() + 143, gui.getY() + 154);

				FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
				renderer.drawString(outstack.getDisplayName(), (gui.getManualWidth() / 2 - renderer.getStringWidth(outstack.getDisplayName()) / 2) + gui.getX(), gui.getY() + 210, Color.BLACK.getRGB(), false);
			} else {
				String missing = I18n.format("grimpack.manual.missing");

				FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
				renderer.drawString(missing, (gui.getManualWidth() / 2 - renderer.getStringWidth(missing) / 2) + gui.getX(), gui.getY() + 204, Color.RED.getRGB(), false);
				renderer.drawString("'" + loc.toString() + "'", (gui.getManualWidth() / 2 - renderer.getStringWidth(loc.toString()) / 2) + gui.getX(), gui.getY() + 215, Color.BLACK.getRGB(), false);
			}
		} else {
			String missing = I18n.format("grimpack.manual.missing");

			FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
			renderer.drawString(missing, (gui.getManualWidth() / 2 - renderer.getStringWidth(missing) / 2) + gui.getX(), gui.getY() + 204, Color.RED.getRGB(), false);
			renderer.drawString("'" + loc.toString() + "'", (gui.getManualWidth() / 2 - renderer.getStringWidth(loc.toString()) / 2) + gui.getX(), gui.getY() + 215, Color.BLACK.getRGB(), false);
		}
	}

	public void drawOreDictionaryItem(GuiManualPage gui, Ingredient item, int x, int y) {
		if (item instanceof OreIngredient) {
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			TextureManager render = Minecraft.getMinecraft().renderEngine;
			render.bindTexture(craftingOverlay);

			((GuiScreen) gui).drawTexturedModalRect(x - 6, y - 6, 0, 0, 26, 26);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();

			this.renderItemCutWild(gui, NBTHelper.setStringItemStack(item.getMatchingStacks()[0], "customTooltip", I18n.format("grimpack.manual.oredictionary") + " : " + RecipeHelper.getOreDict(item.getMatchingStacks())), x - 1, y - 1);
		} else {
			ItemStack[] stacks = item.getMatchingStacks();
			if (stacks.length > 0)
				this.renderItemCutWild(gui, stacks[0], x - 1, y - 1);
			else
				GrimLog.error(GrimPack.modName, "Failed rendering ingredient " + item + " at x:" + (x - 1) + ", y:" + (y - 1));
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
					Ingredient item = shaped.recipeItems.get(x + y * shaped.recipeWidth);
					if (item != Ingredient.EMPTY) {
						drawOreDictionaryItem(gui, item, gui.getX() + x * 29 + 30 - x * 2, gui.getY() + y * 27 + 128);
					}
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
			int width = shapedOre.getWidth();
			int height = shapedOre.getHeight();

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					Ingredient item = shapedOre.getIngredients().get(x + y * width);
					if (item != Ingredient.EMPTY) {
						drawOreDictionaryItem(gui, item, gui.getX() + x * 29 + 30 - x * 2, gui.getY() + y * 27 + 128);
					}
				}
			}

			isShapeless = false;
		} else if (recipe instanceof ShapelessOreRecipe) {
			ShapelessOreRecipe shapelessOre = (ShapelessOreRecipe) recipe;

			for (int i = 0; i < shapelessOre.getIngredients().size(); i++) {
				if (i < 3)
					drawOreDictionaryItem(gui, shapelessOre.getIngredients().get(i), gui.getX() + i * 29 + 30 - i * 2, gui.getY() + 128);
				else if (i < 6)
					drawOreDictionaryItem(gui, shapelessOre.getIngredients().get(i), gui.getX() + (i - 3) * 29 + 30 - (i - 3) * 2, gui.getY() + 155);
				else
					drawOreDictionaryItem(gui, shapelessOre.getIngredients().get(i), gui.getX() + (i - 6) * 29 + 30 - (i - 6) * 2, gui.getY() + 182);
			}

			isShapeless = true;
		}
	}
}