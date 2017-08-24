package com.grim3212.mc.pack.world.blocks;

import java.util.Random;

import com.grim3212.mc.pack.core.common.CommonItems;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.client.ManualWorld;
import com.grim3212.mc.pack.world.config.WorldConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFungusOre extends BlockFungusBase implements IManualBlock {

	protected BlockFungusOre() {
		super("fungus_ore_building", true);
	}

	@Override
	protected IBlockState getState() {
		return this.blockState.getBaseState();
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this));
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
		return (block == Blocks.FLOWING_WATER || block == Blocks.WATER) || block == Blocks.AIR || block instanceof BlockBush || block == Blocks.FIRE || block == Blocks.SNOW_LAYER || block == Blocks.REEDS || block == Blocks.VINE || ((block == WorldBlocks.fungus_growing || block == WorldBlocks.fungus_building || block == WorldBlocks.fungus_killing) && (side != state || block != this));
	}

	public Block getOre(Random random) {
		int number = random.nextInt(100);
		if (number <= 30) {
			return Blocks.COAL_ORE;
		}
		if (CoreConfig.subpartAluminum)
			if (number <= 40) {
				return CommonItems.aluminum_ore;
			}
		if (number <= 50) {
			return Blocks.IRON_ORE;
		}
		if (WorldConfig.subpartRandomite)
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
		return Blocks.STONE;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		// 10% Chance to grow after updateTick is called
		if (rand.nextInt(10) == 0)
			if (!spreadToSide(worldIn, pos, state, false, true)) {
				Block i2b = getOre(rand);
				worldIn.setBlockState(pos, i2b.getDefaultState(), 2);
			}
		return;
	}

	public static final int color = 0x5A9CFF;

	@Override
	public Page getPage(IBlockState state) {
		return ManualWorld.buildFungus_page;
	}
}
