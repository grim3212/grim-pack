package com.grim3212.mc.pack.decor.item;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.entity.EntityFlatItemFrame;

import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFlatItemFrame extends ItemManual {

	public ItemFlatItemFrame() {
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		BlockPos blockpos = pos.offset(facing);

		if (playerIn.canPlayerEdit(blockpos, facing, playerIn.getHeldItem(hand))) {
			EntityHanging entityhanging = this.createEntity(worldIn, blockpos, facing);

			if (entityhanging != null && entityhanging.onValidSurface()) {
				if (!worldIn.isRemote) {
					entityhanging.playPlaceSound();
					worldIn.spawnEntity(entityhanging);
				}
				playerIn.getHeldItem(hand).shrink(1);
			}

			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.FAIL;
		}
	}

	@Nullable
	private EntityHanging createEntity(World worldIn, BlockPos pos, EnumFacing clickedSide) {
		return new EntityFlatItemFrame(worldIn, pos, clickedSide);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualDecor.flatItemFrame_page;
	}
}
