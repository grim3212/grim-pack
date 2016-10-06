package com.grim3212.mc.pack.cuisine.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockChocolateBlock extends BlockManual {

	public BlockChocolateBlock() {
		super(Material.CAKE, SoundType.CLOTH);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualCuisine.chocolateBars_page;
	}

}
