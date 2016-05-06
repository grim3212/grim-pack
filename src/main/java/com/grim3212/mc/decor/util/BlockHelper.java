package com.grim3212.mc.decor.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import com.grim3212.mc.decor.config.DecorConfig;

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
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameData;

public class BlockHelper {

	/**
	 * Get a list of all blocks to be used during the creation of furniture,
	 * fireplaces, lamp posts, etc...
	 * 
	 * @return A hashmap of Blocks and their associated metadata if they contain
	 *         a color or variant property
	 */
	@SuppressWarnings("rawtypes")
	public static LinkedHashMap<Block, Integer> getBlocks() {
		LinkedHashMap<Block, Integer> loadedBlocks = new LinkedHashMap<Block, Integer>();

		if (DecorConfig.useAllBlocks) {
			Iterator<Block> i = GameData.getBlockRegistry().iterator();
			while (i.hasNext()) {
				Block block = (Block) i.next();

				if (block == Blocks.glass || block == Blocks.redstone_ore || block == Blocks.redstone_lamp) {
					// Do nothing!
				} else if (block == null || block == Blocks.air || block instanceof BlockSlab || block instanceof BlockSilverfish || block.hasTileEntity(block.getDefaultState()) || !block.isNormalCube() || !block.isOpaqueCube() || block instanceof BlockRedstoneOre || block instanceof BlockRedstoneLight) {
					continue;
				}

				if (!block.getDefaultState().getProperties().isEmpty()) {
					for (IProperty prop : (Set<IProperty>) (block.getDefaultState().getProperties().keySet())) {
						if ((prop.getName().equals("variant") || prop.getName().equals("color") || prop.getName().equals("type")) && !(block instanceof BlockHugeMushroom)) {
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
					Block block = GameData.getBlockRegistry().getObject(new ResourceLocation(u));

					if (!block.getDefaultState().getProperties().isEmpty()) {
						for (IProperty prop : (Set<IProperty>) (block.getDefaultState().getProperties().keySet())) {
							if ((prop.getName().equals("variant") || prop.getName().equals("color") || prop.getName().equals("type")) && !(block instanceof BlockHugeMushroom)) {
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
		float hardness = state.getBlock().getBlockHardness(world, pos);
		if (hardness < 0.0F) {
			return 0.0F;
		}

		if (!canHarvestBlock(state, player, world, pos)) {
			return player.getBreakSpeed(state, pos) / hardness / 100F;
		} else {
			return player.getBreakSpeed(state, pos) / hardness / 30F;
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

		if (block.getMaterial().isToolNotRequired()) {
			return true;
		}

		ItemStack stack = player.inventory.getCurrentItem();
		String tool = block.getHarvestTool(state);
		if (stack == null || tool == null) {
			return player.canHarvestBlock(block);
		}

		int toolLevel = stack.getItem().getHarvestLevel(stack, tool);
		if (toolLevel < 0) {
			return player.canHarvestBlock(block);
		}

		return toolLevel >= block.getHarvestLevel(state);
	}
}