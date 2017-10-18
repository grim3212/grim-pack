package com.grim3212.mc.pack.world.gen.structure.ruins;

import java.util.Random;

import com.grim3212.mc.pack.world.blocks.BlockRune;
import com.grim3212.mc.pack.world.blocks.BlockRune.EnumRuneType;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.structure.StructureGenerator;
import com.grim3212.mc.pack.world.util.RuinUtil;
import com.grim3212.mc.pack.world.util.WorldLootTables;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureRuinGenerator extends StructureGenerator {

	private int radius;
	private int skip;
	private int type;

	private int skipCounter;
	private boolean skipper;
	private int torchSkip;
	private int numTorches;
	private boolean placedChest;
	private boolean placedSpawn;
	private boolean runePlaced;

	public StructureRuinGenerator(String structName, int radius, int skip, int type) {
		super(structName);

		this.radius = radius;
		this.skip = skip;
		this.type = type;

		if (type == 9) {
			type = 7;
		}
		if (type == 7) {
			radius = radius + 2;
		}
		numTorches = 0;
		torchSkip = 0;
		skipCounter = 0;
		skipper = false;
		placedChest = false;
		placedSpawn = false;
		runePlaced = false;
	}

	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		if (pos.getY() == 0) {
			return false;
		}
		if (isAreaClear(worldIn, pos)) {
			int xOff = pos.getX() - radius;
			int zOff = pos.getZ() - radius;
			int size = radius * 2 + 1;

			if (skip != 0) {
				skipCounter = random.nextInt(skip);
			}

			for (int x = 0; x < size; x++) {
				for (int z = 0; z < size; z++) {
					int radOff = 0;
					int rad = (int) ((double) radius * 0.66666666666666663D);
					if (type == 1 || type == 5) {
						radOff = random.nextInt(rad) - random.nextInt(rad * 2);
					}

					BlockPos newPos = new BlockPos(xOff + x, pos.getY(), zOff + z);
					if (RuinUtil.distanceBetween(pos.getX(), pos.getZ(), newPos.getX(), newPos.getZ()) == radius + radOff) {
						fillWater(worldIn, newPos);
						if (skip != 0) {
							if (!skipper) {
								generateColumn(worldIn, random, newPos);
							}
							if (skipCounter == skip) {
								skipCounter = 0;
								skipper = !skipper;
							} else {
								skipCounter++;
							}
						} else {
							generateColumn(worldIn, random, newPos);
						}
						continue;
					}
					if (RuinUtil.distanceBetween(pos.getX(), pos.getZ(), newPos.getX(), newPos.getZ()) < radius) {
						fillWater(worldIn, newPos);
						clearArea(worldIn, random, newPos);
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}

	private boolean isAreaClear(World world, BlockPos pos) {
		int xOff = pos.getX() - radius;
		int zOff = pos.getZ() - radius;
		int size = radius * 2 + 1;
		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				if (RuinUtil.distanceBetween(pos.getX(), pos.getZ(), xOff + x, zOff + z) > radius) {
					continue;
				}
				BlockPos newPos = world.getTopSolidOrLiquidBlock(new BlockPos(xOff + x, pos.getY(), zOff + z));
				if (newPos.getY() == 0) {
					return false;
				}
				if (Math.abs(pos.getY() - newPos.getY()) > 3) {
					return false;
				}
				if (world.getLight(newPos) < 12) {
					return false;
				}
			}

		}

		return true;
	}

	private void fillWater(World world, BlockPos pos) {
		pos = world.getTopSolidOrLiquidBlock(pos);
		if (pos.getY() == 0) {
			return;
		}
		int newY = pos.getY();
		for (boolean flag = false; !flag; newY--) {
			BlockPos newPos = new BlockPos(pos.getX(), newY, pos.getZ());
			IBlockState state = world.getBlockState(newPos);

			if (state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.FLOWING_WATER || world.getBlockState(newPos.down()).getBlock() == Blocks.ICE) {
				world.setBlockState(newPos, Blocks.SAND.getDefaultState());
				continue;
			}
			if (state.isOpaqueCube()) {
				flag = true;
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void generateColumn(World world, Random random, BlockPos pos) {
		int y = pos.getY();
		pos = world.getTopSolidOrLiquidBlock(pos);
		int topY = pos.getY();

		for (; topY < y; topY++) {
			if (!runePlaced && (double) random.nextFloat() <= WorldConfig.runeChance) {
				runePlaced = true;
				world.setBlockState(pos, WorldBlocks.rune.getDefaultState().withProperty(BlockRune.RUNETYPE, EnumRuneType.values[random.nextInt(16)]));
				continue;
			}
			int blockType = random.nextInt(30);
			if (blockType <= 13) {
				world.setBlockState(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState());
				continue;
			}
			if (blockType <= 25) {
				world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
			}
		}

		if (topY > y + 3) {
			pos = new BlockPos(pos.getX(), y, pos.getZ());
		}
		for (int off = 0; off < 5; off++) {
			if (!world.isAirBlock(pos.up(off)) && !(world.getBlockState(pos.up(off)).getBlock() instanceof BlockLeaves)) {
				continue;
			}
			int blockType = random.nextInt(30 + off * 10);
			if (blockType < 13) {
				world.setBlockState(pos.up(off), Blocks.MOSSY_COBBLESTONE.getDefaultState());
				continue;
			}
			if (blockType < 25) {
				world.setBlockState(pos.up(off), Blocks.COBBLESTONE.getDefaultState());
				continue;
			}
			if (off == 0 || type == 7 || !Blocks.TORCH.canPlaceBlockAt(world, pos.up(off)) || numTorches >= 8) {
				continue;
			}
			if (torchSkip < 8) {
				torchSkip++;
			} else {
				world.setBlockState(pos.up(off), Blocks.TORCH.getStateFromMeta(5));
				numTorches++;
				torchSkip = 0;
			}
		}

	}

	private void clearArea(World world, Random random, BlockPos pos) {
		int y = pos.getY();
		pos = world.getTopSolidOrLiquidBlock(pos);
		int topY = pos.getY();
		if (type == 2 || type == 6) {
			for (; topY < y; topY++) {
				int blockType = random.nextInt(3);
				if (blockType == 1) {
					world.setBlockState(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState());
					continue;
				}
				if (blockType == 2) {
					world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
				}
			}

		}
		if (topY > y + 3) {
			pos = new BlockPos(pos.getX(), y, pos.getZ());
		}
		for (int off = 0; off < 5; off++) {
			if (!world.isAirBlock(pos.up(off))) {
				world.setBlockToAir(pos.up(off));
			}
		}

		if (type == 3 || type == 7) {
			int blockType = random.nextInt(3);
			if (type == 7) {
				blockType = random.nextInt(1);
			}
			if (blockType == 0) {
				world.setBlockState(pos.up(5), Blocks.MOSSY_COBBLESTONE.getDefaultState());
			} else if (blockType == 1) {
				world.setBlockState(pos.up(5), Blocks.COBBLESTONE.getDefaultState());
			}
			int genType = random.nextInt(10);
			if (type == 7) {
				genType = random.nextInt(20);
			}
			if (genType == 1) {
				generateColumn(world, random, pos);
			} else if (genType > 5 && random.nextInt(50) == 1 && type == 7) {
				genMobSpawner(world, random, pos);
				return;
			}
		}
		if (type == 4 || type == 8) {
			if (random.nextInt(5) == 1) {
				generateColumn(world, random, pos);
			}
		}
		if (random.nextInt(250) == 1 && type < 4) {
			genChest(world, random, pos);
		} else if (type == 7 && placedSpawn && random.nextInt(2) == 1) {
			genChest(world, random, pos);
		}
	}

	private void genChest(World world, Random random, BlockPos pos) {
		if (!placedChest) {
			world.setBlockState(pos, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.HORIZONTALS[random.nextInt(EnumFacing.HORIZONTALS.length)]));
			TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(pos);
			tileentitychest.setLootTable(WorldLootTables.CHESTS_RUIN, random.nextLong());

			placedChest = true;
		}
	}

	private void genMobSpawner(World world, Random random, BlockPos pos) {
		if (!placedSpawn) {
			world.setBlockState(pos, Blocks.MOB_SPAWNER.getDefaultState());
			TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) world.getTileEntity(pos);
			tileentitymobspawner.getSpawnerBaseLogic().setEntityId(RuinUtil.getRandomRuneMob(random));
			placedSpawn = true;
		}
	}
}
