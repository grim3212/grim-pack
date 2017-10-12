package com.grim3212.mc.pack.world.gen.structure.fountain;

import java.util.Random;

import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.util.RuinUtil;
import com.grim3212.mc.pack.world.util.WorldLootTables;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureFountainGenerator {

	private int maxHeight;
	private int type;
	private int placedSpawners;
	private int placedChests;
	private int spawnerSkipCount;
	private boolean runePlaced;

	public StructureFountainGenerator(int height, int type) {
		this.maxHeight = height;
		this.type = type;
		this.placedSpawners = 0;
		this.placedChests = 0;
		this.spawnerSkipCount = 0;
		this.runePlaced = false;
	}

	@SuppressWarnings("deprecation")
	public boolean generate(World world, Random random, BlockPos pos) {
		if (pos.getY() == -1) {
			return false;
		}

		if (world.getBlockState(pos).getBlock() != Blocks.WATER) {
			return false;
		}
		for (int x = -halfWidth(maxHeight); x <= halfWidth(maxHeight); x++) {
			for (int z = -halfWidth(maxHeight); z <= halfWidth(maxHeight); z++) {
				int colHeight = getColumnHeight(x, z);
				for (int y = -1; y <= colHeight; y++) {
					BlockPos newPos = new BlockPos(x, y, z);

					Block block = blockToPlace(random, newPos, colHeight);
					int meta = getBlockMeta(block, random);
					world.setBlockState(pos.add(newPos), block.getStateFromMeta(meta));

					if (block == Blocks.CHEST) {
						TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(pos.add(newPos));
						tileentitychest.setLootTable(WorldLootTables.CHESTS_FOUNTAIN, random.nextLong());
					}
					if (block == Blocks.MOB_SPAWNER) {
						TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) world.getTileEntity(pos.add(newPos));
						tileentitymobspawner.getSpawnerBaseLogic().setEntityId(RuinUtil.getRandomRuneMob(random));
					}
				}
			}
		}

		return true;
	}

	private Block blockToPlace(Random random, BlockPos pos, int colHeight) {
		if (pos.getX() != 0 && pos.getY() == -1 && pos.getZ() != 0 && (double) random.nextFloat() <= WorldConfig.runeChance && !runePlaced) {
			runePlaced = true;
			return WorldBlocks.rune;
		}
		if (placeWater(pos, colHeight)) {
			return Blocks.FLOWING_WATER;
		}
		if (placeStone(random, pos, colHeight)) {
			if (type == 0) {
				if (random.nextBoolean()) {
					return Blocks.COBBLESTONE;
				} else {
					return Blocks.MOSSY_COBBLESTONE;
				}
			} else {
				return Blocks.STONEBRICK;
			}
		}
		if (placeSpawner(random, pos)) {
			return Blocks.MOB_SPAWNER;
		}
		if (placeChest(random, pos)) {
			return Blocks.CHEST;
		} else {
			return Blocks.AIR;
		}
	}

	private int getBlockMeta(Block b, Random random) {
		if (b == WorldBlocks.rune) {
			return random.nextInt(16);
		}
		if (b == Blocks.STONEBRICK) {
			return random.nextInt(3);
		}

		return 0;
	}

	private boolean placeWater(BlockPos pos, int colHeight) {
		if (pos.getX() == 0 && pos.getZ() == 0) {
			return colHeight == pos.getY();
		}
		if (pos.getY() == -1) {
			return false;
		} else {
			boolean flag = Math.max(Math.abs(pos.getX()), Math.abs(pos.getZ())) % 2 == 0;
			boolean flag1 = colHeight - pos.getY() < 2;
			return flag && flag1;
		}
	}

	private boolean placeStone(Random random, BlockPos pos, int colHeight) {
		if (pos.getX() == 0 && pos.getZ() == 0) {
			return false;
		}
		if (pos.getY() == -1) {
			return true;
		}
		boolean flag = Math.max(Math.abs(pos.getX()), Math.abs(pos.getZ())) % 2 == 0;
		if (flag && colHeight - pos.getY() == 2) {
			return true;
		}
		if (!flag && colHeight - pos.getY() < 6) {
			if (Math.abs(pos.getX()) != Math.abs(pos.getY()) && colHeight - pos.getY() == 1) {
				return random.nextInt(5) < 3;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	private boolean placeSpawner(Random random, BlockPos pos) {
		if (pos.getY() == 0 && placedSpawners < 2) {
			if (spawnerSkipCount == 0) {
				if (random.nextInt(64) < 2) {
					placedSpawners++;
					spawnerSkipCount++;
					return true;
				}
			} else {
				spawnerSkipCount++;
				if (spawnerSkipCount >= 48) {
					spawnerSkipCount = 0;
				}
			}
		}
		return false;
	}

	private boolean placeChest(Random random, BlockPos pos) {
		if (pos.getY() == 0 && placedChests < placedSpawners * 2 && random.nextInt(28) < 3) {
			placedChests++;
			return true;
		} else {
			return false;
		}
	}

	private int getColumnHeight(int x, int z) {
		return maxHeight - (Math.max(Math.abs(x), Math.abs(z)) / 2) * 4;
	}

	public static int halfWidth(int height) {
		return 2 * (height / 4) - 1;
	}
}