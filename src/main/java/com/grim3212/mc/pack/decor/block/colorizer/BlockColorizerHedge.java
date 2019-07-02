package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.util.DecorUtil;
import com.grim3212.mc.pack.decor.util.DecorUtil.SlopeType;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockColorizerHedge extends BlockColorizerFacing {

	private final SlopeType type;

	public BlockColorizerHedge(SlopeType type) {
		super(type.getName());
		this.type = type;
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!player.abilities.isCreativeMode) {
			if (this.getStoredState(worldIn, pos) != Blocks.AIR.getDefaultState() && !(this.getStoredState(worldIn, pos).getBlock() instanceof LeavesBlock)) {
				BlockState blockState = this.getStoredState(worldIn, pos);
				spawnAsEntity(worldIn, pos, new ItemStack(blockState.getBlock(), 1));
			}
		}
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return DecorUtil.addAxisAlignedBoxes(state, worldIn, pos, context, this.type.getNumPieces());
	}

	@Override
	public Page getPage(BlockState state) {
		switch (this.type) {
		case Pyramid:
			return ManualDecor.pyramid_page;
		case FullPyramid:
			return ManualDecor.fullPyramid_page;
		case SlopedPost:
			return ManualDecor.slopedPost_page;
		default:
			return null;
		}
	}

}
