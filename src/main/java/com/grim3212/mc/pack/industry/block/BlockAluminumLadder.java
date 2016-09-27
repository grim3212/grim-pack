package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;

public class BlockAluminumLadder extends BlockLadder implements IManualBlock {

	public BlockAluminumLadder() {
		setSoundType(SoundType.METAL);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.buckLadd_page;
	}
}
