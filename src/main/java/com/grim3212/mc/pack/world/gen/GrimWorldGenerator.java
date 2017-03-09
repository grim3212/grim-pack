package com.grim3212.mc.pack.world.gen;

import java.util.Random;

import com.grim3212.mc.pack.core.part.GrimWorldGen;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.util.FloatingIslandsBlacklist;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class GrimWorldGenerator extends GrimWorldGen {

	@Override
	protected void generateSurface(World world, Random random, int i, int j) {
		if (WorldConfig.generateRandomite) {
			for (int itr = 0; itr < 11; ++itr) {
				int x = i + random.nextInt(16);
				int y = random.nextInt(110);
				int z = j + random.nextInt(16);
				(new WorldGenMinable(WorldBlocks.randomite.getDefaultState(), 6)).generate(world, random, new BlockPos(x, y, z));
			}
		}

		if (WorldConfig.generateGunpowderReeds) {
			for (int reed = 0; reed < 5; ++reed) {
				int Xcoord1 = i + random.nextInt(16) + 8;
				int Ycoord1 = random.nextInt(128);
				int Zcoord1 = j + random.nextInt(16) + 8;
				(new WorldGenGunReeds()).generate(world, random, new BlockPos(Xcoord1, Ycoord1, Zcoord1));
			}
		}

		//TODO: Check if trailing chunks are occurring with the following generation
		FloatingIslandsBlacklist.generateFloatingIslands(world, random, i, j);

		generateExtras(world, random, i, j);
	}

	@Override
	protected void generateSurface(World world, IChunkProvider chunkProvider, Random random, int chunkX, int chunkZ) {
		if (WorldConfig.generateFlatBedRockSurface) {
			// Check if void chunk first
			boolean voidWorld = world.getBlockState(new BlockPos(chunkX * 16, 0, chunkZ * 16)).getBlock() == Blocks.AIR;

			if (voidWorld) {
				return;
			}

			ExtendedBlockStorage[] storage = chunkProvider.getLoadedChunk(chunkX, chunkZ).getBlockStorageArray();

			for (ExtendedBlockStorage blocks : storage) {
				if (blocks != null) {
					for (int y = 1; y <= 5; y++) {
						for (int x = 0; x < 16; x++) {
							for (int z = 0; z < 16; z++) {
								if (blocks.get(x, y, z).getBlock() == Blocks.BEDROCK) {
									blocks.set(x, y, z, Blocks.STONE.getDefaultState());
								}
							}
						}
					}
				}
			}
		}
	}

	private void generateExtras(World world, Random random, int blockX, int blockZ) {
		Biome biome = world.getBiomeProvider().getBiome(new BlockPos(blockX + 16, 60, blockZ + 16));

		if (biome != null) {
			if (random.nextInt(WorldConfig.frequencyWheatField) == 0 && BiomeDictionary.hasType(biome, Type.PLAINS)) {
				for (int i = 0; i < 5; i++) {
					int j = random.nextInt(40) + 60;
					new WorldGenFarmland(random.nextInt(8) + 4, random.nextInt(6) + 2).generate(world, random, new BlockPos(blockX + random.nextInt(16), j, blockZ + random.nextInt(16)));
				}
			}

			if (random.nextInt(WorldConfig.frequencySaplings) == 0 && !BiomeDictionary.hasType(biome, Type.SANDY)) {
				for (int i = 0; i < 5; i++) {
					int j = random.nextInt(30) + 60;
					new WorldGenSaplingsAndStumps(random.nextInt(16) + 1, true).generate(world, random, new BlockPos(blockX + random.nextInt(16), j, blockZ + random.nextInt(16)));
				}
			}

			if (random.nextInt(WorldConfig.frequencyTreeStumps) == 0 && !BiomeDictionary.hasType(biome, Type.SANDY)) {
				for (int i = 0; i < 5; i++) {
					int j = random.nextInt(30) + 60;
					new WorldGenSaplingsAndStumps(random.nextInt(12) + 3, false).generate(world, random, new BlockPos(blockX + random.nextInt(16), j, blockZ + random.nextInt(16)));
				}
			}

			if (random.nextInt(WorldConfig.frequencySandstonePillars) == 0 && biome == Biomes.DESERT) {
				for (int i = 0; i < 5; i++) {
					int j = random.nextInt(25) + 60;
					new WorldGenSandExpanded(random.nextInt(18) + 4, 0).generate(world, random, new BlockPos(blockX + random.nextInt(16), j, blockZ + random.nextInt(16)));
				}
			}

			if (random.nextInt(WorldConfig.frequencyCactusFields) == 0 && biome == Biomes.DESERT) {
				for (int i = 0; i < 5; i++) {
					int j = random.nextInt(55) + 60;
					new WorldGenSandExpanded(random.nextInt(24) + 3, 1).generate(world, random, new BlockPos(blockX + random.nextInt(16), j, blockZ + random.nextInt(16)));
				}
			}

			if (random.nextInt(WorldConfig.frequencySandPits) == 0 && biome == Biomes.DESERT) {
				for (int i = 0; i < 5; i++) {
					int j = random.nextInt(25) + 60;
					new WorldGenSandExpanded(random.nextInt(19) + 4, 2).generate(world, random, new BlockPos(blockX + random.nextInt(16), j, blockZ + random.nextInt(16)));
				}
			}

			if (random.nextInt(WorldConfig.frequencyMelons) == 0 && (biome == Biomes.RIVER || biome == Biomes.OCEAN || biome == Biomes.PLAINS)) {
				for (int i = 0; i < 5; i++) {
					int j = random.nextInt(10) + 60;
					new WorldGenMelons(random.nextInt(8) + 4).generate(world, random, new BlockPos(blockX + random.nextInt(16), j, blockZ + random.nextInt(16)));
				}
			}
		}
	}

	@Override
	protected void generateNether(World world, IChunkProvider chunkProvider, Random random, int chunkX, int chunkZ) {
		if (WorldConfig.generateFlatBedRockNether) {
			ExtendedBlockStorage[] storage = chunkProvider.getLoadedChunk(chunkX, chunkZ).getBlockStorageArray();

			for (ExtendedBlockStorage blocks : storage) {
				if (blocks != null) {
					// Bottom layer
					for (int y = 1; y <= 5; y++) {
						for (int x = 0; x < 16; x++) {
							for (int z = 0; z < 16; z++) {
								if (blocks.get(x, y, z).getBlock() == Blocks.BEDROCK) {
									blocks.set(x, y, z, Blocks.NETHERRACK.getDefaultState());
								}
							}
						}
					}

					// Top layer
					for (int y = 11; y < 15; y++) {
						for (int x = 0; x < 16; x++) {
							for (int z = 0; z < 16; z++) {
								if (blocks.get(x, y, z).getBlock() == Blocks.BEDROCK) {
									blocks.set(x, y, z, Blocks.NETHERRACK.getDefaultState());
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected void generateNether(World world, Random random, int i, int j) {
		for (int var5 = 0; var5 < 18; ++var5) {
			int var6 = i + random.nextInt(16);
			int var7 = random.nextInt(128);
			int var8 = j + random.nextInt(16);
			(new WorldGenCorruption(10, WorldBlocks.corruption_block)).generate(world, random, new BlockPos(var6, var7, var8));
		}
	}

	@Override
	protected void generateEnd(World world, Random random, int i, int j) {
	}

	@Override
	protected void generateCustom(DimensionType dimension, World world, Random random, int i, int j) {
	}
}
