package com.grim3212.mc.tools.items;

import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.tools.GrimTools;
import com.grim3212.mc.tools.util.BackpackRecipeHandler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ToolsItems implements IPartItems {

	public static Item backpack;
	public static Item portable_workbench;

	@Override
	public void initItems() {
		backpack = new ItemBackpack().setUnlocalizedName("backpack").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		portable_workbench = new ItemPortableWorkbench().setUnlocalizedName("portable_workbench").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());

		GameRegistry.registerItem(backpack, "backpack");
		GameRegistry.registerItem(portable_workbench, "portable_workbench");
	}

	@Override
	public void addRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(backpack, 1), new Object[] { "LLS", "LIS", "LLL", 'L', Items.leather, 'S', Items.string, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(portable_workbench, 1), new Object[] { "III", "IWI", "III", 'W', Blocks.crafting_table, 'I', "ingotIron" }));

		CraftingManager.getInstance().getRecipeList().add(new BackpackRecipeHandler());
		RecipeSorter.register("Backpack_Recipes", BackpackRecipeHandler.class, Category.SHAPELESS, "after:grim3212core");
	}

}
