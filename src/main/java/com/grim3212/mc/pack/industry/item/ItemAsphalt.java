package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemAsphalt extends ItemManual {

	public ItemAsphalt() {
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualIndustry.asphalt_page;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (worldIn.isRemote) {
			return EnumActionResult.SUCCESS;
		} else if (block == Blocks.STONE) {
			worldIn.setBlockState(pos, IndustryBlocks.rway.getDefaultState());
			playerIn.getHeldItem(hand).shrink(1);
			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.FAIL;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
}
