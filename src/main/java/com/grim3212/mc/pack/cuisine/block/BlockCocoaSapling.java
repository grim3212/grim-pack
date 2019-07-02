package com.grim3212.mc.pack.cuisine.block;

import java.util.Random;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.init.CuisineNames;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;
import com.grim3212.mc.pack.cuisine.world.CocoaTreeFeature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BlockCocoaSapling extends SaplingBlock implements IManualBlock {

	protected BlockCocoaSapling() {
		super(new CocoaTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CLOTH));
		this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, 0));
		setRegistryName(CuisineNames.COCOA_TREE_SAPLING);
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		return new ItemStack(CuisineItems.cocoa_fruit);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualCuisine.cocoaFruit_page;
	}

	public static class CocoaTree extends Tree {
		@Override
		protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
			return new CocoaTreeFeature(true, false);
		}
	}
}
