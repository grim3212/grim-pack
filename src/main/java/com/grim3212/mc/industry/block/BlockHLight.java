package com.grim3212.mc.industry.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHLight extends Block {

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);

	protected BlockHLight() {
		super(Material.glass);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING });
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
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
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.west()).getBlock().isOpaqueCube() ? true : (worldIn.getBlockState(pos.east()).getBlock().isOpaqueCube() ? true : (worldIn.getBlockState(pos.north()).getBlock().isOpaqueCube() ? true : (worldIn.getBlockState(pos.south()).getBlock().isOpaqueCube() ? true : (worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube() ? true : worldIn.getBlockState(pos.down()).getBlock().isOpaqueCube()))));
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing, true) ? this.getDefaultState().withProperty(FACING, facing) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		onNeighborBlockChange(worldIn, pos, state, this);
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		this.dropLightIfCantStay(worldIn, pos);
	}

	private void dropLightIfCantStay(World worldIn, BlockPos pos) {
		if (!this.canPlaceBlockAt(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, getDefaultState(), 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

		switch (enumfacing) {
		case UP:
			this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.125F, 0.75F);
			break;
		case DOWN:
			this.setBlockBounds(0.25F, 0.875F, 0.25F, 0.75F, 1.0F, 0.75F);
			break;
		case SOUTH:
			this.setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.125F);
			break;
		case NORTH:
			this.setBlockBounds(0.25F, 0.25F, 0.875F, 0.75F, 0.75F, 1.0F);
			break;
		case EAST:
			this.setBlockBounds(0.0F, 0.25F, 0.25F, 0.125F, 0.75F, 0.75F);
			break;
		case WEST:
			this.setBlockBounds(0.875F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
			break;
		}
	}

	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.875F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
	}
}
