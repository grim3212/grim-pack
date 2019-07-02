package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;

public class ItemLampPost extends ItemManualBlock {

	public ItemLampPost() {
		super(DecorBlocks.lamp_post_bottom, new Item.Properties().group(GrimItemGroups.GRIM_DECOR));
		setRegistryName(DecorNames.LAMP_ITEM);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		ActionResultType result = super.onItemUse(context);

		TileEntity tileentityUp = context.getWorld().getTileEntity(context.getPos().up());
		if (tileentityUp instanceof TileEntityColorizer)
			((TileEntityColorizer) tileentityUp).setStoredBlockState(NBTUtil.readBlockState(NBTHelper.getTagCompound(context.getItem(), "stored_state")));
		TileEntity tileentityUpUp = context.getWorld().getTileEntity(context.getPos().up(2));
		if (tileentityUpUp instanceof TileEntityColorizer)
			((TileEntityColorizer) tileentityUpUp).setStoredBlockState(NBTUtil.readBlockState(NBTHelper.getTagCompound(context.getItem(), "stored_state")));

		return result;
	}

	@Override
	protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
		if (context.getWorld().isAirBlock(context.getPos().up()) && context.getWorld().isAirBlock(context.getPos().up(2))) {
			context.getWorld().setBlockState(context.getPos().up(), DecorBlocks.lamp_post_middle.getDefaultState(), 11);
			context.getWorld().setBlockState(context.getPos().up(2), DecorBlocks.lamp_post_top.getDefaultState(), 11);
			return super.placeBlock(context, state);
		}
		return false;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualDecor.lamps_page;
	}
}
