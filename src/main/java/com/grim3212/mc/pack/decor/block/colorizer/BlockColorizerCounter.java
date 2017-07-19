package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockColorizerCounter extends BlockColorizer {

	public static final AxisAlignedBB COUNTER_AABB = new AxisAlignedBB(0.0F, 1.0F, 0.0F, 1.0F, 0.81F, 1.0F);

	public BlockColorizerCounter() {
		super("counter");
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return COUNTER_AABB;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.counter_page;
	}
}
