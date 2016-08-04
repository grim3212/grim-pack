package com.grim3212.mc.pack.decor.block;

import java.util.Random;

import com.grim3212.mc.pack.decor.GrimDecor;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockFireplace extends BlockFireplaceBase {

	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

	public BlockFireplace() {
		this.setDefaultState(blockState.getBaseState().withProperty(EAST, false).withProperty(WEST, false).withProperty(SOUTH, false).withProperty(NORTH, false).withProperty(ACTIVE, false));
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.getBlockState(pos).getValue(ACTIVE) && worldIn.getBlockState(pos.up()).getBlock() == DecorBlocks.chimney) {
			int smokeheight = 1;
			while (worldIn.getBlockState(pos.up(smokeheight)).getBlock() == DecorBlocks.chimney) {
				smokeheight++;
			}

			GrimDecor.proxy.produceSmoke(worldIn, pos.up(smokeheight), 0.5D, 0.0D, 0.5D, 1, true);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { NORTH, SOUTH, WEST, EAST, ACTIVE }, new IUnlistedProperty[] { BLOCK_STATE });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVE) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, meta == 1 ? true : false);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(NORTH, this.canConnectTo(worldIn, pos.north())).withProperty(EAST, this.canConnectTo(worldIn, pos.east())).withProperty(SOUTH, this.canConnectTo(worldIn, pos.south())).withProperty(WEST, this.canConnectTo(worldIn, pos.west()));
	}

	public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		IBlockState blockState = worldIn.getBlockState(pos).getBlock().getDefaultState();
		return blockState == this.getDefaultState();
	}
}