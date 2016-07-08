package com.grim3212.mc.pack.industry.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSpike extends Block {

	private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
	private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.0F, 0.9375F, 0.0F, 1.0F, 1.0F, 1.0F);
	private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.9375F, 1.0F, 1.0F, 1.0F);
	private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F);
	private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.9375F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.0625F, 1.0F, 1.0F);
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);

	protected BlockSpike() {
		super(Material.IRON);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false).withProperty(FACING, EnumFacing.NORTH));
	}

	public static EnumFacing getFacing(int meta) {
		return EnumFacing.getFront(meta & 7);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(ACTIVE, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | ((EnumFacing) state.getValue(FACING)).getIndex();

		if (((Boolean) state.getValue(ACTIVE))) {
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (!state.getValue(ACTIVE)) {
			EnumFacing enumfacing = state.getValue(FACING);

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
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing, true) ? this.getDefaultState().withProperty(FACING, facing).withProperty(ACTIVE, false) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN).withProperty(ACTIVE, false);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		this.neighborChanged(state, worldIn, pos, this);
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		return worldIn.isSideSolid(pos.offset(side.getOpposite()), side, true);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		EnumFacing[] aenumfacing = EnumFacing.values();
		int i = aenumfacing.length;

		for (int j = 0; j < i; ++j) {
			EnumFacing enumfacing = aenumfacing[j];

			if (worldIn.isSideSolid(pos.offset(enumfacing), enumfacing.getOpposite(), true)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		this.checkForDrop(worldIn, pos);

		if (!(Boolean) state.getValue(ACTIVE) && worldIn.isBlockPowered(pos)) {
			worldIn.setBlockState(pos, state.withProperty(ACTIVE, true));
		} else if ((Boolean) state.getValue(ACTIVE) && !worldIn.isBlockPowered(pos)) {
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
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if ((Boolean) worldIn.getBlockState(pos).getValue(ACTIVE) && entityIn instanceof EntityLivingBase) {
			entityIn.attackEntityFrom(DamageSource.inWall, 10);
		}
	}
}
