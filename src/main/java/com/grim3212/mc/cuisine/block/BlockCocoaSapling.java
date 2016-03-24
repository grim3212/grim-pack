package com.grim3212.mc.cuisine.block;

import java.util.Random;

import com.grim3212.mc.cuisine.item.CuisineItems;
import com.grim3212.mc.cuisine.world.WorldGenCocoaTrees;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockCocoaSapling extends BlockBush implements IGrowable {

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);

	protected BlockCocoaSapling() {
		super(Material.plants);
		float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		setTickRandomly(true);
		setCreativeTab(null);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return CuisineItems.cocoa_fruit;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			super.updateTick(worldIn, pos, state, rand);

			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
				this.grow(worldIn, rand, pos, state);
			}
		}
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		if (((Integer) state.getValue(STAGE)).intValue() == 0) {
			world.setBlockState(pos, state.cycleProperty(STAGE), 4);
		} else {
			this.generateTree(world, pos, state, rand);
		}
	}

	public void generateTree(World world, BlockPos pos, IBlockState state, Random random) {
		world.setBlockToAir(pos);
		WorldGenCocoaTrees worldgencocoatrees = new WorldGenCocoaTrees();
		if (!worldgencocoatrees.generate(world, random, pos)) {
			world.setBlockState(pos, this.getDefaultState());
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(STAGE));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { STAGE });
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(CuisineItems.cocoa_fruit);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return (double) worldIn.rand.nextFloat() < 0.45D;
	}
}
