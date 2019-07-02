package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.entity.EntityWallpaper;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWallpaper extends ItemManual {

	public ItemWallpaper() {
		super(DecorNames.WALLPAPER, new Item.Properties().group(GrimItemGroups.GRIM_DECOR));
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		Direction facing = context.getFace();
		PlayerEntity playerIn = context.getPlayer();
		World worldIn = context.getWorld();
		BlockPos pos = context.getPos();

		if (facing != Direction.DOWN && facing != Direction.UP && playerIn.canPlayerEdit(pos.offset(facing), facing, context.getItem())) {
			EntityWallpaper wallpaper = new EntityWallpaper(worldIn, pos.offset(facing), facing);

			if (wallpaper != null && wallpaper.onValidSurface()) {
				if (!worldIn.isRemote) {
					wallpaper.playPlaceSound();
					worldIn.addEntity(wallpaper);
				}

				context.getItem().shrink(1);
			}

			return ActionResultType.SUCCESS;
		} else {
			return ActionResultType.FAIL;
		}
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualDecor.wallpaper_page;
	}
}