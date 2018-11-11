package com.grim3212.mc.pack.tools.magic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MagicItem extends BaseMagic {

	public MagicItem() {
		super("item");
	}

	@Override
	public int performMagic(World world, EntityPlayer playerIn, EnumHand hand, int dmgLeft, float range) {
		int count = 0;

		for (int i = 0; i < 9; i++) {
			ItemStack stack = playerIn.inventory.getStackInSlot(i);

			if (!stack.isEmpty() && stack.isItemStackDamageable()) {
				int toRepair = stack.getMaxDamage() - stack.getItemDamage();

				stack.damageItem(-toRepair, playerIn);

				count += toRepair / 2;
			}
		}

		// Also check offhand
		ItemStack offStack = playerIn.inventory.offHandInventory.get(0);

		if (!offStack.isEmpty() && offStack.isItemStackDamageable()) {
			int toRepair = offStack.getMaxDamage() - offStack.getItemDamage();

			offStack.damageItem(-toRepair, playerIn);

			count += toRepair / 2;
		}

		return count;
	}

}
