package com.grim3212.mc.decor.block;

import java.util.Random;

import com.grim3212.mc.decor.GrimDecor;
import com.grim3212.mc.decor.item.DecorItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLightBulb extends BlockBreakable {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	protected BlockLightBulb() {
		super(Material.glass, true);
		float f = 0.3F;
		setBlockBounds(0.5F - f, 0.3F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(ACTIVE, false));
	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return getDefaultState().getValue(ACTIVE) ? null : GrimDecor.INSTANCE.getCreativeTab();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVE) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, meta == 1);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, ACTIVE);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
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
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		onNeighborBlockChange(worldIn, pos, state, worldIn.getBlockState(pos.up()).getBlock());
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (worldIn.isBlockIndirectlyGettingPowered(pos) > 0 || worldIn.isBlockIndirectlyGettingPowered(pos.up()) > 0) {
			worldIn.setBlockState(pos, getDefaultState().withProperty(ACTIVE, true));
		} else {
			worldIn.setBlockState(pos, getDefaultState());
		}

		if (!canPlaceBlockAt(worldIn, pos)) {
			dropBlockAsItem(worldIn, pos, getDefaultState(), 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return DecorItems.glass_shard;
	}

	@Override
	public int quantityDropped(Random random) {
		return 2 + random.nextInt(2);
	}

	@Override
	public boolean canProvidePower() {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		return null;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(DecorBlocks.light_bulb, 1, 0);
	}

	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos) {
		IBlockState block = world.getBlockState(pos);
		if (block.getBlock() == this) {
			return block.getValue(ACTIVE) ? 15 : 0;
		}
		return 0;
	}
}
