package com.grim3212.mc.pack.industry.block.storage;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntityWoodCabinet;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWoodCabinet extends BlockStorage {

	public BlockWoodCabinet() {
		super("wood_cabinet", Material.WOOD, SoundType.WOOD);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityWoodCabinet();
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.cabinets_page;
	}
}
