package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTemperedGlass extends GlassBlock implements IManualBlock {

	public BlockTemperedGlass() {
		super(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f, 15.0f));
		setRegistryName(IndustryNames.TEMPERED_GLASS);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.modernFurnaceRecipes_page;
	}

}
