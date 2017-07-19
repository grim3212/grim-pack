package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHTorch extends BlockTorch implements IManualBlock {

	public BlockHTorch() {
		super();
		this.setSoundType(SoundType.GLASS);
		setUnlocalizedName("halogen_torch");
		setRegistryName(new ResourceLocation(GrimPack.modID, "halogen_torch"));
		setHardness(0.1F);
		setResistance(8.0F);
		setLightLevel(1.0F);
		setLightOpacity(0);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.hTorch_page;
	}
}
