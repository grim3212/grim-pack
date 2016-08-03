package com.grim3212.mc.pack.decor.block;

import java.util.Random;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityTextured;
import com.grim3212.mc.pack.decor.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLampPost extends BlockTextured {

	public BlockLampPost(boolean isGlowing) {
		this.setHardness(1.0F);
		this.setCreativeTab(null);

		if (isGlowing) {
			this.setLightLevel(1.0F);
		}
	}

	private static final AxisAlignedBB TOP_AABB = new AxisAlignedBB(0.125F, 0.0F, 0.125F, 0.875F, 0.685F, 0.875F);
	private static final AxisAlignedBB MIDDLE_AABB = new AxisAlignedBB(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
	private static final AxisAlignedBB BOTTOM_AABB = new AxisAlignedBB(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (state.getBlock() == DecorBlocks.lamp_post_top) {
			return TOP_AABB;
		} else if (state.getBlock() == DecorBlocks.lamp_post_middle) {
			return MIDDLE_AABB;
		} else if (state.getBlock() == DecorBlocks.lamp_post_bottom) {
			return BOTTOM_AABB;
		}

		return FULL_BLOCK_AABB;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return DecorItems.lamp_item;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (heldItem != null && heldItem.getItem() != null) {
			Block block = Block.getBlockFromItem(heldItem.getItem());
			if (block != null) {
				if (BlockHelper.getBlocks().containsKey(block)) {
					TileEntity tileentity = worldIn.getTileEntity(pos);
					if (tileentity instanceof TileEntityTextured) {
						setTileEntityData(worldIn, pos, state, block, heldItem);

						if (state.getBlock() == DecorBlocks.lamp_post_top) {
							setTileEntityData(worldIn, pos.down(), DecorBlocks.lamp_post_middle.getDefaultState(), block, heldItem);
							setTileEntityData(worldIn, pos.down(2), DecorBlocks.lamp_post_bottom.getDefaultState(), block, heldItem);
							if (!worldIn.isRemote) {
								worldIn.markBlockRangeForRenderUpdate(pos, pos.down(2));
							}
						} else if (state.getBlock() == DecorBlocks.lamp_post_middle) {
							setTileEntityData(worldIn, pos.down(), DecorBlocks.lamp_post_bottom.getDefaultState(), block, heldItem);
							setTileEntityData(worldIn, pos.up(), DecorBlocks.lamp_post_top.getDefaultState(), block, heldItem);
							if (!worldIn.isRemote) {
								worldIn.markBlockRangeForRenderUpdate(pos.up(), pos.down());
							}
						} else if (state.getBlock() == DecorBlocks.lamp_post_bottom) {
							setTileEntityData(worldIn, pos.up(2), DecorBlocks.lamp_post_top.getDefaultState(), block, heldItem);
							setTileEntityData(worldIn, pos.up(), DecorBlocks.lamp_post_middle.getDefaultState(), block, heldItem);
							if (!worldIn.isRemote) {
								worldIn.markBlockRangeForRenderUpdate(pos, pos.up(2));
							}
						}

						worldIn.playSound(playerIn, pos, block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (block.getSoundType().getVolume() + 1.0F) / 2.0F, block.getSoundType().getPitch() * 0.8F);
						return true;
					}
				}
			}
		} else {
			if (playerIn.isSneaking()) {
				TileEntity tileentity = worldIn.getTileEntity(pos);
				if (tileentity instanceof TileEntityTextured) {
					setTileEntityData(worldIn, pos, state, null, null);

					if (state.getBlock() == DecorBlocks.lamp_post_top) {
						setTileEntityData(worldIn, pos.down(), DecorBlocks.lamp_post_middle.getDefaultState(), null, null);
						setTileEntityData(worldIn, pos.down(2), DecorBlocks.lamp_post_bottom.getDefaultState(), null, null);
						if (!worldIn.isRemote) {
							worldIn.markBlockRangeForRenderUpdate(pos, pos.down(2));
						}
					} else if (state.getBlock() == DecorBlocks.lamp_post_middle) {
						setTileEntityData(worldIn, pos.down(), DecorBlocks.lamp_post_bottom.getDefaultState(), null, null);
						setTileEntityData(worldIn, pos.up(), DecorBlocks.lamp_post_top.getDefaultState(), null, null);
						if (!worldIn.isRemote) {
							worldIn.markBlockRangeForRenderUpdate(pos.up(), pos.down());
						}
					} else if (state.getBlock() == DecorBlocks.lamp_post_bottom) {
						setTileEntityData(worldIn, pos.up(2), DecorBlocks.lamp_post_top.getDefaultState(), null, null);
						setTileEntityData(worldIn, pos.up(), DecorBlocks.lamp_post_middle.getDefaultState(), null, null);
						if (!worldIn.isRemote) {
							worldIn.markBlockRangeForRenderUpdate(pos, pos.up(2));
						}
					}

					worldIn.playSound(playerIn, pos, this.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (this.getSoundType().getVolume() + 1.0F) / 2.0F, this.getSoundType().getPitch() * 0.8F);
					return true;
				}
			}
		}

		return false;
	}

	private void setTileEntityData(World worldIn, BlockPos pos, IBlockState state, Block block, ItemStack stack) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityTextured) {
			((TileEntityTextured) te).setBlockID(block != null ? Block.getIdFromBlock(block) : 0);
			((TileEntityTextured) te).setBlockMeta(stack != null ? stack.getMetadata() : 0);
			worldIn.setBlockState(pos, this.getExtendedState(state, worldIn, pos));
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block == DecorBlocks.lamp_post_bottom) {
			worldIn.setBlockToAir(pos.up());
			worldIn.setBlockToAir(pos.up(2));
		} else if (block == DecorBlocks.lamp_post_middle) {
			worldIn.setBlockToAir(pos.up());
			worldIn.setBlockToAir(pos.down());
		} else if (block == DecorBlocks.lamp_post_top) {
			worldIn.setBlockToAir(pos.down());
			worldIn.setBlockToAir(pos.down(2));
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block == DecorBlocks.lamp_post_bottom) {
			worldIn.setBlockToAir(pos.up());
			worldIn.setBlockToAir(pos.up(2));
		} else if (block == DecorBlocks.lamp_post_middle) {
			worldIn.setBlockToAir(pos.up());
			worldIn.setBlockToAir(pos.down());
		} else if (block == DecorBlocks.lamp_post_top) {
			worldIn.setBlockToAir(pos.down());
			worldIn.setBlockToAir(pos.down(2));
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block == DecorBlocks.lamp_post_bottom) {
			worldIn.setBlockToAir(pos.up());
			worldIn.setBlockToAir(pos.up(2));
		} else if (block == DecorBlocks.lamp_post_middle) {
			worldIn.setBlockToAir(pos.up());
			worldIn.setBlockToAir(pos.down());
		} else if (block == DecorBlocks.lamp_post_top) {
			worldIn.setBlockToAir(pos.down());
			worldIn.setBlockToAir(pos.down(2));
		}
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(DecorItems.lamp_item);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack itemstack = new ItemStack(DecorItems.lamp_item, 1);
		NBTHelper.setInteger(itemstack, "blockID", 0);
		NBTHelper.setInteger(itemstack, "blockMeta", 0);
		return itemstack;
	}
}
