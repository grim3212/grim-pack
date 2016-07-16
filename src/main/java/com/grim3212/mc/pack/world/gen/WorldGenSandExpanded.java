package com.grim3212.mc.pack.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSandExpanded extends WorldGenerator {

	int size; // size of object to be placed
	int type; // type of object to be placed

	public WorldGenSandExpanded(int a, int b) {
		size = a;
		type = b;
	}

	private boolean validCactusBlock(World world, BlockPos pos) {
		Block bid = world.getBlockState(pos.up()).getBlock();
		return world.getBlockState(pos).getBlock() == Blocks.SAND && (bid == Blocks.AIR || bid == Blocks.CACTUS || bid == Blocks.DEADBUSH);
		
	}

	private void recBlockGenerateX(World world, Random random, BlockPos pos, int count) {
		// set block (valid location assumed)
		world.setBlockState(pos, Blocks.SAND.getDefaultState());
		world.setBlockState(pos.up(), Blocks.CACTUS.getDefaultState());
		WorldGenFarmland.removeAboveBlocks(world, pos.east().up());
		WorldGenFarmland.removeAboveBlocks(world, pos.west().up());
		WorldGenFarmland.removeAboveBlocks(world, pos.up().north());
		WorldGenFarmland.removeAboveBlocks(world, pos.up().south());

		// generate z in next valid z block with count = size
		if (validCactusBlock(world, pos.south(2)))
			recBlockGenerateZ(world, random, pos.south(2), size - 1);
		else if (validCactusBlock(world, pos.south(2).down()))
			recBlockGenerateZ(world, random, pos.south(2).down(), size - 1);
		else if (validCactusBlock(world, pos.south(2).up()))
			recBlockGenerateZ(world, random, pos.south(2).up(), size - 1);

		if (validCactusBlock(world, pos.east().south()))
			recBlockGenerateZ(world, random, pos.east().south(), size);
		else if (validCactusBlock(world, pos.east().south().down()))
			recBlockGenerateZ(world, random, pos.east().south().down(), size);
		else if (validCactusBlock(world, pos.east().south().up()))
			recBlockGenerateZ(world, random, pos.east().south().up(), size);

		// if count > 1, generate x in next x block with count - 1
		if (count < 2)
			return;

		if (validCactusBlock(world, pos.east(2)))
			recBlockGenerateX(world, random, pos.east(2), count - 1);
		else if (validCactusBlock(world, pos.east(2).down()))
			recBlockGenerateX(world, random, pos.east(2).down(), count - 1);
		else if (validCactusBlock(world, pos.east(2).up()))
			recBlockGenerateX(world, random, pos.east(2).up(), count - 1);
	}

	private void recBlockGenerateZ(World world, Random random, BlockPos pos, int count) {
		// set block (valid location assumed)
		world.setBlockState(pos, Blocks.SAND.getDefaultState());
		world.setBlockState(pos.up(), Blocks.CACTUS.getDefaultState());
		WorldGenFarmland.removeAboveBlocks(world, pos.east().up());
		WorldGenFarmland.removeAboveBlocks(world, pos.west().up());
		WorldGenFarmland.removeAboveBlocks(world, pos.up().north());
		WorldGenFarmland.removeAboveBlocks(world, pos.up().south());

		// generate z in next valid z block with count = numWheat
		if (count < 2)
			return;
		if (validCactusBlock(world, pos.south(2)))
			recBlockGenerateZ(world, random, pos.south(2), count - 1);
		else if (validCactusBlock(world, pos.south(2).down()))
			recBlockGenerateZ(world, random, pos.south(2).down(), count - 1);
		else if (validCactusBlock(world, pos.south(2).up()))
			recBlockGenerateZ(world, random, pos.south(2).up(), count - 1);
	}

	@Override
	/*
	 * Generates different things in sand, based on the type: 0: Sandstone
	 * Pillar 1: Cactus Field 2: Sand Pit
	 */
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		int maxAttempts;
		switch (type) {
		case 0:
		case 1:
		case 2:
			maxAttempts = 2;
			break;
		default:
			maxAttempts = 2;
			break;
		}
		for (int l = 0; l < maxAttempts; l++) {
			int i1 = (pos.getX() + random.nextInt(16)) - random.nextInt(16);
			int j1 = (pos.getY() + random.nextInt(8)) - random.nextInt(8);
			int k1 = (pos.getZ() + random.nextInt(16)) - random.nextInt(16);

			BlockPos newPos = new BlockPos(i1, j1, k1);

			if (type == 0 && worldIn.getBlockState(newPos).getBlock() == Blocks.SAND && j1 < 126 - size) {
				// Sand Pillar
				for (int l1 = 0; l1 < size; l1++)
					for (int l2 = 0; l2 < size / 4; l2++)
						for (int l3 = 0; l3 < size / 4; l3++)
							worldIn.setBlockState(pos.add(l2, l1, l3), Blocks.SANDSTONE.getDefaultState());
				return true;
			} else if (type == 1 && validCactusBlock(worldIn, newPos)) {
				recBlockGenerateX(worldIn, random, newPos, size);
				return true;
			} else if (type == 2 && worldIn.getBlockState(newPos.down()).getBlock() == Blocks.SAND && worldIn.getBlockState(newPos).getBlock() == Blocks.AIR) {
				int depth = (int) (Math.ceil(((double) size) / 2));
				for (int l0 = 0; l0 < size; l0++)
					for (int l1 = 0; l1 < size; l1++) {
						// place sand block X blocks down; determined by depth
						// and indexes
						int layer0 = l0 < depth ? l0 : size - 1 - l0;
						int layer1 = l1 < depth ? l1 : size - 1 - l1;
						int layer = layer0 < layer1 ? layer0 : layer1;

						// place sand block, and remove above blocks plus fill
						// below blocks
						worldIn.setBlockState(pos.add(l0, -3 - (layer * 3), l1), Blocks.SAND.getDefaultState());
						worldIn.setBlockState(pos.add(l0, -4 - (layer * 3), l1), Blocks.SAND.getDefaultState());
						worldIn.setBlockState(pos.add(l0, -5 - (layer * 3), l1), Blocks.SAND.getDefaultState());
						WorldGenFarmland.removeAboveBlocks(worldIn, pos.add(l0, -2 - (layer * 3), l1));
						WorldGenFarmland.fillBelowBlocks(worldIn, pos.add(l0, -6 - (layer * 3), l1), Blocks.SAND);
						// add random additional sand blocks
						float f = random.nextFloat();
						if (f < 0.75F)
							worldIn.setBlockState(pos.add(l0, -2 - (layer * 3), l1), Blocks.SAND.getDefaultState());
						if (f < 0.45F)
							worldIn.setBlockState(pos.add(l0, -1 - (layer * 3), l1), Blocks.SAND.getDefaultState());
						if (f < 0.15F)
							worldIn.setBlockState(pos.add(l0, -(layer * 3), l1), Blocks.SAND.getDefaultState());
					}
				return true;
			}
		}
		return false;
	}
}
