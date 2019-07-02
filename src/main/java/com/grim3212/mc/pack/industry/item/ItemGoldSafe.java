package com.grim3212.mc.pack.industry.item;

import java.util.List;

import net.minecraft.item.BlockItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import org.apache.commons.lang3.StringUtils;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.industry.tile.TileEntityGoldSafe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;

public class ItemGoldSafe extends BlockItem {

	public ItemGoldSafe(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		if (NBTHelper.hasTag(stack, "GoldSafe")) {
			CompoundNBT compound = NBTHelper.getTagCompound(stack, "GoldSafe");

			if (!StringUtils.isBlank(NBTHelper.getString(compound, "Lock")))
				tooltip.add("\u00a7aLocked");

			ListNBT taglist = compound.getTagList("Items", 10);
			if (taglist.tagCount() > 0) {
				tooltip.add("\u00a7b" + taglist.tagCount() + "\u00a79/" + "\u00a7b9 " + "\u00a77 Slots");
			} else
				tooltip.add("Empty");
		} else {
			tooltip.add("Empty");
		}
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, PlayerEntity player, World world, BlockPos pos, Direction side, float hitX, float hitY, float hitZ, BlockState newState) {
		super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);

		TileEntity te = world.getTileEntity(pos);

		if (te != null && te instanceof TileEntityGoldSafe) {
			TileEntityGoldSafe teSafe = (TileEntityGoldSafe) te;

			if (NBTHelper.hasTag(stack, "GoldSafe")) {
				CompoundNBT compound = NBTHelper.getTagCompound(stack, "GoldSafe");
				teSafe.setLockCode(new LockCode(compound.getString("Lock")));

				ItemStackHelper.loadAllItems(compound, teSafe.getItems());
			}
		}

		return true;
	}
}
