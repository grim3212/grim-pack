package com.grim3212.mc.pack.decor.block;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IColorizer {

	public boolean clearColorizer(World worldIn, BlockPos pos, BlockState state, PlayerEntity player, Hand hand);

	public boolean setColorizer(World worldIn, BlockPos pos, BlockState state, @Nullable BlockState toSetState, PlayerEntity player, Hand hand, boolean consumeItem);

}
