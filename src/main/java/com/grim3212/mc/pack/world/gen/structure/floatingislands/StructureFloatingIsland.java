package com.grim3212.mc.pack.world.gen.structure.floatingislands;

import java.util.ArrayList;
import java.util.Random;

import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.structure.Structure;
import com.grim3212.mc.pack.world.gen.structure.StructureStorage;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureFloatingIsland extends Structure {

	public static StructureFloatingIsland INSTANCE = new StructureFloatingIsland();

	public static ArrayList<Block> blacklist = new ArrayList<Block>();

	static {
		blacklist.add(Blocks.WATER);
		blacklist.add(Blocks.FLOWING_LAVA);
		blacklist.add(Blocks.FLOWING_WATER);
		blacklist.add(Blocks.LAVA);
		blacklist.add(Blocks.SAND);
		blacklist.add(Blocks.LOG);
		blacklist.add(Blocks.LEAVES);
		blacklist.add(Blocks.LOG2);
		blacklist.add(Blocks.LEAVES2);
		blacklist.add(Blocks.ICE);
		blacklist.add(Blocks.OBSIDIAN);
		blacklist.add(Blocks.BEDROCK);
	}

	@Override
	protected String getStructureName() {
		return "FloatingIslands";
	}

	@Override
	protected boolean generateStructureInChunk(long seed, World world, int chunkX, int chunkZ) {
		Random random = new Random(seed);

		int height = 128;
		boolean flag = true;
		int x = chunkX * 16 + 4 + random.nextInt(8);
		int z = chunkZ * 16 + 4 + random.nextInt(8);
		BlockPos pos = new BlockPos(x, height, z);

		while (flag) {
			if (world.isAirBlock(pos)) {
				pos = pos.down();

				if (pos.getY() <= 0)
					flag = false;

			} else {
				flag = false;
			}
		}

		Block block = world.getBlockState(pos).getBlock();
		if (blacklist.contains(block)) {
			return false;
		} else if (world.getBlockState(pos).isOpaqueCube()) {
			int size = 7 + random.nextInt(WorldConfig.sizevariancefrom7);

			return (new WorldGenFloatingIslands(size, getStructureStorage(world))).generate(world, random, pos);
		}

		return false;
	}

	@Override
	protected boolean canGenerateInChunk(World world, Random rand, int chunkX, int chunkZ) {
		return rand.nextInt(WorldConfig.spawnrate) == 0;
	}

	@Override
	protected boolean canGenerate() {
		return WorldConfig.subpartFloatingIslands;
	}

	private static final String STORAGE_NAME = "GrimFloatingIslands";

	@Override
	protected StructureStorage getStructureStorage(World world) {
		int dimensionId = world.provider.getDimension();
		if (!structureData.containsKey(dimensionId)) {
			FloatingIslandStorage data = (FloatingIslandStorage) world.getPerWorldStorage().getOrLoadData(FloatingIslandStorage.class, STORAGE_NAME);
			if (data == null) {
				data = new FloatingIslandStorage(STORAGE_NAME);
				world.getPerWorldStorage().setData(STORAGE_NAME, data);
			}
			structureData.put(dimensionId, data);
		}

		return structureData.get(dimensionId);
	}

	private class FloatingIslandStorage extends StructureStorage {

		public FloatingIslandStorage(String name) {
			super(name);
		}

		@Override
		public String getStructureName() {
			return "FloatingIslands";
		}
	}
}
