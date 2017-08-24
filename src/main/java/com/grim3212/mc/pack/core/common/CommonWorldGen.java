package com.grim3212.mc.pack.core.common;

import java.util.Random;

import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.part.GrimWorldGen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class CommonWorldGen extends GrimWorldGen {

	@Override
	protected void generateSurface(World world, Random random, int i, int j) {
		if (CoreConfig.subpartAluminum && CoreConfig.generateAluminum) {
			for (int k = 0; k < 12; ++k) {
				int x = i + random.nextInt(16);
				int y = random.nextInt(64);
				int z = j + random.nextInt(16);
				(new WorldGenMinable(CommonItems.aluminum_ore.getDefaultState(), 8)).generate(world, random, new BlockPos(x, y, z));
			}
		}
	}

}
