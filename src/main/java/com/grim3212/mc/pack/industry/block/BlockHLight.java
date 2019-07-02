package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Hand;
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
	public static final PropertyEnum<Direction> FACING = PropertyEnum.create("facing", Direction.class);

	protected BlockHLight() {
		super("halogen_light", Material.GLASS, SoundType.GLASS);
		setHardness(0.1F);
		setResistance(6.0F);
		setLightLevel(1.0F);
		setLightOpacity(0);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected BlockState getState() {
		return this.blockState.getBaseState().withProperty(FACING, Direction.SOUTH);
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, Direction.getFront(meta));
	}

	@Override
	public int getMetaFromState(BlockState state) {
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
	public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
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
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(BlockState state) {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.west()).isOpaqueCube() ? true : (worldIn.getBlockState(pos.east()).isOpaqueCube() ? true : (worldIn.getBlockState(pos.north()).isOpaqueCube() ? true : (worldIn.getBlockState(pos.south()).isOpaqueCube() ? true : (worldIn.getBlockState(pos.up()).isOpaqueCube() ? true : worldIn.getBlockState(pos.down()).isOpaqueCube()))));
	}

	@Override
	public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer, Hand hand) {
		return world.isSideSolid(pos.offset(facing.getOpposite()), facing, true) ? this.getDefaultState().withProperty(FACING, facing) : this.getDefaultState().withProperty(FACING, Direction.DOWN);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		this.dropLightIfCantStay(worldIn, pos);
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		this.dropLightIfCantStay(worldIn, pos);
	}

	private void dropLightIfCantStay(World worldIn, BlockPos pos) {
		if (!this.canPlaceBlockAt(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, getDefaultState(), 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.hLight_page;
	}
}
