package com.grim3212.mc.pack.tools.items;

import java.util.List;

import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.entity.EntityPokeball;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemPokeball extends ItemEgg {

	public ItemPokeball() {
		this.setMaxStackSize(1);
		this.setMaxDamage(10);
		this.setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (!playerIn.capabilities.isCreativeMode) {
			--itemStackIn.stackSize;
		}

		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			EntityPokeball pokeball = new EntityPokeball(worldIn, playerIn, itemStackIn.copy());
			pokeball.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
			worldIn.spawnEntityInWorld(pokeball);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
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
