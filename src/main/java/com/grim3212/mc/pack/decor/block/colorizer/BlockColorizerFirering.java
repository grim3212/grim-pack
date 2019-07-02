package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockColorizerFirering extends BlockColorizerFireplaceBase implements IManualBlock {

	public static final VoxelShape FIRERING = Block.makeCuboidShape(0.0F, 0.0F, 0.0F, 16.0F, 6.4F, 16.0F);

	public BlockColorizerFirering() {
		super(DecorNames.FIRERING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return FIRERING;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.firering_page;
	}
}
