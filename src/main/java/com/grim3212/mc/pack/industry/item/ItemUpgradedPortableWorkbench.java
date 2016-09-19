package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.items.ItemPortableWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemUpgradedPortableWorkbench extends ItemPortableWorkbench {

	private final int MAIN_GUI_ID;
	private final int OFF_GUI_ID;

	public ItemUpgradedPortableWorkbench(int mainGui, int offGui) {
		this.MAIN_GUI_ID = mainGui;
		this.OFF_GUI_ID = offGui;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (!worldIn.isRemote) {
			if (hand == EnumHand.MAIN_HAND)
				playerIn.openGui(GrimPack.INSTANCE, MAIN_GUI_ID, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
			else
				playerIn.openGui(GrimPack.INSTANCE, OFF_GUI_ID, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public Page getPage(ItemStack stack) {
		// TODO: Change
		return super.getPage(stack);
	}
}
