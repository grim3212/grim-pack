package com.grim3212.mc.pack.cuisine.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemHealthPack extends ItemManual {

	private int healAmount;

	public ItemHealthPack(String name, int healAmnt) {
		super(name, new Item.Properties().group(GrimItemGroups.GRIM_CUISINE).maxStackSize(16));
		healAmount = healAmnt;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
		if (playerIn.shouldHeal()) {
			playerIn.getHeldItem(hand).shrink(1);
			playerIn.heal(healAmount);
		}
		return ActionResult.newResult(ActionResultType.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualCuisine.health_page;
	}
}
