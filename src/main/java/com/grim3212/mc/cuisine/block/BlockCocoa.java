package com.grim3212.mc.cuisine.block;

import java.util.Random;

import com.grim3212.mc.cuisine.item.CuisineItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockCocoa extends Block {

	protected BlockCocoa() {
		super(Material.circuits);
		this.setBlockBounds(0.28F, 0.25F, 0.28F, 0.72F, 1F, 0.72F);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(CuisineItems.cocoa_fruit);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return CuisineItems.cocoa_fruit;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return block instanceof BlockLeaves;
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		this.checkBlock(worldIn, pos);
	}

	private void checkBlock(World world, BlockPos pos) {
		if (!canBlockStay(world, pos)) {
			dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
			world.setBlockToAir(pos);
		}
	}

	public boolean canBlockStay(World world, BlockPos pos) {
		return canPlaceBlockAt(world, pos);
	}
}