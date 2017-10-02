package com.grim3212.mc.pack.decor.block;

import java.util.Random;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;

import net.minecraft.block.BlockSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNeonSign extends BlockSign implements IManualBlock {

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityNeonSign();
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(DecorItems.neon_sign);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return DecorItems.neon_sign;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			// Right click after place to edit text
			playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.NEON_SIGN_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		} else {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			return tileentity instanceof TileEntityNeonSign ? ((TileEntityNeonSign) tileentity).executeCommand(playerIn) : false;
		}
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.neonSign_page;
	}
}
