package com.grim3212.mc.pack.decor.item;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.block.IColorizer;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBrush extends ItemManual {

	public ItemBrush() {
		this.setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(I18n.format("grimpack.decor.brush_info"));
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (world.isRemote) {
			if (player.isSneaking()) {
				TileEntity tileentity = world.getTileEntity(pos);

				if (tileentity instanceof TileEntityColorizer) {
					IBlockState storedState = ((TileEntityColorizer) tileentity).getBlockState();

					ItemStack storedstack = new ItemStack(storedState.getBlock(), 1, storedState.getBlock().getMetaFromState(storedState));
					if (storedstack.getItem() != null)
						player.sendMessage(new TextComponentTranslation("grimpack.decor.brush.stored", storedstack.getDisplayName()));
					else
						player.sendMessage(new TextComponentTranslation("grimpack.decor.brush.empty"));
				}
				return EnumActionResult.SUCCESS;
			}
		}

		IBlockState targetBlockState = world.getBlockState(pos);

		if (targetBlockState.getBlock() instanceof IColorizer) {
			((IColorizer) targetBlockState.getBlock()).clearColorizer(world, pos, targetBlockState, player);
		}

		return EnumActionResult.PASS;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualDecor.brush_page;
	}
}
