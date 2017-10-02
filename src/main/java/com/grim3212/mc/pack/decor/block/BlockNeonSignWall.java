package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.GrimPack;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNeonSignWall extends BlockNeonSign {

	protected static final AxisAlignedBB SIGN_EAST_AABB = new AxisAlignedBB(0.0D, 0.28125D, 0.0D, 0.125D, 0.78125D, 1.0D);
	protected static final AxisAlignedBB SIGN_WEST_AABB = new AxisAlignedBB(0.875D, 0.28125D, 0.0D, 1.0D, 0.78125D, 1.0D);
	protected static final AxisAlignedBB SIGN_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.28125D, 0.0D, 1.0D, 0.78125D, 0.125D);
	protected static final AxisAlignedBB SIGN_NORTH_AABB = new AxisAlignedBB(0.0D, 0.28125D, 0.875D, 1.0D, 0.78125D, 1.0D);

	public BlockNeonSignWall() {
		setSoundType(SoundType.WOOD);
		setHardness(1.0F);
		disableStats();
		setUnlocalizedName("neon_sign_wall");
		setDefaultState(this.blockState.getBaseState().withProperty(BlockWallSign.FACING, EnumFacing.NORTH));
		setRegistryName(new ResourceLocation(GrimPack.modID, "neon_sign_wall"));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch ((EnumFacing) state.getValue(BlockWallSign.FACING)) {
		case NORTH:
		default:
			return SIGN_NORTH_AABB;
		case SOUTH:
			return SIGN_SOUTH_AABB;
		case WEST:
			return SIGN_WEST_AABB;
		case EAST:
			return SIGN_EAST_AABB;
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		EnumFacing enumfacing = state.getValue(BlockWallSign.FACING);

		if (!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getMaterial().isSolid()) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}

		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(BlockWallSign.FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockWallSign.FACING).getIndex();
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(BlockWallSign.FACING, rot.rotate(state.getValue(BlockWallSign.FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(BlockWallSign.FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { BlockWallSign.FACING });
	}

}
