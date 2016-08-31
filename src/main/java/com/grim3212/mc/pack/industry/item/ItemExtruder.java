package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.entity.EntityExtruder;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemExtruder extends ItemManual {

	public ItemExtruder() {
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing sideHit, float hitX, float hitY, float hitZ) {
		EnumFacing extruderFace = BlockPistonBase.getFacingFromEntity(pos, playerIn);
		--stack.stackSize;

		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			pos = pos.offset(sideHit);
			EntityExtruder extruder = new EntityExtruder(worldIn, extruderFace, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
			extruder.setCustomName(stack.getDisplayName());
			worldIn.spawnEntityInWorld(extruder);
		}

		return EnumActionResult.SUCCESS;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return null;
	}

}
