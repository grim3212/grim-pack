package com.grim3212.mc.world.blocks;

import java.util.Random;

import com.grim3212.mc.world.GrimWorld;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;

public class BlockGlowstoneSeed extends Block {

	private static final int RATE = 10;
	public static final PropertyInteger STEP = PropertyInteger.create("step", 0, 8);

	protected BlockGlowstoneSeed() {
		super(Material.plants);
		disableStats();
		setTickRandomly(true);
		setCreativeTab(GrimWorld.INSTANCE.getCreativeTab());
		this.setDefaultState(this.blockState.getBaseState().withProperty(STEP, 0));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STEP, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (Integer) state.getValue(STEP);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { STEP });
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		int growth = (Integer) state.getValue(STEP);

		if (growth < 8) {
			if (rand.nextInt(RATE) == 0) {
				worldIn.setBlockState(pos, state.withProperty(STEP, growth + 1), 2);
			}
		} else {
			worldIn.setBlockToAir(pos);
			WorldGenGlowStone1 gen = new WorldGenGlowStone1();
			gen.generate(worldIn, rand, pos);
		}
	}

	@Override
	public int quantityDropped(Random random) {
		int num = random.nextInt(3);
		return num == 0 ? 1 : num;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.glowstone_dust;
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
		fallIfNotSupported(worldIn, pos);
	}

	private final void fallIfNotSupported(World world, BlockPos pos) {
		if (!canPlaceBlockAt(world, pos)) {
			dropBlockAsItem(world, pos, getDefaultState(), 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.up()).getBlock() == Blocks.netherrack && (worldIn.provider.getDimensionId() == -1 || pos.getY() <= 15);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}
}
