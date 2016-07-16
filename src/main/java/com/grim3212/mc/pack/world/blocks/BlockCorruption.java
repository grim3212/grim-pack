package com.grim3212.mc.pack.world.blocks;

import java.util.Random;

import com.grim3212.mc.pack.world.GrimWorld;
import com.grim3212.mc.pack.world.config.WorldConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCorruption extends Block {

	public Block newBlock;

	protected BlockCorruption() {
		super(Material.WOOD);
		this.setTickRandomly(true);
		this.setSoundType(SoundType.CLOTH);
		this.setCreativeTab(GrimWorld.INSTANCE.getCreativeTab());
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		boolean var6 = WorldConfig.corruption;
		super.updateTick(worldIn, pos, state, rand);
		if (var6) {
			int var7 = rand.nextInt(3);
			int var8 = rand.nextInt(24);
			int var9 = rand.nextInt(24);
			int var10 = rand.nextInt(24);
			if (var7 == 0) {
				this.newBlock = Blocks.NETHERRACK;
			} else if (var7 == 1) {
				this.newBlock = Blocks.SOUL_SAND;
			} else if (var7 == 2) {
				this.newBlock = WorldBlocks.corruption_block;
			}

			this.setNewBlock(worldIn, pos, this.newBlock, rand, var8 + 1);
			this.setNewBlock(worldIn, pos, this.newBlock, rand, var9 + 1);
			this.setNewBlock(worldIn, pos, this.newBlock, rand, var10 + 1);
		}
	}

	public void setNewBlock(World var1, BlockPos pos, Block newBlock2, Random var6, int var7) {
		boolean var8 = WorldConfig.fire;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if (var7 == 1) {
			++x;
		}

		if (var7 == 2) {
			++z;
		}

		if (var7 == 3) {
			--x;
		}

		if (var7 == 4) {
			--z;
		}

		if (var7 == 5) {
			++x;
			++z;
		}

		if (var7 == 6) {
			--x;
			--z;
		}

		if (var7 == 7) {
			++x;
			--z;
		}

		if (var7 == 8) {
			--x;
			++z;
		}

		if (var7 == 9) {
			++x;
			--y;
		}

		if (var7 == 10) {
			--y;
			++z;
		}

		if (var7 == 11) {
			--x;
			--y;
		}

		if (var7 == 12) {
			--y;
			--z;
		}

		if (var7 == 13) {
			++x;
			--y;
			++z;
		}

		if (var7 == 14) {
			--x;
			--y;
			--z;
		}

		if (var7 == 15) {
			++x;
			--y;
			--z;
		}

		if (var7 == 16) {
			--x;
			--y;
			++z;
		}

		if (var7 == 17) {
			++x;
			++y;
		}

		if (var7 == 18) {
			++y;
			++z;
		}

		if (var7 == 19) {
			--x;
			++y;
		}

		if (var7 == 20) {
			++y;
			--z;
		}

		if (var7 == 21) {
			++x;
			++y;
			++z;
		}

		if (var7 == 22) {
			--x;
			++y;
			--z;
		}

		if (var7 == 23) {
			++x;
			++y;
			--z;
		}

		if (var7 == 24) {
			--x;
			++y;
			++z;
		}

		BlockPos newPos = new BlockPos(x, y, z);
		if (var1.getBlockState(newPos).getBlock() != Blocks.FLOWING_WATER && var1.getBlockState(newPos).getBlock() != Blocks.WATER) {
			if (var1.getBlockState(newPos).getBlock() != Blocks.YELLOW_FLOWER && var1.getBlockState(newPos).getBlock() != Blocks.RED_FLOWER && var1.getBlockState(newPos).getBlock() != Blocks.DOUBLE_PLANT) {
				if (var1.getBlockState(newPos).getBlock() != Blocks.WHEAT && var1.getBlockState(newPos).getBlock() != Blocks.REEDS && var1.getBlockState(newPos).getBlock() != Blocks.CARROTS && var1.getBlockState(newPos).getBlock() != Blocks.POTATOES) {
					if (var1.getBlockState(newPos).getBlock() != Blocks.NETHERRACK && var1.getBlockState(newPos).getBlock() != Blocks.GLOWSTONE && var1.getBlockState(newPos).getBlock() != Blocks.SOUL_SAND && var1.getBlockState(newPos).getBlock() != WorldBlocks.corruption_block && var1.getBlockState(newPos).getBlock() != Blocks.AIR && var1.getBlockState(newPos).getBlock() != Blocks.PORTAL && var1.getBlockState(newPos).getBlock() != Blocks.OBSIDIAN && var1.getBlockState(newPos).getBlock() != Blocks.FLOWING_LAVA && var1.getBlockState(newPos).getBlock() != Blocks.BEDROCK
							&& var1.getBlockState(newPos).getBlock() != Blocks.LAVA && var1.getBlockState(newPos).getBlock() != Blocks.TORCH && var1.getBlockState(newPos).getBlock() != Blocks.FIRE && var1.getBlockState(newPos).getBlock() != Blocks.BROWN_MUSHROOM && var1.getBlockState(newPos).getBlock() != Blocks.RED_MUSHROOM && !(var1.getBlockState(newPos).getBlock() instanceof BlockStairs) && var1.getBlockState(newPos).getBlock() != Blocks.CHEST && var1.getBlockState(newPos).getBlock() != Blocks.REDSTONE_WIRE && var1.getBlockState(newPos).getBlock() != Blocks.FURNACE
							&& var1.getBlockState(newPos).getBlock() != Blocks.LIT_FURNACE && !(var1.getBlockState(newPos).getBlock() instanceof BlockSign) && !(var1.getBlockState(newPos).getBlock() instanceof BlockDoor) && var1.getBlockState(newPos).getBlock() != Blocks.LADDER && var1.getBlockState(newPos).getBlock() != Blocks.RAIL && var1.getBlockState(newPos).getBlock() != Blocks.LEVER && !(var1.getBlockState(newPos).getBlock() instanceof BlockPressurePlate) && var1.getBlockState(newPos).getBlock() != Blocks.REDSTONE_TORCH
							&& var1.getBlockState(newPos).getBlock() != Blocks.UNLIT_REDSTONE_TORCH && var1.getBlockState(newPos).getBlock() != Blocks.GLASS && !(var1.getBlockState(newPos).getBlock() instanceof BlockButton) && var1.getBlockState(newPos).getBlock() != Blocks.JUKEBOX && var1.getBlockState(newPos).getBlock() != Blocks.PUMPKIN && var1.getBlockState(newPos).getBlock() != Blocks.PUMPKIN && var1.getBlockState(newPos).getBlock() != Blocks.CAKE && var1.getBlockState(newPos).getBlock() != Blocks.DISPENSER && var1.getBlockState(newPos).getBlock() != Blocks.CRAFTING_TABLE
							&& !(var1.getBlockState(newPos).getBlock() instanceof BlockBed) && !(var1.getBlockState(newPos).getBlock() instanceof BlockContainer)) {
						int var9 = var6.nextInt(20);
						if (var8 && var9 < 2 && var1.getBlockState(newPos.up()).getBlock() == Blocks.AIR) {
							var1.setBlockState(newPos.up(), Blocks.FIRE.getDefaultState());
						}
						var1.setBlockState(newPos, newBlock2.getDefaultState());
					}
				} else {
					var1.setBlockState(newPos, Blocks.RED_MUSHROOM.getDefaultState());
				}
			} else {
				var1.setBlockState(newPos, Blocks.BROWN_MUSHROOM.getDefaultState());
			}
		} else {
			var1.setBlockState(newPos, Blocks.FLOWING_LAVA.getDefaultState());
		}
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
		if (this == WorldBlocks.corruption_block && side == EnumFacing.UP) {
			return true;
		}
		return false;
	}
}
