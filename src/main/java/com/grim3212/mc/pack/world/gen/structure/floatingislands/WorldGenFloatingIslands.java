package com.grim3212.mc.pack.world.gen.structure.floatingislands;

import java.util.Random;

import com.grim3212.mc.pack.world.gen.structure.StructureStorage;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeHell;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class WorldGenFloatingIslands {

	private int size;
	private final StructureStorage storage;

	public WorldGenFloatingIslands(int size, StructureStorage storage) {
		this.size = size;

		this.storage = storage;
	}

	public boolean generate(World world, Random rand, BlockPos pos) {
		int yStart = rand.nextInt(25) + 20;

		if (!(world.getBiomeForCoordsBody(pos) instanceof BiomeHell))
			if (128 - pos.getY() < 45) {
				return false;
			} else {
				IBlockState block;
				for (int x = -size; x <= size; ++x) {
					for (int z = -size; z <= size; ++z) {
						if (Math.pow((double) x, 2.0D) + Math.pow((double) z, 2.0D) <= Math.pow((double) size, 2.0D)) {
							for (int y = 1; y < 17; ++y) {
								BlockPos newPos = pos.add(x, y, z);
								BlockPos movedPos = pos.add(x, yStart + y, z);
								block = world.getBlockState(newPos);
								if (block.getBlock() == Blocks.BEDROCK) {
									return false;
								} else if (block.getBlock() != Blocks.FLOWING_WATER && block.getBlock() != Blocks.WATER && block.getBlock() != Blocks.FLOWING_LAVA && block.getBlock() != Blocks.LAVA && block.getBlock() != Blocks.SAND && block.getBlock() != Blocks.GRAVEL) {
									world.setBlockState(movedPos, block, 0);
									world.setBlockToAir(newPos);
								} else {
									world.setBlockState(movedPos, block, 3);
									world.setBlockToAir(newPos);
								}
							}
						}
					}
				}

				for (int x = -size; x <= size; ++x) {
					for (int z = -size; z <= size; ++z) {
						for (int y = -size; y <= size; ++y) {
							if (Math.pow((double) x, 2.0D) + Math.pow((double) z, 2.0D) + Math.pow((double) y, 2.0D) <= Math.pow((double) size, 2.0D) && pos.getY() + y <= pos.getY()) {
								BlockPos newPos = pos.add(x, y, z);
								BlockPos movedPos = pos.add(x, yStart + y, z);
								block = world.getBlockState(newPos);
								if (block.getBlock() == Blocks.BEDROCK) {
									return false;
								} else if (block.getBlock() != Blocks.FLOWING_WATER && block.getBlock() != Blocks.WATER && block.getBlock() != Blocks.FLOWING_LAVA && block.getBlock() != Blocks.LAVA && block.getBlock() != Blocks.SAND && block.getBlock() != Blocks.GRAVEL) {
									world.setBlockState(movedPos, block, 0);
									world.setBlockToAir(newPos);
								} else {
									world.setBlockState(movedPos, block, 3);
									world.setBlockToAir(newPos);
								}
							}
						}
					}
				}
			}
		BlockPos start = new BlockPos(pos.getX() - size, pos.getY(), pos.getZ() - size);

		// save it
		storage.addBBSave(StructureFloatingIsland.FLOATING_ISLAND_NAME, new StructureBoundingBox(start.getX(), start.getY(), start.getZ(), start.getX() + (size * 2), start.getY() + 17, start.getZ() + (size * 2)));
		return true;
	}
}
