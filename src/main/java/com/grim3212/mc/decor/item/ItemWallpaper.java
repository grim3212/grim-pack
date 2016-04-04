package com.grim3212.mc.decor.item;

import com.grim3212.mc.decor.entity.EntityWallpaper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemWallpaper extends Item {

	public ItemWallpaper() {
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!playerIn.canPlayerEdit(pos, side, stack)) {
			return false;
		}

		if (side == EnumFacing.UP || side == EnumFacing.DOWN)
			return false;

		if (side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
			side = side.getOpposite();

		EntityWallpaper entitywallpaper = new EntityWallpaper(worldIn, pos, side.getHorizontalIndex());

		if (entitywallpaper.onValidSurface()) {
			if (!worldIn.isRemote) {
				worldIn.spawnEntityInWorld(entitywallpaper);
				entitywallpaper.playWallpaperSound();
			}

			stack.stackSize -= 1;
		}

		return true;
	}
}