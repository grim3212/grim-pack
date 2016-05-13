package com.grim3212.mc.pack.decor.block;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockFenceGate extends BlockFurnitureRotate {

	public static final PropertyBool OPEN = PropertyBool.create("open");
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockFenceGate() {
		this.setDefaultState(this.getDefaultState().withProperty(OPEN, false).withProperty(POWERED, false));
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid() ? super.canPlaceBlockAt(worldIn, pos) : false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		if ((Boolean) state.getValue(OPEN)) {
			return null;
		} else {
			EnumFacing.Axis axis = ((EnumFacing) state.getValue(FACING)).getAxis();
			return axis == EnumFacing.Axis.Z ? new AxisAlignedBB((double) pos.getX(), (double) pos.getY(), (double) ((float) pos.getZ() + 0.375F), (double) (pos.getX() + 1), (double) ((float) pos.getY() + 1.5F), (double) ((float) pos.getZ() + 0.625F)) : new AxisAlignedBB((double) ((float) pos.getX() + 0.375F), (double) pos.getY(), (double) pos.getZ(), (double) ((float) pos.getX() + 0.625F), (double) ((float) pos.getY() + 1.5F), (double) (pos.getZ() + 1));
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		EnumFacing.Axis axis = ((EnumFacing) worldIn.getBlockState(pos).getValue(FACING)).getAxis();

		if (axis == EnumFacing.Axis.Z) {
			this.setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
		} else {
			this.setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
		}
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return (Boolean) worldIn.getBlockState(pos).getValue(OPEN);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if ((Boolean) state.getValue(OPEN)) {
			state = state.withProperty(OPEN, false);
			worldIn.setBlockState(pos, state, 2);
		} else {
			EnumFacing enumfacing1 = EnumFacing.fromAngle((double) playerIn.rotationYaw);

			if (state.getValue(FACING) == enumfacing1.getOpposite()) {
				state = state.withProperty(FACING, enumfacing1);
			}

			state = state.withProperty(OPEN, true);
			worldIn.setBlockState(pos, state, 2);
		}

		worldIn.playAuxSFXAtEntity(playerIn, (Boolean) state.getValue(OPEN) ? 1003 : 1006, pos, 0);
		return true;
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!worldIn.isRemote) {
			boolean flag = worldIn.isBlockPowered(pos);

			if (flag || neighborBlock.canProvidePower()) {
				if (flag && !(Boolean) state.getValue(OPEN) && !(Boolean) state.getValue(POWERED)) {
					worldIn.setBlockState(pos, state.withProperty(POWERED, true).withProperty(OPEN, true), 2);
					worldIn.playAuxSFXAtEntity((EntityPlayer) null, 1003, pos, 0);
				} else if (!flag && (Boolean) state.getValue(OPEN) && (Boolean) state.getValue(POWERED)) {
					worldIn.setBlockState(pos, state.withProperty(POWERED, false).withProperty(OPEN, false), 2);
					worldIn.playAuxSFXAtEntity((EntityPlayer) null, 1006, pos, 0);
				} else if (flag != (Boolean) state.getValue(POWERED)) {
					worldIn.setBlockState(pos, state.withProperty(POWERED, flag), 2);
				}
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWERED, (meta & 8) != 0).withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(OPEN, (meta & 4) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();

		if ((Boolean) state.getValue(POWERED)) {
			i |= 8;
		}

		if ((Boolean) state.getValue(OPEN)) {
			i |= 4;
		}

		return i;
	}

	@Override
	protected BlockState createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { FACING, OPEN, POWERED }, new IUnlistedProperty[] { BLOCKID, BLOCKMETA });
	}
}
