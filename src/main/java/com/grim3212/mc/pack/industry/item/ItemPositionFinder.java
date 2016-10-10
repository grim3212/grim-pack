package com.grim3212.mc.pack.industry.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPositionFinder extends Item {

	public ItemPositionFinder() {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (stack.hasTagCompound()) {
			NBTTagCompound tag = stack.getTagCompound();
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
			NBTTagCompound tag = stack.getTagCompound();
			if (tag.hasKey("X") && tag.hasKey("Y") && tag.hasKey("Z")) {
				return new BlockPos(tag.getInteger("X"), tag.getInteger("Y"), tag.getInteger("Z"));
			}
		}
		return null;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		BlockPos offset = pos.offset(facing);
		NBTTagCompound coords = new NBTTagCompound();
		coords.setInteger("X", offset.getX());
		coords.setInteger("Y", offset.getY());
		coords.setInteger("Z", offset.getZ());
		stack.setTagCompound(coords);

		return EnumActionResult.SUCCESS;
	}

}
