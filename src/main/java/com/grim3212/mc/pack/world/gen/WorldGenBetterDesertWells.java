package com.grim3212.mc.pack.world.gen;

import java.util.Random;

import com.google.common.base.Predicates;
import com.grim3212.mc.pack.world.util.LootTables;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBetterDesertWells extends WorldGenerator {

	private static final BlockStateMatcher IS_SAND = BlockStateMatcher.forBlock(Blocks.SAND).where(BlockSand.VARIANT, Predicates.equalTo(BlockSand.EnumType.SAND));
	private final IBlockState sandSlab = Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND).withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM);
	private final IBlockState sandstone = Blocks.SANDSTONE.getDefaultState();
	private final IBlockState water = Blocks.FLOWING_WATER.getDefaultState();
	private int randomType;

	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		this.randomType = (random.nextInt(5) + 1);

		while ((worldIn.isAirBlock(pos)) && (pos.getY() > 7 + 5 * this.randomType)) {
			pos = pos.down();
		}

		if (!IS_SAND.apply(worldIn.getBlockState(pos))) {
			return false;
		} else {
			Block l = worldIn.getBlockState(pos).getBlock();

			if (l != Blocks.SAND) {
				return false;
			}

			for (int i1 = -2; i1 <= 2; i1++) {
				for (int j1 = -2; j1 <= 2; j1++) {
					if ((worldIn.isAirBlock(pos.add(i1, -1, j1)) && (worldIn.isAirBlock(pos.add(i1, -2, j1))))) {
						return false;
					}
				}
			}

			for (int i1 = -1; i1 <= 0; i1++) {
				for (int j1 = -2; j1 <= 2; j1++) {
					for (int k1 = -2; k1 <= 2; k1++) {
						worldIn.setBlockState(pos.add(j1, i1, k1), sandstone, 2);
					}
				}
			}

			int depth = this.randomType * 5 + 5;

			for (int i = 0; i < depth; i++) {
				worldIn.setBlockState(pos.add(0, -i, 0), water, 2);
				worldIn.setBlockState(pos.add(-1, -i, 0), water, 2);
				worldIn.setBlockState(pos.add(1, -i, 0), water, 2);
				worldIn.setBlockState(pos.add(0, -i, -1), water, 2);
				worldIn.setBlockState(pos.add(0, -i, 1), water, 2);

				if (i != 0) {
					worldIn.setBlockState(pos.add(2, -i, 0), sandstone, 2);
					worldIn.setBlockState(pos.add(-2, -i, 0), sandstone, 2);
					worldIn.setBlockState(pos.add(0, -i, 2), sandstone, 2);
					worldIn.setBlockState(pos.add(0, -i, -2), sandstone, 2);
					worldIn.setBlockState(pos.add(1, -i, 1), sandstone, 2);
					worldIn.setBlockState(pos.add(1, -i, -1), sandstone, 2);
					worldIn.setBlockState(pos.add(-1, -i, 1), sandstone, 2);
					worldIn.setBlockState(pos.add(-1, -i, -1), sandstone, 2);
				}
			}

			worldIn.setBlockState(pos.add(0, -depth - 1, 0), sandstone, 2);
			worldIn.setBlockState(pos.add(-1, -depth, 0), sandstone, 2);
			worldIn.setBlockState(pos.add(1, -depth, 0), sandstone, 2);
			worldIn.setBlockState(pos.add(0, -depth, -1), sandstone, 2);
			worldIn.setBlockState(pos.add(0, -depth, 1), sandstone, 2);
			worldIn.setBlockState(pos.add(0, -depth, 0), Blocks.CHEST.getDefaultState(), 2);

			TileEntityChest chest = (TileEntityChest) worldIn.getTileEntity(pos.add(0, -depth, 0));
			if (chest != null)
				setChestContents(chest, random);

			for (int i1 = -2; i1 <= 2; i1++) {
				for (int j1 = -2; j1 <= 2; j1++) {
					if ((i1 == -2) || (i1 == 2) || (j1 == -2) || (j1 == 2)) {
						worldIn.setBlockState(pos.add(i1, 1, j1), sandstone, 2);
					}
				}
			}

			worldIn.setBlockState(pos.add(2, 1, 0), sandSlab, 2);
			worldIn.setBlockState(pos.add(-2, 1, 0), sandSlab, 2);
			worldIn.setBlockState(pos.add(0, 1, 2), sandSlab, 2);
			worldIn.setBlockState(pos.add(0, 1, -2), sandSlab, 2);

			for (int i1 = -1; i1 <= 1; i1++) {
				for (int j1 = -1; j1 <= 1; j1++) {
					if ((i1 == 0) && (j1 == 0)) {
						worldIn.setBlockState(pos.add(i1, 4, j1), sandstone, 2);
					} else {
						worldIn.setBlockState(pos.add(i1, 4, j1), sandSlab, 2);
					}
				}
			}

			for (int i1 = 1; i1 <= 3; i1++) {
				worldIn.setBlockState(pos.add(-1, i1, -1), sandstone, 2);
				worldIn.setBlockState(pos.add(-1, i1, 1), sandstone, 2);
				worldIn.setBlockState(pos.add(1, i1, -1), sandstone, 2);
				worldIn.setBlockState(pos.add(1, i1, 1), sandstone, 2);
			}
			return true;
		}
	}

	private void setChestContents(TileEntityChest chest, Random random) {
		switch (this.randomType) {
		case 1:
			chest.setLootTable(LootTables.CHESTS_DESERT_LEVEL_10, random.nextLong());
			break;
		case 2:
			chest.setLootTable(LootTables.CHESTS_DESERT_LEVEL_15, random.nextLong());
			break;
		case 3:
			chest.setLootTable(LootTables.CHESTS_DESERT_LEVEL_20, random.nextLong());
			break;
		case 4:
			chest.setLootTable(LootTables.CHESTS_DESERT_LEVEL_25, random.nextLong());
			break;
		case 5:
			chest.setLootTable(LootTables.CHESTS_DESERT_LEVEL_30, random.nextLong());
			break;
		}
	}
}