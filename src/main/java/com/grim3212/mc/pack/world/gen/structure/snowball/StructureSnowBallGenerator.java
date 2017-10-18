package com.grim3212.mc.pack.world.gen.structure.snowball;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.world.gen.structure.StructureGenerator;
import com.grim3212.mc.pack.world.gen.structure.StructureStorage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class StructureSnowBallGenerator extends StructureGenerator {

	private final int radius;
	private List<Vec3d> centrePoints;
	private List<Integer> radii;
	private final StructureStorage storage;

	public StructureSnowBallGenerator(String structName, int radius, StructureStorage storage) {
		super(structName);

		this.radius = radius;
		centrePoints = Lists.newArrayList(new Vec3d(0.0D, 0.0D, 0.0D));
		radii = Lists.newArrayList(radius);
		this.storage = storage;
	}

	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		int rad = radius;
		int newY = 0;

		for (int i = 0; i < 2 + random.nextInt(6); i++) {
			int off = newY + rad;
			rad -= 3;
			newY = off;
			if (rad < 3 || pos.getY() + newY + rad >= 128) {
				break;
			}

			centrePoints.add(new Vec3d(0.0D, off, 0.0D));
			radii.add(rad);
		}

		Map<BlockPos, IBlockState> stateCache = new HashMap<>();

		for (int idx = 0; idx < centrePoints.size(); idx++) {
			Vec3d point = centrePoints.get(idx);
			int radi = radii.get(idx);

			for (int x = -radi; x <= radi; x++) {
				for (int z = -radi; z <= radi; z++) {
					for (int y = -radi; y <= radi; y++) {
						Vec3d newPoint = new Vec3d(x, y + point.y, z);

						if (pos.getY() + (int) newPoint.y >= 128) {
							break;
						}
						Block block = blockToPlace(random, pos, newPoint, point);
						if (block != null) {
							stateCache.put(new BlockPos(pos.getX() + x, pos.getY() + newPoint.y, pos.getZ() + z), block.getDefaultState());
						}
					}
				}
			}
		}

		Iterator<Entry<BlockPos, IBlockState>> iterator = stateCache.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<BlockPos, IBlockState> entry = iterator.next();

			worldIn.setBlockState(entry.getKey(), entry.getValue(), 2);
		}

		BlockPos start = new BlockPos(pos.getX() - radius, pos.getY() - radius - 1, pos.getZ() - radius);
		this.storage.addBBSave(this.structName, new StructureBoundingBox(start.getX(), start.getY(), start.getZ(), start.getX() + (radius * 2) + 1, start.getY() + (radius * centrePoints.size()), start.getZ() + +(radius * 2) + 1));

		return true;
	}

	@Nullable
	private Block blockToPlace(Random random, BlockPos pos, Vec3d point1, Vec3d point2) {
		int points = 0;
		int places = 0;
		for (int point = 0; point < centrePoints.size(); point++) {
			Vec3d centerPoint = centrePoints.get(point);
			int radi = radii.get(point);

			int distance = (int) Math.round(centerPoint.distanceTo(point1));
			if (distance < radi) {
				places++;
				continue;
			}
			if (distance != radi) {
				continue;
			}
			points++;
		}

		if (places > 0) {
			if (point1.x == 0.0D && point1.z == 0.0D) {
				return Blocks.ICE;
			}
			if (point2.y == 0.0D) {
				if ((Math.abs(point1.x) == 1.0D || Math.abs(point1.z) == 1.0D) && Math.abs(point1.x) != Math.abs(point1.z) && Math.abs(point1.x) <= 1.0D && Math.abs(point1.z) <= 1.0D) {
					return Blocks.ICE;
				}
			} else if ((Math.abs(point1.x) == 0.0D || Math.abs(point1.z) == 0.0D) && point1.y - point2.y == 0.0D) {
				return Blocks.ICE;
			}
			if (point2.y == 0.0D && (double) pos.getY() + point1.y < (double) pos.getY()) {
				return Blocks.ICE;
			} else {
				return Blocks.AIR;
			}
		}
		if (points > 0) {
			return Blocks.SNOW;
		} else {
			return null;
		}
	}
}