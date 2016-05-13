package com.grim3212.mc.pack.cuisine.item;

import java.util.List;

import com.grim3212.mc.pack.cuisine.GrimCuisine;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
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
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.shouldHeal()) {
			if (itemStackIn.getItemDamage() == 0) {
				playerIn.heal(10);
			}
			if (itemStackIn.getItemDamage() == 2) {
				playerIn.heal(3);
			}
			if (itemStackIn.getItemDamage() == 4) {
				playerIn.heal(20);
			}
			if (itemStackIn.getItemDamage() == 6) {
				playerIn.heal(5);
			}
			if (itemStackIn.getItemDamage() == 7) {
				playerIn.heal(10);
			}
			if (itemStackIn.getItemDamage() == 8) {
				playerIn.heal(20);
			}
			if (itemStackIn.getItemDamage() == 9) {
				playerIn.heal(8);
			}
			if (itemStackIn.getItemDamage() == 10) {
				playerIn.heal(14);
			}
			if (itemStackIn.getItemDamage() == 11) {
				playerIn.heal(2);
			}
			if (itemStackIn.getItemDamage() == 12) {
				playerIn.heal(5);
			}
			itemStackIn.stackSize--;
		}
		if (itemStackIn.getItemDamage() == 5) {
			playerIn.attackEntityFrom(DamageSource.generic, 8.0F);
			itemStackIn.stackSize--;
		} else {
			playerIn.heal(0);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
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
