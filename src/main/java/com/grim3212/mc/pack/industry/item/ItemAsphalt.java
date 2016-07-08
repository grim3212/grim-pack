package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.industry.block.IndustryBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemAsphalt extends Item {

	public ItemAsphalt() {
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (worldIn.isRemote) {
			return EnumActionResult.SUCCESS;
		} else if (block == Blocks.STONE) {
			worldIn.setBlockState(pos, IndustryBlocks.rway.getDefaultState());
			--stack.stackSize;
			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.FAIL;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
}
