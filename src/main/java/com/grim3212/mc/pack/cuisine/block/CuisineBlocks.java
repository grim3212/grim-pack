package com.grim3212.mc.pack.cuisine.block;

import com.grim3212.mc.pack.core.part.IPartItems;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.cuisine.GrimCuisine;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CuisineBlocks implements IPartItems {

	public static Block butter_churn;
	public static Block cheese_block;
	public static Block cheese_maker;
	public static Block cocoa_block;
	public static Block cocoa_tree_sapling;
	public static Block chocolate_bar_mould;
	public static Block chocolate_cake;
	public static Block chocolate_block;
	public static Block apple_pie;
	public static Block melon_pie;
	public static Block pumpkin_pie;
	public static Block chocolate_pie;
	public static Block pork_pie;

	@Override
	public void initItems() {
		cheese_block = (new BlockCheese()).setHardness(0.5F).setUnlocalizedName("cheese_block").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		cheese_maker = (new BlockCheeseMaker()).setHardness(2.0F).setUnlocalizedName("cheese_maker").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		butter_churn = (new BlockButterchurn()).setHardness(2.0F).setUnlocalizedName("butter_churn").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		cocoa_block = (new BlockCocoa()).setHardness(1.0F).setUnlocalizedName("cocoa_block");
		cocoa_tree_sapling = (new BlockCocoaSapling()).setHardness(0.0F).setUnlocalizedName("cocoa_tree_sapling");
		chocolate_bar_mould = (new BlockCBarMould()).setHardness(1.0F).setUnlocalizedName("chocolate_bar_mould").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_cake = (new BlockChocolateCake()).setHardness(0.5F).setUnlocalizedName("chocolate_cake").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_block = (new BlockChocolateBlock()).setHardness(0.5F).setUnlocalizedName("chocolate_block").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		apple_pie = (new BlockPie()).setHardness(0.5f).setUnlocalizedName("apple_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		melon_pie = (new BlockPie()).setHardness(0.5f).setUnlocalizedName("melon_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		pumpkin_pie = (new BlockPie()).setHardness(0.5f).setUnlocalizedName("pumpkin_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_pie = (new BlockPie()).setHardness(0.5f).setUnlocalizedName("chocolate_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		pork_pie = (new BlockPie()).setHardness(0.5f).setUnlocalizedName("pork_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());

		Utils.registerBlock(butter_churn, "butter_churn");
		Utils.registerBlock(cheese_block, "cheese_block");
		Utils.registerBlock(cheese_maker, "cheese_maker");
		Utils.registerBlock(cocoa_block, "cocoa_block");
		Utils.registerBlock(chocolate_bar_mould, "chocolate_bar_mould");
		Utils.registerBlock(cocoa_tree_sapling, "cocoa_tree_sapling");
		Utils.registerBlock(chocolate_cake, "chocolate_cake");
		Utils.registerBlock(chocolate_block, "chocolate_block");
		Utils.registerBlock(apple_pie, "apple_pie");
		Utils.registerBlock(melon_pie, "melon_pie");
		Utils.registerBlock(pumpkin_pie, "pumpkin_pie");
		Utils.registerBlock(chocolate_pie, "chocolate_pie");
		Utils.registerBlock(pork_pie, "pork_pie");
	}

	@Override
	public void addRecipes() {
		GameRegistry.addSmelting(CuisineItems.raw_apple_pie, new ItemStack(apple_pie), 0.35f);
		GameRegistry.addSmelting(CuisineItems.raw_pork_pie, new ItemStack(pork_pie), 0.35f);
		GameRegistry.addSmelting(CuisineItems.raw_chocolate_pie, new ItemStack(chocolate_pie), 0.35f);
		GameRegistry.addSmelting(CuisineItems.raw_pumpkin_pie, new ItemStack(pumpkin_pie), 0.35f);
		GameRegistry.addSmelting(CuisineItems.raw_melon_pie, new ItemStack(melon_pie), 0.35f);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cheese_maker, 1), new Object[] { "X X", "XIX", "XXX", 'X', "cobblestone", 'I', Items.BUCKET }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(butter_churn, 1), new Object[] { "XIX", "XIX", "XXX", 'X', "plankWood", 'I', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cheese_block, 1), new Object[] { "CCC", "CCC", "CCC", 'C', CuisineItems.cheese }));
		CuisineItems.cheeseRecipe.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_cake, 1), new Object[] { "BXB", "SES", "WWW", 'B', CuisineItems.chocolate_ball, 'X', Items.MILK_BUCKET, 'S', Items.SUGAR, 'E', "egg", 'W', "cropWheat" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_block, 1), new Object[] { "XXX", "XXX", "XXX", 'X', CuisineItems.chocolate_bar }));
		CuisineItems.choc.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_bar_mould, 1), new Object[] { " I ", " I ", "XXX", 'X', "cobblestone", 'I', "stone" }));
	}
}
