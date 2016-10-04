package com.grim3212.mc.pack.core.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class BlockManual extends Block implements IManualBlock {

	public BlockManual(Material material, SoundType type) {
		super(material);
		setSoundType(type);
	}

	@Override
	public abstract Page getPage(IBlockState state);
}
