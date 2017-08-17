package com.grim3212.mc.pack.world.blocks;

import java.util.Random;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.client.ManualWorld;
import com.grim3212.mc.pack.world.util.KillingFungusWhitelist;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFungusKilling extends BlockFungusBase implements IManualBlock {

	protected BlockFungusKilling() {
		super("fungus_killing", true);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if (state.getValue(TYPE) == 1) {
			worldIn.scheduleUpdate(pos, this, worldIn.rand.nextInt(6) + 2);
		}
	}

	@Override
	public boolean canReplace(IBlockState side, IBlockState state) {
		Block block = side.getBlock();
		int meta = (Integer) state.getValue(TYPE);

		switch (meta) {
		case 0:
			return block == WorldBlocks.fungus_growing || block == WorldBlocks.fungus_building || (block == WorldBlocks.fungus_killing && (Integer) side.getValue(TYPE) > 1);
		case 1:
			return block == WorldBlocks.fungus_growing || block == WorldBlocks.fungus_building || (block == WorldBlocks.fungus_killing && (Integer) side.getValue(TYPE) > 1);
		case 2:
			return KillingFungusWhitelist.isDirtWhitelisted(side);
		case 3:
			return KillingFungusWhitelist.isSmoothWhitelisted(side);
		case 4:
			return KillingFungusWhitelist.isWaterLeavesWhitelisted(side);
		case 5:
			return KillingFungusWhitelist.isSandGravelWhitelisted(side);
		case 6:
			return KillingFungusWhitelist.isRocksWhitelisted(side);
		case 8:
			return KillingFungusWhitelist.isDirtWhitelisted(side);
		case 9:
			return KillingFungusWhitelist.isSmoothWhitelisted(side);
		case 10:
			return KillingFungusWhitelist.isWaterLeavesWhitelisted(side);
		case 11:
			return KillingFungusWhitelist.isSandGravelWhitelisted(side);
		case 12:
			return KillingFungusWhitelist.isRocksWhitelisted(side);
		case 13:
			return !(KillingFungusWhitelist.isAllEatingBlacklisted(side)) && !(block == this && (Integer) side.getValue(TYPE) == meta);
		}

		// standard fungus, or block builder (or tree killer)
		return (block == Blocks.FLOWING_WATER || block == Blocks.WATER) || (block == Blocks.FLOWING_LAVA || block == Blocks.LAVA) || block == Blocks.AIR || block instanceof BlockBush || block == Blocks.FIRE || block == Blocks.SNOW_LAYER || block == Blocks.REEDS || block == Blocks.VINE || ((block == WorldBlocks.fungus_growing || block == WorldBlocks.fungus_building || block == WorldBlocks.fungus_killing) && (side != state || block != this))
				|| KillingFungusWhitelist.isForestEatingWhitelisted(side);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		int meta = (Integer) state.getValue(TYPE);

		// dark acid
		if (meta <= 1) {
			IBlockState[] sideStates = { worldIn.getBlockState(pos.down()), worldIn.getBlockState(pos.west()), worldIn.getBlockState(pos.east()), worldIn.getBlockState(pos.north()), worldIn.getBlockState(pos.south()), worldIn.getBlockState(pos.up()) };
			int[] side = { 0, 1, 2, 3 };

			for (int q = 0; q < side.length; q++) {
				int randomPosition = rand.nextInt(side.length);
				int temp = side[q];
				side[q] = side[randomPosition];
				side[randomPosition] = temp;
			}

			boolean go = false;
			for (int r = 0; r <= 5; r++) {
				if (canReplace(sideStates[r], state)) {
					go = true;
					break;
				}
			}

			if (!go) {
				worldIn.setBlockToAir(pos);
				return;
			}

			if (canReplace(sideStates[0], state)) {
				worldIn.setBlockState(pos.down(), state, 2);
				if (meta == 1) {
					worldIn.scheduleUpdate(pos.down(), this, rand.nextInt(20) + 2);
				}
			}

			if (canReplace(sideStates[1], state)) {
				worldIn.setBlockState(pos.west(), state, 2);
				if (meta == 1) {
					worldIn.scheduleUpdate(pos.west(), this, rand.nextInt(20) + 2);
				}
			}
			if (canReplace(sideStates[2], state)) {
				worldIn.setBlockState(pos.east(), state, 2);
				if (meta == 1) {
					worldIn.scheduleUpdate(pos.east(), this, rand.nextInt(20) + 2);
				}
			}
			if (canReplace(sideStates[3], state)) {
				worldIn.setBlockState(pos.north(), state, 2);
				if (meta == 1) {
					worldIn.scheduleUpdate(pos.north(), this, rand.nextInt(20) + 2);
				}
			}
			if (canReplace(sideStates[4], state)) {
				worldIn.setBlockState(pos.south(), state, 2);
				if (meta == 1) {
					worldIn.scheduleUpdate(pos.south(), this, rand.nextInt(20) + 2);
				}
			}

			if (canReplace(sideStates[5], state)) {
				worldIn.setBlockState(pos.up(), state, 2);
				if (meta == 1) {
					worldIn.scheduleUpdate(pos.up(), this, rand.nextInt(20) + 2);
				}
			}
			if (meta == 1) {
				worldIn.scheduleUpdate(pos, this, rand.nextInt(40) + 6);
			}

			return;
		}

		// glass builder fungusK
		if ((meta == 7) || (meta == 15)) {
			if (!spreadToSide(worldIn, pos, state, false, meta == 7)) {
				worldIn.setBlockState(pos, Blocks.GLASS.getDefaultState(), 2);
			}
			return;
		}

		// basic or forest eater
		if (meta == 14) {
			spreadToSide(worldIn, pos, state, true, true);
		}

		// eaters
		spreadToSide(worldIn, pos, state, true, meta <= 7);
	}

	public static final int[] color = { 0x23004E, // 0 darkac
			0x23004E, // 1 darkac
			0x512400, // 2 x dirt
			0x484848, // 3 x sto
			0x00a25f, // 4 x water n leaves
			0x673e00, // 5 x sand
			0x6F5454, // 6 x ROCKS
			0xbbbbcf, // 7 GLASS

			0x512400, // 8 ux dirt
			0x484848, // 9 ux stone
			0x00a25f, // 10 ux water
			0x673e00, // 11 ux sand
			0x6F5454, // 12 ux rocks
			0xFF33FF, // 13 ux all

			0x4F964F, // 14 x forest blob
			0xbbbbcf // 15 layer glass
	};

	@Override
	public Page getPage(IBlockState state) {
		int type = state.getValue(TYPE);

		if (type == 0 || type == 1) {
			return ManualWorld.killingFungus_page;
		}

		if ((type >= 2 && type <= 6) || type == 14) {
			return ManualWorld.eatingFungus_page;
		}

		if (type == 7 || type == 15) {
			return ManualWorld.buildFungus_page;
		}

		return ManualWorld.verticalFungus_page;
	}
}
