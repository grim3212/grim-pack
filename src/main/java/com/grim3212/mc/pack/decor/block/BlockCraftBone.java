package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class BlockCraftBone extends BlockManual {

	private static final int numCycles = 6;
	public static final IntegerProperty CYCLE = IntegerProperty.create("cycle", 0, numCycles);

	protected BlockCraftBone() {
		super(DecorNames.CRAFT_BONE, Block.Properties.create(Material.MISCELLANEOUS).sound(SoundType.GROUND).doesNotBlockMovement());
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(CYCLE, 0);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(CYCLE);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		int cycle = worldIn.getBlockState(pos).get(CYCLE);
		if (cycle == numCycles) {
			cycle = 0;
		} else {
			cycle++;
		}
		worldIn.setBlockState(pos, this.getDefaultState().with(CYCLE, cycle), 2);
		return true;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isSolid(BlockState state) {
		return false;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.crafts_page;
	}
}
