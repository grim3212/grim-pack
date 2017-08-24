package com.grim3212.mc.pack.compat.jei.recipes.chisel;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.tools.util.ChiselRegistry;

import mezz.jei.api.IJeiHelpers;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public class ChiselRecipeMaker {

	private ChiselRecipeMaker() {
	}

	@SuppressWarnings("deprecation")
	public static List<ChiselRecipeWrapper> getChiselRecipes(IJeiHelpers helpers) {
		List<ChiselRecipeWrapper> recipes = Lists.newArrayList();

		List<Pair<Block, Integer>> chiselBlocks = ChiselRegistry.chiselBlocks;
		List<Pair<Block, Integer>> chiselReturnBlocks = ChiselRegistry.chiselReturnb;
		List<NonNullList<ItemStack>> chiselItems = ChiselRegistry.chiselItem;

		for (int i = 0; i < chiselBlocks.size(); i++) {

			if (chiselItems.get(i).isEmpty()) {
				Block block = chiselBlocks.get(i).getLeft();

				NonNullList<ItemStack> drops = NonNullList.create();
				block.getDrops(drops, Minecraft.getMinecraft().world, BlockPos.ORIGIN, block.getStateFromMeta(chiselBlocks.get(i).getRight()), 0);

				recipes.add(new ChiselRecipeWrapper(chiselBlocks.get(i).getLeft(), chiselBlocks.get(i).getRight(), chiselReturnBlocks.get(i).getLeft(), chiselReturnBlocks.get(i).getRight(), drops, true));
			} else {
				recipes.add(new ChiselRecipeWrapper(chiselBlocks.get(i).getLeft(), chiselBlocks.get(i).getRight(), chiselReturnBlocks.get(i).getLeft(), chiselReturnBlocks.get(i).getRight(), chiselItems.get(i), false));
			}
		}

		return recipes;
	}
}
