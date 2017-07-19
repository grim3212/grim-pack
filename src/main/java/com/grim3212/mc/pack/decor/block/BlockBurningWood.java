package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBurningWood extends BlockManual {

	public BlockBurningWood() {
		super("burning_wood", Material.ROCK, SoundType.STONE);
		setHardness(0.8F);
		setResistance(5F);
		setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
		return side == EnumFacing.UP;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.burningWood_page;
	}
}
