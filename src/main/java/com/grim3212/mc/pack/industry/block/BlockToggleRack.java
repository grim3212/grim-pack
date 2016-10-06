package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockToggleRack extends BlockManual {

	public BlockToggleRack() {
		super(Material.ROCK, SoundType.STONE);
		this.setHardness(0.6F);
		this.setResistance(1.0F);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			if (!worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, getDefaultState(), 2);
			} else if (worldIn.isBlockPowered(pos) && worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() == Blocks.AIR) {
				worldIn.setBlockState(pos.add(0, 1, 0), Blocks.FIRE.getDefaultState(), 2);
			}
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block neighborBlock) {
		if (!worldIn.isRemote) {
			if (!worldIn.isBlockPowered(pos)) {
				worldIn.scheduleUpdate(pos, this, 4);
			} else if (worldIn.isBlockPowered(pos) && worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() == Blocks.AIR) {
				worldIn.setBlockState(pos.add(0, 1, 0), Blocks.FIRE.getDefaultState(), 2);
			}
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			if (!worldIn.isBlockPowered(pos) && worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() == Blocks.FIRE) {
				worldIn.setBlockToAir(pos.add(0, 1, 0));
			}
		}
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.togglerack_page;
	}
}