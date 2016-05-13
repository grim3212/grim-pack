package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockFountain extends Block {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockFountain() {
		super(Material.rock);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { ACTIVE });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVE) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, meta == 1 ? true : false);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return true;

		worldIn.setBlockState(pos, state.cycleProperty(ACTIVE));
		worldIn.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);

		return true;
	}

	@Override
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (state.getValue(ACTIVE)) {
			worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.0D, 1.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 1.25D, pos.getZ() + 0.5D, 0.0D, 1.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 0.0D, 1.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 1.75D, pos.getZ() + 0.5D, 0.0D, 1.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, 0.0D, 1.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 2.25D, pos.getZ() + 0.5D, 0.0D, 1.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 2.5D, pos.getZ() + 0.5D, 0.0D, 1.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 2.75D, pos.getZ() + 0.5D, 0.0D, 1.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 3.0D, pos.getZ() + 0.5D, 0.0D, 1.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 3.25D, pos.getZ() + 0.5D, 0.0D, 1.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 3.5D, pos.getZ() + 0.5D, 0.0D, 1.0D, 0.0D, new int[0]);
		}
	}
}
