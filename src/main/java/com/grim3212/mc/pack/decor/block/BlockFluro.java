package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.BlockColored;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class BlockFluro extends BlockColored implements IManualBlock {

	public BlockFluro() {
		super(Material.GLASS);
		setSoundType(SoundType.GLASS);
		setUnlocalizedName("fluro");
		setRegistryName(new ResourceLocation(GrimPack.modID, "fluro"));
		setHardness(0.2F);
		setLightLevel(1.0F);
		setResistance(1.0F);
		setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.fluro_page;
	}
}
