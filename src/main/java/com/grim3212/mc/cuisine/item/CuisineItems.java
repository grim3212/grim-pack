package com.grim3212.mc.cuisine.item;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.cuisine.GrimCuisine;
import com.grim3212.mc.cuisine.block.CuisineBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CuisineItems implements IPartItems {

	// Items
	public static Item soda;
	public static Item dragon_fruit;
	public static Item healthpack;
	public static Item healthpack_super;
	public static Item bandage;
	public static Item sweets;
	public static Item powered_sugar;
	public static Item powered_sweets;
	public static Item butter;
	public static Item cheese;
	public static Item milk_bowl;
	public static Item cocoa_fruit;
	public static Item cocoa_dust;
	public static Item chocolate_bowl;
	public static Item chocolate_bowl_hot;
	public static Item chocolate_bar;
	public static Item chocolate_bar_wrapped;
	public static Item chocolate_ball;
	public static Item wrapper;

	// Recipe lists
	public static IRecipe cocoaRecipe;
	public static List<IRecipe> sodas;
	public static List<IRecipe> carbon;
	public static List<IRecipe> health;
	public static List<IRecipe> food;
	public static List<IRecipe> candy;
	public static List<IRecipe> cheeseRecipe = new ArrayList<IRecipe>();
	public static List<IRecipe> choc = new ArrayList<IRecipe>();

	@Override
	public void initItems() {
		soda = (new ItemSodaBottle()).setUnlocalizedName("soda");
		dragon_fruit = (new ItemFood(4, 0.3F, false)).setUnlocalizedName("dragon_fruit").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		powered_sweets = (new ItemFood(6, 0.3F, false)).setUnlocalizedName("powered_sweets").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		powered_sugar = (new Item()).setUnlocalizedName("powered_sugar").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		sweets = (new ItemFood(2, 0.1F, false)).setUnlocalizedName("sweets").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		bandage = (new ItemHealthPack(3)).setUnlocalizedName("bandage").setMaxStackSize(16).setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		healthpack_super = (new ItemHealthPack(12)).setUnlocalizedName("healthpack_super").setMaxStackSize(4).setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		healthpack = (new ItemHealthPack(5)).setUnlocalizedName("healthpack").setMaxStackSize(4).setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		milk_bowl = (new ItemBowlMilk()).setUnlocalizedName("milk_bowl").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		cheese = (new ItemFood(3, 0.6F, false)).setUnlocalizedName("cheese").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		butter = (new ItemFood(2, 0.4F, false)).setUnlocalizedName("butter").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		cocoa_fruit = (new ItemCocoaFruit()).setUnlocalizedName("cocoa_fruit").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		cocoa_dust = (new Item()).setUnlocalizedName("cocoa_dust").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		wrapper = (new Item()).setUnlocalizedName("wrapper").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_bowl = (new ItemBowlChocolate(16)).setUnlocalizedName("chocolate_bowl").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_bowl_hot = (new ItemBowlChocolate(1)).setUnlocalizedName("chocolate_bowl_hot").setContainerItem(Items.bowl).setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_bar = (new ItemFood(3, 0.8F, false)).setUnlocalizedName("chocolate_bar").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_bar_wrapped = (new ItemFood(5, 0.8F, false)).setUnlocalizedName("chocolate_bar_wrapped").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_ball = (new ItemFood(2, 0.2F, false)).setUnlocalizedName("chocolate_ball").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());

		GameRegistry.registerItem(soda, "soda");
		GameRegistry.registerItem(dragon_fruit, "dragon_fruit");
		GameRegistry.registerItem(powered_sweets, "powered_sweets");
		GameRegistry.registerItem(powered_sugar, "powered_sugar");
		GameRegistry.registerItem(sweets, "sweets");
		GameRegistry.registerItem(bandage, "bandage");
		GameRegistry.registerItem(healthpack_super, "healthpack_super");
		GameRegistry.registerItem(healthpack, "healthpack");
		GameRegistry.registerItem(cheese, "cheese");
		GameRegistry.registerItem(butter, "butter");
		GameRegistry.registerItem(milk_bowl, "milk_bowl");
		GameRegistry.registerItem(cocoa_fruit, "cocoa_fruit");
		GameRegistry.registerItem(cocoa_dust, "cocoa_dust");
		GameRegistry.registerItem(wrapper, "wrapper");
		GameRegistry.registerItem(chocolate_bowl, "chocolate_bowl");
		GameRegistry.registerItem(chocolate_bar, "chocolate_bar");
		GameRegistry.registerItem(chocolate_bar_wrapped, "chocolate_bar_wrapped");
		GameRegistry.registerItem(chocolate_ball, "chocolate_ball");
		GameRegistry.registerItem(chocolate_bowl_hot, "chocolate_bowl_hot");
	}

	@Override
	public void addRecipes() {
		OreDictionary.registerOre("bowlMilk", milk_bowl);

		GameRegistry.addSmelting(chocolate_bowl, new ItemStack(chocolate_bowl_hot), 0.3F);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 1), new Object[] { " X ", "X X", "XXX", 'X', "paneGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 3), new Object[] { " X ", "X0X", " X ", 'X', "ingotIron", '0', Items.flint }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 11), new Object[] { "X", "Y", "Z", 'X', new ItemStack(soda, 1, 3), 'Y', Items.water_bucket, 'Z', new ItemStack(soda, 1, 1) }));
		carbon = RecipeHelper.getLatestIRecipes(3);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 2), new Object[] { "X", "Y", 'X', "slimeball", 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 0), new Object[] { "X", "Y", 'X', Items.apple, 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 4), new Object[] { "X", "Y", 'X', Items.golden_apple, 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 6), new Object[] { "X", "Y", 'X', Items.wheat_seeds, 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 7), new Object[] { "X", "Y", 'X', Items.milk_bucket, 'Y', new ItemStack(soda, 1, 9) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 8), new Object[] { "X", "Y", 'X', "gemDiamond", 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 9), new Object[] { "X", "Y", 'X', Blocks.pumpkin, 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 10), new Object[] { "X", "Y", 'X', "dyeBrown", 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 12), new Object[] { "X", "Y", 'X', Blocks.red_mushroom, 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 5), new Object[] { "X", "Y", 'X', Blocks.lit_pumpkin, 'Y', new ItemStack(soda, 1, 11) }));
		sodas = RecipeHelper.getLatestIRecipes(10);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bandage, 2), new Object[] { "P#P", '#', Blocks.wool, 'P', Items.paper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(healthpack, 1), new Object[] { " # ", "#S#", " # ", '#', Blocks.wool, 'S', Items.sugar }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(healthpack_super, 1), new Object[] { " # ", "#R#", " # ", '#', Blocks.wool, 'R', powered_sugar }));
		health = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(sweets, 8), new Object[] { Items.sugar, Items.paper }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(powered_sugar, 1), new Object[] { "dustRedstone", Items.sugar }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(powered_sweets, 8), new Object[] { powered_sugar, Items.paper }));
		food = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(milk_bowl, 1), new Object[] { "X", "M", 'X', Items.milk_bucket, 'M', Items.bowl }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cheese, 9), new Object[] { "O", 'O', CuisineBlocks.cheese_block }));
		cheeseRecipe.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.dye, 3, 3), new Object[] { new ItemStack(cocoa_dust), new ItemStack(cocoa_dust) }));
		cocoaRecipe = RecipeHelper.getLatestIRecipe();

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_bar, 9), new Object[] { "X", 'X', CuisineBlocks.chocolate_block }));
		choc.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(wrapper, 1), new Object[] { new ItemStack(Items.paper), "dyeBlue" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(chocolate_bar_wrapped, 1), new Object[] { new ItemStack(chocolate_bar), new ItemStack(wrapper) }));
		candy = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cocoa_dust, 2), new Object[] { "X", 'X', cocoa_fruit }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_bowl), new Object[] { " X ", "XAX", " B ", 'X', cocoa_dust, 'A', Items.sugar, 'B', "bowlMilk" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_ball, 2), new Object[] { "X", 'X', chocolate_bowl_hot }));
	}

	@Override
	public void renderItems() {
		RenderHelper.renderItem(powered_sweets);
		RenderHelper.renderItem(powered_sugar);
		RenderHelper.renderItem(sweets);
		RenderHelper.renderItem(bandage);
		RenderHelper.renderItem(healthpack_super);
		RenderHelper.renderItem(healthpack);
		RenderHelper.renderItem(dragon_fruit);
		RenderHelper.renderItem(butter);
		RenderHelper.renderItem(cheese);
		RenderHelper.renderItem(milk_bowl);
		RenderHelper.renderItem(cocoa_fruit);
		RenderHelper.renderItem(cocoa_dust);
		RenderHelper.renderItem(chocolate_bowl);
		RenderHelper.renderItem(chocolate_bowl_hot);
		RenderHelper.renderItem(chocolate_bar);
		RenderHelper.renderItem(chocolate_bar_wrapped);
		RenderHelper.renderItem(chocolate_ball);
		RenderHelper.renderItem(wrapper);
		RenderHelper.renderVariant(soda, ItemSodaBottle.sodaTypes.clone());
	}
}
