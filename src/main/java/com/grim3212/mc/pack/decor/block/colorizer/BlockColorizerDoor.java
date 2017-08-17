package com.grim3212.mc.pack.decor.block.colorizer;

import java.util.Random;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;
import com.grim3212.mc.pack.decor.util.BlockHelper;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.event.DoubleDoor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class BlockColorizerDoor extends BlockColorizer {

	protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
	protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);

	public BlockColorizerDoor() {
		super("decor_door");
		this.setCreativeTab(null);
	}

	@Override
	protected IBlockState getState() {
		return this.blockState.getBaseState().withProperty(BlockDoor.FACING, EnumFacing.NORTH).withProperty(BlockDoor.OPEN, false).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.POWERED, false).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.doors_page;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = state.getActualState(source, pos);
		EnumFacing enumfacing = state.getValue(BlockDoor.FACING);
		boolean flag = !state.getValue(BlockDoor.OPEN);
		boolean flag1 = state.getValue(BlockDoor.HINGE) == BlockDoor.EnumHingePosition.RIGHT;

		switch (enumfacing) {
		case EAST:
		default:
			return flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
		case SOUTH:
			return flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
		case WEST:
			return flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
		case NORTH:
			return flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
		}
	}

	@Override
	public String getLocalizedName() {
		return I18n.translateToLocal((this.getUnlocalizedName() + ".name").replaceAll("tile", "item"));
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return isOpen(BlockDoor.combineMetadata(worldIn, pos));
	}

	private int getCloseSound() {
		return this.blockMaterial == Material.IRON ? 1011 : 1012;
	}

	private int getOpenSound() {
		return this.blockMaterial == Material.IRON ? 1005 : 1006;
	}

	public boolean changeDoors(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		ItemStack heldItem = playerIn.getHeldItem(hand);

		if (!heldItem.isEmpty() && tileentity instanceof TileEntityColorizer) {
			TileEntityColorizer te = (TileEntityColorizer) tileentity;
			Block block = Block.getBlockFromItem(heldItem.getItem());

			if (block != null && !(block instanceof BlockColorizer)) {
				if (BlockHelper.getUsableBlocks().contains(block)) {
					// Can only set blockstate if it contains nothing or if
					// in creative mode
					if (te.getBlockState() == Blocks.AIR.getDefaultState() || playerIn.capabilities.isCreativeMode) {

						IBlockState toPlaceState = block.getStateFromMeta(heldItem.getMetadata());
						this.setColorizer(worldIn, pos, state, toPlaceState, playerIn, hand, true);

						worldIn.playSound(playerIn, pos, block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (block.getSoundType().getVolume() + 1.0F) / 2.0F, block.getSoundType().getPitch() * 0.8F);
						return true;
					} else if (te.getBlockState() != Blocks.AIR.getDefaultState()) {
						return false;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = playerIn.getHeldItem(hand);

		if (!heldItem.isEmpty()) {
			if (heldItem.getItem() == DecorItems.brush) {
				if (this.tryUseBrush(worldIn, playerIn, hand, pos)) {
					return true;
				}
			}

			Block block = Block.getBlockFromItem(heldItem.getItem());
			if (block != Blocks.AIR) {
				if (changeDoors(worldIn, pos, state, playerIn, hand, side)) {
					return true;
				}
			}
		}

		BlockPos blockpos = state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
		IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);

		if (iblockstate.getBlock() != this) {
			return false;
		} else {
			state = iblockstate.cycleProperty(BlockDoor.OPEN);
			worldIn.setBlockState(blockpos, state, 10);
			worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
			worldIn.playEvent(playerIn, state.getValue(BlockDoor.OPEN) ? this.getOpenSound() : this.getCloseSound(), pos, 0);

			// Used to simplify double door logic that would need to be specific
			// for this
			if (UtilConfig.subpartDoubleDoors) {
				int coordX = 0;
				int coordZ = 0;
				int coordOffset = DoubleDoor.isHingeLeft(BlockDoor.combineMetadata(worldIn, blockpos)) ? -1 : 1;

				switch (state.getValue(BlockDoor.FACING)) {
				case SOUTH:
					coordX = blockpos.getX() - coordOffset;
					coordZ = blockpos.getZ();
					break;
				case WEST:
					coordX = blockpos.getX();
					coordZ = blockpos.getZ() - coordOffset;
					break;
				case NORTH:
					coordX = blockpos.getX() + coordOffset;
					coordZ = blockpos.getZ();
					break;
				case EAST:
					coordX = blockpos.getX();
					coordZ = blockpos.getZ() + coordOffset;
					break;
				default:
					break;
				}

				BlockPos neighborPos = new BlockPos(coordX, blockpos.getY(), coordZ);
				IBlockState neighborState = worldIn.getBlockState(neighborPos);

				// Make sure this is a valid door first and same state
				if (!(neighborState.getBlock() instanceof BlockColorizerDoor) || state.getValue(BlockDoor.OPEN) == BlockDoor.isOpen(worldIn, neighborPos)) {
					return true;
				}

				BlockPos neighborOtherPos = neighborState.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER ? neighborPos : neighborPos.down();

				// Open the neighbor state
				neighborState = neighborState.cycleProperty(BlockDoor.OPEN);
				worldIn.setBlockState(neighborPos, neighborState, 10);
				worldIn.markBlockRangeForRenderUpdate(neighborOtherPos, neighborPos);
				worldIn.playEvent(playerIn, state.getValue(BlockDoor.OPEN) ? this.getOpenSound() : this.getCloseSound(), neighborPos, 0);
			}

			return true;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.UPPER) {
			BlockPos blockpos = pos.down();
			IBlockState iblockstate = worldIn.getBlockState(blockpos);

			if (iblockstate.getBlock() != this) {
				worldIn.setBlockToAir(pos);
			} else if (blockIn != this) {
				iblockstate.neighborChanged(worldIn, blockpos, blockIn, fromPos);
			}
		} else {
			boolean flag1 = false;
			BlockPos blockpos1 = pos.up();
			IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);

			if (iblockstate1.getBlock() != this) {
				worldIn.setBlockToAir(pos);
				flag1 = true;
			}

			if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
				worldIn.setBlockToAir(pos);
				flag1 = true;

				if (iblockstate1.getBlock() == this) {
					worldIn.setBlockToAir(blockpos1);
				}
			}

			if (flag1) {
				if (!worldIn.isRemote) {
					this.dropBlockAsItem(worldIn, pos, state, 0);
				}
			} else {
				boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos1);

				if (blockIn != this && (flag || blockIn.getDefaultState().canProvidePower()) && flag != iblockstate1.getValue(BlockDoor.POWERED)) {
					worldIn.setBlockState(blockpos1, iblockstate1.withProperty(BlockDoor.POWERED, flag), 2);

					if (flag != state.getValue(BlockDoor.OPEN)) {
						worldIn.setBlockState(pos, state.withProperty(BlockDoor.OPEN, flag), 2);
						worldIn.markBlockRangeForRenderUpdate(pos, pos);
						worldIn.playEvent((EntityPlayer) null, flag ? this.getOpenSound() : this.getCloseSound(), pos, 0);
					}
				}
			}
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return pos.getY() >= worldIn.getHeight() - 1 ? false : worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up());
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}

	protected static boolean isOpen(int combinedMeta) {
		return (combinedMeta & 4) != 0;
	}

	protected static boolean isTop(int meta) {
		return (meta & 8) != 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { BlockDoor.HALF, BlockDoor.FACING, BlockDoor.OPEN, BlockDoor.HINGE, BlockDoor.POWERED }, new IUnlistedProperty[] { BLOCK_STATE });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER) {
			IBlockState iblockstate = worldIn.getBlockState(pos.up());

			if (iblockstate.getBlock() == this) {
				state = state.withProperty(BlockDoor.HINGE, iblockstate.getValue(BlockDoor.HINGE)).withProperty(BlockDoor.POWERED, iblockstate.getValue(BlockDoor.POWERED));
			}
		} else {
			IBlockState iblockstate1 = worldIn.getBlockState(pos.down());

			if (iblockstate1.getBlock() == this) {
				state = state.withProperty(BlockDoor.FACING, iblockstate1.getValue(BlockDoor.FACING)).withProperty(BlockDoor.OPEN, iblockstate1.getValue(BlockDoor.OPEN));
			}
		}

		return state;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.getValue(BlockDoor.HALF) != BlockDoor.EnumDoorHalf.LOWER ? state : state.withProperty(BlockDoor.FACING, rot.rotate(state.getValue(BlockDoor.FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return mirrorIn == Mirror.NONE ? state : state.withRotation(mirrorIn.toRotation(state.getValue(BlockDoor.FACING))).cycleProperty(BlockDoor.HINGE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return (meta & 8) > 0 ? this.getDefaultState().withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER).withProperty(BlockDoor.HINGE, (meta & 1) > 0 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.POWERED, (meta & 2) > 0) : this.getDefaultState().withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER).withProperty(BlockDoor.FACING, EnumFacing.getHorizontal(meta & 3).rotateYCCW()).withProperty(BlockDoor.OPEN, (meta & 4) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.UPPER) {
			i = i | 8;

			if (state.getValue(BlockDoor.HINGE) == BlockDoor.EnumHingePosition.RIGHT) {
				i |= 1;
			}

			if (state.getValue(BlockDoor.POWERED)) {
				i |= 2;
			}
		} else {
			i = i | (state.getValue(BlockDoor.FACING)).rotateY().getHorizontalIndex();

			if (state.getValue(BlockDoor.OPEN)) {
				i |= 4;
			}
		}

		return i;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(DecorItems.decor_door_item);
	}

	@Override
	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.UPPER ? null : DecorItems.decor_door_item;
	}

	@Override
	public boolean clearColorizer(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityColorizer) {
			TileEntityColorizer tileColorizer = (TileEntityColorizer) te;
			IBlockState storedState = tileColorizer.getBlockState();

			// Can only clear a filled colorizer
			if (storedState != Blocks.AIR.getDefaultState()) {
				if (DecorConfig.consumeBlock && !player.capabilities.isCreativeMode) {
					EntityItem blockDropped = new EntityItem(worldIn, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), new ItemStack(tileColorizer.getBlockState().getBlock(), 1, tileColorizer.getBlockState().getBlock().getMetaFromState(tileColorizer.getBlockState())));
					if (!worldIn.isRemote) {
						worldIn.spawnEntity(blockDropped);
						if (!(player instanceof FakePlayer)) {
							blockDropped.onCollideWithPlayer(player);
						}
					}
				}

				// Clear self
				if (super.setColorizer(worldIn, pos, state, null, player, hand, false)) {

					// Sound if cleared
					worldIn.playSound(player, pos, state.getBlock().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (state.getBlock().getSoundType().getVolume() + 1.0F) / 2.0F, state.getBlock().getSoundType().getPitch() * 0.8F);

					// Clear other half of door
					if (state.getValue(BlockDoor.HALF) == EnumDoorHalf.LOWER) {
						return super.setColorizer(worldIn, pos.up(), state, null, player, hand, false);
					} else if (state.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER) {
						return super.setColorizer(worldIn, pos.down(), state, null, player, hand, false);
					}
				}
			}
		}

		return false;
	}

	@Override
	public boolean setColorizer(World worldIn, BlockPos pos, IBlockState state, IBlockState toSetState, EntityPlayer player, EnumHand hand, boolean consumeItem) {
		// Set self
		if (super.setColorizer(worldIn, pos, state, toSetState, player, hand, consumeItem)) {

			// Set other half of door and consume once
			if (state.getValue(BlockDoor.HALF) == EnumDoorHalf.LOWER) {
				return super.setColorizer(worldIn, pos.up(), state, toSetState, player, hand, false);
			} else if (state.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER) {
				return super.setColorizer(worldIn, pos.down(), state, toSetState, player, hand, false);
			}
		}
		return false;
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		BlockPos blockpos = pos.down();
		BlockPos blockpos1 = pos.up();

		if (player.capabilities.isCreativeMode && state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.UPPER && worldIn.getBlockState(blockpos).getBlock() == this) {
			worldIn.setBlockToAir(blockpos);
		}

		if (state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER && worldIn.getBlockState(blockpos1).getBlock() == this) {
			if (player.capabilities.isCreativeMode) {
				worldIn.setBlockToAir(pos);
			}

			worldIn.setBlockToAir(blockpos1);
		}
	}
}
