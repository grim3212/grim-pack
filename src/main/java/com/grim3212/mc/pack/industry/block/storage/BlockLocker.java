package com.grim3212.mc.pack.industry.block.storage;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.tile.TileEntityLocker;
import com.grim3212.mc.pack.industry.tile.TileEntityStorage;
import com.grim3212.mc.pack.industry.util.StorageUtil;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockLocker extends BlockStorage {

	public BlockLocker() {
		super("locker", Material.IRON, SoundType.METAL);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLocker();
	}

	@Override
	protected int getGuiId() {
		return PackGuiHandler.LOCKER_GUI_ID;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.locker_page;
	}

	@Override
	public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, World worldIn, BlockPos pos) {
		if ((isDualLocker(worldIn, pos)) && (isTopLocker(worldIn, pos))) {
			return getPlayerRelativeBlockHardness(state, player, worldIn, pos.down());
		}

		return super.getPlayerRelativeBlockHardness(state, player, worldIn, pos);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (((worldIn.getBlockState(pos.down()).getBlock() == IndustryBlocks.locker) && (worldIn.getBlockState(pos.down(2)).getBlock() == IndustryBlocks.locker)) || ((worldIn.getBlockState(pos.up()).getBlock() == IndustryBlocks.locker) && (worldIn.getBlockState(pos.up(2)).getBlock() == IndustryBlocks.locker))) {
			return;
		}

		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		TileEntity tileentitytop = worldIn.getTileEntity(pos.up());
		if ((isBottomLocker(worldIn, pos)) && (tileentitytop != null) && ((tileentitytop instanceof TileEntityStorage))) {
			TileEntityStorage tileentitythis = (TileEntityStorage) worldIn.getTileEntity(pos);
			TileEntityStorage tileentitystoragetop = (TileEntityStorage) tileentitytop;

			if (tileentitystoragetop.isLocked()) {
				tileentitythis.setLockCode(tileentitystoragetop.getLockCode());
				tileentitystoragetop.clearLock();
			}
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos.down()).getBlock() == IndustryBlocks.locker) {
			return !isTopLocker(worldIn, pos.down());
		} else if (worldIn.getBlockState(pos.up()).getBlock() == IndustryBlocks.locker) {
			return !isBottomLocker(worldIn, pos.up());
		}

		return super.canPlaceBlockAt(worldIn, pos);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);

		if (te != null && te instanceof TileEntityStorage) {
			TileEntityStorage tileentity = (TileEntityStorage) te;

			if (isDualLocker(worldIn, pos)) {
				if (isTopLocker(worldIn, pos)) {
					return onBlockActivated(worldIn, pos.down(), worldIn.getBlockState(pos.down()), playerIn, hand, facing, hitX, hitY, hitZ);
				}

				if (playerIn.getHeldItem(hand).getItem() == IndustryItems.locksmith_lock) {
					if (StorageUtil.tryPlaceLock(tileentity, playerIn, hand))
						return true;
				}

				if ((!isDoorBlocked(worldIn, pos, state)) && (!isDoorBlocked(worldIn, pos.up(), worldIn.getBlockState(pos.up()))) && (StorageUtil.canAccess(tileentity, playerIn))) {
					playerIn.openGui(GrimPack.INSTANCE, this.getGuiId(), worldIn, pos.getX(), pos.getY(), pos.getZ());
				}
			} else {
				if (playerIn.getHeldItem(hand).getItem() == IndustryItems.locksmith_lock) {
					if (StorageUtil.tryPlaceLock(tileentity, playerIn, hand))
						return true;
				}

				if ((!isDoorBlocked(worldIn, pos, state)) && (StorageUtil.canAccess(tileentity, playerIn))) {
					playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.STORAGE_DEFAULT_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
				}
			}
		}

		return true;
	}

	public boolean isDualLocker(World world, BlockPos pos) {
		return (isTopLocker(world, pos)) || (isBottomLocker(world, pos));
	}

	public boolean isTopLocker(World world, BlockPos pos) {
		return world.getBlockState(pos.down()) == world.getBlockState(pos);
	}

	public boolean isBottomLocker(World world, BlockPos pos) {
		return world.getBlockState(pos.up()) == world.getBlockState(pos);
	}
}
