package com.grim3212.mc.industry.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockElemental extends Block {

	public static enum ElementType {
		WATER, FIRE, LAVA
	}

	private ElementType type;

	protected BlockElemental(ElementType type) {
		super(Material.iron);
		this.type = type;
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!worldIn.isRemote) {
			boolean isPowered = worldIn.isBlockPowered(pos);

			if (isPowered) {
				if (worldIn.getBlockState(pos.up()).getBlock() == Blocks.air) {
					if (type == ElementType.WATER) {
						worldIn.setBlockState(pos.up(), Blocks.flowing_water.getDefaultState());
					} else if (type == ElementType.FIRE) {
						worldIn.setBlockState(pos.up(), Blocks.fire.getDefaultState());
					} else if (type == ElementType.LAVA) {
						worldIn.setBlockState(pos.up(), Blocks.flowing_lava.getDefaultState());
					}
				}
			} else {
				Block upBlock = worldIn.getBlockState(pos.up()).getBlock();
				if (upBlock == Blocks.flowing_water || upBlock == Blocks.water || upBlock == Blocks.lava || upBlock == Blocks.flowing_lava || upBlock == Blocks.fire) {
					worldIn.setBlockToAir(pos.up());
				}
			}
		}
	}

	@Override
	public boolean canProvidePower() {
		return false;
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		Block upBlock = worldIn.getBlockState(pos.up()).getBlock();
		if (upBlock == Blocks.flowing_water || upBlock == Blocks.water || upBlock == Blocks.lava || upBlock == Blocks.flowing_lava || upBlock == Blocks.fire) {
			worldIn.setBlockToAir(pos.up());
		}
	}
}
