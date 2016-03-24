package com.grim3212.mc.cuisine.block;

import java.util.List;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.cuisine.GrimCuisine;
import com.grim3212.mc.cuisine.item.CuisineItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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

	public static List<IRecipe> cakes;

	@Override
	public void initItems() {
		cheese_block = (new BlockCheese()).setHardness(0.5F).setStepSound(Block.soundTypeCloth).setUnlocalizedName("cheese_block").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		cheese_maker = (new BlockCheeseMaker()).setHardness(2.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("cheese_maker").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		butter_churn = (new BlockButterchurn()).setHardness(2.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("butter_churn").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		cocoa_block = (new BlockCocoa()).setHardness(1.0F).setStepSound(Block.soundTypeCloth).setUnlocalizedName("cocoa_block");
		cocoa_tree_sapling = (new BlockCocoaSapling()).setHardness(0.0F).setStepSound(Block.soundTypeCloth).setUnlocalizedName("cocoa_tree_sapling");
		chocolate_bar_mould = (new BlockCBarMould()).setHardness(1.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("chocolate_bar_mould").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_cake = (new BlockChocolateCake()).setHardness(0.5F).setStepSound(Block.soundTypeCloth).setUnlocalizedName("chocolate_cake").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_block = (new Block(Material.cake)).setHardness(0.5F).setStepSound(Block.soundTypeCloth).setUnlocalizedName("chocolate_block").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		apple_pie = (new BlockPie()).setHardness(0.5f).setStepSound(Block.soundTypeCloth).setUnlocalizedName("apple_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		melon_pie = (new BlockPie()).setHardness(0.5f).setStepSound(Block.soundTypeCloth).setUnlocalizedName("melon_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		pumpkin_pie = (new BlockPie()).setHardness(0.5f).setStepSound(Block.soundTypeCloth).setUnlocalizedName("pumpkin_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_pie = (new BlockPie()).setHardness(0.5f).setStepSound(Block.soundTypeCloth).setUnlocalizedName("chocolate_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		pork_pie = (new BlockPie()).setHardness(0.5f).setStepSound(Block.soundTypeCloth).setUnlocalizedName("pork_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());

		GameRegistry.registerBlock(butter_churn, "butter_churn");
		GameRegistry.registerBlock(cheese_block, "cheese_block");
		GameRegistry.registerBlock(cheese_maker, "cheese_maker");
		GameRegistry.registerBlock(cocoa_block, "cocoa_block");
		GameRegistry.registerBlock(chocolate_bar_mould, "chocolate_bar_mould");
		GameRegistry.registerBlock(cocoa_tree_sapling, "cocoa_tree_sapling");
		GameRegistry.registerBlock(chocolate_cake, "chocolate_cake");
		GameRegistry.registerBlock(chocolate_block, "chocolate_block");
		GameRegistry.registerBlock(apple_pie, "apple_pie");
		GameRegistry.registerBlock(melon_pie, "melon_pie");
		GameRegistry.registerBlock(pumpkin_pie, "pumpkin_pie");
		GameRegistry.registerBlock(chocolate_pie, "chocolate_pie");
		GameRegistry.registerBlock(pork_pie, "pork_pie");
	}

	@Override
	public void addRecipes() {
		GameRegistry.addSmelting(CuisineItems.raw_apple_pie, new ItemStack(apple_pie), 0.35f);
		GameRegistry.addSmelting(CuisineItems.raw_pork_pie, new ItemStack(pork_pie), 0.35f);
		GameRegistry.addSmelting(CuisineItems.raw_chocolate_pie, new ItemStack(chocolate_pie), 0.35f);
		GameRegistry.addSmelting(CuisineItems.raw_pumpkin_pie, new ItemStack(pumpkin_pie), 0.35f);
		GameRegistry.addSmelting(CuisineItems.raw_melon_pie, new ItemStack(melon_pie), 0.35f);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cheese_maker, 1), new Object[] { "X X", "XIX", "XXX", 'X', "cobblestone", 'I', Items.bucket }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(butter_churn, 1), new Object[] { "XIX", "XIX", "XXX", 'X', "plankWood", 'I', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cheese_block, 1), new Object[] { "CCC", "CCC", "CCC", 'C', CuisineItems.cheese }));
		CuisineItems.cheeseRecipe.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_cake, 1), new Object[] { "BXB", "SES", "WWW", 'B', CuisineItems.chocolate_ball, 'X', Items.milk_bucket, 'S', Items.sugar, 'E', Items.egg, 'W', "cropWheat" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_cake, 1), new Object[] { "BXB", "SES", "WWW", 'B', CuisineItems.chocolate_ball, 'X', "bowlMilk", 'S', Items.sugar, 'E', Items.egg, 'W', "cropWheat" }));
		cakes = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_block, 1), new Object[] { "XXX", "XXX", "XXX", 'X', CuisineItems.chocolate_bar }));
		CuisineItems.choc.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_bar_mould, 1), new Object[] { " I ", " I ", "XXX", 'X', "cobblestone", 'I', "stone" }));
	}

	@Override
	public void renderItems() {
		RenderHelper.renderBlock(cheese_block);
		RenderHelper.renderBlock(butter_churn);
		RenderHelper.renderBlock(cheese_maker);
		RenderHelper.renderBlock(cocoa_block);
		RenderHelper.renderBlock(cocoa_tree_sapling);
		RenderHelper.renderBlock(chocolate_bar_mould);
		RenderHelper.renderBlock(chocolate_cake);
		RenderHelper.renderBlock(chocolate_block);
		RenderHelper.renderBlock(apple_pie);
		RenderHelper.renderBlock(melon_pie);
		RenderHelper.renderBlock(pumpkin_pie);
		RenderHelper.renderBlock(chocolate_pie);
		RenderHelper.renderBlock(pork_pie);
	}

}
