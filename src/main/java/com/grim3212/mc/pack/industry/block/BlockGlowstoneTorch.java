package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGlowstoneTorch extends BlockTorch implements IManualBlock {

	public BlockGlowstoneTorch() {
		setUnlocalizedName("glowstone_torch");
		setDefaultState(getState());
		setRegistryName(new ResourceLocation(GrimPack.modID, "glowstone_torch"));
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	protected IBlockState getState() {
		return this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP).withProperty(BlockFlipFlopTorch.ON, false);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.glowstoneTorch_page;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			if (state.getValue(BlockFlipFlopTorch.ON) && !worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, state.withProperty(BlockFlipFlopTorch.ON, false), 2);
			} else if (!state.getValue(BlockFlipFlopTorch.ON) && worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, state.withProperty(BlockFlipFlopTorch.ON, true), 2);
			}
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.onNeighborChangeInternal(worldIn, pos, state)) {
			if (!worldIn.isRemote) {
				if (state.getValue(BlockFlipFlopTorch.ON) && !worldIn.isBlockPowered(pos)) {
					worldIn.scheduleUpdate(pos, this, 4);
				} else if (!state.getValue(BlockFlipFlopTorch.ON) && worldIn.isBlockPowered(pos)) {
					worldIn.setBlockState(pos, state.withProperty(BlockFlipFlopTorch.ON, true), 2);
				}
			}
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			if (state.getValue(BlockFlipFlopTorch.ON) && !worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, state.withProperty(BlockFlipFlopTorch.ON, false), 2);
			}
		}
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(BlockFlipFlopTorch.ON) ? 15 : 0;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.getValue(BlockFlipFlopTorch.ON)) {
			double d0 = (double) pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
			double d1 = (double) pos.getY() + 0.7D + (rand.nextDouble() - 0.5D) * 0.2D;
			double d2 = (double) pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
			EnumFacing enumfacing = stateIn.getValue(FACING);

			if (enumfacing.getAxis().isHorizontal()) {
				EnumFacing enumfacing1 = enumfacing.getOpposite();
				double d3 = 0.27D;
				d0 += d3 * (double) enumfacing1.getFrontOffsetX();
				d1 += 0.22D;
				d2 += d3 * (double) enumfacing1.getFrontOffsetZ();
			}

			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, BlockFlipFlopTorch.getFacing(meta)).withProperty(BlockFlipFlopTorch.ON, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | state.getValue(FACING).getIndex();

		if (state.getValue(BlockFlipFlopTorch.ON)) {
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, BlockFlipFlopTorch.ON });
	}
}
