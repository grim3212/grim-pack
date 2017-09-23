package com.grim3212.mc.pack.cuisine.item;

import com.grim3212.mc.pack.core.item.ItemManualFood;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;

import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

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
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (CuisineConfig.subpartDairy || CuisineConfig.subpartPie)
			r.register(knife);

		if (CuisineConfig.subpartSoda)
			r.register(soda);

		if (CuisineConfig.subpartDragonFruit)
			r.register(dragon_fruit);

		if (CuisineConfig.subpartHealth) {
			r.register(powered_sugar);
			r.register(powered_sweets);
			r.register(bandage);
			r.register(healthpack);
			r.register(sweets);
			r.register(healthpack_super);
		}

		if (CuisineConfig.subpartDairy) {
			r.register(mixer);
			r.register(butter);
			r.register(cheese);
			r.register(bread_slice);
			r.register(cheese_burger);
			r.register(hot_cheese);
			r.register(eggs_mixed);
			r.register(eggs_unmixed);
			r.register(eggs_cooked);
		}

		if (CuisineConfig.subpartChocolate) {
			r.register(cocoa_fruit);
			r.register(cocoa_dust);
			r.register(chocolate_ball);
			r.register(wrapper);
			r.register(chocolate_bowl);
			r.register(chocolate_bar);
			r.register(chocolate_bar_wrapped);
			r.register(chocolate_bowl_hot);
		}

		if (CuisineConfig.subpartPie) {
			r.register(dough);
			r.register(pan);
			r.register(pumpkin_slice);
			r.register(raw_empty_pie);
			r.register(raw_chocolate_pie);
			r.register(raw_apple_pie);
			r.register(raw_melon_pie);
			r.register(raw_pork_pie);
			r.register(raw_pumpkin_pie);
		}

		initOreDict();
	}

	private void initOreDict() {
		if (CuisineConfig.subpartDairy) {
			OreDictionary.registerOre("bread", bread_slice);
			OreDictionary.registerOre("listAllbread", bread_slice);
			OreDictionary.registerOre("foodCheese", cheese);
			OreDictionary.registerOre("foodButter", butter);
			OreDictionary.registerOre("foodScrambledegg", eggs_cooked);
			OreDictionary.registerOre("toolJuicer", new ItemStack(mixer, 1, OreDictionary.WILDCARD_VALUE));
		}

		if (CuisineConfig.subpartPie)
			OreDictionary.registerOre("foodDough", dough);

		if (CuisineConfig.subpartDairy || CuisineConfig.subpartPie) {
			OreDictionary.registerOre("toolCuttingboard", new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE));
		}

		OreDictionary.registerOre("bucketMilk", Items.MILK_BUCKET);
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		if (CuisineConfig.subpartChocolate)
			GameRegistry.addSmelting(chocolate_bowl, new ItemStack(chocolate_bowl_hot), 0.3F);
		if (CuisineConfig.subpartDairy)
			GameRegistry.addSmelting(eggs_mixed, new ItemStack(eggs_cooked), 0.35F);
	}
}