package com.grim3212.mc.pack.cuisine.block;

import java.util.Random;

import com.grim3212.mc.pack.cuisine.item.CuisineItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCocoa extends Block {

	protected static final AxisAlignedBB COCOA_AABB = new AxisAlignedBB(0.28F, 0.25F, 0.28F, 0.72F, 1F, 0.72F);

	protected BlockCocoa() {
		super(Material.CIRCUITS);
		setSoundType(SoundType.CLOTH);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return COCOA_AABB;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
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
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
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