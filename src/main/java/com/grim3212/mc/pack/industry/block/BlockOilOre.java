package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockOilOre extends BlockManual {

	protected BlockOilOre() {
		super(IndustryNames.OIL_ORE, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.0f, 5.0f));
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.derrick_page;
	}
}
