package com.grim3212.mc.world.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFungusGrowing extends BlockFungusBase {

	protected BlockFungusGrowing() {
		super(false);
	}

	@Override
	public boolean canReplace(IBlockState side, IBlockState state) {
		Block block = side.getBlock();
		return block == Blocks.air || block instanceof BlockBush || block == Blocks.fire || block == Blocks.snow_layer || block == Blocks.reeds || block == Blocks.vine || ((block == WorldBlocks.fungus_growing || block == WorldBlocks.fungus_building || block == WorldBlocks.fungus_killing) && (side != state || block != this));
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if ((Integer) state.getValue(TYPE) < 8 && rand.nextInt(3) != 0) {
			return;
		}
		if ((Integer) state.getValue(TYPE) >= 8 && rand.nextInt(5) == 0) {
			return;
		}
		spreadToSide(worldIn, pos, state, true, true);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
		super.harvestBlock(worldIn, player, pos, state, te);

		if (worldIn.rand.nextInt(4) != 0) {
			return;
		}

		float f = 0.7F;
		double d = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
		double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
		double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
		EntityItem entityitem = new EntityItem(worldIn, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(Items.slime_ball, worldIn.rand.nextInt(2) + 1));
		entityitem.setPickupDelay(10);
		if (!worldIn.isRemote)
			worldIn.spawnEntityInWorld(entityitem);
	}

	@Override
	public void buildWithMixing(World world, BlockPos pos, IBlockState state) {
		Block id2 = world.getBlockState(pos).getBlock();
		if (id2 != this) {
			world.setBlockState(pos, state, 2);
			return;
		}

		int ometa = (Integer) world.getBlockState(pos).getValue(TYPE);
		int colorMeta = (Integer) state.getValue(TYPE);
		int color = colorMeta & 7;
		int ocolor = ometa & 7;
		boolean fast = colorMeta > 7;

		int ncolor = color;

		if ((color == 0 && ocolor == 2) || (color == 2 && ocolor == 0)) {
			ncolor = 1;
		} else if ((color == 0 && ocolor == 3) || (color == 3 && ocolor == 0)) {
			ncolor = 7;
		} else if ((color == 1 && ocolor == 2) || (color == 2 && ocolor == 1)) {
			ncolor = 5;
		} else if ((color == 1 && ocolor == 3) || (color == 1 && ocolor == 3)) {
			ncolor = 0;
		} else if ((color == 3 && ocolor == 2) || (color == 2 && ocolor == 3)) {
			ncolor = 4;
		} else if ((color == 4 && ocolor == 7) || (color == 7 && ocolor == 4)) {
			ncolor = 6;
		}

		if ((color != ncolor && world.rand.nextInt(7) == 0) || fast) {
			ncolor |= 8;
		}

		world.setBlockState(pos, state.withProperty(BlockFungusGrowing.TYPE, ncolor));
	}

	public static final int[] color = { 0x66ff44, // 0 green
			0xeee000, // 1 yellow
			0xdd0000, // 2 red
			0x6464ED, // 3 blue
			0xC600FF, // 4 purple
			0xff8000, // 5 orange
			0xFF92BB, // 6 pink
			0x00ddcc, // 7 cyan
			// rapid
			0x77ff44, // 8 green
			0xffee00, // 9 yellow
			0xff0000, // 10 red
			0x4B4BED, // 11 blue
			0xBC00FF, // 12 purple
			0xff7700, // 13 orange
			0xFF80BB, // 14 pink
			0x00ffee // 15 cyan
	};

	@Override
	public int getRenderColor(IBlockState state) {
		return color[(Integer) state.getValue(TYPE)];
	}

	@Override
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
		return color[(Integer) worldIn.getBlockState(pos).getValue(TYPE)];
	}
}
