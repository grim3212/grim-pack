package com.grim3212.mc.pack.industry.block;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTemperedGlass extends BlockGlass {

	public BlockTemperedGlass(Material materialIn, boolean ignoreSimilarity) {
		super(materialIn, ignoreSimilarity);
		setSoundType(SoundType.GLASS);
	}

}
