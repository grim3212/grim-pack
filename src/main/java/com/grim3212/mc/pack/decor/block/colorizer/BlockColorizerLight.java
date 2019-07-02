package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockColorizerLight extends BlockColorizer {

	public BlockColorizerLight() {
		super(DecorNames.COLORIZER_LIGHT, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 12f).lightValue(15));
	}
}
