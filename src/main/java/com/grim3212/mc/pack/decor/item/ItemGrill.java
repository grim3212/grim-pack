package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;

public class ItemGrill extends ItemColorizer {

	public ItemGrill(Block block) {
		super(block);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		ActionResultType actionresulttype = this.tryPlace(new BlockItemUseContext(context));

		TileEntity tileentity = context.getWorld().getTileEntity(context.getPos());
		if (tileentity instanceof TileEntityGrill) {
			((TileEntityGrill) tileentity).setStoredBlockState(NBTUtil.readBlockState(NBTHelper.getTagCompound(context.getItem(), "stored_state")));
			((TileEntityGrill) tileentity).setCustomName(context.getItem().getDisplayName());
		}

		return actionresulttype != ActionResultType.SUCCESS && this.isFood() ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : actionresulttype;
	}
}