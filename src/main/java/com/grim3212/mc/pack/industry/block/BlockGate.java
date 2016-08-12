package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGate extends BlockManual {

	private static final float WIDTH = 0.125F;
	private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(1.0F - 2.0F * WIDTH, 0.0F, 0.0F, 1.0F - WIDTH, 1.0F, 1.0F);
	private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0F, 0.0F, WIDTH, 1.0F, 1.0F, 2.0F * WIDTH);
	private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(WIDTH, 0.0F, 0.0F, 2.0F * WIDTH, 1.0F, 1.0F);
	private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0F, 0.0F, 1.0F - 2.0F * WIDTH, 1.0F, 1.0F, 1.0F - WIDTH);
	private static final AxisAlignedBB TOP_EAST_AABB = new AxisAlignedBB(1.0F - 2.0F * WIDTH, 0.44F, 0.0F, 1.0F - WIDTH, 1.0F, 1.0F);
	private static final AxisAlignedBB TOP_NORTH_AABB = new AxisAlignedBB(0.0F, 0.44F, WIDTH, 1.0F, 1.0F, 2.0F * WIDTH);
	private static final AxisAlignedBB TOP_WEST_AABB = new AxisAlignedBB(WIDTH, 0.44F, 0.0F, 2.0F * WIDTH, 1.0F, 1.0F);
	private static final AxisAlignedBB TOP_SOUTH_AABB = new AxisAlignedBB(0.0F, 0.44F, 1.0F - 2.0F * WIDTH, 1.0F, 1.0F, 1.0F - WIDTH);
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyBool TOP = PropertyBool.create("top");

	protected BlockGate() {
		super(Material.IRON, SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH).withProperty(ACTIVE, false).withProperty(TOP, false));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 7)).withProperty(ACTIVE, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | state.getValue(FACING).getHorizontalIndex();

		if (state.getValue(ACTIVE)) {
			i |= 8;
		}

		return i;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if ((worldIn.getBlockState(pos.up()).getBlock() != this && worldIn.getBlockState(pos.up()).getBlock() != Blocks.AIR) && worldIn.getBlockState(pos.down()).getBlock() == this) {
			return state.withProperty(TOP, true);
		} else {
			return state.withProperty(TOP, false);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, ACTIVE, TOP });
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	@SuppressWarnings("incomplete-switch")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (state.getBlock() == this) {
			if (state.getValue(ACTIVE)) {
				EnumFacing facing = state.getValue(FACING);

				if (getActualState(state, source, pos).getValue(TOP)) {
					switch (facing) {
					case NORTH:
						return TOP_NORTH_AABB;
					case SOUTH:
						return TOP_SOUTH_AABB;
					case WEST:
						return TOP_WEST_AABB;
					case EAST:
						return TOP_EAST_AABB;
					}
				}
			} else {
				EnumFacing facing = state.getValue(FACING);

				switch (facing) {
				case NORTH:
					return NORTH_AABB;
				case SOUTH:
					return SOUTH_AABB;
				case WEST:
					return WEST_AABB;
				case EAST:
					return EAST_AABB;
				}
			}
		}

		return Utils.NULL_AABB;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		for (int j = pos.getY(); worldIn.getBlockState(pos.down()).getBlock() == Blocks.AIR; --j) {
			pos = new BlockPos(pos.getX(), j - 1, pos.getZ());
			worldIn.setBlockState(pos, getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVE, false), 2);
		}

		return getActualState(getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVE, false), worldIn, pos);
	}

	public void updateNeighbors(World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean active = (Boolean) worldIn.getBlockState(pos).getValue(ACTIVE);

		if ((worldIn.getBlockState(pos.west()).getBlock() == this && worldIn.getBlockState(pos.west()).getValue(FACING) == worldIn.getBlockState(pos).getValue(FACING)) && (((Boolean) worldIn.getBlockState(pos.west()).getValue(ACTIVE)) != active)) {
			this.onBlockActivated(worldIn, pos.west(), worldIn.getBlockState(pos.west()), player, hand, heldItem, side, hitX, hitY, hitZ);
		}

		if ((worldIn.getBlockState(pos.east()).getBlock() == this && worldIn.getBlockState(pos.east()).getValue(FACING) == worldIn.getBlockState(pos).getValue(FACING)) && (((Boolean) worldIn.getBlockState(pos.east()).getValue(ACTIVE)) != active)) {
			this.onBlockActivated(worldIn, pos.east(), worldIn.getBlockState(pos.east()), player, hand, heldItem, side, hitX, hitY, hitZ);
		}

		if ((worldIn.getBlockState(pos.north()).getBlock() == this && worldIn.getBlockState(pos.north()).getValue(FACING) == worldIn.getBlockState(pos).getValue(FACING)) && (((Boolean) worldIn.getBlockState(pos.north()).getValue(ACTIVE)) != active)) {
			this.onBlockActivated(worldIn, pos.north(), worldIn.getBlockState(pos.north()), player, hand, heldItem, side, hitX, hitY, hitZ);
		}

		if ((worldIn.getBlockState(pos.south()).getBlock() == this && worldIn.getBlockState(pos.south()).getValue(FACING) == worldIn.getBlockState(pos).getValue(FACING)) && (((Boolean) worldIn.getBlockState(pos.south()).getValue(ACTIVE)) != active)) {
			this.onBlockActivated(worldIn, pos.south(), worldIn.getBlockState(pos.south()), player, hand, heldItem, side, hitX, hitY, hitZ);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		Item activator = null;
		if (state.getBlock() == IndustryBlocks.garage) {
			activator = IndustryItems.garage_remote;
		} else if (state.getBlock() == IndustryBlocks.castle_gate) {
			activator = IndustryItems.gate_trumpet;
		}

		if (heldItem == null || heldItem.getItem() != activator) {
			return false;
		} else {
			if (worldIn.isRemote) {
				return true;
			} else {
				if (getActualState(state, worldIn, pos).getValue(TOP)) {
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

				this.updateNeighbors(worldIn, pos, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
				worldIn.playEvent((EntityPlayer) null, 1006, pos, 0);
				return true;
			}
		}
	}

	public void openGate(World worldIn, BlockPos pos, boolean flag) {
		if (!getActualState(worldIn.getBlockState(pos), worldIn, pos).getValue(TOP)) {
			if (worldIn.getBlockState(pos.up()).getBlock() == this) {
				this.openGate(worldIn, pos.up(), flag);
			}
		} else {
			boolean active = worldIn.getBlockState(pos).getValue(ACTIVE);

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

				worldIn.playEvent((EntityPlayer) null, 1006, pos, 0);
			}
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		boolean flag = false;

		if (!getActualState(state, worldIn, pos).getValue(TOP)) {
			if (worldIn.getBlockState(pos.up()).getBlock() != this) {
				worldIn.setBlockToAir(pos);
			}

			if (blockIn != Blocks.AIR && blockIn.getDefaultState().canProvidePower()) {
				flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.down());
				this.openGate(worldIn, pos, flag);
			}

			return;
		}

		if (!worldIn.getBlockState(pos.up()).isOpaqueCube()) {
			worldIn.setBlockToAir(pos);
			flag = true;

			for (int var8 = pos.getY(); worldIn.getBlockState(new BlockPos(pos.getX(), var8 - 1, pos.getZ())).getBlock() == this; --var8) {
				worldIn.setBlockToAir(new BlockPos(pos.getX(), var8 - 1, pos.getZ()));
			}
		}

		if (flag && !worldIn.isRemote) {
			this.dropBlockAsItem(worldIn, pos, this.getDefaultState(), 0);
		}

		if (blockIn != Blocks.AIR && blockIn.getDefaultState().canProvidePower()) {
			flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.down());
			this.openGate(worldIn, pos, flag);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return pos.getY() >= 127 ? false : worldIn.getBlockState(pos.up()).isOpaqueCube() && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.down());
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
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}

	@Override
	public Page getPage(IBlockState state) {
		if (state.getBlock() == IndustryBlocks.garage)
			return ManualIndustry.garage_page;

		return ManualIndustry.gate_page;
	}
}
