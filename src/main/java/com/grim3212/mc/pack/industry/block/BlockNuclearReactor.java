package com.grim3212.mc.pack.industry.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNuclearReactor extends Block {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockNuclearReactor() {
		super(Material.iron);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
	}

	@Override
	public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
		return (Boolean) worldIn.getBlockState(pos).getValue(ACTIVE) ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
		return (Boolean) worldIn.getBlockState(pos).getValue(ACTIVE) ? 15 : 0;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			worldIn.setBlockState(pos, state.cycleProperty(ACTIVE));
			worldIn.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.click", 0.3F, ((Boolean) state.getValue(ACTIVE)) ? 0.6F : 0.5F);
			worldIn.notifyNeighborsOfStateChange(pos, this);
			return true;
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if ((Boolean) state.getValue(ACTIVE)) {
			worldIn.notifyNeighborsOfStateChange(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, meta > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if ((Boolean) state.getValue(ACTIVE)) {
			i = 1;
		}

		return i;
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { ACTIVE });
	}
}
