package com.grim3212.mc.pack.compat.jei.recipes.grill;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.compat.jei.drawables.DrawableBlock;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.DecorBlocks;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GrillRecipeCategory implements IRecipeCategory<GrillRecipeWrapper> {

	public static final String UID = "grimpack.grill";
	private final IDrawableStatic background;
	private final IDrawableStatic icon;
	private final IDrawableAnimated animatedGrill;
	private final String localizedName;

	private final int inputSlot = 0;
	private final int outputSlot = 6;
	private final int fuelSlot = 5;

	public GrillRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation(GrimPack.modID, "textures/gui/jei_grill.png");
		background = guiHelper.createDrawable(location, 0, 0, 124, 44);

		IDrawableStatic staticGrill = guiHelper.createDrawable(location, 124, 0, 15, 11);
		animatedGrill = guiHelper.createAnimatedDrawable(staticGrill, 200, IDrawableAnimated.StartDirection.LEFT, false);

		ItemStack stack = new ItemStack(DecorBlocks.grill);
		NBTHelper.setString(stack, "registryName", "minecraft:gold_block");
		NBTHelper.setInteger(stack, "meta", 0);
		icon = new DrawableBlock(stack, 16, 16, 0, 0, 0, 0, 0, 0);

		localizedName = I18n.format("grimpack.jei.grill");
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		animatedGrill.draw(minecraft, 63, 16);
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public String getModName() {
		return GrimPack.modName;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, GrillRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 3, 3);
		guiItemStacks.init(1, true, 23, 3);
		guiItemStacks.init(2, true, 3, 22);
		guiItemStacks.init(3, true, 23, 22);

		guiItemStacks.init(fuelSlot, false, 93, 13);
		guiItemStacks.init(outputSlot, false, 45, 14);

		guiItemStacks.set(ingredients);
	}
}