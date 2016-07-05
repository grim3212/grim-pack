package com.grim3212.mc.pack.core.manual;

import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemManual extends Item {

	public ItemManual() {
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.MANUAL_GUI_ID, worldIn, 0, 0, 0);
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag) {
		list.add("Mods Registered: " + ManualRegistry.getLoadedMods().size());
	}
}
