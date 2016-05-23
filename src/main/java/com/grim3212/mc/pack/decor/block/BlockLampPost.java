package com.grim3212.mc.pack.decor.block;

import java.util.Random;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityTextured;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityTextured) {
			ItemStack itemstack = new ItemStack(DecorItems.lamp_item, 1);
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityTextured) te).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityTextured) te).getBlockMeta());
			return itemstack;
		}
		return super.getPickBlock(state, target, world, pos, player);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return DecorItems.lamp_item;
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
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		ItemStack itemstack = new ItemStack(DecorItems.lamp_item, 1);
		if (tileentity instanceof TileEntityTextured) {
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityTextured) tileentity).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityTextured) tileentity).getBlockMeta());
			spawnAsEntity(worldIn, pos, itemstack);
		} else {
			super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
		}
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		if (te instanceof TileEntityTextured) {
			ItemStack itemstack = new ItemStack(DecorItems.lamp_item, 1);
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityTextured) te).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityTextured) te).getBlockMeta());
			spawnAsEntity(worldIn, pos, itemstack);
		} else {
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}
}
