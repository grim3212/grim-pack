package com.grim3212.mc.pack.decor.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBurningWood extends Block {

	public BlockBurningWood() {
		super(Material.ROCK);
		this.setSoundType(SoundType.STONE);
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
		return side == EnumFacing.UP;
	}
}
