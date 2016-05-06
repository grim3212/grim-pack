package com.grim3212.mc.tools.items;

import com.google.common.collect.Multimap;
import com.grim3212.mc.tools.entity.EntitySlimeSpear;
import com.grim3212.mc.tools.entity.EntitySpear;
import com.grim3212.mc.tools.util.EnumSpearType;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSpear extends Item {

	private EnumSpearType type;

	public ItemSpear(EnumSpearType type) {
		setMaxStackSize(2);
		this.type = type;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.inventory.decrStackSize(entityplayer.inventory.currentItem, 1);
		world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!world.isRemote) {
			if (type == EnumSpearType.SLIME)
				world.spawnEntityInWorld(new EntitySlimeSpear(world, entityplayer, 1.0F));
			else
				world.spawnEntityInWorld(new EntitySpear(world, entityplayer, 1.4F, type));
		}
		return itemstack;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(stack);
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", this.type.getItemDamage(), 0));
		return multimap;
	}
}
