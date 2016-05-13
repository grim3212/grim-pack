package com.grim3212.mc.pack.decor.block;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockChair extends BlockFurnitureRotate {

	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 0.5F, 1F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);

		int l = worldIn.getBlockState(pos).getBlock().getMetaFromState(state) % 4;

		if (l == 1) {
			this.setBlockBounds(0.81F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		} else if (l == 2) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.19F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		} else if (l == 0) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.19F, 1F, 1F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		} else if (l == 3) {
			this.setBlockBounds(0.0F, 0.0F, 0.81F, 1.0F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		}

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 1F, 1F);
	}
}
