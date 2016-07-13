package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.tools.entity.EntityTomahawk;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemTomahawk extends Item {

	public ItemTomahawk() {
		super();
		this.maxStackSize = 16;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		--itemStackIn.stackSize;
		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			EntityTomahawk tomahawk = new EntityTomahawk(worldIn, playerIn);
			tomahawk.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.5F, 1.1F);
			worldIn.spawnEntityInWorld(tomahawk);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}
}
