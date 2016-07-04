package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockFirepit extends BlockFireplaceBase {

	public static final AxisAlignedBB FIREPIT_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.6F, 1.0F);

	public BlockFirepit() {
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (DecorConfig.enableFirepitNet)
			return FULL_BLOCK_AABB;
		else
			return FIREPIT_AABB;
	}
}
