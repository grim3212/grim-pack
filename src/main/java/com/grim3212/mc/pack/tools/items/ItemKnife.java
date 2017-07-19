package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntityKnife;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemKnife extends ItemManual {

	public ItemKnife() {
		super("throwing_knife");
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
		this.maxStackSize = 16;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.throwingKnife_page;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		playerIn.getHeldItem(hand).shrink(1);
		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			EntityKnife knife = new EntityKnife(worldIn, playerIn);
			knife.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.4F, 1.3F);
			worldIn.spawnEntity(knife);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}
}
