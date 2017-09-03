package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.util.frozen.FrozenCapability;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemPhoenixStaff extends ItemStaff {

	public ItemPhoenixStaff() {
		super("phoenix_staff");
	}

	@Override
	public Page getPage(ItemStack stack) {
		return null;
	}

	@Override
	protected void hitEntity(EntityLivingBase target) {
		FrozenCapability.unFreezeEntity(target);
	}
}
