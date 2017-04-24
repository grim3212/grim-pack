package com.grim3212.mc.pack.cuisine.item;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManualFood;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.part.IPartItems;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.cuisine.GrimCuisine;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
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
	public static Item cocoa_fruit;
	public static Item cocoa_dust;
	public static Item chocolate_bowl;
	public static Item chocolate_bowl_hot;
	public static Item chocolate_bar;
	public static Item chocolate_bar_wrapped;
	public static Item chocolate_ball;
	public static Item wrapper;
	public static Item bread_slice;
	public static Item cheese_burger;
	public static Item hot_cheese;
	public static Item eggs_mixed;
	public static Item eggs_unmixed;
	public static Item eggs_cooked;
	public static Item knife;
	public static Item mixer;
	public static Item dough;
	public static Item pan;
	public static Item pumpkin_slice;
	public static Item raw_empty_pie;
	public static Item raw_apple_pie;
	public static Item raw_chocolate_pie;
	public static Item raw_pork_pie;
	public static Item raw_melon_pie;
	public static Item raw_pumpkin_pie;

	@Override
	public void initItems() {
		soda = (new ItemSodaBottle()).setUnlocalizedName("soda");
		dragon_fruit = (new ItemManualFood(4, 0.3F, false, "cuisine:dragonfruit")).setUnlocalizedName("dragon_fruit").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		powered_sweets = (new ItemManualFood(6, 0.3F, false, "cuisine:sugar.sweets")).setUnlocalizedName("powered_sweets").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		powered_sugar = (new ItemManualPage("cuisine:sugar.sweets")).setUnlocalizedName("powered_sugar").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		sweets = (new ItemManualFood(2, 0.1F, false, "cuisine:sugar.sweets")).setUnlocalizedName("sweets").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		bandage = (new ItemHealthPack(3)).setUnlocalizedName("bandage").setMaxStackSize(16).setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		healthpack_super = (new ItemHealthPack(12)).setUnlocalizedName("healthpack_super").setMaxStackSize(4).setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		healthpack = (new ItemHealthPack(5)).setUnlocalizedName("healthpack").setMaxStackSize(4).setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		cheese = (new ItemManualFood(3, 0.6F, false, "cuisine:dairy.cheeseblock")).setUnlocalizedName("cheese").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		butter = (new ItemManualFood(2, 0.4F, false, "cuisine:dairy.butter")).setUnlocalizedName("butter").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		cocoa_fruit = (new ItemCocoaFruit()).setUnlocalizedName("cocoa_fruit").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		cocoa_dust = (new ItemManualPage("cuisine:cocoa.dye")).setUnlocalizedName("cocoa_dust").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		wrapper = (new ItemManualPage("cuisine:choco.candy")).setUnlocalizedName("wrapper").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_bowl = (new ItemBowlChocolate(16)).setUnlocalizedName("chocolate_bowl").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_bowl_hot = (new ItemBowlChocolate(1)).setUnlocalizedName("chocolate_bowl_hot").setContainerItem(Items.BOWL).setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_bar = (new ItemManualFood(3, 0.8F, false, "cuisine:choco.bars")).setUnlocalizedName("chocolate_bar").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_bar_wrapped = (new ItemManualFood(5, 0.8F, false, "cuisine:choco.candy")).setUnlocalizedName("chocolate_bar_wrapped").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		chocolate_ball = (new ItemManualFood(2, 0.2F, false, "cuisine:bowlchoc.chocBall")).setUnlocalizedName("chocolate_ball").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		bread_slice = (new ItemManualFood(2, 0.4F, false, "cuisine:food.sandwiches")).setUnlocalizedName("bread_slice").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		cheese_burger = (new ItemManualFood(12, 0.95F, false, "cuisine:food.sandwiches")).setUnlocalizedName("cheese_burger").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		hot_cheese = (new ItemManualFood(8, 0.75F, false, "cuisine:food.sandwiches")).setUnlocalizedName("hot_cheese").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		eggs_mixed = (new ItemManualFood(4, 0.4F, false, "cuisine:food.eggs")).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F).setUnlocalizedName("eggs_mixed").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		eggs_unmixed = (new ItemManualFood(2, 0.1F, false, "cuisine:food.eggs")).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F).setUnlocalizedName("eggs_unmixed").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		eggs_cooked = (new ItemManualFood(10, 0.8F, false, "cuisine:food.cooked")).setUnlocalizedName("eggs_cooked").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		knife = (new ItemDamage(63)).setUnlocalizedName("knife").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		mixer = (new ItemDamage(63)).setUnlocalizedName("mixer").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		dough = (new ItemManualFood(1, 0.2f, false, "cuisine:food.extras")).setMaxStackSize(16).setUnlocalizedName("dough").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		pan = (new ItemManualPage("cuisine:food.utensils")).setMaxStackSize(16).setUnlocalizedName("pan").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		pumpkin_slice = (new ItemManualFood(1, 0.2f, false, "cuisine:pie.craft")).setUnlocalizedName("pumpkin_slice").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_empty_pie = (new ItemManualFood(2, 0.3f, false, "cuisine:pie.craft")).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 0), 0.1F).setUnlocalizedName("raw_empty_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_pumpkin_pie = (new ItemManualFood(2, 0.3f, false, "cuisine:pie.craft")).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 0), 0.1F).setUnlocalizedName("raw_pumpkin_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_apple_pie = (new ItemManualFood(2, 0.3f, false, "cuisine:pie.craft")).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 0), 0.1F).setUnlocalizedName("raw_apple_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_pork_pie = (new ItemManualFood(2, 0.3f, false, "cuisine:pie.craft")).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 0), 0.1F).setUnlocalizedName("raw_pork_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_melon_pie = (new ItemManualFood(2, 0.3f, false, "cuisine:pie.craft")).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 0), 0.1F).setUnlocalizedName("raw_melon_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_chocolate_pie = (new ItemManualFood(2, 0.3f, false, "cuisine:pie.craft")).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 0), 0.1F).setUnlocalizedName("raw_chocolate_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());

		Utils.registerItem(soda, "soda");
		Utils.registerItem(dragon_fruit, "dragon_fruit");
		Utils.registerItem(powered_sweets, "powered_sweets");
		Utils.registerItem(powered_sugar, "powered_sugar");
		Utils.registerItem(sweets, "sweets");
		Utils.registerItem(bandage, "bandage");
		Utils.registerItem(healthpack_super, "healthpack_super");
		Utils.registerItem(healthpack, "healthpack");
		Utils.registerItem(cheese, "cheese");
		Utils.registerItem(butter, "butter");
		Utils.registerItem(cocoa_fruit, "cocoa_fruit");
		Utils.registerItem(cocoa_dust, "cocoa_dust");
		Utils.registerItem(wrapper, "wrapper");
		Utils.registerItem(chocolate_bowl, "chocolate_bowl");
		Utils.registerItem(chocolate_bar, "chocolate_bar");
		Utils.registerItem(chocolate_bar_wrapped, "chocolate_bar_wrapped");
		Utils.registerItem(chocolate_ball, "chocolate_ball");
		Utils.registerItem(chocolate_bowl_hot, "chocolate_bowl_hot");
		Utils.registerItem(bread_slice, "bread_slice");
		Utils.registerItem(cheese_burger, "cheese_burger");
		Utils.registerItem(hot_cheese, "hot_cheese");
		Utils.registerItem(eggs_mixed, "eggs_mixed");
		Utils.registerItem(eggs_unmixed, "eggs_unmixed");
		Utils.registerItem(eggs_cooked, "eggs_cooked");
		Utils.registerItem(dough, "dough");
		Utils.registerItem(pan, "pan");
		Utils.registerItem(mixer, "mixer");
		Utils.registerItem(knife, "knife");
		Utils.registerItem(pumpkin_slice, "pumpkin_slice");
		Utils.registerItem(raw_empty_pie, "raw_empty_pie");
		Utils.registerItem(raw_chocolate_pie, "raw_chocolate_pie");
		Utils.registerItem(raw_apple_pie, "raw_apple_pie");
		Utils.registerItem(raw_pumpkin_pie, "raw_pumpkin_pie");
		Utils.registerItem(raw_melon_pie, "raw_melon_pie");
		Utils.registerItem(raw_pork_pie, "raw_pork_pie");
		
		OreDictionary.registerOre("bread", bread_slice);
		OreDictionary.registerOre("foodCheese", cheese);
		OreDictionary.registerOre("foodDough", dough);
		OreDictionary.registerOre("foodButter", butter);
		OreDictionary.registerOre("foodScrambledegg", eggs_cooked);
		OreDictionary.registerOre("listAllbread", bread_slice);
		OreDictionary.registerOre("toolCuttingboard", new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolJuicer", new ItemStack(mixer, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("bucketMilk", Items.MILK_BUCKET);
	}

	public static IRecipe cocoaRecipe;
	public static List<IRecipe> sodas;
	public static List<IRecipe> carbon;
	public static List<IRecipe> health;
	public static List<IRecipe> food;
	public static List<IRecipe> candy;
	public static List<IRecipe> eggs;
	public static List<IRecipe> sandwiches;
	public static List<IRecipe> utensils;
	public static List<IRecipe> pie;
	public static List<IRecipe> extra;
	public static List<IRecipe> cheeseRecipe = new ArrayList<IRecipe>();
	public static List<IRecipe> choc = new ArrayList<IRecipe>();

	@Override
	public void addRecipes() {
		GameRegistry.addSmelting(chocolate_bowl, new ItemStack(chocolate_bowl_hot), 0.3F);
		GameRegistry.addSmelting(eggs_mixed, new ItemStack(eggs_cooked), 0.35F);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 1), new Object[] { " X ", "X X", "XXX", 'X', "paneGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 4, 3), new Object[] { " X ", "X0X", " X ", 'X', "ingotIron", '0', Items.FLINT }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 11), new Object[] { "X", "Y", "Z", 'X', new ItemStack(soda, 1, 3), 'Y', Items.WATER_BUCKET, 'Z', new ItemStack(soda, 1, 1) }));
		carbon = RecipeHelper.getLatestIRecipes(3);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 2), new Object[] { "X", "Y", 'X', "slimeball", 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 0), new Object[] { "X", "Y", 'X', Items.APPLE, 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 4), new Object[] { "X", "Y", 'X', Items.GOLDEN_APPLE, 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 6), new Object[] { "X", "Y", 'X', Items.WHEAT_SEEDS, 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 7), new Object[] { "X", "Y", 'X', "bucketMilk", 'Y', new ItemStack(soda, 1, 9) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 8), new Object[] { "X", "Y", 'X', "gemDiamond", 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 9), new Object[] { "X", "Y", 'X', Blocks.PUMPKIN, 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 10), new Object[] { "X", "Y", 'X', "dyeBrown", 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 12), new Object[] { "X", "Y", 'X', Blocks.RED_MUSHROOM, 'Y', new ItemStack(soda, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(soda, 1, 5), new Object[] { "X", "Y", 'X', Blocks.LIT_PUMPKIN, 'Y', new ItemStack(soda, 1, 11) }));
		sodas = RecipeHelper.getLatestIRecipes(10);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bandage, 2), new Object[] { "P#P", '#', Blocks.WOOL, 'P', "paper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(healthpack, 1), new Object[] { " # ", "#S#", " # ", '#', Blocks.WOOL, 'S', Items.SUGAR }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(healthpack_super, 1), new Object[] { " # ", "#R#", " # ", '#', Blocks.WOOL, 'R', powered_sugar }));
		health = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(sweets, 8), new Object[] { Items.SUGAR, "paper" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(powered_sugar, 1), new Object[] { "dustRedstone", Items.SUGAR }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(powered_sweets, 8), new Object[] { powered_sugar, "paper" }));
		food = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cheese, 9), new Object[] { "O", 'O', CuisineBlocks.cheese_block }));
		cheeseRecipe.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.DYE, 3, 3), new Object[] { new ItemStack(cocoa_dust), new ItemStack(cocoa_dust) }));
		cocoaRecipe = RecipeHelper.getLatestIRecipe();

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_bar, 9), new Object[] { "X", 'X', CuisineBlocks.chocolate_block }));
		choc.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(wrapper, 1), new Object[] { "paper", "dyeBlue" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(chocolate_bar_wrapped, 1), new Object[] { new ItemStack(chocolate_bar), new ItemStack(wrapper) }));
		candy = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cocoa_dust, 2), new Object[] { "X", 'X', cocoa_fruit }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_bowl), new Object[] { " X ", "XAX", " B ", 'X', cocoa_dust, 'A', Items.SUGAR, 'B', "bucketMilk" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_ball, 2), new Object[] { "X", 'X', chocolate_bowl_hot }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(knife, 1), new Object[] { "X  ", " W ", "  W", 'X', "plankWood", 'W', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mixer, 1), new Object[] { "X  ", " XX", " X ", 'X', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pan, 1), new Object[] { "X X", " X ", 'X', "stone" }));
		utensils = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(dough, 2), new Object[] { "bucketMilk", "cropWheat", "cropWheat", "egg" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pumpkin_slice, 6), new Object[] { Blocks.PUMPKIN, "toolCuttingboard" }));
		extra = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(bread_slice, 2), new Object[] { new ItemStack(Items.BREAD), "toolCuttingboard" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cheese_burger, 1), new Object[] { " C ", "AOA", " C ", 'C', "bread", 'A', "foodCheese", 'O', Items.COOKED_BEEF }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hot_cheese, 1), new Object[] { " C ", "AAA", " C ", 'C', "bread", 'A', "foodCheese" }));
		sandwiches = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(eggs_unmixed, 1), new Object[] { "XIX", " M ", 'X', "egg", 'I', "listAllbutter", 'M', Items.BOWL }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(eggs_mixed, 1), new Object[] { eggs_unmixed, "toolJuicer" }));
		eggs = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_empty_pie, 1), new Object[] { "X", "M", 'X', "foodDough", 'M', pan }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_apple_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', "foodDough", 'M', Items.APPLE, 'Y', raw_empty_pie }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_chocolate_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', "foodDough", 'M', chocolate_ball, 'Y', raw_empty_pie }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_pork_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', "foodDough", 'M', Items.PORKCHOP, 'Y', raw_empty_pie }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_melon_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', "foodDough", 'M', Items.MELON, 'Y', raw_empty_pie }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_pumpkin_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', "foodDough", 'M', pumpkin_slice, 'Y', raw_empty_pie }));
		pie = RecipeHelper.getLatestIRecipes(6);
	}
}
