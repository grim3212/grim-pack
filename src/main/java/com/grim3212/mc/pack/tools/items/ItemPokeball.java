package com.grim3212.mc.pack.tools.items;

import java.util.List;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntityPokeball;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemPokeball extends ItemEgg implements IManualItem {

	public ItemPokeball() {
		this.setMaxStackSize(1);
		this.setMaxDamage(10);
		this.setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.pokeball_page;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		ItemStack itemStackIn = playerIn.getHeldItem(hand);

		if (!playerIn.capabilities.isCreativeMode) {
			itemStackIn.shrink(1);
		}

		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			EntityPokeball pokeball = new EntityPokeball(worldIn, playerIn, itemStackIn.copy());
			pokeball.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
			worldIn.spawnEntity(pokeball);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List<String> list, boolean flag) {
		if (itemstack.hasTagCompound()) {
			if (itemstack.getTagCompound().hasKey("name")) {
				list.add(I18n.format("tooltip.pokeball.stored") + itemstack.getTagCompound().getString("name"));
			} else {
				list.add(I18n.format("tooltip.pokeball.stored") + itemstack.getTagCompound().getString("id"));
			}
		} else {
			list.add(I18n.format("tooltip.pokeball.stored") + I18n.format("tooltip.pokeball.empty"));
		}
	}
}
