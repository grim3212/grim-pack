package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.block.BlockRway;
import com.grim3212.mc.pack.industry.block.BlockSiding;
import com.grim3212.mc.pack.industry.block.BlockSiding.EnumSidingColor;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPaintRollerColor extends ItemManual {

	public EnumSidingColor paintcolor;

	public ItemPaintRollerColor(EnumSidingColor color) {
		this.paintcolor = color;
		this.maxStackSize = 1;
		this.setMaxDamage(64);
		this.setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualIndustry.paintTech_page;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return EnumActionResult.SUCCESS;
		}

		IBlockState state = worldIn.getBlockState(pos);
		if (state.getBlock() instanceof BlockSiding) {
			if (state.getValue(BlockSiding.COLOR) != this.paintcolor) {
				worldIn.setBlockState(pos, state.withProperty(BlockSiding.COLOR, paintcolor));

				if (stack.getItemDamage() == 64) {
					playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(IndustryItems.paint_roller));
				}

				stack.damageItem(1, playerIn);
			}
		} else if (state.getBlock() == IndustryBlocks.rway) {
			worldIn.setBlockState(pos, worldIn.getBlockState(pos).cycleProperty(BlockRway.TYPE));

			if (stack.getItemDamage() == 64) {
				playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(IndustryItems.paint_roller));
			}

			stack.damageItem(1, playerIn);
		}

		return EnumActionResult.SUCCESS;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
}
