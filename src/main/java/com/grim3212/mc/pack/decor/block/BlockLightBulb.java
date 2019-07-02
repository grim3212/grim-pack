package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockLightBulb extends BreakableBlock implements IManualBlock {

	protected static final VoxelShape BULB_AABB = Block.makeCuboidShape(3.2f, 4.8f, 3.2f, 12.8f, 16.0f, 12.8f);
	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	protected BlockLightBulb() {
		super(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.1f).doesNotBlockMovement());
		setRegistryName(DecorNames.LIGHT_BULB);
		setDefaultState(this.stateContainer.getBaseState().with(ACTIVE, false));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(ACTIVE);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean flag) {
		neighborChanged(state, worldIn, pos, worldIn.getBlockState(pos.up()).getBlock(), pos, flag);
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean flag) {
		if (worldIn.getRedstonePowerFromNeighbors(pos) > 0 || worldIn.getRedstonePowerFromNeighbors(pos.up()) > 0) {
			worldIn.setBlockState(pos, getDefaultState().with(ACTIVE, true));
		} else {
			worldIn.setBlockState(pos, getDefaultState());
		}

		if (!isValidPosition(state, worldIn, pos))
			worldIn.destroyBlock(pos, true);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.up()).isOpaqueCube(worldIn, pos);
	}

	@Override
	public boolean canProvidePower(BlockState state) {
		return false;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext ctx) {
		return BULB_AABB;
	}

	@Override
	public int getLightValue(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
		return state.get(ACTIVE) ? 15 : 0;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.lights_page;
	}
}
