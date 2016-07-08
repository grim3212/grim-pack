package com.grim3212.mc.pack.industry.block;

import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockChainFence extends BlockPane {

	public BlockChainFence(Material materialIn) {
		super(materialIn, true);
		setSoundType(SoundType.METAL);
	}
}
