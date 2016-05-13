package com.grim3212.mc.pack.cuisine.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockChocolate extends Block {

	public BlockChocolate() {
		super(Material.cake);
		setStepSound(SoundType.CLOTH);
	}

}
