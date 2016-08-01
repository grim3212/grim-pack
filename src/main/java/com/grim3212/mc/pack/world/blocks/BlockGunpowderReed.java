package com.grim3212.mc.pack.world.blocks;

import java.util.Random;

import com.grim3212.mc.pack.world.items.WorldItems;

import net.minecraft.block.BlockReed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGunpowderReed extends BlockReed {

	protected BlockGunpowderReed() {
		super();
		this.setSoundType(SoundType.PLANT);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (worldIn.getBlockState(pos.down()).getBlock() == WorldBlocks.gunpowder_reed_block || this.checkForDrop(worldIn, pos, state)) {
			if (worldIn.isAirBlock(pos.up())) {
				int i;

				for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i) {
					;
				}

				if (i < 3) {
					int j = state.getValue(AGE).intValue();

					if (j == 15) {
						worldIn.setBlockState(pos.up(), this.getDefaultState());
						worldIn.setBlockState(pos, state.withProperty(AGE, 0), 4);
					} else {
						worldIn.setBlockState(pos, state.withProperty(AGE, j + 1), 4);
					}
				}
			}
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return WorldItems.gunpowder_reed_item;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(WorldItems.gunpowder_reed_item);
	}
}