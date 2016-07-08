package com.grim3212.mc.pack.industry.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockElemental extends Block {

	public static enum ElementType {
		WATER, FIRE, LAVA
	}

	private ElementType type;

	protected BlockElemental(ElementType type) {
		super(Material.IRON);
		this.type = type;
		setSoundType(SoundType.METAL);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
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
}
