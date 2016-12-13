package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntityDiamondBoomerang;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemDiamondBoomerang extends ItemBoomerang {

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.getHeldItem(hand).getItemDamage() == 0) {
			EntityDiamondBoomerang boom = new EntityDiamondBoomerang(worldIn, playerIn, playerIn.getHeldItem(hand));
			worldIn.spawnEntity(boom);
			playerIn.inventory.removeStackFromSlot(playerIn.inventory.currentItem);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.diamondBoomerang_page;
	}

}
