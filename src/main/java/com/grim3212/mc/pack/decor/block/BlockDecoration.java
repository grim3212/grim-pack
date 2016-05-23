package com.grim3212.mc.pack.decor.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDecoration extends Block {

	private boolean useCollision;

	protected BlockDecoration(Material mat, boolean useCollision) {
		super(mat);
		this.useCollision = useCollision;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		if (useCollision) {
			return super.getCollisionBoundingBox(blockState, worldIn, pos);
		} else {
			return NULL_AABB;
		}
	}

	@Override
	public boolean isNormalCube(IBlockState blockState) {
		return useCollision;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}
}
