package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHLight extends BlockManual {

	private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 0.125F, 0.75F);
	private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.25F, 0.875F, 0.25F, 0.75F, 1.0F, 0.75F);
	private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.25F, 0.25F, 0.875F, 0.75F, 0.75F, 1.0F);
	private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.125F);
	private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.875F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
	private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0F, 0.25F, 0.25F, 0.125F, 0.75F, 0.75F);
	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);

	protected BlockHLight() {
		super(Material.GLASS, SoundType.GLASS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(FACING)) {
		case DOWN:
			return DOWN_AABB;
		case EAST:
			return EAST_AABB;
		case NORTH:
			return NORTH_AABB;
		case SOUTH:
			return SOUTH_AABB;
		case UP:
			return UP_AABB;
		case WEST:
			return WEST_AABB;
		}

		return FULL_BLOCK_AABB;
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
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.west()).isOpaqueCube() ? true : (worldIn.getBlockState(pos.east()).isOpaqueCube() ? true : (worldIn.getBlockState(pos.north()).isOpaqueCube() ? true : (worldIn.getBlockState(pos.south()).isOpaqueCube() ? true : (worldIn.getBlockState(pos.up()).isOpaqueCube() ? true : worldIn.getBlockState(pos.down()).isOpaqueCube()))));
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return world.isSideSolid(pos.offset(facing.getOpposite()), facing, true) ? this.getDefaultState().withProperty(FACING, facing) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		this.dropLightIfCantStay(worldIn, pos);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		this.dropLightIfCantStay(worldIn, pos);
	}

	private void dropLightIfCantStay(World worldIn, BlockPos pos) {
		if (!this.canPlaceBlockAt(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, getDefaultState(), 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.hLight_page;
	}
}
