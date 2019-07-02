package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockAluminumLadder extends LadderBlock implements IManualBlock {

	public BlockAluminumLadder() {
		super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(1.0f, 5.0f));
		setRegistryName(IndustryNames.ALUMINUM_LADDER);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.buckLadd_page;
	}
}
