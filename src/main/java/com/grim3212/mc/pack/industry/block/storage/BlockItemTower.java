package com.grim3212.mc.pack.industry.block.storage;

import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntityItemTower;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockItemTower extends BlockStorage {

	public BlockItemTower() {
		super("item_tower", Material.IRON, SoundType.METAL);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityItemTower();
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.itemTower_page;
	}

	@Override
	public boolean isDoorBlocked(World world, BlockPos pos, BlockState state) {
		return false;
	}

	@Override
	protected int getGuiId() {
		return PackGuiHandler.ITEM_TOWER_GUI_ID;
	}

	@Override
	public boolean canBeLocked() {
		return false;
	}
}
