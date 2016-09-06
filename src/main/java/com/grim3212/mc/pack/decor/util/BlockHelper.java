package com.grim3212.mc.pack.decor.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.google.common.collect.ImmutableSet;
import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockRedstoneLight;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHelper {

	/**
	 * Get a list of all blocks to be used during the creation of furniture,
	 * fireplaces, lamp posts, etc...
	 * 
	 * @return A hashmap of Blocks and their associated metadata if they contain
	 *         a color or variant property
	 */
	@SuppressWarnings({ "rawtypes" })
	public static LinkedHashMap<Block, Integer> getBlocks() {
		LinkedHashMap<Block, Integer> loadedBlocks = new LinkedHashMap<Block, Integer>();

		if (DecorConfig.useAllBlocks) {
			Iterator<Block> i = Block.REGISTRY.iterator();
			while (i.hasNext()) {
				Block block = (Block) i.next();

				if (block == Blocks.GLASS || block == Blocks.REDSTONE_ORE || block == Blocks.REDSTONE_LAMP) {
					// Do nothing!
				} else if (block == null || block == Blocks.AIR || block instanceof BlockSlab || block instanceof BlockSilverfish || block.hasTileEntity(block.getDefaultState()) || !block.getDefaultState().isNormalCube() || !block.getDefaultState().isOpaqueCube() || block instanceof BlockRedstoneOre || block instanceof BlockRedstoneLight) {
					continue;
				}

				if (!block.getDefaultState().getProperties().isEmpty()) {
					for (IProperty prop : (ImmutableSet<IProperty<?>>) (block.getDefaultState().getProperties().keySet())) {
						// Shade is used by Flat Colored Blocks
						if ((prop.getName().equals("variant") || prop.getName().equals("color") || prop.getName().equals("type") || prop.getName().equals("shade")) && !(block instanceof BlockHugeMushroom)) {
							loadedBlocks.put(block, prop.getAllowedValues().size());
						} else {
							loadedBlocks.put(block, 0);
						}
					}
				} else {
					loadedBlocks.put(block, 0);
				}
			}
		} else {
			String[] blocks = DecorConfig.decorationBlocks;

			if (blocks.length > 0) {
				for (String u : blocks) {
					Block block = Block.REGISTRY.getObject(new ResourceLocation(u));

					if (!block.getDefaultState().getProperties().isEmpty()) {
						for (IProperty prop : (ImmutableSet<IProperty<?>>) (block.getDefaultState().getProperties().keySet())) {
							// Shade is used by Flat Colored Blocks
							if ((prop.getName().equals("variant") || prop.getName().equals("color") || prop.getName().equals("type") || prop.getName().equals("shade")) && !(block instanceof BlockHugeMushroom)) {
								loadedBlocks.put(block, prop.getAllowedValues().size());

							} else {
								loadedBlocks.put(block, 0);
							}
						}
					} else {
						loadedBlocks.put(block, 0);
					}
				}
			}

		}
		return loadedBlocks;
	}

	public static ArrayList<Block> getUsableBlocks() {
		ArrayList<Block> loadedBlocks = new ArrayList<Block>();

		if (DecorConfig.useAllBlocks) {
			Iterator<Block> i = Block.REGISTRY.iterator();
			while (i.hasNext()) {
				Block block = (Block) i.next();

				if (block == Blocks.GLASS || block == Blocks.REDSTONE_ORE || block == Blocks.REDSTONE_LAMP) {
					// Do nothing!
				} else if (block == null || block == Blocks.AIR || block instanceof BlockSlab || block instanceof BlockSilverfish || block.hasTileEntity(block.getDefaultState()) || !block.getDefaultState().isNormalCube() || !block.getDefaultState().isOpaqueCube() || block instanceof BlockRedstoneOre || block instanceof BlockRedstoneLight) {
					continue;
				}

				loadedBlocks.add(block);
			}
		} else {
			String[] blocks = DecorConfig.decorationBlocks;

			if (blocks.length > 0) {
				for (String u : blocks) {
					Block block = Block.REGISTRY.getObject(new ResourceLocation(u));
					loadedBlocks.add(block);
				}
			}
		}
		return loadedBlocks;
	}

	/**
	 * Retrieve the input states block strength
	 * 
	 * Copied from ForgeHooks to allow for custom input
	 * 
	 * @param state
	 * @param player
	 * @param world
	 * @param pos
	 * @return The blockstates blockStrength
	 */
	public static float blockStrength(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
		float hardness = state.getBlockHardness(world, pos);
		if (hardness < 0.0F) {
			return 0.0F;
		}

		if (!canHarvestBlock(state, player, world, pos)) {
			return player.getDigSpeed(state, pos) / hardness / 100F;
		} else {
			return player.getDigSpeed(state, pos) / hardness / 30F;
		}
	}

	/**
	 * IBlockState instead of Block version of canHarvestBlock from ForgeHooks
	 * 
	 * @param state
	 * @param player
	 * @param world
	 * @param pos
	 * @return If the player can harvest this block
	 */
	public static boolean canHarvestBlock(IBlockState state, EntityPlayer player, IBlockAccess world, BlockPos pos) {
		Block block = state.getBlock();

		if (state.getMaterial().isToolNotRequired()) {
			return true;
		}

		ItemStack stack = player.inventory.getCurrentItem();
		String tool = block.getHarvestTool(state);
		if (stack == null || tool == null) {
			return player.canHarvestBlock(state);
		}

		int toolLevel = stack.getItem().getHarvestLevel(stack, tool, player, state);
		if (toolLevel < 0) {
			return player.canHarvestBlock(state);
		}

		return toolLevel >= block.getHarvestLevel(state);
	}
}