package com.grim3212.mc.pack.core;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSound extends Block {

	public BlockSound(Material material, SoundType type) {
		super(material);
		setSoundType(type);
	}
}
