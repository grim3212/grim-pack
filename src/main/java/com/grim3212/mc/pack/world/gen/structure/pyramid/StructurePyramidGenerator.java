package com.grim3212.mc.pack.world.gen.structure.pyramid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.gen.structure.StructureGenerator;
import com.grim3212.mc.pack.world.util.RuinUtil;
import com.grim3212.mc.pack.world.util.WorldLootTables;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructurePyramidGenerator extends StructureGenerator {

	private final int maxHeight;
	private final int type;

	private int placedSpawners;
	private int placedChests;
	private int spawnerSkipCount;
	private List<BlockPos> spawnerList;
	private Map<Integer, IBlockState> cachedStates;

	public StructurePyramidGenerator(String structName, int maxHeight, int type) {
		super(structName);

		this.maxHeight = maxHeight;
		this.type = type;
		placedSpawners = 0;
		placedChests = 0;
		spawnerSkipCount = 0;
		spawnerList = new ArrayList<>();
		cachedStates = new HashMap<>();
	}

	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		if (pos.getY() == -1) {
			return false;
		}

		pos = pos.down(maxHeight / 2);

		int halfWidth = halfWidth(maxHeight);
		int colHeight = 0;

		BlockPos newPos;
		Map<BlockPos, IBlockState> states = new HashMap<>();

		for (int x = -halfWidth; x <= halfWidth; x++) {
			for (int z = -halfWidth; z <= halfWidth; z++) {
				colHeight = getColumnHeight(x, z);
				// GrimLog.info("ColHieght", "" + colHeight);
				for (int y = -1; y <= colHeight; y++) {
					newPos = new BlockPos(x, y, z);

					states.put(pos.add(newPos), getState(random, newPos, colHeight));
				}
			}
		}

		// Outside the triple for is actually saving a lot of time
		// 38 size was generating in about ~16s
		// Now it is generating in about ~2s
		Iterator<Entry<BlockPos, IBlockState>> iterator = states.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<BlockPos, IBlockState> entry = iterator.next();

			worldIn.setBlockState(entry.getKey(), entry.getValue(), 2);

			if (entry.getValue().getBlock() == Blocks.CHEST) {
				TileEntityChest tileentitychest = (TileEntityChest) worldIn.getTileEntity(entry.getKey());
				tileentitychest.setLootTable(WorldLootTables.CHESTS_PYRAMID, random.nextLong());
			} else if (entry.getValue().getBlock() == Blocks.MOB_SPAWNER) {
				TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) worldIn.getTileEntity(entry.getKey());
				tileentitymobspawner.getSpawnerBaseLogic().setEntityId(RuinUtil.getRandomRuneMob(random));
			}
		}

		return true;
	}

	@SuppressWarnings("deprecation")
	private IBlockState getState(Random rand, BlockPos pos, int colHeight) {
		Block block = blockToPlace(rand, pos, colHeight);
		int meta = blockMeta(block, rand);

		int key = block.hashCode() ^ meta;

		if (!cachedStates.containsKey(block.hashCode() ^ meta)) {
			cachedStates.put(key, block.getStateFromMeta(meta));
		}

		return cachedStates.get(key);
	}

	private Block blockToPlace(Random random, BlockPos pos, int colHeight) {
		if (pos.getX() == 0 && pos.getY() == 0 && pos.getZ() == 0) {
			return WorldBlocks.rune;
		}
		if (placeStone(random, pos, colHeight)) {
			if (type == 1) {
				return Blocks.SANDSTONE;
			}
			if (random.nextInt(10) == 2) {
				return Blocks.SAND;
			} else {
				return Blocks.SANDSTONE;
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

	private int blockMeta(Block block, Random random) {
		if (block == WorldBlocks.rune) {
			return random.nextInt(16);
		}
		return 0;
	}

	private boolean placeStone(Random random, BlockPos pos, int colHeight) {
		int y = pos.getY();

		if (y == -1) {
			return true;
		}

		if ((y % 6) == 0 && y > 4) {
			if (type == 1) {
				return random.nextInt(100) < 95;
			} else {
				return random.nextInt(14) < 11;
			}
		}

		if (colHeight - y < 3) {
			if (type == 1) {
				return random.nextInt(100) < 85;
			} else {
				return random.nextInt(5) < 4;
			}
		}

		if (Math.max(Math.abs(pos.getX()), Math.abs(pos.getZ())) % 8 == 0) {
			if (type == 1) {
				return random.nextInt(100) < 92;
			} else {
				return random.nextInt(3) < 2;
			}
		}

		return false;
	}

	private boolean placeSpawner(Random random, BlockPos pos) {
		if ((pos.getY() == 0 || (pos.getY() - 1) % 6 == 0 && pos.getY() > 4) && placedSpawners < maxHeight / 4) {
			if (spawnerSkipCount == 0) {
				if (random.nextInt(98) < 2) {
					boolean flag = false;
					for (int idx = 0; idx < spawnerList.size(); idx++) {
						double d = spawnerList.get(idx).distanceSq(pos.up(pos.getY()));
						if (d < 6D) {
							flag = true;
						}
					}

					if (!flag) {
						placedSpawners++;

						spawnerList.add(pos.up(pos.getY()));
						return true;
					}
				}
				spawnerSkipCount++;
			} else {
				spawnerSkipCount++;
				if (spawnerSkipCount >= 64) {
					spawnerSkipCount = 0;
				}
			}
		}
		return false;
	}

	private boolean placeChest(Random random, BlockPos pos) {
		if ((pos.getY() == 0 || (pos.getY() - 1) % 6 == 0 && pos.getY() > 4) && placedChests < placedSpawners * 2 && random.nextInt(28) < 3) {
			placedChests++;
			return true;
		} else {
			return false;
		}
	}

	private int getColumnHeight(int x, int z) {
		return maxHeight - (Math.max(Math.abs(x), Math.abs(z)) / 2) * 2;
	}

	public static int halfWidth(int height) {
		return 2 * (height / 2) - 1;
	}
}
