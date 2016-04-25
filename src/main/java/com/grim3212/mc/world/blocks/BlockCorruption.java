package com.grim3212.mc.world.blocks;

import java.util.Random;

import com.grim3212.mc.world.GrimWorld;
import com.grim3212.mc.world.config.WorldConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockCorruption extends Block {

	public Block newBlock;

	protected BlockCorruption() {
		super(Material.wood);
		this.setTickRandomly(true);
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
				this.newBlock = Blocks.netherrack;
			} else if (var7 == 1) {
				this.newBlock = Blocks.soul_sand;
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
		if (var1.getBlockState(newPos).getBlock() != Blocks.flowing_water && var1.getBlockState(newPos).getBlock() != Blocks.water) {
			if (var1.getBlockState(newPos).getBlock() != Blocks.yellow_flower && var1.getBlockState(newPos).getBlock() != Blocks.red_flower && var1.getBlockState(newPos).getBlock() != Blocks.double_plant) {
				if (var1.getBlockState(newPos).getBlock() != Blocks.wheat && var1.getBlockState(newPos).getBlock() != Blocks.reeds && var1.getBlockState(newPos).getBlock() != Blocks.carrots && var1.getBlockState(newPos).getBlock() != Blocks.potatoes) {
					if (var1.getBlockState(newPos).getBlock() != Blocks.netherrack && var1.getBlockState(newPos).getBlock() != Blocks.glowstone && var1.getBlockState(newPos).getBlock() != Blocks.soul_sand && var1.getBlockState(newPos).getBlock() != WorldBlocks.corruption_block && var1.getBlockState(newPos).getBlock() != Blocks.air && var1.getBlockState(newPos).getBlock() != Blocks.portal && var1.getBlockState(newPos).getBlock() != Blocks.obsidian && var1.getBlockState(newPos).getBlock() != Blocks.flowing_lava && var1.getBlockState(newPos).getBlock() != Blocks.bedrock
							&& var1.getBlockState(newPos).getBlock() != Blocks.lava && var1.getBlockState(newPos).getBlock() != Blocks.torch && var1.getBlockState(newPos).getBlock() != Blocks.fire && var1.getBlockState(newPos).getBlock() != Blocks.brown_mushroom && var1.getBlockState(newPos).getBlock() != Blocks.red_mushroom && !(var1.getBlockState(newPos).getBlock() instanceof BlockStairs) && var1.getBlockState(newPos).getBlock() != Blocks.chest && var1.getBlockState(newPos).getBlock() != Blocks.redstone_wire && var1.getBlockState(newPos).getBlock() != Blocks.furnace
							&& var1.getBlockState(newPos).getBlock() != Blocks.lit_furnace && !(var1.getBlockState(newPos).getBlock() instanceof BlockSign) && !(var1.getBlockState(newPos).getBlock() instanceof BlockDoor) && var1.getBlockState(newPos).getBlock() != Blocks.ladder && var1.getBlockState(newPos).getBlock() != Blocks.rail && var1.getBlockState(newPos).getBlock() != Blocks.lever && !(var1.getBlockState(newPos).getBlock() instanceof BlockPressurePlate) && var1.getBlockState(newPos).getBlock() != Blocks.redstone_torch
							&& var1.getBlockState(newPos).getBlock() != Blocks.unlit_redstone_torch && var1.getBlockState(newPos).getBlock() != Blocks.glass && !(var1.getBlockState(newPos).getBlock() instanceof BlockButton) && var1.getBlockState(newPos).getBlock() != Blocks.jukebox && var1.getBlockState(newPos).getBlock() != Blocks.pumpkin && var1.getBlockState(newPos).getBlock() != Blocks.lit_pumpkin && var1.getBlockState(newPos).getBlock() != Blocks.cake && var1.getBlockState(newPos).getBlock() != Blocks.dispenser && var1.getBlockState(newPos).getBlock() != Blocks.crafting_table
							&& !(var1.getBlockState(newPos).getBlock() instanceof BlockBed) && !(var1.getBlockState(newPos).getBlock() instanceof BlockContainer)) {
						int var9 = var6.nextInt(20);
						if (var8 && var9 < 2 && var1.getBlockState(newPos.up()).getBlock() == Blocks.air) {
							var1.setBlockState(newPos.up(), Blocks.fire.getDefaultState());
						}
						var1.setBlockState(newPos, newBlock2.getDefaultState());
					}
				} else {
					var1.setBlockState(newPos, Blocks.red_mushroom.getDefaultState());
				}
			} else {
				var1.setBlockState(newPos, Blocks.brown_mushroom.getDefaultState());
			}
		} else {
			var1.setBlockState(newPos, Blocks.flowing_lava.getDefaultState());
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
