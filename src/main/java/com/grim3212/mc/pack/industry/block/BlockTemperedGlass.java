package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockTemperedGlass extends BlockGlass implements IManualBlock {

	public BlockTemperedGlass(Material materialIn, boolean ignoreSimilarity) {
		super(materialIn, ignoreSimilarity);
		setSoundType(SoundType.GLASS);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.modernFurnaceRecipes_page;
	}

}
