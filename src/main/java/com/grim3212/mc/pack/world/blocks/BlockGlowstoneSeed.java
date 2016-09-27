package com.grim3212.mc.pack.world.blocks;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.GrimWorld;
import com.grim3212.mc.pack.world.client.ManualWorld;
import com.grim3212.mc.pack.world.config.WorldConfig;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;

public class BlockGlowstoneSeed extends BlockManual {

	private static final int RATE = 10;
	public static final PropertyInteger STEP = PropertyInteger.create("step", 0, 8);

	protected BlockGlowstoneSeed() {
		super(Material.PLANTS, SoundType.GLASS);
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
		return state.getValue(STEP);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { STEP });
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		int growth = state.getValue(STEP);

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
		return Items.GLOWSTONE_DUST;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
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
		return worldIn.getBlockState(pos.up()).getBlock() == Blocks.NETHERRACK && (worldIn.provider.getDimensionType() == DimensionType.NETHER || pos.getY() <= WorldConfig.glowstoneSeedPlantHeight);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualWorld.glowSeeds_page;
	}
}
