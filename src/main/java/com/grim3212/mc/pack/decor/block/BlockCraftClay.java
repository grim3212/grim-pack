package com.grim3212.mc.pack.decor.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockCraftClay extends Block {

	private static final int numCycles = 7;
	public static final PropertyInteger CYCLE = PropertyInteger.create("cycle", 0, numCycles);

	protected BlockCraftClay() {
		super(Material.circuits);
		this.setDefaultState(this.blockState.getBaseState().withProperty(CYCLE, 0));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		int meta = worldIn.getBlockState(pos).getBlock().getMetaFromState(state);
		if (meta == numCycles) {
			meta = 0;
		} else {
			meta++;
		}
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(CYCLE, meta), 2);
		worldIn.markBlockForUpdate(pos);
		return true;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
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
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { CYCLE });
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		return null;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
