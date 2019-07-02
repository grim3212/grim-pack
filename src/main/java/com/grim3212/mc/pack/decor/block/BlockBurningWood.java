package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockBurningWood extends BlockManual {

	public BlockBurningWood() {
		super(DecorNames.BURNING_WOOD, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.8f, 5f));
	}

	@Override
	public boolean isFireSource(BlockState state, IBlockReader world, BlockPos pos, Direction side) {
		return side == Direction.UP;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.burningWood_page;
	}
}
