package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFireSensor extends BlockManual {

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockFireSensor() {
		super(Material.ROCK, SoundType.STONE);
		this.setTickRandomly(true);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(POWERED, false));
	}

	@Override
	public int tickRate(World worldIn) {
		return 20;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (isFireNearby(worldIn, pos)) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, true));
			worldIn.playSound(null, pos, SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 0.3F, 0.6F);
		} else {
			worldIn.setBlockState(pos, state.withProperty(POWERED, false));
		}
	}

	protected boolean isFireNearby(World world, BlockPos pos) {
		for (int l = pos.getX() - 5; l <= pos.getX() + 5; l++) {
			for (int i1 = pos.getY() - 5; i1 <= pos.getY() - 1; i1++) {
				for (int j1 = pos.getZ() - 5; j1 <= pos.getZ() + 5; j1++) {
					if (world.getBlockState(new BlockPos(l, i1, j1)).getBlock() == Blocks.FIRE) {
						return true;
					}
				}

			}

		}

		return false;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return state.getValue(POWERED);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWERED) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWERED, meta == 1);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { POWERED });
	}

	@Override
	public Page getPage(IBlockState state) {
		return null;
	}

}
