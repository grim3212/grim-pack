package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.tile.TileEntityWarehouseCrate;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWarehouseCrate extends BlockStorage {

	public BlockWarehouseCrate() {
		super(Material.WOOD, SoundType.WOOD);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityWarehouseCrate();
	}

	@Override
	protected int getGuiId() {
		return PackGuiHandler.WAREHOUSE_CRATE_GUI_ID;
	}

	@Override
	public Page getPage(IBlockState state) {
		return null;
	}

	@Override
	public boolean isDoorBlocked(World world, BlockPos pos, IBlockState state) {
		return isInvalidBlock(world, pos.up());
	}
}
