package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockFancyStone extends BlockManual {

	public BlockFancyStone() {
		super(DecorNames.FANCY_STONE, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.5f, 10f));
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.fancyStone_page;
	}
}
