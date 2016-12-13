package com.grim3212.mc.pack.industry.block;

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

public class BlockElemental extends BlockManual {

	public static enum ElementType {
		WATER, FIRE, LAVA
	}

	private ElementType type;

	protected BlockElemental(ElementType type) {
		super(Material.IRON, SoundType.METAL);
		this.type = type;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {
			boolean isPowered = worldIn.isBlockPowered(pos);

			if (isPowered) {
				if (worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
					if (type == ElementType.WATER) {
						worldIn.setBlockState(pos.up(), Blocks.FLOWING_WATER.getDefaultState());
					} else if (type == ElementType.FIRE) {
						worldIn.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
					} else if (type == ElementType.LAVA) {
						worldIn.setBlockState(pos.up(), Blocks.FLOWING_LAVA.getDefaultState());
					}
				}
			} else {
				Block upBlock = worldIn.getBlockState(pos.up()).getBlock();
				if (upBlock == Blocks.FLOWING_WATER || upBlock == Blocks.WATER || upBlock == Blocks.LAVA || upBlock == Blocks.FLOWING_LAVA || upBlock == Blocks.FIRE) {
					worldIn.setBlockToAir(pos.up());
				}
			}
		}
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return false;
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		Block upBlock = worldIn.getBlockState(pos.up()).getBlock();
		if (upBlock == Blocks.FLOWING_WATER || upBlock == Blocks.WATER || upBlock == Blocks.LAVA || upBlock == Blocks.FLOWING_LAVA || upBlock == Blocks.FIRE) {
			worldIn.setBlockToAir(pos.up());
		}
	}

	@Override
	public Page getPage(IBlockState state) {
		if (state.getBlock() == IndustryBlocks.fire_block) {
			return ManualIndustry.fireBlock_page;
		} else if (state.getBlock() == IndustryBlocks.water_block) {
			return ManualIndustry.waterBlock_page;
		}

		return ManualIndustry.lavaBlock_page;
	}
}
