package com.grim3212.mc.cuisine.item;

import java.util.List;

import com.grim3212.mc.cuisine.GrimCuisine;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSodaBottle extends Item {

	public static final String[] sodaTypes = new String[] { "apple", "bottle", "slurm", "co2", "golden_apple", "spiked_orange", "root_beer", "cream_orange", "diamond", "orange", "cocoa", "carbonated_water", "mushroom" };

	public ItemSodaBottle() {
		maxStackSize = 16;
		setHasSubtypes(true);
		setMaxDamage(0);
		this.setCreativeTab(GrimCuisine.INSTANCE.getCreativeTab());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.shouldHeal()) {
			if (itemstack.getItemDamage() == 0) {
				entityplayer.heal(10);
			}
			if (itemstack.getItemDamage() == 2) {
				entityplayer.heal(3);
			}
			if (itemstack.getItemDamage() == 4) {
				entityplayer.heal(20);
			}
			if (itemstack.getItemDamage() == 6) {
				entityplayer.heal(5);
			}
			if (itemstack.getItemDamage() == 7) {
				entityplayer.heal(10);
			}
			if (itemstack.getItemDamage() == 8) {
				entityplayer.heal(20);
			}
			if (itemstack.getItemDamage() == 9) {
				entityplayer.heal(8);
			}
			if (itemstack.getItemDamage() == 10) {
				entityplayer.heal(14);
			}
			if (itemstack.getItemDamage() == 11) {
				entityplayer.heal(2);
			}
			if (itemstack.getItemDamage() == 12) {
				entityplayer.heal(5);
			}
			itemstack.stackSize--;
		}
		if (itemstack.getItemDamage() == 5) {
			entityplayer.attackEntityFrom(DamageSource.generic, 8.0F);
			itemstack.stackSize--;
		} else {
			entityplayer.heal(0);
		}
		return itemstack;
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 13);
		return super.getUnlocalizedName() + "." + sodaTypes[i];
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
		for (int j = 0; j < 13; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}
}
