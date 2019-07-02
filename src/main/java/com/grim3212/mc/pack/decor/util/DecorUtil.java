package com.grim3212.mc.pack.decor.util;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerFacing;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerHedge;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerSlopedRotate;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerSlopedRotate.EnumHalf;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class DecorUtil {

	public static String[] getDyeNames() {
		DyeColor[] states = DyeColor.values();
		String[] names = new String[states.length];

		for (int i = 0; i < states.length; i++) {
			names[i] = states[i].name();
		}

		return names;
	}

	public static ItemStack createFurnitureWithState(Block block, BlockState toPlace) {
		ItemStack furniture = new ItemStack(block);
		NBTHelper.setTagCompound(furniture, "stored_state", NBTUtil.writeBlockState(toPlace));
		return furniture;
	}

	/**
	 * Adds the AxisAlginedBB that it generates using getCollision to the blocks
	 * colliding boxes list
	 * 
	 * @param pos            The pos we are checking for collision at
	 * @param entityBox      The entitybox to make sure it is intersecting
	 * @param collidingBoxes The blocks colliding blocks list
	 * @param state          The blockstate we are going to be evaluating
	 * @param numPieces      The number of pieces that this Slope has
	 */
	public static VoxelShape addAxisAlignedBoxes(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context, int numPieces) {
		VoxelShape endShape = VoxelShapes.empty();

		for (int piece = 0; piece < DecorConfig.smoothness.get(); piece++) {
			for (int smoothness = 0; smoothness < DecorConfig.smoothness.get(); smoothness++) {
				VoxelShape collision = getCollision(state, piece, smoothness, numPieces);

				if (collision != VoxelShapes.empty()) {
					endShape = VoxelShapes.or(endShape, collision);
				}
			}
		}

		return endShape;
	}

	/**
	 * Returns a new AxisAlignedBB for the specified piece at the smoothness level
	 * 
	 * Based off of CarpentryBlocks
	 * https://github.com/Mineshopper/carpentersblocks/blob/master/src/main/java
	 * /com/carpentersblocks/util/slope/SlopeUtil.java#L56
	 * 
	 * @param state      The BlockState that we are grabbing the collision for
	 * @param piece      The current piece that we are on
	 * @param smoothness The current smoothness we are evaluating
	 * @param numPieces  The number of pieces that this Slope has
	 * 
	 * @return A new AxisAlignedBB at this smoothness level
	 */
	public static VoxelShape getCollision(BlockState state, int piece, int smoothness, int numPieces) {
		piece++;

		float zeroPieceOffset = (float) (piece - 1) / numPieces;
		float onePieceOffset = (float) piece / numPieces;

		float zeroOffset = (float) smoothness / DecorConfig.smoothness.get();
		float oneOffset = (float) (smoothness + 1) / DecorConfig.smoothness.get();

		if (state.getBlock() instanceof BlockColorizerSlopedRotate) {
			if (state.getBlock() == DecorBlocks.slanted_corner) {
				if (state.get(BlockColorizerSlopedRotate.HALF) == EnumHalf.BOTTOM) {
					switch (state.get(BlockColorizerSlopedRotate.HORIZONTAL_FACING)) {
					case EAST:
						return VoxelShapes.create(new AxisAlignedBB(zeroPieceOffset + zeroOffset * (1.0F - zeroPieceOffset), 0.0F, 0.0F, 1.0F, onePieceOffset, oneOffset * (1.0F - zeroPieceOffset)));
					case NORTH:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, oneOffset * (1.0F - zeroPieceOffset), onePieceOffset, 1.0F - zeroPieceOffset - zeroOffset * (1.0F - zeroPieceOffset)));
					case SOUTH:
						return VoxelShapes.create(new AxisAlignedBB(zeroPieceOffset + zeroOffset * (1.0F - zeroPieceOffset), 0.0F, 1.0F - oneOffset * (1.0F - zeroPieceOffset), 1.0F, onePieceOffset, 1.0F));
					case WEST:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, zeroPieceOffset + zeroOffset * (1.0F - zeroPieceOffset), oneOffset * (1.0F - zeroPieceOffset), onePieceOffset, 1.0F));
					default:
						return VoxelShapes.empty();
					}
				} else {
					switch (state.get(BlockColorizerSlopedRotate.HORIZONTAL_FACING)) {
					case EAST:
						return VoxelShapes.create(new AxisAlignedBB(zeroPieceOffset + zeroOffset * (1.0F - zeroPieceOffset), 1.0F - onePieceOffset, 0.0F, 1.0F, 1.0F, oneOffset * (1.0F - zeroPieceOffset)));
					case NORTH:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, 1.0F - onePieceOffset, 0.0F, oneOffset * (1.0F - zeroPieceOffset), 1.0F, 1.0F - zeroPieceOffset - zeroOffset * (1.0F - zeroPieceOffset)));
					case SOUTH:
						return VoxelShapes.create(new AxisAlignedBB(zeroPieceOffset + zeroOffset * (1.0F - zeroPieceOffset), 1.0F - onePieceOffset, 1.0F - oneOffset * (1.0F - zeroPieceOffset), 1.0F, 1.0F, 1.0F));
					case WEST:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, 1.0F - onePieceOffset, zeroPieceOffset + zeroOffset * (1.0F - zeroPieceOffset), oneOffset * (1.0F - zeroPieceOffset), 1.0F, 1.0F));
					default:
						return VoxelShapes.empty();
					}
				}
			}

			if (state.getBlock() == DecorBlocks.sloped_angle) {
				if (state.get(BlockColorizerSlopedRotate.HALF) == EnumHalf.BOTTOM) {
					switch (state.get(BlockColorizerSlopedRotate.HORIZONTAL_FACING)) {
					case EAST:
						return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, 0.0F, 1.0F, oneOffset, 1.0F - zeroOffset));
					case NORTH:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F - zeroOffset, oneOffset, 1.0F - zeroOffset));
					case SOUTH:
						return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, zeroOffset, 1.0F, oneOffset, 1.0F));
					case WEST:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, zeroOffset, 1.0F - zeroOffset, oneOffset, 1.0F));
					default:
						return VoxelShapes.empty();
					}
				} else {
					switch (state.get(BlockColorizerSlopedRotate.HORIZONTAL_FACING)) {
					case EAST:
						return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 1.0F - oneOffset, 0.0F, 1.0F, 1.0F, 1.0F - zeroOffset));
					case NORTH:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, 1.0F - oneOffset, 0.0F, 1.0F - zeroOffset, 1.0F, 1.0F - zeroOffset));
					case SOUTH:
						return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 1.0F - oneOffset, zeroOffset, 1.0F, 1.0F, 1.0F));
					case WEST:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, 1.0F - oneOffset, zeroOffset, 1.0F - zeroOffset, 1.0F, 1.0F));
					default:
						return VoxelShapes.empty();
					}
				}
			}

			if (state.getBlock() == DecorBlocks.slope) {
				if (state.get(BlockColorizerSlopedRotate.HALF) == EnumHalf.BOTTOM) {
					switch (state.get(BlockColorizerSlopedRotate.HORIZONTAL_FACING)) {
					case EAST:
						return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, 0.0F, 1.0F, oneOffset, 1.0F));
					case NORTH:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - zeroOffset, oneOffset));
					case SOUTH:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, zeroOffset, 1.0F, oneOffset, 1.0F));
					case WEST:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, oneOffset, 1.0F - zeroOffset, 1.0F));
					default:
						return VoxelShapes.empty();
					}
				} else {
					switch (state.get(BlockColorizerSlopedRotate.HORIZONTAL_FACING)) {
					case EAST:
						return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 1.0F - oneOffset, 0.0F, 1.0F, 1.0F, 1.0F));
					case NORTH:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset, 0.0F, 1.0F, 1.0F, oneOffset));
					case SOUTH:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, 1.0F - oneOffset, zeroOffset, 1.0F, 1.0F, 1.0F));
					case WEST:
						return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset, 0.0F, oneOffset, 1.0F, 1.0F));
					default:
						return VoxelShapes.empty();
					}
				}
			}

			if (state.getBlock() == DecorBlocks.oblique_slope) {
				if (state.get(BlockColorizerSlopedRotate.HALF) == EnumHalf.BOTTOM) {
					switch (state.get(BlockColorizerSlopedRotate.HORIZONTAL_FACING)) {
					case EAST:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, 0.0F, 1.0F, 1.0F, oneOffset));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, 0.0F, 1.0F, oneOffset, 1.0F));
						case 3:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - zeroOffset, oneOffset));
						}
					case NORTH:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, oneOffset, 1.0F, 1.0F - zeroOffset));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, oneOffset, 1.0F - zeroOffset, 1.0F));
						case 3:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - zeroOffset, oneOffset));
						}
					case SOUTH:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, 1.0F - oneOffset, 1.0F, 1.0F, 1.0F));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, 0.0F, 1.0F, oneOffset, 1.0F));
						case 3:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, zeroOffset, 1.0F, oneOffset, 1.0F));
						}
					case WEST:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, zeroOffset, oneOffset, 1.0F, 1.0F));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, oneOffset, 1.0F - zeroOffset, 1.0F));
						case 3:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, zeroOffset, 1.0F, oneOffset, 1.0F));
						}
					default:
						return VoxelShapes.empty();
					}
				} else {
					switch (state.get(BlockColorizerSlopedRotate.HORIZONTAL_FACING)) {
					case EAST:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, 0.0F, 1.0F, 1.0F, oneOffset));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 1.0F - oneOffset, 0.0F, 1.0F, 1.0F, 1.0F));
						case 3:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset, 0.0F, 1.0F, 1.0F, oneOffset));
						}
					case NORTH:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, oneOffset, 1.0F, 1.0F - zeroOffset));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset, 0.0F, oneOffset, 1.0F, 1.0F));
						case 3:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset, 0.0F, 1.0F, 1.0F, oneOffset));
						}
						return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset, 0.0F, 1.0F, 1.0F, oneOffset));
					case SOUTH:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, 1.0F - oneOffset, 1.0F, 1.0F, 1.0F));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 1.0F - oneOffset, 0.0F, 1.0F, 1.0F, 1.0F));
						case 3:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 1.0F - oneOffset, zeroOffset, 1.0F, 1.0F, 1.0F));
						}
					case WEST:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, zeroOffset, oneOffset, 1.0F, 1.0F));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset, 0.0F, oneOffset, 1.0F, 1.0F));
						case 3:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 1.0F - oneOffset, zeroOffset, 1.0F, 1.0F, 1.0F));
						}
					default:
						return VoxelShapes.empty();
					}
				}
			}

			if (state.getBlock() == DecorBlocks.sloped_intersection) {
				if (state.get(BlockColorizerSlopedRotate.HALF) == EnumHalf.BOTTOM) {
					switch (state.get(BlockColorizerSlopedRotate.HORIZONTAL_FACING)) {
					case EAST:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, 0.0F, 1.0F, oneOffset, 1.0F));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - zeroOffset, oneOffset));
						}
					case NORTH:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, oneOffset, 1.0F - zeroOffset, 1.0F));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - zeroOffset, oneOffset));
						}
					case SOUTH:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, 0.0F, 1.0F, oneOffset, 1.0F));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, zeroOffset, 1.0F, oneOffset, 1.0F));
						}
					case WEST:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, oneOffset, 1.0F - zeroOffset, 1.0F));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, zeroOffset, 1.0F, oneOffset, 1.0F));
						}
					default:
						return VoxelShapes.empty();
					}
				} else {
					switch (state.get(BlockColorizerSlopedRotate.HORIZONTAL_FACING)) {
					case EAST:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 1.0F - oneOffset, 0.0F, 1.0F, 1.0F, 1.0F));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset, 0.0F, 1.0F, 1.0F, oneOffset));
						}
					case NORTH:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset, 0.0F, oneOffset, 1.0F, 1.0F));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset, 0.0F, 1.0F, 1.0F, oneOffset));
						}
					case SOUTH:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 1.0F - oneOffset, 0.0F, 1.0F, 1.0F, 1.0F));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 1.0F - oneOffset, zeroOffset, 1.0F, 1.0F, 1.0F));
						}
					case WEST:
						switch (piece) {
						case 1:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset, 0.0F, oneOffset, 1.0F, 1.0F));
						case 2:
							return VoxelShapes.create(new AxisAlignedBB(0.0F, 1.0F - oneOffset, zeroOffset, 1.0F, 1.0F, 1.0F));
						}
					default:
						return VoxelShapes.empty();
					}
				}
			}

			if (state.getBlock() == DecorBlocks.corner) {
				switch (state.get(BlockColorizerSlopedRotate.HORIZONTAL_FACING)) {
				case EAST:
					return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, 0.0F, 1.0F, 1.0F, oneOffset));
				case NORTH:
					return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, 0.0F, oneOffset, 1.0F, 1.0F - zeroOffset));
				case SOUTH:
					return VoxelShapes.create(new AxisAlignedBB(zeroOffset, 0.0F, 1.0F - oneOffset, 1.0F, 1.0F, 1.0F));
				case WEST:
					return VoxelShapes.create(new AxisAlignedBB(0.0F, 0.0F, zeroOffset, oneOffset, 1.0F, 1.0F));
				default:
					return VoxelShapes.empty();
				}
			}

		} else if (state.getBlock() instanceof BlockColorizerHedge) {
			if (state.getBlock() == DecorBlocks.pyramid) {
				switch (state.get(BlockColorizerFacing.FACING)) {
				case DOWN:
					return VoxelShapes.create(new AxisAlignedBB(zeroOffset * 0.5F, 1.0F - oneOffset * 0.68F, zeroOffset * 0.5F, 1.0F - zeroOffset * 0.5F, 1.0F, 1.0F - zeroOffset * 0.5F));
				case EAST:
					return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset * 0.5F, zeroOffset * 0.5F, oneOffset * 0.68F, 1.0F - zeroOffset * 0.5F, 1.0F - zeroOffset * 0.5F));
				case NORTH:
					return VoxelShapes.create(new AxisAlignedBB(zeroOffset * 0.5F, zeroOffset * 0.5F, 1.0F - oneOffset * 0.68F, 1.0F - zeroOffset * 0.5F, 1.0F - zeroOffset * 0.5F, 1.0F));
				case SOUTH:
					return VoxelShapes.create(new AxisAlignedBB(zeroOffset * 0.5F, zeroOffset * 0.5F, oneOffset * 0.68F, 1.0F - zeroOffset * 0.5F, 1.0F - zeroOffset * 0.5F, 0.0F));
				case UP:
					return VoxelShapes.create(new AxisAlignedBB(zeroOffset * 0.5F, 0.0F, zeroOffset * 0.5F, 1.0F - zeroOffset * 0.5F, oneOffset * 0.68F, 1.0F - zeroOffset * 0.5F));
				case WEST:
					return VoxelShapes.create(new AxisAlignedBB(1.0F, zeroOffset * 0.5F, zeroOffset * 0.5F, 1.0F - oneOffset * 0.68F, 1.0F - zeroOffset * 0.5F, 1.0F - zeroOffset * 0.5F));
				default:
					return VoxelShapes.empty();
				}
			}

			if (state.getBlock() == DecorBlocks.full_pyramid) {
				switch (state.get(BlockColorizerFacing.FACING)) {
				case DOWN:
					return VoxelShapes.create(new AxisAlignedBB(zeroOffset * 0.5F, 1.0F - oneOffset, zeroOffset * 0.5F, 1.0F - zeroOffset * 0.5F, 1.0F, 1.0F - zeroOffset * 0.5F));
				case EAST:
					return VoxelShapes.create(new AxisAlignedBB(0.0F, zeroOffset * 0.5F, zeroOffset * 0.5F, oneOffset, 1.0F - zeroOffset * 0.5F, 1.0F - zeroOffset * 0.5F));
				case NORTH:
					return VoxelShapes.create(new AxisAlignedBB(zeroOffset * 0.5F, zeroOffset * 0.5F, 1.0F, 1.0F - zeroOffset * 0.5F, 1.0F - zeroOffset * 0.5F, 1.0F - oneOffset));
				case SOUTH:
					return VoxelShapes.create(new AxisAlignedBB(zeroOffset * 0.5F, zeroOffset * 0.5F, 0.0F, 1.0F - zeroOffset * 0.5F, 1.0F - zeroOffset * 0.5F, oneOffset));
				case UP:
					return VoxelShapes.create(new AxisAlignedBB(zeroOffset * 0.5F, 0.0F, zeroOffset * 0.5F, 1.0F - zeroOffset * 0.5F, oneOffset, 1.0F - zeroOffset * 0.5F));
				case WEST:
					return VoxelShapes.create(new AxisAlignedBB(1.0F, zeroOffset * 0.5F, zeroOffset * 0.5F, 1.0F - oneOffset, 1.0F - zeroOffset * 0.5F, 1.0F - zeroOffset * 0.5F));
				default:
					return VoxelShapes.empty();
				}
			}

			if (state.getBlock() == DecorBlocks.sloped_post) {
				return VoxelShapes.fullCube();
			}

		}

		return VoxelShapes.empty();
	}

	public enum SlopeType {
		SlantedCorner(DecorNames.SLANTED_CORNER), Corner(DecorNames.CORNER, 2), Slope(DecorNames.SLOPE, 2), SlopedAngle(DecorNames.SLOPED_ANGLE, 2), ObliqueSlope(DecorNames.OBLIQUE_SLOPE, 3), SlopedIntersection(DecorNames.SLOPED_INTERSECTION, 2), Pyramid(DecorNames.PYRAMID, 2), FullPyramid(DecorNames.FULL_PYRAMID, 2), SlopedPost(DecorNames.SLOPED_POST, 1);

		private final int numPieces;
		private final String name;

		private SlopeType(String name) {
			this(name, -1);
		}

		private SlopeType(String name, int numPieces) {
			this.name = name;
			this.numPieces = numPieces;
		}

		public String getName() {
			return name;
		}

		public int getNumPieces() {
			// Special override for SlantedAngles
			if (this == SlantedCorner)
				return DecorConfig.smoothness.get();
			else
				return numPieces;
		}
	}
}
