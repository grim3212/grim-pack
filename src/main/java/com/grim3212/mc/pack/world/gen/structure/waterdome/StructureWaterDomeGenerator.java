package com.grim3212.mc.pack.world.gen.structure.waterdome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.world.blocks.BlockRune;
import com.grim3212.mc.pack.world.blocks.BlockRune.EnumRuneType;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.gen.structure.StructureGenerator;
import com.grim3212.mc.pack.world.gen.structure.StructureStorage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class StructureWaterDomeGenerator extends StructureGenerator {

	private final int radius;
	private final int tries;
	private boolean runePlaced;
	private int type;
	private List<Vec3d> centrePoints;
	private List<Integer> radii;
	private final StructureStorage storage;

	public StructureWaterDomeGenerator(String structName, int radius, StructureStorage storage) {
		super(structName);

		this.radius = radius;
		this.tries = WorldConfig.ruinTries;
		this.storage = storage;

		this.runePlaced = false;

		centrePoints = new ArrayList<>();
		radii = new ArrayList<>();
	}

	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		int l = 0;
		int i1;
		for (i1 = 0; l == 0 && i1 == 0; i1 = -1 + random.nextInt(3)) {
			l = -1 + random.nextInt(3);
		}

		int rad = 3 + random.nextInt(radius);
		int xOff = 0;
		int zOff = 0;
		for (int idx = 0; idx < 4 + random.nextInt(1 + tries / 2); idx++) {
			int x = xOff + l * (1 + (1 + rad / 2) + random.nextInt(1 + rad / 2));
			int z = zOff + i1 * (1 + (1 + rad / 2) + random.nextInt(1 + rad / 2));
			rad = 3 + random.nextInt(radius);
			xOff = x;
			zOff = z;
			centrePoints.add(new Vec3d(x, 0.0D, z));
			radii.add(rad);
		}

		StructureBoundingBox finalBB = null;
		for (int pIdx = 0; pIdx < centrePoints.size(); pIdx++) {
			Vec3d point = centrePoints.get(pIdx);
			int radi = radii.get(pIdx);

			// Set type
			type = random.nextInt(20);

			BlockPos depth = worldIn.getTopSolidOrLiquidBlock(pos.add(point.x, 0, point.z));

			// Get start position
			for (Block b = worldIn.getBlockState(depth.down()).getBlock(); (b == Blocks.GLASS || b == Blocks.AIR || b == Blocks.IRON_BLOCK || b == Blocks.COBBLESTONE || b == Blocks.GLOWSTONE) && depth.getY() > 0; depth = depth.down(), b = worldIn.getBlockState(depth).getBlock())
				;

			if (depth.getY() == -1 || depth.getY() > worldIn.getSeaLevel() - 11) {
				return false;
			}

			// Get correct position
			pos = new BlockPos(pos.getX(), depth.getY(), pos.getZ());

			for (int x = -radi; x <= radi; x++) {
				for (int z = -radi; z <= radi; z++) {
					for (int y = -radi; y <= radi; y++) {
						Vec3d newPoint = new Vec3d(x + point.x, y, z + point.z);

						IBlockState state = blockToPlace(random, pos, newPoint);
						if (state == null) {
							continue;
						}

						Block b = worldIn.getBlockState(pos.add(newPoint.x, newPoint.y, newPoint.z)).getBlock();
						if (b == Blocks.FLOWING_WATER || b == Blocks.WATER) {
							worldIn.setBlockState(pos.add(newPoint.x, newPoint.y, newPoint.z), state);
						}
					}
				}
			}

			BlockPos start = new BlockPos(pos.getX() + point.x - radi - 1, pos.getY() - radi - 1, pos.getZ() + point.z - radi - 1);

			if (finalBB == null) {
				finalBB = new StructureBoundingBox(start.getX(), start.getY(), start.getZ(), start.getX() + (radi * 2) + 1, start.getY() + (radi * 2) + 1, start.getZ() + (radi * 2) + 1);
			} else {
				finalBB.expandTo(new StructureBoundingBox(start.getX(), start.getY(), start.getZ(), start.getX() + (radi * 2) + 1, start.getY() + (radi * 2) + 1, start.getZ() + (radi * 2) + 1));
			}
		}

		this.storage.addBBSave(this.structName, finalBB);

		return true;
	}

	@Nullable
	private IBlockState blockToPlace(Random random, BlockPos pos, Vec3d point1) {
		int blocks = 0;
		int places = 0;
		int equalPoints = 0;
		for (int pIdx = 0; pIdx < centrePoints.size(); pIdx++) {
			Vec3d point = centrePoints.get(pIdx);
			int radi = radii.get(pIdx);

			int distance = (int) Math.round(point.distanceTo(point1));
			if (distance < radi) {
				places++;
				if (point1.x == point.x && point1.y == point.y && point1.z == point.z && !runePlaced) {
					runePlaced = true;
					return WorldBlocks.rune.getDefaultState().withProperty(BlockRune.RUNETYPE, EnumRuneType.values[random.nextInt(16)]);
				}
				continue;
			}
			if (distance != radi) {
				continue;
			}
			blocks++;
			if (point1.x == point.x || point1.y == point.y || point1.z == point.z) {
				equalPoints++;
			}
		}

		if (places > 0) {
			if (type % 4 != 0)
				;
			return Blocks.AIR.getDefaultState();
		}
		if (blocks > 0) {
			if (equalPoints > 0) {
				if (type == 5) {
					return Blocks.GLOWSTONE.getDefaultState();
				}
				if (type == 10) {
					return Blocks.IRON_BLOCK.getDefaultState();
				}
				if (type == 15) {
					return Blocks.OBSIDIAN.getDefaultState();
				} else {
					return Blocks.COBBLESTONE.getDefaultState();
				}
			} else {
				return Blocks.GLASS.getDefaultState();
			}
		} else {
			return null;
		}
	}

}
