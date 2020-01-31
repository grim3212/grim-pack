package com.grim3212.mc.pack.core.manual.pages;

import java.awt.Color;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.TooltipHelper;
import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.core.util.generator.Generator;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
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

		RenderSystem.color4f(1f, 1f, 1f, 1f);
		((Screen) gui).blit(gui.getX() + 21, gui.getY() + 120, 21, 120, 147, 85);

		tooltipItem = ItemStack.EMPTY;

		this.renderRecipe(gui, recipes);

		if (!tooltipItem.isEmpty()) {
			TooltipHelper.renderToolTip(tooltipItem, mouseX, mouseY);
		}
	}

	public void renderRecipe(GuiManualPage gui, List<ResourceLocation> output) {
		ResourceLocation loc = output.get(recipeShown);

		Optional<? extends IRecipe<?>> recipe = Minecraft.getInstance().world.getRecipeManager().getRecipe(loc);

		if (recipe != null && recipe.isPresent()) {
			Ingredient input = recipe.get().getIngredients().get(0);

			// Check if Ingredient has a tag if so mark it
			RecipeHelper.getTag(input).<Runnable>map(tag -> () -> {
				RenderSystem.pushMatrix();
				RenderSystem.enableBlend();
				TextureManager render = Minecraft.getInstance().getTextureManager();
				render.bindTexture(furnaceOverlay);

				((Screen) gui).blit(gui.getX() + 44, gui.getY() + 139, 0, 0, 26, 26);
				RenderSystem.disableBlend();
				RenderSystem.popMatrix();
				this.renderItemCutWild(gui, NBTHelper.setStringItemStack(input.getMatchingStacks()[0], "customTooltip", I18n.format("grimpack.manual.tags") + " : " + tag), gui.getX() + 49, gui.getY() + 145);
			}).orElse(() -> this.renderItemCutWild(gui, input.getMatchingStacks()[0], gui.getX() + 49, gui.getY() + 145)).run();

			ItemStack outstack = recipe.get().getRecipeOutput();
			if (outstack != ItemStack.EMPTY) {
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
			recipeShown++;

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
			Optional<? extends IRecipe<?>> ir = Minecraft.getInstance().world.getRecipeManager().getRecipe(loc);
			if (ir == null) {
				GrimLog.error(Generator.GENERATOR_NAME, "Error finding recipe " + loc);
				continue;
			}

			JsonObject recipe = new JsonObject();

			if (ir.isPresent() && ir.get() instanceof FurnaceRecipe) {
				FurnaceRecipe fr = (FurnaceRecipe) ir.get();
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