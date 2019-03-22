package com.grim3212.mc.pack.cuisine.block;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;
import com.grim3212.mc.pack.cuisine.init.CuisineNames;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class CuisineBlocks {

	@ObjectHolder(CuisineNames.BUTTER_CHURN)
	public static Block butter_churn;
	@ObjectHolder(CuisineNames.CHEESE_BLOCK)
	public static Block cheese_block;
	@ObjectHolder(CuisineNames.CHEESE_MAKER)
	public static Block cheese_maker;
	@ObjectHolder(CuisineNames.COCOA_BLOCK)
	public static Block cocoa_block;
	@ObjectHolder(CuisineNames.COCOA_TREE_SAPLING)
	public static Block cocoa_tree_sapling;
	@ObjectHolder(CuisineNames.CHOCOLATE_BAR_MOULD)
	public static Block chocolate_bar_mould;
	@ObjectHolder(CuisineNames.CHOCOLATE_CAKE)
	public static Block chocolate_cake;
	@ObjectHolder(CuisineNames.CHOCOLATE_BLOCK)
	public static Block chocolate_block;
	@ObjectHolder(CuisineNames.APPLE_PIE)
	public static Block apple_pie;
	@ObjectHolder(CuisineNames.MELON_PIE)
	public static Block melon_pie;
	@ObjectHolder(CuisineNames.PUMPKIN_PIE)
	public static Block pumpkin_pie;
	@ObjectHolder(CuisineNames.CHOCOLATE_PIE)
	public static Block chocolate_pie;
	@ObjectHolder(CuisineNames.PORK_PIE)
	public static Block pork_pie;

	@SubscribeEvent
	public void initBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		if (CuisineConfig.subpartDairy.get()) {
			r.register(new BlockCheeseMaker());
			r.register(new BlockCheese());
			r.register(new BlockButterchurn());
		}

		if (CuisineConfig.subpartChocolate.get()) {
			r.register(new BlockCocoa());
			r.register(new BlockCocoaSapling());
			r.register(new BlockCBarMould());
			r.register(new BlockChocolateCake());
			r.register(new BlockChocolateBlock());
		}

		if (CuisineConfig.subpartPie.get()) {
			r.register(new BlockPie(CuisineNames.APPLE_PIE));
			r.register(new BlockPie(CuisineNames.MELON_PIE));
			r.register(new BlockPie(CuisineNames.PUMPKIN_PIE));
			r.register(new BlockPie(CuisineNames.CHOCOLATE_PIE));
			r.register(new BlockPie(CuisineNames.PORK_PIE));
		}
	}

	@SubscribeEvent
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (CuisineConfig.subpartDairy.get()) {
			r.register(new ItemManualBlock(cheese_block, new Item.Properties().group(GrimItemGroups.GRIM_CUISINE)).setRegistryName(cheese_block.getRegistryName()));
			r.register(new ItemManualBlock(cheese_maker, new Item.Properties().group(GrimItemGroups.GRIM_CUISINE)).setRegistryName(cheese_maker.getRegistryName()));
			r.register(new ItemManualBlock(butter_churn, new Item.Properties().group(GrimItemGroups.GRIM_CUISINE)).setRegistryName(butter_churn.getRegistryName()));
		}

		if (CuisineConfig.subpartChocolate.get()) {
			r.register(new ItemManualBlock(chocolate_bar_mould, new Item.Properties().group(GrimItemGroups.GRIM_CUISINE)).setRegistryName(chocolate_bar_mould.getRegistryName()));
			r.register(new ItemManualBlock(chocolate_cake, new Item.Properties().group(GrimItemGroups.GRIM_CUISINE)).setRegistryName(chocolate_cake.getRegistryName()));
			r.register(new ItemManualBlock(chocolate_block, new Item.Properties().group(GrimItemGroups.GRIM_CUISINE)).setRegistryName(chocolate_block.getRegistryName()));
		}

		if (CuisineConfig.subpartPie.get()) {
			r.register(new ItemManualBlock(apple_pie, new Item.Properties().group(GrimItemGroups.GRIM_CUISINE)).setRegistryName(apple_pie.getRegistryName()));
			r.register(new ItemManualBlock(melon_pie, new Item.Properties().group(GrimItemGroups.GRIM_CUISINE)).setRegistryName(melon_pie.getRegistryName()));
			r.register(new ItemManualBlock(pumpkin_pie, new Item.Properties().group(GrimItemGroups.GRIM_CUISINE)).setRegistryName(pumpkin_pie.getRegistryName()));
			r.register(new ItemManualBlock(chocolate_pie, new Item.Properties().group(GrimItemGroups.GRIM_CUISINE)).setRegistryName(chocolate_pie.getRegistryName()));
			r.register(new ItemManualBlock(pork_pie, new Item.Properties().group(GrimItemGroups.GRIM_CUISINE)).setRegistryName(pork_pie.getRegistryName()));
		}
	}
}