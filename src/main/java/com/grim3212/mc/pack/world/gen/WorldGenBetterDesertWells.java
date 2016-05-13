package com.grim3212.mc.pack.world.gen;

import java.util.Random;

import com.google.common.base.Predicates;
import com.grim3212.mc.pack.world.util.DesertWellLoot;
import com.grim3212.mc.pack.world.util.WellLootStorage;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBetterDesertWells extends WorldGenerator {

	private static final BlockStateHelper variant = BlockStateHelper.forBlock(Blocks.sand).where(BlockSand.VARIANT, Predicates.equalTo(BlockSand.EnumType.SAND));
	private final IBlockState stone_slab;
	private final IBlockState sandstone;
	private final IBlockState water;
	private int randomType;

	public WorldGenBetterDesertWells() {
		this.stone_slab = Blocks.stone_slab.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND).withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		this.sandstone = Blocks.sandstone.getDefaultState();
		this.water = Blocks.flowing_water.getDefaultState();
	}

	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos) {
		this.randomType = (random.nextInt(5) + 1);

		while ((worldIn.isAirBlock(pos)) && (pos.getY() > 7 + 5 * this.randomType)) {
			pos = pos.down();
		}

		if (!variant.apply(worldIn.getBlockState(pos))) {
			return false;
		} else {
			Block l = worldIn.getBlockState(pos).getBlock();

			if (l != Blocks.sand) {
				return false;
			}

			for (int i1 = -2; i1 <= 2; i1++) {
				for (int j1 = -2; j1 <= 2; j1++) {
					if ((worldIn.isAirBlock(pos.add(i1, -1, j1)) && (worldIn.isAirBlock(pos.add(i1, -2, j1))))) {
						return false;
					}
				}
			}

			for (int i1 = -1; i1 <= 0; i1++) {
				for (int j1 = -2; j1 <= 2; j1++) {
					for (int k1 = -2; k1 <= 2; k1++) {
						worldIn.setBlockState(pos.add(j1, i1, k1), sandstone, 2);
					}
				}
			}

			int depth = this.randomType * 5 + 5;

			for (int i = 0; i < depth; i++) {
				worldIn.setBlockState(pos.add(0, -i, 0), water, 2);
				worldIn.setBlockState(pos.add(-1, -i, 0), water, 2);
				worldIn.setBlockState(pos.add(1, -i, 0), water, 2);
				worldIn.setBlockState(pos.add(0, -i, -1), water, 2);
				worldIn.setBlockState(pos.add(0, -i, 1), water, 2);

				if (i != 0) {
					worldIn.setBlockState(pos.add(2, -i, 0), sandstone, 2);
					worldIn.setBlockState(pos.add(-2, -i, 0), sandstone, 2);
					worldIn.setBlockState(pos.add(0, -i, 2), sandstone, 2);
					worldIn.setBlockState(pos.add(0, -i, -2), sandstone, 2);
					worldIn.setBlockState(pos.add(1, -i, 1), sandstone, 2);
					worldIn.setBlockState(pos.add(1, -i, -1), sandstone, 2);
					worldIn.setBlockState(pos.add(-1, -i, 1), sandstone, 2);
					worldIn.setBlockState(pos.add(-1, -i, -1), sandstone, 2);
				}
			}

			worldIn.setBlockState(pos.add(0, -depth - 1, 0), sandstone, 2);
			worldIn.setBlockState(pos.add(-1, -depth, 0), sandstone, 2);
			worldIn.setBlockState(pos.add(1, -depth, 0), sandstone, 2);
			worldIn.setBlockState(pos.add(0, -depth, -1), sandstone, 2);
			worldIn.setBlockState(pos.add(0, -depth, 1), sandstone, 2);
			worldIn.setBlockState(pos.add(0, -depth, 0), Blocks.chest.getDefaultState(), 2);

			TileEntityChest chest = (TileEntityChest) worldIn.getTileEntity(pos.add(0, -depth, 0));
			if (chest != null)
				setChestContents(chest, random);

			for (int i1 = -2; i1 <= 2; i1++) {
				for (int j1 = -2; j1 <= 2; j1++) {
					if ((i1 == -2) || (i1 == 2) || (j1 == -2) || (j1 == 2)) {
						worldIn.setBlockState(pos.add(i1, 1, j1), sandstone, 2);
					}
				}
			}

			worldIn.setBlockState(pos.add(2, 1, 0), stone_slab, 2);
			worldIn.setBlockState(pos.add(-2, 1, 0), stone_slab, 2);
			worldIn.setBlockState(pos.add(0, 1, 2), stone_slab, 2);
			worldIn.setBlockState(pos.add(0, 1, -2), stone_slab, 2);

			for (int i1 = -1; i1 <= 1; i1++) {
				for (int j1 = -1; j1 <= 1; j1++) {
					if ((i1 == 0) && (j1 == 0)) {
						worldIn.setBlockState(pos.add(i1, 4, j1), sandstone, 2);
					} else {
						worldIn.setBlockState(pos.add(i1, 4, j1), stone_slab, 2);
					}
				}
			}

			for (int i1 = 1; i1 <= 3; i1++) {
				worldIn.setBlockState(pos.add(-1, i1, -1), sandstone, 2);
				worldIn.setBlockState(pos.add(-1, i1, 1), sandstone, 2);
				worldIn.setBlockState(pos.add(1, i1, -1), sandstone, 2);
				worldIn.setBlockState(pos.add(1, i1, 1), sandstone, 2);
			}
			return true;
		}
	}

	public void setChestContents(TileEntityChest chest, Random random) {
		ItemStack stack = null;
		for (int i = 0; i < chest.getSizeInventory(); i++) {
			if (random.nextInt(4) == 0) {
				switch (this.randomType) {
				case 1:
					int chestRand0 = random.nextInt(DesertWellLoot.level10storage.size());

					WellLootStorage loot0 = (WellLootStorage) DesertWellLoot.level10storage.get(chestRand0);

					if (loot0.isOneItem())
						loot0.getStack().stackSize = 1;
					else {
						loot0.getStack().stackSize = (random.nextInt(loot0.getMaxAmount()) + loot0.getMinAmount());
					}

					stack = loot0.getStack();

					break;
				case 2:
					int chestRand1 = random.nextInt(DesertWellLoot.level15storage.size());

					WellLootStorage loot1 = (WellLootStorage) DesertWellLoot.level15storage.get(chestRand1);

					if (loot1.isOneItem())
						loot1.getStack().stackSize = 1;
					else {
						loot1.getStack().stackSize = (random.nextInt(loot1.getMaxAmount()) + loot1.getMinAmount());
					}

					stack = loot1.getStack();

					break;
				case 3:
					int chestRand2 = random.nextInt(DesertWellLoot.level20storage.size());

					WellLootStorage loot2 = (WellLootStorage) DesertWellLoot.level20storage.get(chestRand2);

					if (loot2.isOneItem())
						loot2.getStack().stackSize = 1;
					else {
						loot2.getStack().stackSize = (random.nextInt(loot2.getMaxAmount()) + loot2.getMinAmount());
					}

					stack = loot2.getStack();

					break;
				case 4:
					int chestRand3 = random.nextInt(DesertWellLoot.level25storage.size());

					WellLootStorage loot3 = (WellLootStorage) DesertWellLoot.level25storage.get(chestRand3);

					if (loot3.isOneItem())
						loot3.getStack().stackSize = 1;
					else {
						loot3.getStack().stackSize = (random.nextInt(loot3.getMaxAmount()) + loot3.getMinAmount());
					}

					stack = loot3.getStack();

					break;
				case 5:
					int chestRand4 = random.nextInt(DesertWellLoot.level30storage.size());

					WellLootStorage loot4 = (WellLootStorage) DesertWellLoot.level30storage.get(chestRand4);

					if (loot4.isOneItem())
						loot4.getStack().stackSize = 1;
					else {
						loot4.getStack().stackSize = (random.nextInt(loot4.getMaxAmount()) + loot4.getMinAmount());
					}

					stack = loot4.getStack();
					break;
				}

				if (stack != null) {
					chest.setInventorySlotContents(i, stack.copy());
				}
			}
		}
	}
}