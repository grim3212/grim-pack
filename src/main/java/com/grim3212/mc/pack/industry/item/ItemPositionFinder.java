package com.grim3212.mc.pack.industry.item;

import java.util.List;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPositionFinder extends ItemManual {

	public ItemPositionFinder() {
		super("position_finder");
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.hasTagCompound()) {
			CompoundNBT tag = stack.getTagCompound();
			if (tag.hasKey("X") && tag.hasKey("Y") && tag.hasKey("Z")) {
				tooltip.add(I18n.format("grimpack.industry.gps.coords"));
				tooltip.add(tag.getInteger("X") + ", " + tag.getInteger("Y") + ", " + tag.getInteger("Z"));
			} else {
				tooltip.add(I18n.format("grimpack.industry.gps.noCoords"));
			}
		}
	}

	@Nullable
	public BlockPos getCoords(ItemStack stack) {
		if (stack.hasTagCompound()) {
			CompoundNBT tag = stack.getTagCompound();
			if (tag.hasKey("X") && tag.hasKey("Y") && tag.hasKey("Z")) {
				return new BlockPos(tag.getInteger("X"), tag.getInteger("Y"), tag.getInteger("Z"));
			}
		}
		return null;
	}

	@Override
	public ActionResultType onItemUse(PlayerEntity playerIn, World worldIn, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
		BlockPos offset = pos.offset(facing);
		CompoundNBT coords = new CompoundNBT();
		coords.setInteger("X", offset.getX());
		coords.setInteger("Y", offset.getY());
		coords.setInteger("Z", offset.getZ());
		playerIn.getHeldItem(hand).setTagCompound(coords);

		return ActionResultType.SUCCESS;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualIndustry.positionFinder_page;
	}

}
