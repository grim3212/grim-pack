package com.grim3212.mc.pack.tools.util;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.tools.GrimTools;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChiselRegistry {

	public static List<Pair<Block, Integer>> chiselBlocks = Lists.newArrayList();
	public static List<Pair<Block, Integer>> chiselReturnb = Lists.newArrayList();
	public static List<NonNullList<ItemStack>> chiselItem = Lists.newArrayList();

	public static void registerBlock(ItemStack inputBlock, ItemStack returnBlock, NonNullList<ItemStack> out) {
		registerBlock(Block.getBlockFromItem(inputBlock.getItem()), inputBlock.getMetadata(), Block.getBlockFromItem(returnBlock.getItem()), returnBlock.getMetadata(), out);
	}

	public static void registerBlock(Block block, int bMeta, Block returnBlock, int rbMeta, NonNullList<ItemStack> out) {
		chiselBlocks.add(Pair.of(block, bMeta));
		chiselReturnb.add(Pair.of(returnBlock, rbMeta));
		chiselItem.add(out);

		GrimLog.debugInfo(GrimTools.partName, "Registered chisel recipe " + block.getRegistryName() + " -> " + returnBlock.getRegistryName() + " : " + (out.size() == 0 ? " default drops" : out.toString()));
	}

	@SuppressWarnings("deprecation")
	public static IBlockState getState(int place, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, EntityLivingBase placer, EnumHand hand) {
		return chiselReturnb.get(place).getLeft().getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, chiselReturnb.get(place).getRight(), placer);
	}
}
