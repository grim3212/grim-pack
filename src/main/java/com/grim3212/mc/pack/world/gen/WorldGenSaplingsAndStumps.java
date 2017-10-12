package com.grim3212.mc.pack.world.gen;

import java.util.Random;

import com.grim3212.mc.pack.world.gen.structure.wheatfield.WorldGenFarmland;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSaplingsAndStumps extends WorldGenerator {

	int maxNum; // maximum number of saplings or stumps for the chunk
	boolean sapling; // if true, place saplings, otherwise place stumps

	public WorldGenSaplingsAndStumps(int i, boolean b) {
		maxNum = i;
		sapling = b;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		int numMade = 0; // # of Saplings/Stumps already placed
		for (int l = 0; l < 40; l++) {
			int i1 = (pos.getX() + random.nextInt(8)) - random.nextInt(8);
			int j1 = (pos.getY() + random.nextInt(4)) - random.nextInt(4);
			int k1 = (pos.getZ() + random.nextInt(8)) - random.nextInt(8);

			BlockPos newPos = new BlockPos(i1, j1, k1);

			if (worldIn.getBlockState(newPos).getBlock() == Blocks.AIR && worldIn.getBlockState(newPos.down()).getBlock() == Blocks.GRASS) {
				Block bID = sapling ? Blocks.SAPLING : Blocks.LOG;
				int meta = sapling ? 3 + (random.nextInt(2) * 8) : 3;
				worldIn.setBlockState(newPos, bID.getStateFromMeta(random.nextInt(meta)), 2);
				WorldGenFarmland.removeAboveBlocks(worldIn, newPos.up());
				numMade++;
			}
			if (numMade > maxNum)
				return true;
		}
		if (numMade == 0)
			return false;
		else
			return true;
	}
}