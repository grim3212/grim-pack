package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntityGoldSafe;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGoldSafe extends BlockStorage {

	public BlockGoldSafe() {
		super(Material.IRON, SoundType.METAL);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGoldSafe();
	}

	@Override
	protected int getGuiId() {
		return PackGuiHandler.GOLD_SAFE_GUI_ID;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);

		if ((te != null) && ((te instanceof TileEntityGoldSafe))) {
			TileEntityGoldSafe storage = (TileEntityGoldSafe) te;
			ItemStack item = new ItemStack(IndustryBlocks.gold_safe);

			NBTTagCompound compound = new NBTTagCompound();
			if (storage.isLocked()) {
				compound.setString("Lock", storage.getLockCode().getLock());
				storage.clearLock();
			}

			ItemStackHelper.saveAllItems(compound, storage.getItems());
			NBTHelper.setTagCompound(item, "GoldSafe", compound);

			InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		if (hasTileEntity(state)) {
			worldIn.removeTileEntity(pos);
		}
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 0;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.safes_page;
	}

}
