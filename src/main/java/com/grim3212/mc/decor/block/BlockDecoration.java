package com.grim3212.mc.decor.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockDecoration extends Block {

	private boolean useCollision;

	protected BlockDecoration(Material mat, boolean useCollision) {
		super(mat);
		this.useCollision = useCollision;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		if (useCollision) {
			return super.getCollisionBoundingBox(worldIn, pos, state);
		} else {
			return null;
		}
	}

	@Override
	public boolean isNormalCube() {
		return useCollision;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
