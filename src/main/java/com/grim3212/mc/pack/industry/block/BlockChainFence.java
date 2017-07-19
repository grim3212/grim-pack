package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class BlockChainFence extends BlockPane implements IManualBlock {

	public BlockChainFence(Material materialIn) {
		super(materialIn, true);
		setSoundType(SoundType.METAL);
		setHardness(0.5F);
		setResistance(5.0F);
		setUnlocalizedName("chain_fence");
		setRegistryName(new ResourceLocation(GrimPack.modID, "chain_fence"));
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.others_page;
	}
}
