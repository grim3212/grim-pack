package com.grim3212.mc.pack.decor.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCraftClay extends Block {

	private static final int numCycles = 7;
	public static final PropertyInteger CYCLE = PropertyInteger.create("cycle", 0, numCycles);

	protected BlockCraftClay() {
		super(Material.CIRCUITS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(CYCLE, 0));
	}

	@Override
	public boolean onBlockActivated(World worldIn, net.minecraft.util.math.BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		int meta = worldIn.getBlockState(pos).getBlock().getMetaFromState(state);
		if (meta == numCycles) {
			meta = 0;
		} else {
			meta++;
		}
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(CYCLE, meta), 2);
		return true;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (Integer) state.getValue(CYCLE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(CYCLE, meta);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { CYCLE });
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isNormalCube(IBlockState blockState) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}
}
