package com.grim3212.mc.pack.decor.block;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IColorizer {

	public boolean clearColorizer(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand);

	public boolean setColorizer(World worldIn, BlockPos pos, IBlockState state, @Nullable IBlockState toSetState, EntityPlayer player, EnumHand hand, boolean consumeItem);

}
