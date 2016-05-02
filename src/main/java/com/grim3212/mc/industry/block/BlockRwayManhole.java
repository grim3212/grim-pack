package com.grim3212.mc.industry.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRwayManhole extends Block {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockRwayManhole() {
		super(Material.rock);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, meta == 0 ? false : true);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (Boolean) state.getValue(ACTIVE) ? 1 : 0;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { ACTIVE });
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		boolean active = false;
		if (worldIn.getBlockState(pos).getBlock() == this) {
			active = (Boolean) worldIn.getBlockState(pos).getValue(ACTIVE);
		}
		return !active ? super.getCollisionBoundingBox(worldIn, pos, state) : null;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			worldIn.setBlockState(pos, worldIn.getBlockState(pos).cycleProperty(ACTIVE));
			if (Math.random() < 0.5D) {
				worldIn.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.door_open", 1.0F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
			} else {
				worldIn.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.door_close", 1.0F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
			}

			return true;
		}
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		return worldIn.getBlockState(pos).getBlock() != this;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
