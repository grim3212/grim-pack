package com.grim3212.mc.pack.world.gen.structure.cactusfield;

import java.util.Random;

import com.grim3212.mc.pack.world.gen.structure.StructureGenerator;
import com.grim3212.mc.pack.world.gen.structure.StructureStorage;
import com.grim3212.mc.pack.world.gen.structure.wheatfield.WorldGenFarmland;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class StructureCactusFieldGenerator extends StructureGenerator {

	private final int size;
	private final int attempts;
	private final StructureStorage storage;

	public StructureCactusFieldGenerator(String structName, int size, int attempts, StructureStorage storage) {
		super(structName);

		this.size = size;
		this.attempts = attempts;
		this.storage = storage;
	}

	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		int maxAttempts = attempts;
		for (int l = 0; l < maxAttempts; l++) {
			int x = (pos.getX() + random.nextInt(8)) - random.nextInt(8);
			int y = (pos.getY() + random.nextInt(4)) - random.nextInt(4);
			int z = (pos.getZ() + random.nextInt(8)) - random.nextInt(8);

			BlockPos newPos = new BlockPos(x, y, z);

			if (validCactusBlock(worldIn, newPos)) {
				recBlockGenerateX(worldIn, random, newPos, size);

				// Save struct
				storage.addBBSave(structName, new StructureBoundingBox(newPos.getX(), newPos.getY() - size, newPos.getZ(), newPos.getX() + (size * 2), newPos.getY() + size, newPos.getZ() + (size * 2)));
				return true;
			}
		}

		return false;
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

		// generate z in next valid z block with count = size
		if (count < 2)
			return;

		if (validCactusBlock(world, pos.south(2)))
			recBlockGenerateZ(world, random, pos.south(2), count - 1);
		else if (validCactusBlock(world, pos.south(2).down()))
			recBlockGenerateZ(world, random, pos.south(2).down(), count - 1);
		else if (validCactusBlock(world, pos.south(2).up()))
			recBlockGenerateZ(world, random, pos.south(2).up(), count - 1);
	}
}
