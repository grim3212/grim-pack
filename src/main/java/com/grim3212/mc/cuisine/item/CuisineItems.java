package com.grim3212.mc.cuisine.item;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.cuisine.GrimCuisine;
import com.grim3212.mc.cuisine.block.CuisineBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
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
		dragon_fruit = (new ItemFood(4, 0.3F, false)).setUnlocalizedName("dragon_fruit").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		powered_sweets = (new ItemFood(6, 0.3F, false)).setUnlocalizedName("powered_sweets").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		powered_sugar = (new Item()).setUnlocalizedName("powered_sugar").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		sweets = (new ItemFood(2, 0.1F, false)).setUnlocalizedName("sweets").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		bandage = (new ItemHealthPack(3)).setUnlocalizedName("bandage").setMaxStackSize(16).setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		healthpack_super = (new ItemHealthPack(12)).setUnlocalizedName("healthpack_super").setMaxStackSize(4).setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		healthpack = (new ItemHealthPack(5)).setUnlocalizedName("healthpack").setMaxStackSize(4).setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
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
		eggs_mixed = (new ItemFood(4, 0.4F, false)).setPotionEffect(new PotionEffect(MobEffects.hunger, 600, 0), 0.3F).setUnlocalizedName("eggs_mixed").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		eggs_unmixed = (new ItemFood(2, 0.1F, false)).setPotionEffect(new PotionEffect(MobEffects.hunger, 600, 0), 0.3F).setUnlocalizedName("eggs_unmixed").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		eggs_cooked = (new ItemFood(10, 0.8F, false)).setUnlocalizedName("eggs_cooked").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		knife = (new ItemDamage(63)).setUnlocalizedName("knife").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		mixer = (new ItemDamage(63)).setUnlocalizedName("mixer").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		dough = (new ItemFood(1, 0.2f, false)).setMaxStackSize(16).setUnlocalizedName("dough").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		pan = (new Item()).setMaxStackSize(16).setUnlocalizedName("pan").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		pumpkin_slice = (new ItemFood(1, 0.2f, false)).setUnlocalizedName("pumpkin_slice").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_empty_pie = (new ItemFood(2, 0.3f, false)).setPotionEffect(new PotionEffect(MobEffects.hunger, 300, 0), 0.1F).setUnlocalizedName("raw_empty_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_pumpkin_pie = (new ItemFood(2, 0.3f, false)).setPotionEffect(new PotionEffect(MobEffects.hunger, 300, 0), 0.1F).setUnlocalizedName("raw_pumpkin_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_apple_pie = (new ItemFood(2, 0.3f, false)).setPotionEffect(new PotionEffect(MobEffects.hunger, 300, 0), 0.1F).setUnlocalizedName("raw_apple_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_pork_pie = (new ItemFood(2, 0.3f, false)).setPotionEffect(new PotionEffect(MobEffects.hunger, 300, 0), 0.1F).setUnlocalizedName("raw_pork_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_melon_pie = (new ItemFood(2, 0.3f, false)).setPotionEffect(new PotionEffect(MobEffects.hunger, 300, 0), 0.1F).setUnlocalizedName("raw_melon_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
		raw_chocolate_pie = (new ItemFood(2, 0.3f, false)).setPotionEffect(new PotionEffect(MobEffects.hunger, 300, 0), 0.1F).setUnlocalizedName("raw_chocolate_pie").setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());

		GameRegistry.register(soda, new ResourceLocation(GrimCuisine.modID, "soda"));
		GameRegistry.register(dragon_fruit, new ResourceLocation(GrimCuisine.modID, "dragon_fruit"));
		GameRegistry.register(powered_sweets, new ResourceLocation(GrimCuisine.modID, "powered_sweets"));
		GameRegistry.register(powered_sugar, new ResourceLocation(GrimCuisine.modID, "powered_sugar"));
		GameRegistry.register(sweets, new ResourceLocation(GrimCuisine.modID, "sweets"));
		GameRegistry.register(bandage, new ResourceLocation(GrimCuisine.modID, "bandage"));
		GameRegistry.register(healthpack_super, new ResourceLocation(GrimCuisine.modID, "healthpack_super"));
		GameRegistry.register(healthpack, new ResourceLocation(GrimCuisine.modID, "healthpack"));
		GameRegistry.register(cheese, new ResourceLocation(GrimCuisine.modID, "cheese"));
		GameRegistry.register(butter, new ResourceLocation(GrimCuisine.modID, "butter"));
		GameRegistry.register(cocoa_fruit, new ResourceLocation(GrimCuisine.modID, "cocoa_fruit"));
		GameRegistry.register(cocoa_dust, new ResourceLocation(GrimCuisine.modID, "cocoa_dust"));
		GameRegistry.register(wrapper, new ResourceLocation(GrimCuisine.modID, "wrapper"));
		GameRegistry.register(chocolate_bowl, new ResourceLocation(GrimCuisine.modID, "chocolate_bowl"));
		GameRegistry.register(chocolate_bar, new ResourceLocation(GrimCuisine.modID, "chocolate_bar"));
		GameRegistry.register(chocolate_bar_wrapped, new ResourceLocation(GrimCuisine.modID, "chocolate_bar_wrapped"));
		GameRegistry.register(chocolate_ball, new ResourceLocation(GrimCuisine.modID, "chocolate_ball"));
		GameRegistry.register(chocolate_bowl_hot, new ResourceLocation(GrimCuisine.modID, "chocolate_bowl_hot"));
		GameRegistry.register(bread_slice, new ResourceLocation(GrimCuisine.modID, "bread_slice"));
		GameRegistry.register(cheese_burger, new ResourceLocation(GrimCuisine.modID, "cheese_burger"));
		GameRegistry.register(hot_cheese, new ResourceLocation(GrimCuisine.modID, "hot_cheese"));
		GameRegistry.register(eggs_mixed, new ResourceLocation(GrimCuisine.modID, "eggs_mixed"));
		GameRegistry.register(eggs_unmixed, new ResourceLocation(GrimCuisine.modID, "eggs_unmixed"));
		GameRegistry.register(eggs_cooked, new ResourceLocation(GrimCuisine.modID, "eggs_cooked"));
		GameRegistry.register(dough, new ResourceLocation(GrimCuisine.modID, "dough"));
		GameRegistry.register(pan, new ResourceLocation(GrimCuisine.modID, "pan"));
		GameRegistry.register(mixer, new ResourceLocation(GrimCuisine.modID, "mixer"));
		GameRegistry.register(knife, new ResourceLocation(GrimCuisine.modID, "knife"));
		GameRegistry.register(pumpkin_slice, new ResourceLocation(GrimCuisine.modID, "pumpkin_slice"));
		GameRegistry.register(raw_empty_pie, new ResourceLocation(GrimCuisine.modID, "raw_empty_pie"));
		GameRegistry.register(raw_chocolate_pie, new ResourceLocation(GrimCuisine.modID, "raw_chocolate_pie"));
		GameRegistry.register(raw_apple_pie, new ResourceLocation(GrimCuisine.modID, "raw_apple_pie"));
		GameRegistry.register(raw_pumpkin_pie, new ResourceLocation(GrimCuisine.modID, "raw_pumpkin_pie"));
		GameRegistry.register(raw_melon_pie, new ResourceLocation(GrimCuisine.modID, "raw_melon_pie"));
		GameRegistry.register(raw_pork_pie, new ResourceLocation(GrimCuisine.modID, "raw_pork_pie"));
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

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bandage, 2), new Object[] { "P#P", '#', Blocks.wool, 'P', "paper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(healthpack, 1), new Object[] { " # ", "#S#", " # ", '#', Blocks.wool, 'S', Items.sugar }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(healthpack_super, 1), new Object[] { " # ", "#R#", " # ", '#', Blocks.wool, 'R', powered_sugar }));
		health = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(sweets, 8), new Object[] { Items.sugar, "paper" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(powered_sugar, 1), new Object[] { "dustRedstone", Items.sugar }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(powered_sweets, 8), new Object[] { powered_sugar, "paper" }));
		food = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cheese, 9), new Object[] { "O", 'O', CuisineBlocks.cheese_block }));
		cheeseRecipe.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.dye, 3, 3), new Object[] { new ItemStack(cocoa_dust), new ItemStack(cocoa_dust) }));
		cocoaRecipe = RecipeHelper.getLatestIRecipe();

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_bar, 9), new Object[] { "X", 'X', CuisineBlocks.chocolate_block }));
		choc.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(wrapper, 1), new Object[] { "paper", "dyeBlue" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(chocolate_bar_wrapped, 1), new Object[] { new ItemStack(chocolate_bar), new ItemStack(wrapper) }));
		candy = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cocoa_dust, 2), new Object[] { "X", 'X', cocoa_fruit }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_bowl), new Object[] { " X ", "XAX", " B ", 'X', cocoa_dust, 'A', Items.sugar, 'B', Items.milk_bucket }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chocolate_ball, 2), new Object[] { "X", 'X', chocolate_bowl_hot }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(knife, 1), new Object[] { "X  ", " W ", "  W", 'X', "plankWood", 'W', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mixer, 1), new Object[] { "X  ", " XX", " X ", 'X', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pan, 1), new Object[] { "X X", " X ", 'X', "stone" }));
		utensils = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(dough, 2), new Object[] { Items.milk_bucket, "cropWheat", "cropWheat", "egg" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pumpkin_slice, 6), new Object[] { Blocks.pumpkin, new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE) }));
		extra = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(bread_slice, 2), new Object[] { new ItemStack(Items.bread), new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cheese_burger, 1), new Object[] { " C ", "AOA", " C ", 'C', bread_slice, 'A', cheese, 'O', Items.cooked_porkchop }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hot_cheese, 1), new Object[] { " C ", "AAA", " C ", 'C', bread_slice, 'A', cheese }));
		sandwiches = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(eggs_unmixed, 1), new Object[] { "XIX", " M ", 'X', "egg", 'I', butter, 'M', Items.bowl }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(eggs_mixed, 1), new Object[] { eggs_unmixed, new ItemStack(mixer, 1, OreDictionary.WILDCARD_VALUE) }));
		eggs = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_empty_pie, 1), new Object[] { "X", "M", 'X', dough, 'M', pan }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_apple_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', dough, 'M', Items.apple, 'Y', raw_empty_pie }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_chocolate_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', dough, 'M', chocolate_ball, 'Y', raw_empty_pie }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_pork_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', dough, 'M', Items.porkchop, 'Y', raw_empty_pie }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_melon_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', dough, 'M', Items.melon, 'Y', raw_empty_pie }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(raw_pumpkin_pie, 1), new Object[] { " X ", "MMM", " Y ", 'X', dough, 'M', pumpkin_slice, 'Y', raw_empty_pie }));
		pie = RecipeHelper.getLatestIRecipes(6);
	}
}
