package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockToggleRack extends BlockManual {

	public BlockToggleRack() {
		super(IndustryNames.TOGGLE_RACK, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.6f, 1.0f));
	}

	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState neighbor, boolean flag) {
		if (!worldIn.isRemote) {
			if (!worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, getDefaultState(), 2);
			} else if (worldIn.isBlockPowered(pos) && worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() == Blocks.AIR) {
				worldIn.setBlockState(pos.add(0, 1, 0), Blocks.FIRE.getDefaultState(), 2);
			}
		}
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
		if (!worldIn.isRemote) {
			if (!worldIn.isBlockPowered(pos)) {
				worldIn.getPendingBlockTicks().scheduleTick(pos, this, 4);
			} else if (worldIn.isBlockPowered(pos) && worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() == Blocks.AIR) {
				worldIn.setBlockState(pos.add(0, 1, 0), Blocks.FIRE.getDefaultState(), 2);
			}
		}
	}

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		if (!worldIn.isRemote) {
			if (!worldIn.isBlockPowered(pos) && worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() == Blocks.FIRE) {
				worldIn.destroyBlock(pos.add(0, 1, 0), false);
			}
		}
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.togglerack_page;
	}
}