package com.grim3212.mc.pack.world.gen;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEvents {

	@SubscribeEvent
	public void populateDesertWells(BiomeEvent.CreateDecorator event) {
		if (event.getBiome() == Biomes.DESERT || event.getBiome() == Biomes.MUTATED_DESERT) {
			event.setNewBiomeDecorator(new BiomeDecoratorWells());
		}
	}

	private static class BiomeDecoratorWells extends BiomeDecorator {

		@Override
		public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
			super.decorate(worldIn, random, biome, pos);

			if (random.nextInt(1000) == 0) {

				int i = random.nextInt(16) + 8;
				int j = random.nextInt(16) + 8;
				BlockPos blockpos1 = worldIn.getHeight(pos.add(i, 0, j)).up();
				System.out.println("Generated new well at [" + blockpos1 + "]");
				(new WorldGenBetterDesertWells()).generate(worldIn, random, blockpos1);
			}
		}

	}
}
