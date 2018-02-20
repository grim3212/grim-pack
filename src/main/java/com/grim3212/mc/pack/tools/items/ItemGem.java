package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.util.EnumCrystalType;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

public class ItemGem extends ItemManual {

	public ItemGem(String name) {
		super(name);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return null;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = MathHelper.clamp(stack.getItemDamage(), 0, EnumCrystalType.values.length);
		return super.getUnlocalizedName() + "." + EnumCrystalType.values[i].getUnlocalized();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab))
			for (int j = 0; j < EnumCrystalType.values.length; ++j) {
				items.add(new ItemStack(this, 1, j));
			}
	}
}
