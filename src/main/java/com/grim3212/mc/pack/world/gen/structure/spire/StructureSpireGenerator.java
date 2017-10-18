package com.grim3212.mc.pack.world.gen.structure.spire;

import java.util.Random;

import com.grim3212.mc.pack.world.blocks.BlockRune;
import com.grim3212.mc.pack.world.blocks.BlockRune.EnumRuneType;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.structure.StructureGenerator;
import com.grim3212.mc.pack.world.util.RuinUtil;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureSpireGenerator extends StructureGenerator {

	private final int radius;
	private final int height;
	private final int type;
	private final boolean deathSpire;
	private boolean runePlaced;

	public StructureSpireGenerator(String structName, int radius, int height, int type, boolean deathSpire) {
		super(structName);

		this.radius = radius;
		this.height = height;
		this.type = type;
		this.deathSpire = deathSpire;
	}

	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		if (isAreaClear(worldIn, pos)) {
			int xOff = pos.getX() - radius;
			int zOff = pos.getZ() - radius;
			int size = radius * 2 + 1;
			for (int x = 0; x < size; x++) {
				for (int z = 0; z < size; z++) {
					int radMod = 0;
					int radCheck = (int) ((double) radius * 0.66666666666666663D);
					if (type == 1 || type == 5) {
						if (radCheck == 0) {
							radCheck = 1;
						}
						radMod = random.nextInt(radCheck) - random.nextInt(radCheck * 2);
					}
					BlockPos newPos = new BlockPos(xOff + x, pos.getY(), zOff + z);

					int distance = RuinUtil.distanceBetween(pos.getX(), pos.getZ(), newPos.getX(), newPos.getZ());
					if (distance <= radius + radMod) {
						fillWater(worldIn, newPos);
						generateColumn(worldIn, random, newPos, distance);
						continue;
					}
					if (distance > radius && random.nextInt(4) == 1) {
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
				if (Math.abs(pos.getY() - newPos.getY()) > 7) {
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
		if (pos.getY() <= 0) {
			return;
		}
		int newY = pos.getY();
		for (boolean flag = false; !flag; newY--) {
			BlockPos newPos = new BlockPos(pos.getX(), newY, pos.getZ());
			IBlockState state = world.getBlockState(newPos);

			if (state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.FLOWING_WATER || world.getBlockState(newPos.down()).getBlock() == Blocks.ICE) {
				if (deathSpire) {
					world.setBlockState(newPos, Blocks.NETHERRACK.getDefaultState());
				} else {
					world.setBlockState(newPos, Blocks.STONE.getDefaultState());
				}
				continue;
			}
			if (state.isOpaqueCube()) {
				flag = true;
			}
		}
	}

	private void generateColumn(World world, Random random, BlockPos pos, int heightMod) {
		int startY = pos.getY();

		for (pos = world.getTopSolidOrLiquidBlock(pos); pos.getY() < startY; pos = pos.up()) {
			if (!runePlaced && (double) random.nextFloat() <= WorldConfig.runeChance) {
				runePlaced = true;
				world.setBlockState(pos, WorldBlocks.rune.getDefaultState().withProperty(BlockRune.RUNETYPE, EnumRuneType.values[random.nextInt(16)]));
				continue;
			}

			if (deathSpire) {
				float blockType = random.nextFloat();
				if (blockType < 0.01F) {
					world.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState());
				} else {
					world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
				}
				continue;
			}

			int blockType = random.nextInt(2);
			if (blockType == 1) {
				world.setBlockState(pos, Blocks.STONE.getDefaultState());
				continue;
			}
			if (blockType == 0) {
				world.setBlockState(pos, Blocks.DIRT.getDefaultState());
			}
		}

		if (pos.getY() > startY + 7) {
			pos = new BlockPos(pos.getX(), startY, pos.getZ());
		}

		int startHeight = height;
		int mod = (int) ((float) heightMod * (1.0F + random.nextFloat() * 2.0F));
		if (mod == 0) {
			mod = 1;
			startHeight *= 2;
		}
		startHeight /= mod;
		for (int heightOff = 0; heightOff < startHeight; heightOff++) {
			if (!world.isAirBlock(pos.up(heightOff)) && !(world.getBlockState(pos.up(heightOff)).getBlock() instanceof BlockLeaves) && !(world.getBlockState(pos.up(heightOff)).getBlock() instanceof BlockLog)) {
				continue;
			}
			if (deathSpire) {
				int check = random.nextInt(2 + heightOff);
				if (check > startHeight / (heightOff + 1)) {
					break;
				}
				float blockType = random.nextFloat();
				if (blockType < 0.01F) {
					world.setBlockState(pos.up(heightOff), Blocks.FLOWING_LAVA.getDefaultState());
				} else {
					world.setBlockState(pos.up(heightOff), Blocks.NETHERRACK.getDefaultState());
				}
				continue;
			}
			int check = random.nextInt(2 + heightOff);
			if (check > startHeight / (heightOff + 1)) {
				break;
			}
			world.setBlockState(pos.up(heightOff), Blocks.STONE.getDefaultState());
		}
	}

	private void clearArea(World world, Random random, BlockPos pos) {
		int startY = pos.getY();

		for (pos = world.getTopSolidOrLiquidBlock(pos); pos.getY() < startY; pos = pos.up()) {
			if (deathSpire) {
				float blockType = random.nextFloat();
				if (blockType < 0.01F) {
					world.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState());
				} else {
					world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
				}
				continue;
			}

			int blockType = random.nextInt(2);
			if (blockType == 1) {
				world.setBlockState(pos, Blocks.STONE.getDefaultState());
				continue;
			}
			if (blockType == 0) {
				world.setBlockState(pos, Blocks.DIRT.getDefaultState());
			}
		}
		if (pos.getY() > startY + 7) {
			pos = new BlockPos(pos.getX(), startY, pos.getZ());
		}

		for (int off = 0; off < height; off++) {
			if (!world.isAirBlock(pos.up(off))) {
				world.setBlockToAir(pos.up(off));
			}
		}
	}
}