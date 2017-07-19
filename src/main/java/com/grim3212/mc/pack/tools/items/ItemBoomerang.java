package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntityBoomerang;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBoomerang extends ItemManual {

	public ItemBoomerang(String name) {
		super(name);
		this.setMaxStackSize(1);
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.getHeldItem(hand).getItemDamage() == 0) {
			EntityBoomerang boom = new EntityBoomerang(worldIn, playerIn, playerIn.getHeldItem(hand));
			worldIn.spawnEntity(boom);
			playerIn.inventory.removeStackFromSlot(playerIn.inventory.currentItem);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.boomerang_page;
	}

}
