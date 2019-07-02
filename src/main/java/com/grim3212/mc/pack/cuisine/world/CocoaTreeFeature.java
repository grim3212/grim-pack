package com.grim3212.mc.pack.cuisine.world;

import java.util.Random;
import java.util.Set;

import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class CocoaTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
	private static final BlockState LOG = Blocks.JUNGLE_LOG.getDefaultState();
	private static final BlockState LEAF = Blocks.JUNGLE_LEAVES.getDefaultState();
	private static final BlockState COCOA = CuisineBlocks.cocoa_block.getDefaultState();
	private final boolean useExtraRandomHeight;

	public CocoaTreeFeature(boolean notify, boolean useExtraRandomHeightIn) {
		super(NoFeatureConfig::deserialize, notify);
		this.useExtraRandomHeight = useExtraRandomHeightIn;
	}

	@Override
	protected boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox boundingBox) {
		int i = rand.nextInt(3) + 5;
		if (this.useExtraRandomHeight) {
			i += rand.nextInt(7);
		}

		boolean flag = true;
		if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxHeight()) {
			for (int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
				int k = 1;
				if (j == position.getY()) {
					k = 0;
				}

				if (j >= position.getY() + 1 + i - 2) {
					k = 2;
				}

				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < worldIn.getMaxHeight()) {
							if (!func_214587_a(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}

			if (!flag) {
				return false;
			} else if ((isSoil(worldIn, position.down(), getSapling())) && position.getY() < worldIn.getMaxHeight() - i - 1) {
				this.setDirtAt(worldIn, position.down(), position);

				for (int i2 = position.getY() - 3 + i; i2 <= position.getY() + i; ++i2) {
					int k2 = i2 - (position.getY() + i);
					int l2 = 1 - k2 / 2;

					for (int i3 = position.getX() - l2; i3 <= position.getX() + l2; ++i3) {
						int j1 = i3 - position.getX();

						for (int k1 = position.getZ() - l2; k1 <= position.getZ() + l2; ++k1) {
							int l1 = k1 - position.getZ();

							if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || rand.nextInt(2) != 0 && k2 != 0) {
								BlockPos blockpos = new BlockPos(i3, i2, k1);
								if (isAirOrLeaves(worldIn, blockpos)) {
									this.setLogState(changedBlocks, worldIn, blockpos, LEAF, boundingBox);
								}

								if (blockpos.getY() == position.getY() + i - 3 && blockpos.getX() != position.getX() && blockpos.getZ() != position.getZ()) {
									if (rand.nextInt(3) == 0) {
										if (isAir(worldIn, blockpos.down())) {
											this.setBlockState(worldIn, blockpos.down(), COCOA);
										}
									}
								}
							}
						}
					}
				}

				for (int i2 = 0; i2 < i; ++i2) {
					if (isAirOrLeaves(worldIn, position.up(i2))) {
						this.setLogState(changedBlocks, worldIn, position.up(i2), LOG, boundingBox);
					}
				}

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}