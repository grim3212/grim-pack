package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;

public class BlockHardenedWood extends BlockManual {

	public BlockHardenedWood() {
		super(DecorNames.HARDENED_WOOD, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 12f));
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.colorizer_page;
	}
}
