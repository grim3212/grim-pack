package com.grim3212.mc.pack.tools.items;

import com.google.common.collect.Multimap;
import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntitySlimeSpear;
import com.grim3212.mc.pack.tools.entity.EntitySpear;
import com.grim3212.mc.pack.tools.util.EnumSpearType;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemSpear extends ItemManual {

	private EnumSpearType type;

	public ItemSpear(EnumSpearType type) {
		setMaxStackSize(16);
		this.type = type;
	}

	public EnumSpearType getType() {
		return type;
	}

	@Override
	public Page getPage(ItemStack stack) {
		if (stack.getItem() == ToolsItems.spear || stack.getItem() == ToolsItems.iron_spear || stack.getItem() == ToolsItems.diamond_spear)
			return ManualTools.spears_page;

		return ManualTools.specialSpears_page;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		playerIn.inventory.decrStackSize(playerIn.inventory.currentItem, 1);
		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isRemote) {
			if (type == EnumSpearType.SLIME) {
				EntitySlimeSpear slimeSpear = new EntitySlimeSpear(worldIn, playerIn);
				slimeSpear.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.4F, 1.2F);
				worldIn.spawnEntity(slimeSpear);
			} else {
				EntitySpear spear = new EntitySpear(worldIn, playerIn, type);
				spear.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.4F + (float) (type.getDamage() / 2), 1.2F);
				worldIn.spawnEntity(spear);
			}
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if (slot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.type.getItemDamage(), 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.8f, 0));
		}
		return multimap;
	}
}
