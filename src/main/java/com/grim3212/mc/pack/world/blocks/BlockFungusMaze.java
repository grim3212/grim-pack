package com.grim3212.mc.pack.world.blocks;

import java.util.Random;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.client.ManualWorld;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//TODO: Should be more types of mazes beside STONEBRICK?
//Could use maze to create mining tunnels?
//Create chains of fungus if they are stacked vertically together?

public class BlockFungusMaze extends BlockFungusBase implements IManualBlock {

	protected BlockFungusMaze() {
		super(true, false);
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {});
	}

	@Override
	public boolean canReplace(IBlockState side, IBlockState state) {
		Block block = side.getBlock();
		return block == Blocks.AIR || block instanceof BlockBush || block == Blocks.FIRE || block == Blocks.SNOW_LAYER || block == Blocks.REEDS || block == Blocks.VINE || ((block == WorldBlocks.fungus_growing || block == WorldBlocks.fungus_building || block == WorldBlocks.fungus_killing) && (side != state || block != this));
	}

	// maze builds with bricks.. this returns a random brick type
	public int getBrick(Random random) {
		int number = random.nextInt(30);
		if (number <= 8) {
			return 0;
		}
		if (number <= 16) {
			return 1;
		}
		if (number <= 24) {
			return 2;
		}
		return 3;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		// ticks randomly? Does each new instance have a separate tick?

		// builder 'except maze
		// Scotto: Maze should mirror new blocks it creates 4 high if possible.
		// Maze passages should be at least 2 wide instead of 1
		// Scotto: Maze tends to create lots of closed in spaces
		// Dark acid fungus is useless on maze as is fungicide once maze is
		// large because of non contiguous blocks
		// changed last condition from meta 11 to meta 1
		if (worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR) { // this
																		// line
																		// shows
																		// a
																		// test
																		// for
																		// AIR
																		// for
																		// hole
																		// filling
			// way to create random numbers if(random.nextInt(7)==0){return;}
			boolean spread = spreadToSideMaze(worldIn, pos, state); // spreadToSideMaze
																	// returns
																	// boolean
																	// after
																	// testing
																	// meta
																	// values
			if (!spread) {
				int m2b = getBrick(worldIn.rand);
				IBlockState stonebrick = Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.byMetadata(m2b));
				worldIn.setBlockState(pos, stonebrick, 2);
				if (canReplace(worldIn.getBlockState(pos.down()), state)) {
					worldIn.setBlockState(pos.down(), state);
					return;
				}
			} else {
				if (rand.nextInt(32) == 0) {
					worldIn.setBlockToAir(pos);
					for (int jj = pos.getY(); jj > 1; jj--) {
						BlockPos newPos = new BlockPos(pos.getX(), jj, pos.getZ());
						if (worldIn.getBlockState(newPos).getBlock() == this || worldIn.getBlockState(newPos).getBlock() == Blocks.STONEBRICK) {
							worldIn.setBlockToAir(newPos);
						} else {
							break;
						}
					}
					return;
				}
				if (canReplace(worldIn.getBlockState(pos.down()), state)) {
					worldIn.setBlockState(pos.down(), state);
					return;
				}
			}
			// block above is not air
		} else {
			if (canReplace(worldIn.getBlockState(pos.down()), state)) {
				worldIn.setBlockState(pos.down(), state);
				return;
			} else {
				int m2b = getBrick(worldIn.rand);
				IBlockState stonebrick = Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.byMetadata(m2b));
				worldIn.setBlockState(pos, stonebrick);
			}
		}
		return;
		// }
	}

	// MAZE START
	public boolean spreadToSideMaze(World world, BlockPos pos, IBlockState state) {
		IBlockState[] sideStates = { world.getBlockState(pos.west()), world.getBlockState(pos.east()), world.getBlockState(pos.north()), world.getBlockState(pos.south()) };
		int[] side = { 0, 1, 2, 3 };

		for (int q = 0; q < side.length; q++) {
			int randomPosition = world.rand.nextInt(side.length);
			int temp = side[q];
			side[q] = side[randomPosition];
			side[randomPosition] = temp;
		}

		// sides
		for (int r = 0; r <= 3; r++) {
			if (side[r] == 0 && good4maze(world, pos.west(), state, sideStates[0])) {
				world.setBlockState(pos.west(), state);
				return true;
			}
			if (side[r] == 1 && good4maze(world, pos.east(), state, sideStates[1])) {
				world.setBlockState(pos.east(), state);
				return true;
			}
			if (side[r] == 2 && good4maze(world, pos.north(), state, sideStates[2])) {
				world.setBlockState(pos.north(), state);
				return true;
			}
			if (side[r] == 3 && good4maze(world, pos.south(), state, sideStates[3])) {
				world.setBlockState(pos.south(), state);
				return true;
			}
		}
		return false;
	}

	public boolean good4maze(World world, BlockPos pos, IBlockState state, IBlockState side) {
		if (!canReplace(side, state)) {
			return false;
		}

		int sides = 0;
		int corners = 0;
		IBlockState[] sideStates = { world.getBlockState(pos.west()), world.getBlockState(pos.east()), world.getBlockState(pos.north()), world.getBlockState(pos.south()), world.getBlockState(pos.west().north()), world.getBlockState(pos.east().south()), world.getBlockState(pos.east().north()), world.getBlockState(pos.east().south()) };

		int ls = -1;
		for (int q = 0; q <= 3; q++) {
			if (!canReplace(sideStates[q], state)) {
				sides++;
				ls = q;
			}
		}

		int c1 = -1;
		int c2 = -1;
		for (int q = 4; q <= 7; q++) {
			if (!canReplace(sideStates[q], state)) {
				corners++;

				if (corners == 1) {
					c1 = q;
				} else if (corners == 2) {
					c2 = q;
				} else {
					return false;
				}
			}
		}

		if (sides > 1)
			return world.rand.nextInt(200) == 0;

		if (corners > 2) {
			return false;
		}
		if (corners == 0) {
			return true;
		}
		boolean good = false;
		if (ls == 0 && ((c1 == 4 && c2 == -1) || (c1 == 5 && c2 == -1) || (c1 == 4 && c2 == 5))) {
			good = true;
		}
		if (ls == 1 && ((c1 == 6 && c2 == -1) || (c1 == 7 && c2 == -1) || (c1 == 6 && c2 == 7))) {
			good = true;
		}
		if (ls == 2 && ((c1 == 4 && c2 == -1) || (c1 == 6 && c2 == -1) || (c1 == 4 && c2 == 6))) {
			good = true;
		}
		if (ls == 3 && ((c1 == 5 && c2 == -1) || (c1 == 7 && c2 == -1) || (c1 == 5 && c2 == 7))) {
			good = true;
		}
		return good || world.rand.nextInt(200) == 0;
	}

	public static final int color = 0x66849a;

	@Override
	public Page getPage(IBlockState state) {
		return ManualWorld.mazeFungus_page;
	}
}
