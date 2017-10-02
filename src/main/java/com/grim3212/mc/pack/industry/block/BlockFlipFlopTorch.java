package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntityFlipFlopTorch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFlipFlopTorch extends BlockTorch implements IManualBlock, ITileEntityProvider {

	public static final PropertyBool ON = PropertyBool.create("on");

	public BlockFlipFlopTorch() {
		setUnlocalizedName("flip_flop_torch");
		setDefaultState(getState());
		setRegistryName(new ResourceLocation(GrimPack.modID, "flip_flop_torch"));
		setTickRandomly(true);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	protected IBlockState getState() {
		return this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP).withProperty(ON, false);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFlipFlopTorch();
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.flipFlopTorch_page;
	}

	@Override
	public int tickRate(World worldIn) {
		return 1;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (state.getValue(ON)) {
			for (EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}
		}
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(ON) && blockState.getValue(FACING) != side ? 15 : 0;
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
	}

	private boolean checkPower(World world, BlockPos pos, IBlockState state) {
		EnumFacing enumfacing = state.getValue(FACING).getOpposite();
		return world.isSidePowered(pos.offset(enumfacing), enumfacing);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityFlipFlopTorch) {
			TileEntityFlipFlopTorch flipTE = (TileEntityFlipFlopTorch) te;

			boolean flag = checkPower(worldIn, pos, state);
			boolean prevFlag = flipTE.getPrevState();
			flipTE.setPrevState(flag);

			if (flag != prevFlag) {
				if (flag) {
					if (state.getValue(ON)) {
						worldIn.setBlockState(pos, state.withProperty(ON, false), 3);
					} else {
						worldIn.setBlockState(pos, state.withProperty(ON, true), 3);
					}
				}
			}
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return side == EnumFacing.DOWN ? blockState.getWeakPower(blockAccess, pos, side) : 0;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.getValue(ON)) {
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

	public static EnumFacing getFacing(int meta) {
		EnumFacing facing = EnumFacing.getFront(meta & 7);

		// There is no down state
		if (facing == EnumFacing.DOWN)
			facing = EnumFacing.UP;

		return facing;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(ON, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | state.getValue(FACING).getIndex();

		if (state.getValue(ON)) {
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, ON });
	}
}
