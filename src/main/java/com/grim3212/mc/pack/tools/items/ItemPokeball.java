package com.grim3212.mc.pack.tools.items;

import java.util.List;

import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.entity.EntityPokeball;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPokeball extends ItemEgg {

	public ItemPokeball() {
		this.setMaxStackSize(1);
		this.setMaxDamage(10);
		this.setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
		}

		world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote) {
			world.spawnEntityInWorld(new EntityPokeball(world, entityplayer, itemstack));
		}

		return itemstack;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List<String> list, boolean flag) {
		if (itemstack.hasTagCompound()) {
			String entity = itemstack.getTagCompound().getString("id");
			list.add("Stored: " + entity);
		} else {
			list.add("Stored: Empty");
		}
	}
}
