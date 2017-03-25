package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.tile.TileEntityObsidianSafe;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockObsidianSafe extends BlockStorage {

	public BlockObsidianSafe() {
		super(Material.ROCK, SoundType.STONE);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityObsidianSafe();
	}

	@Override
	public Page getPage(IBlockState state) {
		return null;
	}
}
