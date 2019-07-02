package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustrySounds;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSpike extends BlockManual {

	private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
	private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.0F, 0.9375F, 0.0F, 1.0F, 1.0F, 1.0F);
	private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.9375F, 1.0F, 1.0F, 1.0F);
	private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F);
	private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.9375F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.0625F, 1.0F, 1.0F);
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyEnum<Direction> FACING = PropertyEnum.create("facing", Direction.class);

	protected BlockSpike() {
		super("spike", Material.IRON, SoundType.METAL);
		setHardness(1.5F);
		setResistance(10F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected BlockState getState() {
		return this.blockState.getBaseState().withProperty(ACTIVE, false).withProperty(FACING, Direction.NORTH);
	}

	public static Direction getFacing(int meta) {
		return Direction.getFront(meta & 7);
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(ACTIVE, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		byte b0 = 0;
		int i = b0 | state.getValue(FACING).getIndex();

		if (state.getValue(ACTIVE)) {
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, ACTIVE });
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
		if (!state.getValue(ACTIVE)) {
			Direction enumfacing = state.getValue(FACING);

			switch (enumfacing) {
			case UP:
				return UP_AABB;
			case DOWN:
				return DOWN_AABB;
			case SOUTH:
				return SOUTH_AABB;
			case NORTH:
				return NORTH_AABB;
			case EAST:
				return EAST_AABB;
			case WEST:
				return WEST_AABB;
			}
		}

		return FULL_BLOCK_AABB;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockAccess blockAccess, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(BlockState state) {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
		return true;
	}

	@Override
	public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer, Hand hand) {
		return world.isSideSolid(pos.offset(facing.getOpposite()), facing, true) ? this.getDefaultState().withProperty(FACING, facing).withProperty(ACTIVE, false) : this.getDefaultState().withProperty(FACING, Direction.DOWN).withProperty(ACTIVE, false);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		this.neighborChanged(state, worldIn, pos, this, pos);
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, Direction side) {
		return worldIn.isSideSolid(pos.offset(side.getOpposite()), side, true);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		Direction[] aenumfacing = Direction.values();
		int i = aenumfacing.length;

		for (int j = 0; j < i; ++j) {
			Direction enumfacing = aenumfacing[j];

			if (worldIn.isSideSolid(pos.offset(enumfacing), enumfacing.getOpposite(), true)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		this.checkForDrop(worldIn, pos);

		if (!state.getValue(ACTIVE) && worldIn.isBlockPowered(pos)) {
			worldIn.playSound((PlayerEntity) null, pos, IndustrySounds.spikeDeploySound, SoundCategory.BLOCKS, 0.3F, 0.6F);
			worldIn.setBlockState(pos, state.withProperty(ACTIVE, true));
		} else if (state.getValue(ACTIVE) && !worldIn.isBlockPowered(pos)) {
			worldIn.playSound((PlayerEntity) null, pos, IndustrySounds.spikeCloseSound, SoundCategory.BLOCKS, 0.3F, 0.6F);
			worldIn.setBlockState(pos, state.withProperty(ACTIVE, false));
		}
	}

	private void checkForDrop(World worldIn, BlockPos pos) {
		if (!canPlaceBlockAt(worldIn, pos)) {
			dropBlockAsItem(worldIn, pos, getDefaultState(), 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, BlockState state, Entity entityIn) {
		if (worldIn.getBlockState(pos).getValue(ACTIVE) && entityIn instanceof LivingEntity) {
			entityIn.attackEntityFrom(DamageSource.IN_WALL, 10);
		}
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.spike_page;
	}
}
