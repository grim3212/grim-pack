package com.grim3212.mc.pack.cuisine.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemHealthPack extends Item {

	private int healAmount;

	public ItemHealthPack(int healAmnt) {
		healAmount = healAmnt;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.shouldHeal()) {
			itemStackIn.stackSize--;
			playerIn.heal(healAmount);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}
}
