package com.grim3212.mc.tools.items;

import com.grim3212.mc.tools.entity.EntityKnife;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemKnife extends Item {

	public ItemKnife() {
		super();
		this.maxStackSize = 16;
	}

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
		--var1.stackSize;
		var2.playSoundAtEntity(var3, "random.wood_click", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!var2.isRemote) {
			var2.spawnEntityInWorld(new EntityKnife(var2, var3, 2.0F));
		}

		return var1;
	}
}
