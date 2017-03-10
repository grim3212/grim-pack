package com.grim3212.mc.pack.util.grave;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockGrave extends BlockManual {

	public BlockGrave() {
		super(Material.ROCK, SoundType.STONE);
	}

	@Override
	public Page getPage(IBlockState state) {
		return null;
	}

}
