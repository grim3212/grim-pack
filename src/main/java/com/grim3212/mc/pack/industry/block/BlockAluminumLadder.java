package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class BlockAluminumLadder extends BlockLadder implements IManualBlock {

	public BlockAluminumLadder() {
		setSoundType(SoundType.METAL);
		setHardness(1.0F);
		setResistance(5.0F);
		setUnlocalizedName("aluminum_ladder");
		setRegistryName(new ResourceLocation(GrimPack.modID, "aluminum_ladder"));
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.buckLadd_page;
	}
}
