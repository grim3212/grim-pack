package com.grim3212.mc.pack.core.util;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.item.ItemBlockGrim;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Utils {

	public static void registerBlock(Block block, String name) {
		GameRegistry.register(block, new ResourceLocation(GrimPack.modID, name));
		GameRegistry.register(new ItemBlockGrim(block), block.getRegistryName());
	}

	public static void registerBlock(Block block, String name, ItemBlock item) {
		GameRegistry.register(block, new ResourceLocation(GrimPack.modID, name));
		GameRegistry.register(item, block.getRegistryName());
	}

	public static void registerItem(Item item, String name) {
		GameRegistry.register(item, new ResourceLocation(GrimPack.modID, name));
	}
}
