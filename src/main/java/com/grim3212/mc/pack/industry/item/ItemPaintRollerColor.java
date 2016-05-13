package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.block.BlockRway;
import com.grim3212.mc.pack.industry.block.BlockSiding;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.block.BlockSiding.EnumSidingColor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemPaintRollerColor extends Item {

	public EnumSidingColor paintcolor;

	public ItemPaintRollerColor(EnumSidingColor color) {
		this.paintcolor = color;
		this.maxStackSize = 1;
		this.setMaxDamage(64);
		this.setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
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

		return true;
	}

	public boolean isFull3D() {
		return true;
	}
}
