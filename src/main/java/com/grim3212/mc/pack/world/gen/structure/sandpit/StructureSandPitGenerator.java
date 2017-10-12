package com.grim3212.mc.pack.world.gen.structure.sandpit;

import java.util.Random;

import com.grim3212.mc.pack.world.gen.structure.StructureGenerator;
import com.grim3212.mc.pack.world.gen.structure.wheatfield.WorldGenFarmland;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureSandPitGenerator extends StructureGenerator {

	private final int size;
	private final int attempts;

	public StructureSandPitGenerator(String structName, int size, int attempts) {
		super(structName);

		this.size = size;
		this.attempts = attempts;
	}

	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		int maxAttempts = attempts;
		for (int l = 0; l < maxAttempts; l++) {
			int x = (pos.getX() + random.nextInt(8)) - random.nextInt(8);
			int y = (pos.getY() + random.nextInt(4)) - random.nextInt(4);
			int z = (pos.getZ() + random.nextInt(8)) - random.nextInt(8);

			BlockPos newPos = new BlockPos(x, y, z);

			if (worldIn.getBlockState(newPos.down()).getBlock() == Blocks.SAND && worldIn.getBlockState(newPos).getBlock() == Blocks.AIR) {
				int depth = (int) (Math.ceil(((double) size) / 2));
				for (int newX = 0; newX < size; newX++) {
					for (int newZ = 0; newZ < size; newZ++) {
						// place sand block X blocks down; determined by depth
						// and indexes
						int layer0 = newX < depth ? newX : size - 1 - newX;
						int layer1 = newZ < depth ? newZ : size - 1 - newZ;
						int layer = layer0 < layer1 ? layer0 : layer1;

						// place sand block, and remove above blocks plus fill
						// below blocks
						worldIn.setBlockState(pos.add(newX, -3 - (layer * 3), newZ), Blocks.SAND.getDefaultState());
						worldIn.setBlockState(pos.add(newX, -4 - (layer * 3), newZ), Blocks.SAND.getDefaultState());
						worldIn.setBlockState(pos.add(newX, -5 - (layer * 3), newZ), Blocks.SAND.getDefaultState());
						WorldGenFarmland.removeAboveBlocks(worldIn, pos.add(newX, -2 - (layer * 3), newZ));
						WorldGenFarmland.fillBelowBlocks(worldIn, pos.add(newX, -6 - (layer * 3), newZ), Blocks.SAND);
						// add random additional sand blocks
						float f = random.nextFloat();
						if (f < 0.75F)
							worldIn.setBlockState(pos.add(newX, -2 - (layer * 3), newZ), Blocks.SAND.getDefaultState());
						if (f < 0.45F)
							worldIn.setBlockState(pos.add(newX, -1 - (layer * 3), newZ), Blocks.SAND.getDefaultState());
						if (f < 0.15F)
							worldIn.setBlockState(pos.add(newX, -(layer * 3), newZ), Blocks.SAND.getDefaultState());
					}
				}

				return true;
			}
		}

		return false;
	}

}
