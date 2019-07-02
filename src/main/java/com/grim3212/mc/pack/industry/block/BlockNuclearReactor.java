package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockNuclearReactor extends BlockManual {

	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	public BlockNuclearReactor() {
		super(IndustryNames.REACTOR, Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(1.0f).lightValue(12));
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(ACTIVE, false);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(ACTIVE);
	}

	@Override
	public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return blockAccess.getBlockState(pos).get(ACTIVE) ? 15 : 0;
	}

	@Override
	public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return blockAccess.getBlockState(pos).get(ACTIVE) ? 15 : 0;
	}

	@Override
	public boolean canProvidePower(BlockState state) {
		return true;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			return true;
		} else {
			worldIn.setBlockState(pos, state.cycle(ACTIVE));
			worldIn.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, state.get(ACTIVE) ? 0.6F : 0.5F);
			worldIn.notifyNeighborsOfStateChange(pos, this);
			return true;
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, BlockState state) {
		if ((Boolean) state.getValue(ACTIVE)) {
			worldIn.notifyNeighborsOfStateChange(pos, this, true);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.reactor_page;
	}
}
