package com.grim3212.mc.core.manual;

import java.util.List;

import com.grim3212.mc.core.GrimCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemManual extends Item {

	public ItemManual() {
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		playerIn.openGui(GrimCore.INSTANCE, 0, worldIn, 0, 0, 0);
		return itemStackIn;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag) {
		list.add("Mods Registered: " + ManualRegistry.getLoadedMods().size());
	}
}
