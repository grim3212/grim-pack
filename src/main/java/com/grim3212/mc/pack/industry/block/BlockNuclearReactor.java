package com.grim3212.mc.pack.industry.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNuclearReactor extends Block {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockNuclearReactor() {
		super(Material.IRON);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockAccess.getBlockState(pos).getValue(ACTIVE) ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockAccess.getBlockState(pos).getValue(ACTIVE) ? 15 : 0;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			worldIn.setBlockState(pos, state.cycleProperty(ACTIVE));
			worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, state.getValue(ACTIVE) ? 0.6F : 0.5F);
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

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { ACTIVE });
	}
}
