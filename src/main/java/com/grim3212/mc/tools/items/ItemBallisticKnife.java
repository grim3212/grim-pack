package com.grim3212.mc.tools.items;

import com.google.common.collect.Multimap;
import com.grim3212.mc.tools.entity.EntityBallisticKnife;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBallisticKnife extends Item {

	private boolean isLoaded;
	private boolean isKnife;

	public ItemBallisticKnife(boolean isLoaded, boolean isKnife) {
		maxStackSize = 1;
		this.isLoaded = isLoaded;
		this.isKnife = isKnife;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (isLoaded) {
			world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote) {
				world.spawnEntityInWorld(new EntityBallisticKnife(world, entityplayer, 2.0F));
			}
			return new ItemStack(ToolsItems.unloaded_knife);
		}
		if (!isLoaded && !isKnife) {
			if (entityplayer.inventory.consumeInventoryItem(ToolsItems.ammo_part)) {
				return new ItemStack(ToolsItems.loaded_knife);
			} else {
				return itemstack;
			}
		} else {
			return itemstack;
		}
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack) {
		if (!isLoaded && !isKnife) {
			Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(stack);
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", (double) 0, 0));
			return multimap;
		}
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(stack);
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", (double) 50, 0));
		return multimap;
	}
}
