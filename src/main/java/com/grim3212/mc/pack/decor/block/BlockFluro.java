package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockFluro extends BlockManual {

	public BlockFluro(String name) {
		super(name, Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2f, 1.0f).lightValue(15));
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.fluro_page;
	}
}
