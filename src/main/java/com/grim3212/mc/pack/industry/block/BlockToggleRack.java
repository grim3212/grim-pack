package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockToggleRack extends Block {

	public BlockToggleRack() {
		super(Material.rock);
		this.setHardness(0.6F);
		this.setStepSound(Block.soundTypeStone);
		this.setResistance(1.0F);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			if (!worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, getDefaultState(), 2);
			} else if (worldIn.isBlockPowered(pos) && worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() == Blocks.air) {
				worldIn.setBlockState(pos.add(0, 1, 0), Blocks.fire.getDefaultState(), 2);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!worldIn.isRemote) {
			if (!worldIn.isBlockPowered(pos)) {
				worldIn.scheduleUpdate(pos, this, 4);
			} else if (worldIn.isBlockPowered(pos) && worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() == Blocks.air) {
				worldIn.setBlockState(pos.add(0, 1, 0), Blocks.fire.getDefaultState(), 2);
			}
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			if (!worldIn.isBlockPowered(pos) && worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() == Blocks.fire) {
				worldIn.setBlockToAir(pos.add(0, 1, 0));
			}
		}
	}
}