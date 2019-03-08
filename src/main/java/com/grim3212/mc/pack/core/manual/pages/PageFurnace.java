package com.grim3212.mc.pack.core.manual.pages;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.TooltipHelper;
import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.core.util.generator.Generator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class PageFurnace extends Page {

	private ResourceLocation furnaceOverlay = new ResourceLocation(GrimPack.modID, "textures/gui/furnace_overlay.png");

	private int recipeShown = 0;
	private int update = 0;
	private int updateTime = 1;
	private final List<ResourceLocation> recipes;
	private boolean isArray = false;

	public PageFurnace(String pageName, ResourceLocation recipe) {
		super(pageName, false);
		this.recipes = ImmutableList.of(recipe);
	}

	public PageFurnace(String pageName, List<ResourceLocation> recipes, int updateTime) {
		super(pageName, false);
		this.recipes = recipes;
		this.updateTime = updateTime;
		this.isArray = true;
	}
	
	public PageFurnace(String pageName, ItemStack output) {
		super(pageName, false);
		this.recipes = ImmutableList.of(output.getItem().getRegistryName());
	}

	public boolean isArray() {
		return this.isArray;
	}

	@Override
	public void drawScreen(GuiManualPage gui, int mouseX, int mouseY) {
		super.drawScreen(gui, mouseX, mouseY);

		Minecraft mc = Minecraft.getInstance();

		relativeMouseX = mouseX;
		relativeMouseY = mouseY;

		int x = gui.getX() + 15;
		int y = gui.getY() + 28;
		PageInfo.drawText(x, y, this.getInfo());

		TextureManager render = mc.getTextureManager();
		render.bindTexture(furnaceOverlay);

		GL11.glColor4f(1F, 1F, 1F, 1F);
		((GuiScreen) gui).drawTexturedModalRect(gui.getX(), gui.getY(), 0, 0, gui.getManualWidth(), gui.getManualHeight());

		tooltipItem = ItemStack.EMPTY;

		this.renderRecipe(gui, recipes);

		if (!tooltipItem.isEmpty()) {
			TooltipHelper.renderToolTip(tooltipItem, mouseX, mouseY);
		}
	}

	public void renderRecipe(GuiManualPage gui, List<ResourceLocation> output) {
		ResourceLocation loc = output.get(recipeShown);

		IRecipe recipe = Minecraft.getInstance().world.getRecipeManager().getRecipe(loc);

		if (recipe != null) {
			// TODO: Probably fix
			// Get the only input
			ItemStack input = recipe.getIngredients().get(0).getMatchingStacks()[0];
			ItemStack outstack = recipe.getRecipeOutput();
			if (input != ItemStack.EMPTY && outstack != ItemStack.EMPTY) {
				this.renderItem(gui, input, gui.getX() + 49, gui.getY() + 145);
				this.renderItem(gui, outstack, gui.getX() + 122, gui.getY() + 143);

				FontRenderer renderer = Minecraft.getInstance().fontRenderer;
				renderer.drawString(outstack.getDisplayName().getFormattedText(), (gui.getManualWidth() / 2 - renderer.getStringWidth(outstack.getDisplayName().getFormattedText()) / 2) + gui.getX(), gui.getY() + 210, Color.BLACK.getRGB());
			} else {
				String missing = I18n.format("grimpack.manual.missing");

				FontRenderer renderer = Minecraft.getInstance().fontRenderer;
				renderer.drawString(missing, (gui.getManualWidth() / 2 - renderer.getStringWidth(missing) / 2) + gui.getX(), gui.getY() + 204, Color.RED.getRGB());
				renderer.drawString("'" + loc.toString() + "'", (gui.getManualWidth() / 2 - renderer.getStringWidth(loc.toString()) / 2) + gui.getX(), gui.getY() + 215, Color.BLACK.getRGB());
			}
		} else {
			String missing = I18n.format("grimpack.manual.missing");

			FontRenderer renderer = Minecraft.getInstance().fontRenderer;
			renderer.drawString(missing, (gui.getManualWidth() / 2 - renderer.getStringWidth(missing) / 2) + gui.getX(), gui.getY() + 204, Color.RED.getRGB());
			renderer.drawString("'" + loc.toString() + "'", (gui.getManualWidth() / 2 - renderer.getStringWidth(loc.toString()) / 2) + gui.getX(), gui.getY() + 215, Color.BLACK.getRGB());
		}
	}

	@Override
	public void updateScreen() {
		if (update % this.updateTime == 0) {

			if (recipeShown == recipes.size())
				recipeShown = 0;
		}
		++update;
	}

	@Override
	public JsonObject deconstruct() {
		JsonObject json = super.deconstruct();

		JsonArray recipes = new JsonArray();
		// Construct array out of all inputs
		for (ResourceLocation loc : this.recipes) {
			IRecipe ir = Minecraft.getInstance().world.getRecipeManager().getRecipe(loc);
			if (ir == null) {
				GrimLog.error(Generator.GENERATOR_NAME, "Error finding recipe " + loc);
				continue;
			}

			JsonObject recipe = new JsonObject();

			if (ir instanceof FurnaceRecipe) {
				FurnaceRecipe fr = (FurnaceRecipe) ir;
				// TODO: Probably fix
				// Get the only input
				ItemStack input = fr.getIngredients().get(0).getMatchingStacks()[0];
				recipe.addProperty("id", input.getItem().getRegistryName().toString());
				recipe.addProperty("recipeType", "furnace");
				recipe.add("input", this.deconstructItem(input));
				recipe.add("output", this.deconstructItem(fr.getRecipeOutput()));
				recipe.addProperty("xp", fr.getExperience());
				recipes.add(recipe);
			} else {
				GrimLog.error(Generator.GENERATOR_NAME, "Found non-furnace recipe when deconstructing a Furnace Recipe " + loc);
				continue;
			}

		}

		// Add recipes to json
		json.add("recipes", recipes);

		return json;
	}
}