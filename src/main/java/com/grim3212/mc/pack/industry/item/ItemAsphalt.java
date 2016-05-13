package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.industry.block.IndustryBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemAsphalt extends Item {

	public ItemAsphalt() {
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (worldIn.isRemote) {
			return true;
		} else if (block == Blocks.stone) {
			worldIn.setBlockState(pos, IndustryBlocks.rway.getDefaultState());
			--stack.stackSize;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
}
