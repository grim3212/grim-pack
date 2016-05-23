package com.grim3212.mc.pack.cuisine.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBowlChocolate extends Item {

	public ItemBowlChocolate(int stacksize) {
		super();
		maxStackSize = stacksize;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entity) {
		if (entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		if (!worldIn.isRemote) {
			entity.heal(4f);
		}

		return stack.stackSize <= 0 ? new ItemStack(Items.BOWL) : stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		playerIn.setActiveHand(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.DRINK;
	}
}
