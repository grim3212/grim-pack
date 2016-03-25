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
import net.minecraft.potion.Potion;
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

	// TODO: Add in another way to get the pumpkin pie
	public static Item raw_pumpkin_pie;

	// Recipe lists
	public static IRecipe cocoaRecipe;
	public static List<IRecipe> sodas;
	public static List<IRecipe> carbon;
	public static List<IRecipe> health;
	public static List<IRecipe> food;
	public static List<IRecipe> candy;
	public static List<IRecipe> eggs;
	public static List<IRecipe> sandwiches;
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
		milk_bowl = (new Item()).setMaxStackSize(1).setUnlocalizedName("milk_bowl").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
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
		bread_slice = (new ItemFood(2, 0.4F, false)).setUnlocalizedName("bread_slice").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		cheese_burger = (new ItemFood(12, 0.95F, false)).setUnlocalizedName("cheese_burger").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		hot_cheese = (new ItemFood(8, 0.75F, false)).setUnlocalizedName("hot_cheese").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		eggs_mixed = (new ItemFood(4, 0.4F, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F).setUnlocalizedName("eggs_mixed").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		eggs_unmixed = (new ItemFood(2, 0.1F, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F).setUnlocalizedName("eggs_unmixed").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		eggs_cooked = (new ItemFood(10, 0.8F, false)).setUnlocalizedName("eggs_cooked").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		knife = (new ItemDamage(63)).setUnlocalizedName("knife").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		mixer = (new ItemDamage(63)).setUnlocalizedName("mixer").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		dough = (new Item()).setMaxStackSize(16).setUnlocalizedName("dough").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		pan = (new Item()).setMaxStackSize(16).setUnlocalizedName("pan").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		pumpkin_slice = (new ItemFood(1, 0.2f, false)).setUnlocalizedName("pumpkin_slice").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_empty_pie = (new ItemFood(2, 0.3f, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.1F).setUnlocalizedName("raw_empty_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_pumpkin_pie = (new ItemFood(2, 0.3f, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.1F).setUnlocalizedName("raw_pumpkin_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_apple_pie = (new ItemFood(2, 0.3f, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.1F).setUnlocalizedName("raw_apple_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_pork_pie = (new ItemFood(2, 0.3f, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.1F).setUnlocalizedName("raw_pork_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_melon_pie = (new ItemFood(2, 0.3f, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.1F).setUnlocalizedName("raw_melon_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_chocolate_pie = (new ItemFood(2, 0.3f, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.1F).setUnlocalizedName("raw_chocolate_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());

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
		GameRegistry.registerItem(bread_slice, "bread_slice");
		GameRegistry.registerItem(cheese_burger, "cheese_burger");
		GameRegistry.registerItem(hot_cheese, "hot_cheese");
		GameRegistry.registerItem(eggs_mixed, "eggs_mixed");
		GameRegistry.registerItem(eggs_unmixed, "eggs_unmixed");
		GameRegistry.registerItem(eggs_cooked, "eggs_cooked");
		GameRegistry.registerItem(dough, "dough");
		GameRegistry.registerItem(pan, "pan");
		GameRegistry.registerItem(mixer, "mixer");
		GameRegistry.registerItem(knife, "knife");
		GameRegistry.registerItem(pumpkin_slice, "pumpkin_slice");
		GameRegistry.registerItem(raw_empty_pie, "raw_empty_pie");
		GameRegistry.registerItem(raw_chocolate_pie, "raw_chocolate_pie");
		GameRegistry.registerItem(raw_apple_pie, "raw_apple_pie");
		GameRegistry.registerItem(raw_pumpkin_pie, "raw_pumpkin_pie");
		GameRegistry.registerItem(raw_melon_pie, "raw_melon_pie");
		GameRegistry.registerItem(raw_pork_pie, "raw_pork_pie");
	}

	@Override
	public void addRecipes() {
		OreDictionary.registerOre("bowlMilk", milk_bowl);

		GameRegistry.addSmelting(chocolate_bowl, new ItemStack(chocolate_bowl_hot), 0.3F);
		GameRegistry.addSmelting(eggs_mixed, new ItemStack(eggs_cooked), 0.35F);

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

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(bread_slice, 2), new Object[] { new ItemStack(Items.bread) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cheese_burger, 1), new Object[] { " C ", "AOA", " C ", 'C', bread_slice, 'A', cheese, 'O', Items.cooked_porkchop }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hot_cheese, 1), new Object[] { " C ", "AAA", " C ", 'C', bread_slice, 'A', cheese }));
		sandwiches = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(eggs_unmixed, 1), new Object[] { "XIX", " M ", 'X', Items.egg, 'I', butter, 'M', Items.bowl }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(eggs_mixed, 1), new Object[] { eggs_unmixed, new ItemStack(mixer, 1, OreDictionary.WILDCARD_VALUE) }));
		eggs = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(knife, 1), new Object[] { "X  ", " W ", "  W", 'X', "plankWood", 'W', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mixer, 1), new Object[] { "X  ", " XX", " X ", 'X', "ingotIron" }));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(dough, 2), new Object[] { Items.milk_bucket, "cropWheat", "cropWheat", Items.egg }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pumpkin_slice, 6), new Object[] { Blocks.pumpkin, new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pan, 1), new Object[] { "X X", " X ", 'X', Blocks.stone }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_empty_pie, 1), new Object[] { "X", "M", 'X', dough, 'M', pan }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_apple_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', dough, 'M', Items.apple, 'Y', raw_empty_pie }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_chocolate_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', dough, 'M', chocolate_ball, 'Y', raw_empty_pie }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_pork_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', dough, 'M', Items.porkchop, 'Y', raw_empty_pie }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_melon_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', dough, 'M', Items.melon, 'Y', raw_empty_pie }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_pumpkin_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', dough, 'M', pumpkin_slice, 'Y', raw_empty_pie }));
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
		RenderHelper.renderItem(bread_slice);
		RenderHelper.renderItem(cheese_burger);
		RenderHelper.renderItem(hot_cheese);
		RenderHelper.renderItem(eggs_unmixed);
		RenderHelper.renderItem(eggs_mixed);
		RenderHelper.renderItem(eggs_cooked);
		RenderHelper.renderItem(pan);
		RenderHelper.renderItem(dough);
		RenderHelper.renderItem(knife);
		RenderHelper.renderItem(mixer);
		RenderHelper.renderItem(raw_empty_pie);
		RenderHelper.renderItem(raw_apple_pie);
		RenderHelper.renderItem(raw_chocolate_pie);
		RenderHelper.renderItem(raw_pork_pie);
		RenderHelper.renderItem(raw_pumpkin_pie);
		RenderHelper.renderItem(raw_melon_pie);
		RenderHelper.renderItem(pumpkin_slice);
		RenderHelper.renderVariantForge(soda, ItemSodaBottle.sodaTypes);
	}
}
