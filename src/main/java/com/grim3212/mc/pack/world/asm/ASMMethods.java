package com.grim3212.mc.pack.world.asm;

import java.util.Random;

import com.grim3212.mc.pack.world.gen.WorldGenBetterDesertWells;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class ASMMethods extends BiomeGenBase {

	public ASMMethods(int par1) {
		super(par1);
	}

	@Override
	public void decorate(World world, Random random, BlockPos pos) {
		super.decorate(world, random, pos);

		if (random.nextInt(1000) == 0) {
			int i = random.nextInt(16) + 8;
			int j = random.nextInt(16) + 8;
			BlockPos blockpos1 = world.getHeight(pos.add(i, 0, j)).up();
			(new WorldGenBetterDesertWells()).generate(world, random, blockpos1);
		}
	}
}