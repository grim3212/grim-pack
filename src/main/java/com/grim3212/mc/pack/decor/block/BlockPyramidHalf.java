package com.grim3212.mc.pack.decor.block;

import java.util.List;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPyramidHalf extends BlockHedge {

	private static final AxisAlignedBB BASE_BOTTOM_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.5f, 1.0F);
	private static final AxisAlignedBB BASE_TOP_AABB = new AxisAlignedBB(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.pyramid_page;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
		if (state.getValue(BlockSloped.HALF) == EnumHalf.BOTTOM)
			addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_BOTTOM_AABB);
		else
			addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_TOP_AABB);
	}

}
