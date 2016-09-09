package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntityBoomerang;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBoomerang extends ItemManual {

	public ItemBoomerang() {
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (itemStackIn.getItemDamage() == 0) {
			EntityBoomerang boom = new EntityBoomerang(worldIn, playerIn, itemStackIn);
			worldIn.spawnEntityInWorld(boom);
			playerIn.inventory.removeStackFromSlot(playerIn.inventory.currentItem);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.boomerang_page;
	}

}
