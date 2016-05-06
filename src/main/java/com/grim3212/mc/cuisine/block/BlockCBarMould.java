package com.grim3212.mc.cuisine.block;

import java.util.List;
import java.util.Random;

import com.grim3212.mc.cuisine.item.CuisineItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCBarMould extends Block {

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 15);

	protected BlockCBarMould() {
		super(Material.wood);
		setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(STAGE));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { STAGE });
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!this.canBlockStay(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	private boolean canBlockStay(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid();
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		int current_stage = 2;
		if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.ice || worldIn.getBlockState(pos.down()).getBlock() == Blocks.snow || worldIn.getBlockState(pos.down()).getBlock() == Blocks.packed_ice) {
			current_stage = 4;
		}

		int meta = (Integer) worldIn.getBlockState(pos).getValue(STAGE);
		if (meta >= 12) {
			current_stage = (current_stage + (15 - meta)) - current_stage;
		}
		if (meta == 0) {
			worldIn.setBlockState(pos, state.withProperty(STAGE, 0), 2);
		} else if (meta == 15) {
			worldIn.setBlockState(pos, state.withProperty(STAGE, 15), 2);
		} else {
			worldIn.setBlockState(pos, state.withProperty(STAGE, meta + current_stage), 2);
		}
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if ((Integer) worldIn.getBlockState(pos).getValue(STAGE) == 15) {
			if (!worldIn.isRemote) {
				worldIn.setBlockState(pos, CuisineBlocks.chocolate_bar_mould.getDefaultState(), 3);
				float f = 0.7F;
				double d = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.20000000000000001D + 0.59999999999999998D;
				double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				EntityItem entityitem = new EntityItem(worldIn, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(CuisineItems.chocolate_bar, 2));
				entityitem.setPickupDelay(10);
				worldIn.spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (playerIn.getCurrentEquippedItem() == null) {
			return false;
		} else if (playerIn.getHeldItem().getItem() == CuisineItems.chocolate_bowl_hot) {
			if ((Integer) worldIn.getBlockState(pos).getValue(STAGE) == 0) {
				worldIn.setBlockState(pos, state.withProperty(STAGE, 1), 2);
				playerIn.inventory.consumeInventoryItem(playerIn.getHeldItem().getItem());
				playerIn.inventory.addItemStackToInventory(new ItemStack(Items.bowl));
			}
		}
		return true;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		float f = 0.0625F;
		float f1 = 0.0625F;
		float f2 = 0.5F;
		setBlockBounds(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		float f = 0.0625F;
		float f1 = 0.5F;
		setBlockBounds(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
	}

	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
		this.setBlockBoundsBasedOnState(worldIn, pos);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}
}
