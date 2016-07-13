package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemPortableWorkbench extends Item {

	public ItemPortableWorkbench() {
		super();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (!worldIn.isRemote) {
			if (hand == EnumHand.MAIN_HAND)
				playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.PORTABLE_MAIN_GUI_ID, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
			else
				playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.PORTABLE_OFF_GUI_ID, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

}
