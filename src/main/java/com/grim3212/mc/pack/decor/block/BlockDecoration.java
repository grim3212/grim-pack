package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDecoration extends BlockManual {

	private boolean useCollision;

	protected BlockDecoration(Material mat, boolean useCollision) {
		super(mat, SoundType.METAL);
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

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.cage_page;
	}
}
