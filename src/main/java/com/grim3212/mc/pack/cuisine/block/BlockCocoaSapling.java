package com.grim3212.mc.pack.cuisine.block;

import java.util.Random;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.init.CuisineNames;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;
import com.grim3212.mc.pack.cuisine.world.CocoaTreeFeature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.trees.AbstractTree;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BlockCocoaSapling extends BlockSapling implements IManualBlock {

	protected BlockCocoaSapling() {
		super(new CocoaTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CLOTH));
		this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, 0));
		setRegistryName(CuisineNames.COCOA_TREE_SAPLING);
	}

	@Override
	public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
		return CuisineItems.cocoa_fruit;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(CuisineItems.cocoa_fruit);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualCuisine.cocoaFruit_page;
	}

	public static class CocoaTree extends AbstractTree {
		@Override
		protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
			return new CocoaTreeFeature(true, false);
		}
	}
}
