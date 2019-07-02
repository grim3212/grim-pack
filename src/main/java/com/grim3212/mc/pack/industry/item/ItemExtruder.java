package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.entity.EntityExtruder;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemExtruder extends ItemManual {

	public ItemExtruder() {
		super("extruder");
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	public ActionResultType onItemUse(PlayerEntity playerIn, World worldIn, BlockPos pos, Hand hand, Direction sideHit, float hitX, float hitY, float hitZ) {
		Direction extruderFace = Direction.getDirectionFromEntityLiving(pos, playerIn);
		playerIn.getHeldItem(hand).shrink(1);

		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			pos = pos.offset(sideHit);
			EntityExtruder extruder = new EntityExtruder(worldIn, extruderFace, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
			extruder.setCustomName(playerIn.getHeldItem(hand).getDisplayName());
			worldIn.spawnEntity(extruder);
		}

		return ActionResultType.SUCCESS;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualIndustry.extruder_page;
	}

}
