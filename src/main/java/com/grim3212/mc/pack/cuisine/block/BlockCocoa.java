package com.grim3212.mc.pack.cuisine.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.init.CuisineNames;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockCocoa extends BlockManual {

	protected static final VoxelShape COCOA_SHAPE = Block.makeCuboidShape(0.28F, 0.25F, 0.28F, 0.72F, 1F, 0.72F);

	protected BlockCocoa() {
		super(CuisineNames.COCOA_BLOCK, Block.Properties.create(Material.CIRCUITS).sound(SoundType.CLOTH).hardnessAndResistance(1.0f));
	}

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return COCOA_SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return COCOA_SHAPE;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(CuisineItems.cocoa_fruit);
	}

	@Override
	public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
		return CuisineItems.cocoa_fruit;
	}

	@Override
	public boolean isValidPosition(IBlockState state, IWorldReaderBase worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return block instanceof BlockLeaves;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		this.checkBlock(worldIn, pos);
	}

	private void checkBlock(World world, BlockPos pos) {
		if (!isValidPosition(world.getBlockState(pos), world, pos)) {
			world.destroyBlock(pos, true);
		}
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualCuisine.cocoaFruit_page;
	}
}