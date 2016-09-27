package com.grim3212.mc.pack.tools.items;

import com.google.common.collect.Multimap;
import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntityBallisticKnife;

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

public class ItemBallisticKnife extends ItemManual {

	private boolean isLoaded;
	private boolean isKnife;

	public ItemBallisticKnife(boolean isLoaded, boolean isKnife) {
		maxStackSize = 1;
		this.isLoaded = isLoaded;
		this.isKnife = isKnife;
	}

	@Override
	public Page getPage(ItemStack stack) {
		if (stack.getItem() == ToolsItems.ammo_part) {
			return ManualTools.ballisticKnife_page;
		}

		return ManualTools.ballistic_page;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (isLoaded) {
			worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!worldIn.isRemote) {
				EntityBallisticKnife bKnife = new EntityBallisticKnife(worldIn, playerIn);
				bKnife.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.75F, 1.0F);
				worldIn.spawnEntityInWorld(bKnife);
			}
			return ActionResult.newResult(EnumActionResult.SUCCESS, new ItemStack(ToolsItems.unloaded_knife));
		}
		if (!isLoaded && !isKnife) {
			if (Utils.consumeInventoryItem(playerIn, ToolsItems.ammo_part)) {
				return ActionResult.newResult(EnumActionResult.SUCCESS, new ItemStack(ToolsItems.loaded_knife));
			}
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

		if (slot == EntityEquipmentSlot.MAINHAND) {
			if (!isLoaded && !isKnife) {
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0, 0));
			} else {
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 20, 0));
			}

			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4f, 0));
		}
		return multimap;
	}
}
