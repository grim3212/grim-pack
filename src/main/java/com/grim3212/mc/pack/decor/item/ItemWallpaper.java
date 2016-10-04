package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.entity.EntityWallpaper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWallpaper extends ItemManual {

	public ItemWallpaper() {
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (facing != EnumFacing.DOWN && facing != EnumFacing.UP && playerIn.canPlayerEdit(pos.offset(facing), facing, stack)) {
			EntityWallpaper wallpaper = new EntityWallpaper(worldIn, pos.offset(facing), facing);

			if (wallpaper != null && wallpaper.onValidSurface()) {
				if (!worldIn.isRemote) {
					wallpaper.playPlaceSound();
					worldIn.spawnEntityInWorld(wallpaper);
				}

				--stack.stackSize;
			}

			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualDecor.wallpaper_page;
	}
}