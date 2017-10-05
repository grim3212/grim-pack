package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManualArmor;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.client.ManualTools;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemChickenSuit extends ItemManualArmor {

	public ItemChickenSuit(String name, EntityEquipmentSlot equipmentSlotIn) {
		super(name, ToolsItems.chicken_suit, 2, equipmentSlotIn);
		this.setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.chickenSuit_page;
	}
}
