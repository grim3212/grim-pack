package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRwayLight extends BlockManual {

	public BlockRwayLight(boolean light) {
		super(light ? IndustryNames.RWAY_LIGHT_ON : IndustryNames.RWAY_LIGHT_OFF, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).lightValue(light ? 15 : 0).hardnessAndResistance(1.0f, 15.0f));
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
		boolean powered = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
		if (powered) {
			worldIn.setBlockState(pos, IndustryBlocks.rway_light_on.getDefaultState());
		} else if (!powered) {
			worldIn.setBlockState(pos, IndustryBlocks.rway_light_off.getDefaultState());
		}
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.rways_page;
	}
}
