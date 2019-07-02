package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockLantern extends BlockManual {

	protected BlockLantern(String name) {
		super(name, Block.Properties.create(Material.MISCELLANEOUS).sound(SoundType.STONE).lightValue(14).hardnessAndResistance(0.1f));
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return worldIn.getMaxLightLevel();
	}

	@Override
	public boolean isSolid(BlockState state) {
		return false;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.lantern_page;
	}
}
