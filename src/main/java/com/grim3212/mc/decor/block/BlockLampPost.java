package com.grim3212.mc.decor.block;

import java.util.List;
import java.util.Random;

import com.grim3212.mc.decor.item.DecorItems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLampPost extends BlockTextured {

	public BlockLampPost(boolean isGlowing) {
		this.setHardness(1.0F);
		this.setCreativeTab(null);

		if (isGlowing) {
			this.setLightLevel(1.0F);
		}
	}

	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
		if (state.getBlock() == DecorBlocks.lamp_post_bottom) {
			this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		} else if (state.getBlock() == DecorBlocks.lamp_post_middle) {
			this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		} else {
			this.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.685F, 0.875F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos).getBlock() == DecorBlocks.lamp_post_bottom) {
			this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
		} else if (worldIn.getBlockState(pos).getBlock() == DecorBlocks.lamp_post_middle) {
			this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
		} else {
			this.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.685F, 0.875F);
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return DecorItems.lamp_item;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block == DecorBlocks.lamp_post_bottom) {
			worldIn.setBlockToAir(pos.up());
			worldIn.setBlockToAir(pos.up(2));
		} else if (block == DecorBlocks.lamp_post_middle) {
			worldIn.setBlockToAir(pos.up());
			worldIn.setBlockToAir(pos.down());
		} else if (block == DecorBlocks.lamp_post_top) {
			worldIn.setBlockToAir(pos.down());
			worldIn.setBlockToAir(pos.down(2));
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block == DecorBlocks.lamp_post_bottom) {
			worldIn.setBlockToAir(pos.up());
			worldIn.setBlockToAir(pos.up(2));
		} else if (block == DecorBlocks.lamp_post_middle) {
			worldIn.setBlockToAir(pos.up());
			worldIn.setBlockToAir(pos.down());
		} else if (block == DecorBlocks.lamp_post_top) {
			worldIn.setBlockToAir(pos.down());
			worldIn.setBlockToAir(pos.down(2));
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block == DecorBlocks.lamp_post_bottom) {
			worldIn.setBlockToAir(pos.up());
			worldIn.setBlockToAir(pos.up(2));
		} else if (block == DecorBlocks.lamp_post_middle) {
			worldIn.setBlockToAir(pos.up());
			worldIn.setBlockToAir(pos.down());
		} else if (block == DecorBlocks.lamp_post_top) {
			worldIn.setBlockToAir(pos.down());
			worldIn.setBlockToAir(pos.down(2));
		}
	}

	@Override
	public Item getItem(World worldIn, BlockPos pos) {
		return DecorItems.lamp_item;
	}
}
