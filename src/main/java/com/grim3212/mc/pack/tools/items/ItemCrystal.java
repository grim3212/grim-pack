package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.tools.util.EnumCrystalType;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ItemCrystal extends ItemManualBlock {

	public ItemCrystal(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = MathHelper.clamp(stack.getItemDamage(), 0, EnumCrystalType.values.length);
		return super.getUnlocalizedName() + "." + EnumCrystalType.values[i].getUnlocalized();
	}
}
