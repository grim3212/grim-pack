package com.grim3212.mc.pack.world.blocks;

import java.util.Random;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.client.ManualWorld;
import com.grim3212.mc.pack.world.blocks.BlockRandomite;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFungusOre extends BlockFungusBase implements IManualBlock {

	protected BlockFungusOre() {
		super(true);
	}

	@Override
	public boolean canReplace(IBlockState side, IBlockState state) {
		Block block = side.getBlock();
		int meta = (Integer) state.getValue(TYPE);
		return ((block == Blocks.FLOWING_WATER || block == Blocks.WATER) && !(meta == 0)) || ((block == Blocks.FLOWING_LAVA || block == Blocks.LAVA) && !(meta == 8)) || block == Blocks.AIR || block instanceof BlockBush || block == Blocks.FIRE || block == Blocks.SNOW_LAYER || block == Blocks.REEDS || block == Blocks.VINE || ((block == WorldBlocks.fungus_growing || block == WorldBlocks.fungus_building || block == WorldBlocks.fungus_killing) && (side != state || block != this));
	}

	public Block getOre(Random random) {
		int number = random.nextInt(100);
		if (number <= 30) {
			return Blocks.COAL_ORE;
		}
		if (number <= 50) {
			return Blocks.IRON_ORE;
		}
		if (number <= 60) {
			return WorldBlocks.randomite;
		}
		if (number <= 70) {
			return Blocks.REDSTONE_ORE;
		}
		if (number <= 80) {
			return Blocks.LAPIS_ORE;
		}
		if (number <= 85) {
			return Blocks.GOLD_ORE;
		}
		if (number <= 90) {
			return Blocks.DIAMOND_ORE;
		}
		if (number <= 95) {
			return Blocks.EMERALD_ORE;
		}
		return WorldBlocks.randomite;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		int meta = (Integer) state.getValue(TYPE);

		if (!spreadToSide(worldIn, pos, state, false, true)) {
			Block i2b = getOre(rand);
			worldIn.setBlockState(pos, i2b.getDefaultState(), 2);
		}
		return;
	}

	public static final int[] color = { 0x5A9CFF };

	@Override
	public Page getPage(IBlockState state) {
		return ManualWorld.buildFungus_page;
	}
}

