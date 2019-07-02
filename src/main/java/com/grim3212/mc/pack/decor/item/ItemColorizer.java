package com.grim3212.mc.pack.decor.item;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerFurnitureRotate;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class ItemColorizer extends ItemManualBlock {

	public ItemColorizer(Block block) {
		super(block, new Item.Properties().group(GrimItemGroups.GRIM_DECOR));
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		ActionResultType actionresulttype = this.tryPlace(new BlockItemUseContext(context));

		TileEntity tileentity = context.getWorld().getTileEntity(context.getPos());
		if (tileentity instanceof TileEntityColorizer)
			((TileEntityColorizer) tileentity).setStoredBlockState(NBTUtil.readBlockState(NBTHelper.getTagCompound(context.getItem(), "stored_state")));

		return actionresulttype != ActionResultType.SUCCESS && this.isFood() ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : actionresulttype;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new ItemStack(NBTUtil.readBlockState(NBTHelper.getTagCompound(stack, "stored_state")).getBlock()).getDisplayName());
	}

	@Override
	protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		Block toPlace = this.getBlock();
		return world.setBlockState(pos, toPlace instanceof BlockColorizerFurnitureRotate ? state.with(BlockColorizerFurnitureRotate.HORIZONTAL_FACING, context.getPlayer().getHorizontalFacing()) : state, 11);
	}
}
