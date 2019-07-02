package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;
import com.grim3212.mc.pack.decor.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class BlockColorizerLampPost extends BlockColorizer {

	public BlockColorizerLampPost(String name, boolean isGlowing) {
		super(name, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.0f, 12f).lightValue(isGlowing ? 15 : 0));
	}

	private static final VoxelShape TOP = Block.makeCuboidShape(2F, 0.0F, 2F, 14F, 10.96F, 14F);
	private static final VoxelShape MIDDLE = Block.makeCuboidShape(6F, 0.0F, 6F, 10F, 16F, 10F);
	private static final VoxelShape BOTTOM = Block.makeCuboidShape(6F, 0.0F, 6F, 10F, 16F, 10F);

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.getBlock() == DecorBlocks.lamp_post_top) {
			return TOP;
		} else if (state.getBlock() == DecorBlocks.lamp_post_middle) {
			return MIDDLE;
		} else {
			return BOTTOM;
		}
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult hit) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		ItemStack heldItem = playerIn.getHeldItem(hand);

		if (!heldItem.isEmpty() && tileentity instanceof TileEntityColorizer) {
			if (heldItem.getItem() == DecorItems.brush) {
				if (this.tryUseBrush(worldIn, playerIn, hand, pos)) {
					return true;
				}
			}

			TileEntityColorizer te = (TileEntityColorizer) tileentity;
			Block block = Block.getBlockFromItem(heldItem.getItem());

			if (block != Blocks.AIR && !(block instanceof BlockColorizer)) {
				if (BlockHelper.getUsableBlocks().contains(block.getDefaultState())) {
					// Can only set blockstate if it contains nothing or if
					// in creative mode
					if (te.getStoredBlockState() == Blocks.AIR.getDefaultState() || playerIn.abilities.isCreativeMode) {
						BlockState toPlaceState = block.getStateForPlacement(new BlockItemUseContext(new ItemUseContext(playerIn, hand, hit)));
						this.setColorizer(worldIn, pos, state, toPlaceState, playerIn, hand, true);

						SoundType placeSound = toPlaceState.getSoundType(worldIn, pos, playerIn);

						worldIn.playSound(playerIn, pos, placeSound.getPlaceSound(), SoundCategory.BLOCKS, (placeSound.getVolume() + 1.0F) / 2.0F, placeSound.getPitch() * 0.8F);
						return true;
					} else if (te.getStoredBlockState() != Blocks.AIR.getDefaultState()) {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		return true;
	}

	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block == DecorBlocks.lamp_post_bottom) {
			worldIn.removeBlock(pos.up(), false);
			worldIn.removeBlock(pos.up(2), false);
		} else if (block == DecorBlocks.lamp_post_middle) {
			worldIn.removeBlock(pos.up(), false);
			worldIn.removeBlock(pos.down(), false);
		} else if (block == DecorBlocks.lamp_post_top) {
			worldIn.removeBlock(pos.down(), false);
			worldIn.removeBlock(pos.down(2), false);
		}

		super.onPlayerDestroy(worldIn, pos, state);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBlockHarvested(worldIn, pos, state, player);

		Block block = worldIn.getBlockState(pos).getBlock();
		if (block == DecorBlocks.lamp_post_bottom) {
			worldIn.removeBlock(pos.up(), false);
			worldIn.removeBlock(pos.up(2), false);
		} else if (block == DecorBlocks.lamp_post_middle) {
			worldIn.removeBlock(pos.up(), false);
			worldIn.removeBlock(pos.down(), false);
		} else if (block == DecorBlocks.lamp_post_top) {
			worldIn.removeBlock(pos.down(), false);
			worldIn.removeBlock(pos.down(2), false);
		}
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		ItemStack itemstack = new ItemStack(DecorItems.lamp_item);
		NBTHelper.setTagCompound(itemstack, "stored_state", NBTUtil.writeBlockState(Blocks.AIR.getDefaultState()));
		return itemstack;
	}

	@Override
	public boolean clearColorizer(World worldIn, BlockPos pos, BlockState state, PlayerEntity player, Hand hand) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityColorizer) {
			TileEntityColorizer tileColorizer = (TileEntityColorizer) te;
			BlockState storedState = tileColorizer.getStoredBlockState();

			// Can only clear a filled colorizer
			if (storedState != Blocks.AIR.getDefaultState()) {

				if (DecorConfig.consumeBlock.get() && !player.abilities.isCreativeMode) {
					ItemEntity blockDropped = new ItemEntity(worldIn, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), new ItemStack(tileColorizer.getStoredBlockState().getBlock(), 1));
					if (!worldIn.isRemote) {
						worldIn.addEntity(blockDropped);
						if (!(player instanceof FakePlayer)) {
							blockDropped.onCollideWithPlayer(player);
						}
					}
				}

				// Clear self
				if (super.setColorizer(worldIn, pos, state, null, player, hand, false)) {

					// Clear other lamp parts
					if (state.getBlock() == DecorBlocks.lamp_post_bottom) {
						super.setColorizer(worldIn, pos.up(), state, null, player, hand, false);
						super.setColorizer(worldIn, pos.up(2), state, null, player, hand, false);
					} else if (state.getBlock() == DecorBlocks.lamp_post_middle) {
						super.setColorizer(worldIn, pos.up(), state, null, player, hand, false);
						super.setColorizer(worldIn, pos.down(), state, null, player, hand, false);
					} else if (state.getBlock() == DecorBlocks.lamp_post_top) {
						super.setColorizer(worldIn, pos.down(), state, null, player, hand, false);
						super.setColorizer(worldIn, pos.down(2), state, null, player, hand, false);
					}

					SoundType placeSound = state.getSoundType(worldIn, pos, player);

					worldIn.playSound(player, pos, placeSound.getPlaceSound(), SoundCategory.BLOCKS, (placeSound.getVolume() + 1.0F) / 2.0F, placeSound.getPitch() * 0.8F);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean setColorizer(World worldIn, BlockPos pos, BlockState state, BlockState toSetState, PlayerEntity player, Hand hand, boolean consumeItem) {
		// Set self block
		if (super.setColorizer(worldIn, pos, state, toSetState, player, hand, consumeItem)) {

			Block self = state.getBlock();

			// Set other parts of lantern
			if (self == DecorBlocks.lamp_post_bottom) {
				super.setColorizer(worldIn, pos.up(), state, toSetState, player, hand, false);
				super.setColorizer(worldIn, pos.up(2), state, toSetState, player, hand, false);
			} else if (self == DecorBlocks.lamp_post_middle) {
				super.setColorizer(worldIn, pos.up(), state, toSetState, player, hand, false);
				super.setColorizer(worldIn, pos.down(), state, toSetState, player, hand, false);
			} else if (self == DecorBlocks.lamp_post_top) {
				super.setColorizer(worldIn, pos.down(), state, toSetState, player, hand, false);
				super.setColorizer(worldIn, pos.down(2), state, toSetState, player, hand, false);
			}
			return true;
		}
		return false;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.lamps_page;
	}
}
