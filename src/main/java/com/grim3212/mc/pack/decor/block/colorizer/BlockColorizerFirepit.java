package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class BlockColorizerFirepit extends BlockColorizerFireplaceBase {

	public static final VoxelShape FIREPIT = Block.makeCuboidShape(0.0F, 0.0F, 0.0F, 16.0F, 9.6F, 16.0F);

	public BlockColorizerFirepit() {
		super(DecorNames.FIREPIT);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return DecorConfig.enableFirepitNet.get() ? VoxelShapes.fullCube() : FIREPIT;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.firepit_page;
	}
}
