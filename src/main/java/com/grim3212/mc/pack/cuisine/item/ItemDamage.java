package com.grim3212.mc.pack.cuisine.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;

import net.minecraft.item.ItemStack;

public class ItemDamage extends ItemManual {

	public ItemDamage(String name, int uses) {
		super(name);
		setMaxStackSize(1);
		setMaxDamage(uses);
		setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return new ItemStack(itemStack.getItem(), 1, itemStack.getItemDamage() + 1);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualCuisine.utensils_page;
	}
}
