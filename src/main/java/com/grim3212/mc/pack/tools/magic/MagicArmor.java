package com.grim3212.mc.pack.tools.magic;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MagicArmor extends BaseMagic {

	public MagicArmor() {
		super("armor");
	}

	@Override
	public int performMagic(World world, EntityPlayer playerIn, EnumHand hand, int dmgLeft, float range) {
		Iterator<ItemStack> itr = playerIn.getArmorInventoryList().iterator();

		int count = 0;

		while (itr.hasNext()) {
			ItemStack stack = itr.next();

			if (count <= dmgLeft && !stack.isEmpty() && stack.getItem() instanceof ItemArmor && stack.getItemDamage() != 0) {
				int toRepair = stack.getMaxDamage() - stack.getItemDamage();

				stack.damageItem(-toRepair, playerIn);

				count += toRepair / 2;
			}
		}

		return count;
	}

}
