package com.grim3212.mc.pack.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCorruption extends WorldGenerator {

	private Block blockID;
	private int numberOfBlocks;

	public WorldGenCorruption(int var1, Block var2) {
		super();
		this.blockID = var2;
		this.numberOfBlocks = var1;
	}

	@Override
	public boolean generate(World var1, Random var2, BlockPos pos) {
		float var6 = var2.nextFloat() * 3.141593F;
		double var7 = (double) ((float) (pos.getX() + 8) + MathHelper.sin(var6) * (float) this.numberOfBlocks / 8.0F);
		double var9 = (double) ((float) (pos.getX() + 8) - MathHelper.sin(var6) * (float) this.numberOfBlocks / 8.0F);
		double var11 = (double) ((float) (pos.getZ() + 8) + MathHelper.cos(var6) * (float) this.numberOfBlocks / 8.0F);
		double var13 = (double) ((float) (pos.getZ() + 8) - MathHelper.cos(var6) * (float) this.numberOfBlocks / 8.0F);
		double var15 = (double) (pos.getY() + var2.nextInt(3) + 2);
		double var17 = (double) (pos.getY() + var2.nextInt(3) + 2);

		for (int var19 = 0; var19 <= this.numberOfBlocks; ++var19) {
			double var20 = var7 + (var9 - var7) * (double) var19 / (double) this.numberOfBlocks;
			double var22 = var15 + (var17 - var15) * (double) var19 / (double) this.numberOfBlocks;
			double var24 = var11 + (var13 - var11) * (double) var19 / (double) this.numberOfBlocks;
			double var26 = var2.nextDouble() * (double) this.numberOfBlocks / 16.0D;
			double var28 = (double) (MathHelper.sin((float) var19 * 3.141593F / (float) this.numberOfBlocks) + 1.0F) * var26 + 1.0D;
			double var30 = (double) (MathHelper.sin((float) var19 * 3.141593F / (float) this.numberOfBlocks) + 1.0F) * var26 + 1.0D;

			for (int var32 = (int) (var20 - var28 / 2.0D); var32 <= (int) (var20 + var28 / 2.0D); ++var32) {
				for (int var33 = (int) (var22 - var30 / 2.0D); var33 <= (int) (var22 + var30 / 2.0D); ++var33) {
					for (int var34 = (int) (var24 - var28 / 2.0D); var34 <= (int) (var24 + var28 / 2.0D); ++var34) {
						double var35 = ((double) var32 + 0.5D - var20) / (var28 / 2.0D);
						double var37 = ((double) var33 + 0.5D - var22) / (var30 / 2.0D);
						double var39 = ((double) var34 + 0.5D - var24) / (var28 / 2.0D);

						BlockPos pos2 = new BlockPos(var32, var33, var34);
						if (var35 * var35 + var37 * var37 + var39 * var39 < 1.0D && var1.getBlockState(pos2.up()).getBlock() == Blocks.netherrack) {
							var1.setBlockState(pos2, this.blockID.getDefaultState());
							var1.setBlockState(pos2.add(-1, 0, 0), this.blockID.getDefaultState());
							var1.setBlockState(pos2.add(0, 0, -1), this.blockID.getDefaultState());
							var1.setBlockState(pos2.add(-1, 0, -1), this.blockID.getDefaultState());
						}
					}
				}
			}
		}

		return true;
	}
}
