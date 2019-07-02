package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockChainFence extends PaneBlock implements IManualBlock {

	public BlockChainFence() {
		super(Block.Properties.create(Material.MISCELLANEOUS).sound(SoundType.METAL).hardnessAndResistance(0.5f, 5.0f));
		setRegistryName(IndustryNames.CHAIN_FENCE);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.others_page;
	}
}
