package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;
import com.grim3212.mc.pack.industry.tile.TileEntityFlipFlopTorch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockFlipFlopTorch extends TorchBlock implements IManualBlock {

	public static final BooleanProperty ON = BooleanProperty.create("on");

	public BlockFlipFlopTorch() {
		super(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0f).tickRandomly());
		setRegistryName(IndustryNames.FLIP_FLOP_TORCH);
		setDefaultState(this.getDefaultState().with(ON, false).with(FACING, Direction.UP));
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityFlipFlopTorch();
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.flipFlopTorch_page;
	}

	@Override
	public int tickRate(IWorldReader worldIn) {
		return 1;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, BlockState state) {
		if (state.get(ON)) {
			for (Direction enumfacing : Direction.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}
		}
	}

	@Override
	public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return blockState.get(ON) && blockState.get(FACING) != side ? 15 : 0;
	}

	private boolean checkPower(World world, BlockPos pos, BlockState state) {
		Direction enumfacing = state.get(FACING).getOpposite();
		return world.isSidePowered(pos.offset(enumfacing), enumfacing);
	}

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityFlipFlopTorch) {
			TileEntityFlipFlopTorch flipTE = (TileEntityFlipFlopTorch) te;

			boolean flag = checkPower(worldIn, pos, state);
			boolean prevFlag = flipTE.getPrevState();
			flipTE.setPrevState(flag);

			if (flag != prevFlag) {
				if (flag) {
					if (state.get(ON)) {
						worldIn.setBlockState(pos, state.with(ON, false), 3);
					} else {
						worldIn.setBlockState(pos, state.with(ON, true), 3);
					}
				}
			}
		}
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
		worldIn.getPendingBlockTicks().scheduleTick(pos, blockIn, this.tickRate(worldIn));
	}

	@Override
	public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return side == Direction.DOWN ? blockState.getWeakPower(blockAccess, pos, side) : 0;
	}

	@Override
	public boolean canProvidePower(BlockState state) {
		return true;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.get(ON)) {
			double d0 = (double) pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
			double d1 = (double) pos.getY() + 0.7D + (rand.nextDouble() - 0.5D) * 0.2D;
			double d2 = (double) pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
			Direction enumfacing = stateIn.get(FACING);

			if (enumfacing.getAxis().isHorizontal()) {
				Direction enumfacing1 = enumfacing.getOpposite();
				double d3 = 0.27D;
				d0 += d3 * (double) enumfacing.getXOffset();
				d1 += 0.22D;
				d2 += d3 * (double) enumfacing1.getZOffset();
			}

			worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}
}
