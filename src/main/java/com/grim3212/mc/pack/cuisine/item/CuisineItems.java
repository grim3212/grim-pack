package com.grim3212.mc.pack.cuisine.item;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManualFood;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.CreateRecipes;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class CuisineItems {

	// Items
	public static final Item soda = new ItemSodaBottle();
	public static final Item dragon_fruit = (new ItemManualFood("dragon_fruit", 4, 0.3F, false, "cuisine:dragonfruit")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item healthpack = (new ItemHealthPack("healthpack", 5)).setMaxStackSize(4);
	public static final Item healthpack_super = (new ItemHealthPack("healthpack_super", 12)).setMaxStackSize(4);
	public static final Item bandage = (new ItemHealthPack("bandage", 3)).setMaxStackSize(16);
	public static final Item sweets = (new ItemManualFood("sweets", 2, 0.1F, false, "cuisine:sugar.sweets")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item powered_sugar = (new ItemManualPage("powered_sugar", "cuisine:sugar.sweets")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item powered_sweets = (new ItemManualFood("powered_sweets", 6, 0.3F, false, "cuisine:sugar.sweets")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item butter = (new ItemManualFood("butter", 2, 0.4F, false, "cuisine:dairy.butter")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item cheese = (new ItemManualFood("cheese", 3, 0.6F, false, "cuisine:dairy.cheeseblock")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item cocoa_fruit = new ItemCocoaFruit();
	public static final Item cocoa_dust = (new ItemManualPage("cocoa_dust", "cuisine:cocoa.dye")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item chocolate_bowl = new ItemBowlChocolate("chocolate_bowl", 16);
	public static final Item chocolate_bowl_hot = (new ItemBowlChocolate("chocolate_bowl_hot", 1)).setContainerItem(Items.BOWL);
	public static final Item chocolate_bar = (new ItemManualFood("chocolate_bar", 3, 0.8F, false, "cuisine:choco.bars")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item chocolate_bar_wrapped = (new ItemManualFood("chocolate_bar_wrapped", 5, 0.8F, false, "cuisine:choco.candy")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item chocolate_ball = (new ItemManualFood("chocolate_ball", 2, 0.2F, false, "cuisine:bowlchoc.chocBall")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item wrapper = (new ItemManualPage("wrapper", "cuisine:choco.candy")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item bread_slice = (new ItemManualFood("bread_slice", 2, 0.4F, false, "cuisine:food.sandwiches")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item cheese_burger = (new ItemManualFood("cheese_burger", 12, 0.95F, false, "cuisine:food.sandwiches")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item hot_cheese = (new ItemManualFood("hot_cheese", 8, 0.75F, false, "cuisine:food.sandwiches")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item eggs_mixed = (new ItemManualFood("eggs_mixed", 4, 0.4F, false, "cuisine:food.eggs")).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item eggs_unmixed = (new ItemManualFood("eggs_unmixed", 2, 0.1F, false, "cuisine:food.eggs")).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item eggs_cooked = (new ItemManualFood("eggs_cooked", 10, 0.8F, false, "cuisine:food.cooked")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item knife = new ItemDamage("knife", 63);
	public static final Item mixer = new ItemDamage("mixer", 63);
	public static final Item dough = (new ItemManualFood("dough", 1, 0.2f, false, "cuisine:food.extras")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item pan = (new ItemManualPage("pan", "cuisine:food.utensils", 16)).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item pumpkin_slice = (new ItemManualFood("pumpkin_slice", 1, 0.2f, false, "cuisine:pie.craft")).setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	public static final Item raw_empty_pie = new ItemRawPie("raw_empty_pie");
	public static final Item raw_apple_pie = new ItemRawPie("raw_apple_pie");
	public static final Item raw_chocolate_pie = new ItemRawPie("raw_chocolate_pie");
	public static final Item raw_pork_pie = new ItemRawPie("raw_pork_pie");
	public static final Item raw_melon_pie = new ItemRawPie("raw_melon_pie");
	public static final Item raw_pumpkin_pie = new ItemRawPie("raw_pumpkin_pie");

	@SubscribeEvent
	public static void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		r.register(soda);
		r.register(dragon_fruit);
		r.register(powered_sugar);
		r.register(powered_sweets);
		r.register(bandage);
		r.register(healthpack);
		r.register(sweets);
		r.register(healthpack_super);
		r.register(butter);
		r.register(cheese);
		r.register(cocoa_fruit);
		r.register(cocoa_dust);
		r.register(chocolate_ball);
		r.register(wrapper);
		r.register(chocolate_bowl);
		r.register(chocolate_bar);
		r.register(chocolate_bar_wrapped);
		r.register(chocolate_bowl_hot);
		r.register(bread_slice);
		r.register(cheese_burger);
		r.register(hot_cheese);
		r.register(eggs_mixed);
		r.register(eggs_unmixed);
		r.register(eggs_cooked);
		r.register(dough);
		r.register(pan);
		r.register(mixer);
		r.register(knife);
		r.register(pumpkin_slice);
		r.register(raw_empty_pie);
		r.register(raw_chocolate_pie);
		r.register(raw_apple_pie);
		r.register(raw_melon_pie);
		r.register(raw_pork_pie);
		r.register(raw_pumpkin_pie);

		initOreDict();
	}

	private static void initOreDict() {
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

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		GameRegistry.addSmelting(chocolate_bowl, new ItemStack(chocolate_bowl_hot), 0.3F);
		GameRegistry.addSmelting(eggs_mixed, new ItemStack(eggs_cooked), 0.35F);
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

	public static void addRecipes() {

		CreateRecipes.addShapedRecipe(new ItemStack(soda, 1, 1), new Object[] { " X ", "X X", "XXX", 'X', "paneGlass" });
		CreateRecipes.addShapedRecipe(new ItemStack(soda, 4, 3), new Object[] { " X ", "X0X", " X ", 'X', "ingotIron", '0', Items.FLINT });
		CreateRecipes.addShapedRecipe(new ItemStack(soda, 1, 11), new Object[] { "X", "Y", "Z", 'X', new ItemStack(soda, 1, 3), 'Y', Items.WATER_BUCKET, 'Z', new ItemStack(soda, 1, 1) });
		// carbon = RecipeHelper.getLatestIRecipes(3);
		CreateRecipes.addShapedRecipe(new ItemStack(soda, 1, 2), new Object[] { "X", "Y", 'X', "slimeball", 'Y', new ItemStack(soda, 1, 11) });
		CreateRecipes.addShapedRecipe(new ItemStack(soda, 1, 0), new Object[] { "X", "Y", 'X', Items.APPLE, 'Y', new ItemStack(soda, 1, 11) });
		CreateRecipes.addShapedRecipe(new ItemStack(soda, 1, 4), new Object[] { "X", "Y", 'X', Items.GOLDEN_APPLE, 'Y', new ItemStack(soda, 1, 11) });
		CreateRecipes.addShapedRecipe(new ItemStack(soda, 1, 6), new Object[] { "X", "Y", 'X', Items.WHEAT_SEEDS, 'Y', new ItemStack(soda, 1, 11) });
		CreateRecipes.addShapedRecipe(new ItemStack(soda, 1, 7), new Object[] { "X", "Y", 'X', "bucketMilk", 'Y', new ItemStack(soda, 1, 9) });
		CreateRecipes.addShapedRecipe(new ItemStack(soda, 1, 8), new Object[] { "X", "Y", 'X', "gemDiamond", 'Y', new ItemStack(soda, 1, 11) });
		CreateRecipes.addShapedRecipe(new ItemStack(soda, 1, 9), new Object[] { "X", "Y", 'X', Blocks.PUMPKIN, 'Y', new ItemStack(soda, 1, 11) });
		CreateRecipes.addShapedRecipe(new ItemStack(soda, 1, 10), new Object[] { "X", "Y", 'X', "dyeBrown", 'Y', new ItemStack(soda, 1, 11) });
		CreateRecipes.addShapedRecipe(new ItemStack(soda, 1, 12), new Object[] { "X", "Y", 'X', Blocks.RED_MUSHROOM, 'Y', new ItemStack(soda, 1, 11) });
		CreateRecipes.addShapedRecipe(new ItemStack(soda, 1, 5), new Object[] { "X", "Y", 'X', Blocks.LIT_PUMPKIN, 'Y', new ItemStack(soda, 1, 11) });
		// sodas = RecipeHelper.getLatestIRecipes(10);

		CreateRecipes.addShapedRecipe(new ItemStack(bandage, 2), new Object[] { "P#P", '#', Blocks.WOOL, 'P', "paper" });
		CreateRecipes.addShapedRecipe(new ItemStack(healthpack, 1), new Object[] { " # ", "#S#", " # ", '#', Blocks.WOOL, 'S', Items.SUGAR });
		CreateRecipes.addShapedRecipe(new ItemStack(healthpack_super, 1), new Object[] { " # ", "#R#", " # ", '#', Blocks.WOOL, 'R', powered_sugar });
		// health = RecipeHelper.getLatestIRecipes(3);

		CreateRecipes.addShapelessRecipe(new ItemStack(sweets, 8), new Object[] { Items.SUGAR, "paper" });
		CreateRecipes.addShapelessRecipe(new ItemStack(powered_sugar, 1), new Object[] { "dustRedstone", Items.SUGAR });
		CreateRecipes.addShapelessRecipe(new ItemStack(powered_sweets, 8), new Object[] { powered_sugar, "paper" });
		// food = RecipeHelper.getLatestIRecipes(3);

		CreateRecipes.addShapedRecipe(new ItemStack(cheese, 9), new Object[] { "O", 'O', CuisineBlocks.cheese_block });
		// cheeseRecipe.add(RecipeHelper.getLatestIRecipe());

		CreateRecipes.addShapelessRecipe(new ItemStack(Items.DYE, 3, 3), new Object[] { new ItemStack(cocoa_dust), new ItemStack(cocoa_dust) });
		// cocoaRecipe = RecipeHelper.getLatestIRecipe();

		CreateRecipes.addShapedRecipe(new ItemStack(chocolate_bar, 9), new Object[] { "X", 'X', CuisineBlocks.chocolate_block });
		// choc.add(RecipeHelper.getLatestIRecipe());

		CreateRecipes.addShapelessRecipe(new ItemStack(wrapper, 1), new Object[] { "paper", "dyeBlue" });
		CreateRecipes.addShapelessRecipe(new ItemStack(chocolate_bar_wrapped, 1), new Object[] { new ItemStack(chocolate_bar), new ItemStack(wrapper) });
		// candy = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(cocoa_dust, 2), new Object[] { "X", 'X', cocoa_fruit });
		CreateRecipes.addShapedRecipe(new ItemStack(chocolate_bowl), new Object[] { " X ", "XAX", " B ", 'X', cocoa_dust, 'A', Items.SUGAR, 'B', "bucketMilk" });
		CreateRecipes.addShapedRecipe(new ItemStack(chocolate_ball, 2), new Object[] { "X", 'X', chocolate_bowl_hot });

		CreateRecipes.addShapedRecipe(new ItemStack(knife, 1), new Object[] { "X  ", " W ", "  W", 'X', "plankWood", 'W', "ingotIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(mixer, 1), new Object[] { "X  ", " XX", " X ", 'X', "ingotIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(pan, 1), new Object[] { "X X", " X ", 'X', "stone" });
		// utensils = RecipeHelper.getLatestIRecipes(3);

		CreateRecipes.addShapelessRecipe(new ItemStack(dough, 2), new Object[] { "bucketMilk", "cropWheat", "cropWheat", "egg" });
		CreateRecipes.addShapelessRecipe(new ItemStack(pumpkin_slice, 6), new Object[] { Blocks.PUMPKIN, "toolCuttingboard" });
		// extra = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapelessRecipe(new ItemStack(bread_slice, 2), new Object[] { new ItemStack(Items.BREAD), "toolCuttingboard" });
		CreateRecipes.addShapedRecipe(new ItemStack(cheese_burger, 1), new Object[] { " C ", "AOA", " C ", 'C', "bread", 'A', "foodCheese", 'O', Items.COOKED_BEEF });
		CreateRecipes.addShapedRecipe(new ItemStack(hot_cheese, 1), new Object[] { " C ", "AAA", " C ", 'C', "bread", 'A', "foodCheese" });
		// sandwiches = RecipeHelper.getLatestIRecipes(3);

		CreateRecipes.addShapedRecipe(new ItemStack(eggs_unmixed, 1), new Object[] { "XIX", " M ", 'X', "egg", 'I', "listAllbutter", 'M', Items.BOWL });
		CreateRecipes.addShapelessRecipe(new ItemStack(eggs_mixed, 1), new Object[] { eggs_unmixed, "toolJuicer" });
		// eggs = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(raw_empty_pie, 1), new Object[] { "X", "M", 'X', "foodDough", 'M', pan });
		CreateRecipes.addShapedRecipe(new ItemStack(raw_apple_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', "foodDough", 'M', Items.APPLE, 'Y', raw_empty_pie });
		CreateRecipes.addShapedRecipe(new ItemStack(raw_chocolate_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', "foodDough", 'M', chocolate_ball, 'Y', raw_empty_pie });
		CreateRecipes.addShapedRecipe(new ItemStack(raw_pork_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', "foodDough", 'M', Items.PORKCHOP, 'Y', raw_empty_pie });
		CreateRecipes.addShapedRecipe(new ItemStack(raw_melon_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', "foodDough", 'M', Items.MELON, 'Y', raw_empty_pie });
		CreateRecipes.addShapedRecipe(new ItemStack(raw_pumpkin_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', "foodDough", 'M', pumpkin_slice, 'Y', raw_empty_pie });
		// pie = RecipeHelper.getLatestIRecipes(6);
	}
}
