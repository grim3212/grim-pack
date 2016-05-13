package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.decor.entity.EntityWallpaper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWallpaper extends Item {

	public ItemWallpaper() {
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!playerIn.canPlayerEdit(pos, facing, stack)) {
			return EnumActionResult.FAIL;
		}

		if (facing == EnumFacing.UP || facing == EnumFacing.DOWN)
			return EnumActionResult.FAIL;

		if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH)
			facing = facing.getOpposite();

		EntityWallpaper entitywallpaper = new EntityWallpaper(worldIn, pos, facing.getHorizontalIndex());

		if (entitywallpaper.onValidSurface()) {
			if (!worldIn.isRemote) {
				worldIn.spawnEntityInWorld(entitywallpaper);
				entitywallpaper.playWallpaperSound();
			}

			--stack.stackSize;
		}

		return EnumActionResult.SUCCESS;
	}
}