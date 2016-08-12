package com.grim3212.mc.pack.decor.block;

import java.util.Random;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.item.ItemBrush;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;
import com.grim3212.mc.pack.decor.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLampPost extends BlockColorizer implements IManualBlock {

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
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (heldItem != null && heldItem.getItem() != null && tileentity instanceof TileEntityColorizer) {
			TileEntityColorizer te = (TileEntityColorizer) tileentity;
			Block block = Block.getBlockFromItem(heldItem.getItem());

			if (block != null && !(block instanceof BlockColorizer)) {
				if (BlockHelper.getUsableBlocks().contains(block)) {
					// Can only set blockstate if it contains nothing or if
					// in creative mode
					if (te.getBlockState() == Blocks.AIR.getDefaultState() || playerIn.capabilities.isCreativeMode) {
						if (!playerIn.capabilities.isCreativeMode)
							--heldItem.stackSize;

						// Set self data
						Block self = state.getBlock();
						IBlockState toPlaceState = block.getStateFromMeta(heldItem.getMetadata());
						ItemBrush.setTileEntityData(worldIn, pos, state, toPlaceState);

						if (self == DecorBlocks.lamp_post_bottom) {
							ItemBrush.setTileEntityData(worldIn, pos.up(), state, toPlaceState);
							ItemBrush.setTileEntityData(worldIn, pos.up(2), state, toPlaceState);
						} else if (self == DecorBlocks.lamp_post_middle) {
							ItemBrush.setTileEntityData(worldIn, pos.up(), state, toPlaceState);
							ItemBrush.setTileEntityData(worldIn, pos.down(), state, toPlaceState);
						} else if (self == DecorBlocks.lamp_post_top) {
							ItemBrush.setTileEntityData(worldIn, pos.down(), state, toPlaceState);
							ItemBrush.setTileEntityData(worldIn, pos.down(2), state, toPlaceState);
						}

						worldIn.playSound(playerIn, pos, block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (block.getSoundType().getVolume() + 1.0F) / 2.0F, block.getSoundType().getPitch() * 0.8F);
						return true;
					} else if (te.getBlockState() != Blocks.AIR.getDefaultState()) {
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
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		if (te instanceof TileEntityColorizer) {
			player.addStat(StatList.getBlockStats(this));
			player.addExhaustion(0.025F);

			harvesters.set(player);
			ItemStack itemstack = new ItemStack(DecorItems.lamp_item);
			NBTHelper.setString(itemstack, "registryName", Block.REGISTRY.getNameForObject(Blocks.AIR).toString());
			NBTHelper.setInteger(itemstack, "meta", 0);
			spawnAsEntity(worldIn, pos, itemstack);
			harvesters.set(null);
		} else {
			super.harvestBlock(worldIn, player, pos, state, (TileEntity) null, stack);
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

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(worldIn, pos, state, player);

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
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack itemstack = new ItemStack(DecorItems.lamp_item);
		NBTHelper.setString(itemstack, "registryName", Block.REGISTRY.getNameForObject(Blocks.AIR).toString());
		NBTHelper.setInteger(itemstack, "meta", 0);
		return itemstack;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.lamps_page;
	}
}
