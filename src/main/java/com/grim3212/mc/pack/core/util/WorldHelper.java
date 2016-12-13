package com.grim3212.mc.pack.core.util;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WorldHelper {

	/**
	 * This is a modified version of isAABBInMaterial from World to account for
	 * other Materials besides fluids.
	 * 
	 * @param worldObj
	 *            Reference to the world
	 * @param boundingBox
	 *            Bounding box that you are searching in
	 * @param material
	 *            Material you are checking for
	 * @return True if the Material is in the BoundingBox false otherwise
	 */
	public static boolean isAABBInMaterial(World worldObj, AxisAlignedBB boundingBox, Material material) {
		int i = MathHelper.floor(boundingBox.minX);
		int j = MathHelper.floor(boundingBox.maxX + 1.0D);
		int k = MathHelper.floor(boundingBox.minY);
		int l = MathHelper.floor(boundingBox.maxY + 1.0D);
		int i1 = MathHelper.floor(boundingBox.minZ);
		int j1 = MathHelper.floor(boundingBox.maxZ + 1.0D);

		for (int k1 = i; k1 < j; ++k1) {
			for (int l1 = k; l1 < l; ++l1) {
				for (int i2 = i1; i2 < j1; ++i2) {
					BlockPos blockpos = new BlockPos(k1, l1, i2);
					IBlockState iblockstate = worldObj.getBlockState(blockpos);

					if (iblockstate.getMaterial() == material) {
						int j2 = iblockstate.getBlock().getMetaFromState(iblockstate);
						double d0 = (double) (l1 + 1);

						if (j2 < 8) {
							d0 = (double) (l1 + 1) - (double) j2 / 8.0D;
						}

						if (d0 >= boundingBox.minY) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

}
