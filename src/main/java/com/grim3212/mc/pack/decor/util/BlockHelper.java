package com.grim3212.mc.pack.decor.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.block.SilverfishBlock;
import net.minecraft.block.SlabBlock;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockHelper {

	/**
	 * Get a list of all blocks to be used during the creation of furniture,
	 * fireplaces, lamp posts, etc...
	 * 
	 * @return A hashmap of Blocks and their associated metadata if they contain a
	 *         color or variant property
	 */
	public static List<BlockState> getBlocks() {
		List<BlockState> loadedBlocks = new ArrayList<BlockState>();

		if (DecorConfig.useAllBlocks.get()) {
			Iterator<Block> i = ForgeRegistries.BLOCKS.iterator();
			while (i.hasNext()) {
				Block block = (Block) i.next();

				if (block == Blocks.GLASS || block == Blocks.REDSTONE_ORE || block == Blocks.REDSTONE_LAMP) {
					// Do nothing!
				} else if (block == null || block == Blocks.AIR || block instanceof SlabBlock || block instanceof SilverfishBlock || block.hasTileEntity(block.getDefaultState()) || block instanceof RedstoneOreBlock || block instanceof RedstoneLampBlock) {
					continue;
				}
				// Add the block
				loadedBlocks.add(block.getDefaultState());
			}
			return loadedBlocks;
		} else {
			return DecorConfig.decorBlocks;
		}
	}

	public static List<BlockState> getUsableBlocks() {
		List<BlockState> loadedBlocks = new ArrayList<BlockState>();

		if (DecorConfig.useAllBlocks.get()) {
			Iterator<Block> i = ForgeRegistries.BLOCKS.iterator();
			while (i.hasNext()) {
				Block block = (Block) i.next();

				if (block == Blocks.GLASS || block == Blocks.REDSTONE_ORE || block == Blocks.REDSTONE_LAMP) {
					// Do nothing!
				} else if (block == null || block == Blocks.AIR || block instanceof SlabBlock || block instanceof SilverfishBlock || block.hasTileEntity(block.getDefaultState()) || block instanceof RedstoneOreBlock || block instanceof RedstoneLampBlock) {
					continue;
				}

				loadedBlocks.add(block.getDefaultState());
			}
			return loadedBlocks;
		} else {
			return DecorConfig.decorBlocks;
		}
	}

}