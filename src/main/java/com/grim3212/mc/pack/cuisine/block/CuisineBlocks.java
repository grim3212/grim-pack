package com.grim3212.mc.pack.cuisine.block;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class CuisineBlocks {

	public static final Block butter_churn = new BlockButterchurn();
	public static final Block cheese_block = new BlockCheese();
	public static final Block cheese_maker = new BlockCheeseMaker();
	public static final Block cocoa_block = new BlockCocoa();
	public static final Block cocoa_tree_sapling = new BlockCocoaSapling();
	public static final Block chocolate_bar_mould = new BlockCBarMould();
	public static final Block chocolate_cake = new BlockChocolateCake();
	public static final Block chocolate_block = new BlockChocolateBlock();
	public static final Block apple_pie = new BlockPie("apple_pie");
	public static final Block melon_pie = new BlockPie("melon_pie");
	public static final Block pumpkin_pie = new BlockPie("pumpkin_pie");
	public static final Block chocolate_pie = new BlockPie("chocolate_pie");
	public static final Block pork_pie = new BlockPie("pork_pie");

	@SubscribeEvent
	public void initBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		if (CuisineConfig.subpartDairy) {
			r.register(cheese_block);
			r.register(cheese_maker);
			r.register(butter_churn);
		}

		if (CuisineConfig.subpartChocolate) {
			r.register(cocoa_block);
			r.register(cocoa_tree_sapling);
			r.register(chocolate_bar_mould);
			r.register(chocolate_cake);
			r.register(chocolate_block);
		}

		if (CuisineConfig.subpartPie) {
			r.register(apple_pie);
			r.register(melon_pie);
			r.register(pumpkin_pie);
			r.register(chocolate_pie);
			r.register(pork_pie);
		}
	}

	@SubscribeEvent
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (CuisineConfig.subpartDairy) {
			r.register(new ItemManualBlock(cheese_block).setRegistryName(cheese_block.getRegistryName()));
			r.register(new ItemManualBlock(cheese_maker).setRegistryName(cheese_maker.getRegistryName()));
			r.register(new ItemManualBlock(butter_churn).setRegistryName(butter_churn.getRegistryName()));
		}

		if (CuisineConfig.subpartChocolate) {
			r.register(new ItemManualBlock(cocoa_block).setRegistryName(cocoa_block.getRegistryName()));
			r.register(new ItemManualBlock(cocoa_tree_sapling).setRegistryName(cocoa_tree_sapling.getRegistryName()));
			r.register(new ItemManualBlock(chocolate_bar_mould).setRegistryName(chocolate_bar_mould.getRegistryName()));
			r.register(new ItemManualBlock(chocolate_cake).setRegistryName(chocolate_cake.getRegistryName()));
			r.register(new ItemManualBlock(chocolate_block).setRegistryName(chocolate_block.getRegistryName()));
		}

		if (CuisineConfig.subpartPie) {
			r.register(new ItemManualBlock(apple_pie).setRegistryName(apple_pie.getRegistryName()));
			r.register(new ItemManualBlock(melon_pie).setRegistryName(melon_pie.getRegistryName()));
			r.register(new ItemManualBlock(pumpkin_pie).setRegistryName(pumpkin_pie.getRegistryName()));
			r.register(new ItemManualBlock(chocolate_pie).setRegistryName(chocolate_pie.getRegistryName()));
			r.register(new ItemManualBlock(pork_pie).setRegistryName(pork_pie.getRegistryName()));
		}
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		if (CuisineConfig.subpartPie) {
			GameRegistry.addSmelting(CuisineItems.raw_apple_pie, new ItemStack(apple_pie), 0.35f);
			GameRegistry.addSmelting(CuisineItems.raw_pork_pie, new ItemStack(pork_pie), 0.35f);
			GameRegistry.addSmelting(CuisineItems.raw_chocolate_pie, new ItemStack(chocolate_pie), 0.35f);
			GameRegistry.addSmelting(CuisineItems.raw_pumpkin_pie, new ItemStack(pumpkin_pie), 0.35f);
			GameRegistry.addSmelting(CuisineItems.raw_melon_pie, new ItemStack(melon_pie), 0.35f);
		}
	}
}