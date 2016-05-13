package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGate extends Block {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyBool TOP = PropertyBool.create("top");

	protected BlockGate() {
		super(Material.iron);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH).withProperty(ACTIVE, false).withProperty(TOP, false));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 7)).withProperty(ACTIVE, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();

		if (((Boolean) state.getValue(ACTIVE))) {
			i |= 8;
		}

		return i;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if ((worldIn.getBlockState(pos.up()).getBlock() != this && worldIn.getBlockState(pos.up()).getBlock() != Blocks.air) && worldIn.getBlockState(pos.down()).getBlock() == this) {
			return state.withProperty(TOP, true);
		} else {
			return state.withProperty(TOP, false);
		}
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING, ACTIVE, TOP });
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		float var6 = 0.125F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		if (state.getBlock() == this) {
			if ((Boolean) state.getValue(ACTIVE)) {
				EnumFacing facing = (EnumFacing) state.getValue(FACING);

				if ((Boolean) getActualState(state, worldIn, pos).getValue(TOP)) {
					switch (facing) {
					case NORTH:
						this.setBlockBounds(0.0F, 0.44F, var6, 1.0F, 1.0F, 2.0F * var6);
						break;
					case SOUTH:
						this.setBlockBounds(0.0F, 0.44F, 1.0F - 2.0F * var6, 1.0F, 1.0F, 1.0F - var6);
						break;
					case WEST:
						this.setBlockBounds(var6, 0.44F, 0.0F, 2.0F * var6, 1.0F, 1.0F);
						break;
					case EAST:
						this.setBlockBounds(1.0F - 2.0F * var6, 0.44F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
						break;
					default:
						this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
						break;
					}
				} else {
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
				}
			} else {
				EnumFacing facing = (EnumFacing) state.getValue(FACING);

				switch (facing) {
				case NORTH:
					this.setBlockBounds(0.0F, 0.0F, var6, 1.0F, 1.0F, 2.0F * var6);
					break;
				case SOUTH:
					this.setBlockBounds(0.0F, 0.0F, 1.0F - 2.0F * var6, 1.0F, 1.0F, 1.0F - var6);
					break;
				case WEST:
					this.setBlockBounds(var6, 0.0F, 0.0F, 2.0F * var6, 1.0F, 1.0F);
					break;
				case EAST:
					this.setBlockBounds(1.0F - 2.0F * var6, 0.0F, 0.0F, 1.0F - var6, 1.0F, 1.0F);
					break;
				default:
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;
				}
			}
		}
	}

	@Override
	public void setBlockBoundsForItemRender() {
		float var1 = 0.125F;
		this.setBlockBounds(var1, 0.0F, 0.0F, 2.0F * var1, 1.0F, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		boolean active = (Boolean) state.getValue(ACTIVE);
		if (active) {
			return null;
		} else {
			this.setBlockBoundsBasedOnState(worldIn, pos);
			return super.getCollisionBoundingBox(worldIn, pos, state);
		}
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		for (int j = pos.getY(); worldIn.getBlockState(pos.down()).getBlock() == Blocks.air; --j) {
			pos = new BlockPos(pos.getX(), j - 1, pos.getZ());
			worldIn.setBlockState(pos, getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVE, false), 2);
		}

		return getActualState(getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVE, false), worldIn, pos);
	}

	public void updateNeighbors(World worldIn, BlockPos pos, EntityPlayer player, EnumFacing side, float f1, float f2, float f3) {
		boolean active = (Boolean) worldIn.getBlockState(pos).getValue(ACTIVE);

		if ((worldIn.getBlockState(pos.west()).getBlock() == this && worldIn.getBlockState(pos.west()).getValue(FACING) == worldIn.getBlockState(pos).getValue(FACING)) && (((Boolean) worldIn.getBlockState(pos.west()).getValue(ACTIVE)) != active)) {
			this.onBlockActivated(worldIn, pos.west(), worldIn.getBlockState(pos.west()), player, side, f1, f2, f3);
		}

		if ((worldIn.getBlockState(pos.east()).getBlock() == this && worldIn.getBlockState(pos.east()).getValue(FACING) == worldIn.getBlockState(pos).getValue(FACING)) && (((Boolean) worldIn.getBlockState(pos.east()).getValue(ACTIVE)) != active)) {
			this.onBlockActivated(worldIn, pos.east(), worldIn.getBlockState(pos.east()), player, side, f1, f2, f3);
		}

		if ((worldIn.getBlockState(pos.north()).getBlock() == this && worldIn.getBlockState(pos.north()).getValue(FACING) == worldIn.getBlockState(pos).getValue(FACING)) && (((Boolean) worldIn.getBlockState(pos.north()).getValue(ACTIVE)) != active)) {
			this.onBlockActivated(worldIn, pos.north(), worldIn.getBlockState(pos.north()), player, side, f1, f2, f3);
		}

		if ((worldIn.getBlockState(pos.south()).getBlock() == this && worldIn.getBlockState(pos.south()).getValue(FACING) == worldIn.getBlockState(pos).getValue(FACING)) && (((Boolean) worldIn.getBlockState(pos.south()).getValue(ACTIVE)) != active)) {
			this.onBlockActivated(worldIn, pos.south(), worldIn.getBlockState(pos.south()), player, side, f1, f2, f3);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {

		Item activator = null;
		if (state.getBlock() == IndustryBlocks.garage) {
			activator = IndustryItems.garage_remote;
		} else if (state.getBlock() == IndustryBlocks.castle_gate) {
			activator = IndustryItems.gate_trumpet;
		}

		if (playerIn.getHeldItem() == null || playerIn.getHeldItem().getItem() != activator) {
			return false;
		} else {
			if (worldIn.isRemote) {
				return true;
			} else {
				if ((Boolean) getActualState(state, worldIn, pos).getValue(TOP)) {
					// Update top gate block
					worldIn.setBlockState(pos, state.cycleProperty(ACTIVE), 2);

					BlockPos newPos = pos;
					// Handle gate blocks only below since this is the top
					for (int var7 = pos.getY(); worldIn.getBlockState(new BlockPos(newPos.getX(), var7 - 1, newPos.getZ())).getBlock() == this; --var7) {
						newPos = new BlockPos(newPos.getX(), var7 - 1, newPos.getZ());
						worldIn.setBlockState(newPos, state.cycleProperty(ACTIVE), 2);
					}
				} else {
					BlockPos newPos = pos;
					// Handle gate blocks below position
					for (int var7 = pos.getY(); worldIn.getBlockState(new BlockPos(newPos.getX(), var7 - 1, newPos.getZ())).getBlock() == this; --var7) {
						newPos = new BlockPos(newPos.getX(), var7 - 1, newPos.getZ());
						worldIn.setBlockState(newPos, state.cycleProperty(ACTIVE), 2);
					}

					// Handle gate blocks above position
					for (int var7 = pos.getY(); worldIn.getBlockState(new BlockPos(newPos.getX(), var7 + 1, newPos.getZ())).getBlock() == this; ++var7) {
						newPos = new BlockPos(newPos.getX(), var7 + 1, newPos.getZ());
						worldIn.setBlockState(newPos, state.cycleProperty(ACTIVE), 2);
					}
					// Handle selected gate block
					worldIn.setBlockState(pos, state.cycleProperty(ACTIVE), 2);

				}

				this.updateNeighbors(worldIn, pos, playerIn, side, hitX, hitY, hitZ);
				worldIn.playAuxSFXAtEntity((EntityPlayer) null, 1003, pos, 0);
				return true;
			}
		}
	}

	public void openGate(World worldIn, BlockPos pos, boolean flag) {
		if (!(Boolean) getActualState(worldIn.getBlockState(pos), worldIn, pos).getValue(TOP)) {
			if (worldIn.getBlockState(pos.up()).getBlock() == this) {
				this.openGate(worldIn, pos.up(), flag);
			}
		} else {
			boolean active = (Boolean) worldIn.getBlockState(pos).getValue(ACTIVE);

			if (active != flag) {
				BlockPos newPos = pos;
				for (int var8 = pos.getY(); worldIn.getBlockState(new BlockPos(newPos.getX(), var8 - 1, newPos.getZ())).getBlock() == this; --var8) {
					newPos = new BlockPos(pos.getX(), var8 - 1, pos.getZ());
					worldIn.setBlockState(newPos, worldIn.getBlockState(newPos).cycleProperty(ACTIVE), 2);
				}

				worldIn.setBlockState(pos, worldIn.getBlockState(pos).cycleProperty(ACTIVE), 2);

				if (worldIn.getBlockState(pos.east()).getBlock() == this) {
					this.openGate(worldIn, pos.east(), flag);
				}

				if (worldIn.getBlockState(pos.west()).getBlock() == this) {
					this.openGate(worldIn, pos.west(), flag);
				}

				if (worldIn.getBlockState(pos.north()).getBlock() == this) {
					this.openGate(worldIn, pos.north(), flag);
				}

				if (worldIn.getBlockState(pos.south()).getBlock() == this) {
					this.openGate(worldIn, pos.south(), flag);
				}

				worldIn.playAuxSFXAtEntity((EntityPlayer) null, 1003, pos, 0);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		boolean flag = false;

		if (!(Boolean) getActualState(state, worldIn, pos).getValue(TOP)) {
			if (worldIn.getBlockState(pos.up()).getBlock() != this) {
				worldIn.setBlockToAir(pos);
			}

			if (neighborBlock != Blocks.air && neighborBlock.canProvidePower()) {
				flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.down());
				this.openGate(worldIn, pos, flag);
			}

			return;
		}

		if (!worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube()) {
			worldIn.setBlockToAir(pos);
			flag = true;

			for (int var8 = pos.getY(); worldIn.getBlockState(new BlockPos(pos.getX(), var8 - 1, pos.getZ())).getBlock() == this; --var8) {
				worldIn.setBlockToAir(new BlockPos(pos.getX(), var8 - 1, pos.getZ()));
			}
		}

		if (flag && !worldIn.isRemote) {
			this.dropBlockAsItem(worldIn, pos, this.getDefaultState(), 0);
		}

		if (neighborBlock != Blocks.air && neighborBlock.canProvidePower()) {
			flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.down());
			this.openGate(worldIn, pos, flag);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return pos.getY() >= 127 ? false : worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube() && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.down());
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		this.removeGate(worldIn, pos);
		if (!worldIn.isRemote)
			this.dropBlockAsItemWithChance(worldIn, pos, worldIn.getBlockState(pos), 0.5F, 0);
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		this.removeGate(worldIn, pos);
	}

	public void removeGate(World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos.up()).getBlock() == this) {
			this.removeGate(worldIn, pos.up());
			worldIn.setBlockToAir(pos);
		} else {
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public int getMobilityFlag() {
		return 1;
	}
}
