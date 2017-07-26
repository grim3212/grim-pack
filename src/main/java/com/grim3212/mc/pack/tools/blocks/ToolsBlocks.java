package com.grim3212.mc.pack.tools.blocks;

import com.grim3212.mc.pack.core.item.ItemManualBlock;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class ToolsBlocks {

	public static final Block black_diamond_block = new BlockBlackDiamond(Material.IRON, SoundType.METAL);
	public static final Block black_diamond_ore = new BlockBlackDiamond(Material.ROCK, SoundType.STONE);
	public static final Block element_115_ore = new BlockElement();

	@SubscribeEvent
	public static void initBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		r.register(black_diamond_block);
		r.register(black_diamond_ore);
		r.register(element_115_ore);
	}

	@SubscribeEvent
	public static void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		r.register(new ItemManualBlock(black_diamond_ore).setRegistryName(black_diamond_ore.getRegistryName()));
		r.register(new ItemManualBlock(black_diamond_block).setRegistryName(black_diamond_block.getRegistryName()));
		r.register(new ItemManualBlock(element_115_ore).setRegistryName(element_115_ore.getRegistryName()));

		initOreDict();
	}

	private static void initOreDict() {
		OreDictionary.registerOre("oreBlackDiamond", black_diamond_ore);
		OreDictionary.registerOre("blockBlackDiamond", black_diamond_block);
		OreDictionary.registerOre("oreElement115", element_115_ore);
	}
}