package com.grim3212.mc.pack.decor.item;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;
import com.grim3212.mc.pack.decor.network.MessageNeonOpen;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class ItemNeonSign extends WallOrFloorItem implements IManualItem {

	public ItemNeonSign() {
		super(DecorBlocks.neon_sign_standing, DecorBlocks.neon_sign_wall, new Item.Properties().maxStackSize(16).group(GrimItemGroups.GRIM_DECOR));
		this.setRegistryName(DecorNames.NEON_SIGN);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualDecor.neonSign_page;
	}

	@Override
	protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
		boolean flag = super.onBlockPlaced(pos, worldIn, player, stack, state);
		if (!worldIn.isRemote && !flag && player != null) {
			((TileEntityNeonSign) worldIn.getTileEntity(pos)).setOwner(player);
			PacketDispatcher.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageNeonOpen(pos));
		}

		return flag;
	}
}