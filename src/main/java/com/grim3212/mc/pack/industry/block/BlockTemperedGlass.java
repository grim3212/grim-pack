package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class BlockTemperedGlass extends BlockGlass implements IManualBlock {

	public BlockTemperedGlass() {
		super(Material.GLASS, false);
		setSoundType(SoundType.GLASS);
		setHardness(1.0F);
		setResistance(15.0F);
		setLightOpacity(0);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
		setUnlocalizedName("tempered_glass");
		setRegistryName(new ResourceLocation(GrimPack.modID, "tempered_glass"));
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.modernFurnaceRecipes_page;
	}

}
