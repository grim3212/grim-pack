package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockColorizerFirepit extends BlockColorizerFireplaceBase {

	public static final AxisAlignedBB FIREPIT_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.6F, 1.0F);

	public BlockColorizerFirepit() {
		super("firepit");
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (DecorConfig.enableFirepitNet)
			return FULL_BLOCK_AABB;
		else
			return FIREPIT_AABB;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.firepit_page;
	}
}
