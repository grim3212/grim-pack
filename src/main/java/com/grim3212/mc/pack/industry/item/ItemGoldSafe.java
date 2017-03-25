package com.grim3212.mc.pack.industry.item;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.industry.tile.TileEntityGoldSafe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;

public class ItemGoldSafe extends ItemBlock {

	public ItemGoldSafe(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);

		if (NBTHelper.hasTag(stack, "GoldSafe")) {
			NBTTagCompound compound = NBTHelper.getTagCompound(stack, "GoldSafe");

			if (!StringUtils.isBlank(NBTHelper.getString(compound, "Lock")))
				tooltip.add("\u00a7aLocked");

			NBTTagList taglist = compound.getTagList("Items", 10);
			if (taglist.tagCount() > 0) {
				tooltip.add("\u00a7b" + taglist.tagCount() + "\u00a79/" + "\u00a7b9 " + "\u00a77 Slots");
			} else
				tooltip.add("Empty");
		} else {
			tooltip.add("Empty");
		}
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);

		TileEntity te = world.getTileEntity(pos);

		if (te != null && te instanceof TileEntityGoldSafe) {
			TileEntityGoldSafe teSafe = (TileEntityGoldSafe) te;

			if (NBTHelper.hasTag(stack, "GoldSafe")) {
				NBTTagCompound compound = NBTHelper.getTagCompound(stack, "GoldSafe");
				teSafe.setLockCode(new LockCode(compound.getString("Lock")));

				ItemStackHelper.loadAllItems(compound, teSafe.getItems());
			}
		}

		return true;
	}
}
