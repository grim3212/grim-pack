package com.grim3212.mc.pack.cuisine.block;

import java.util.Random;

import com.grim3212.mc.pack.cuisine.item.CuisineItems;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCBarMould extends Block {

	protected static final AxisAlignedBB MOULD_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D);
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 15);

	protected BlockCBarMould() {
		super(Material.WOOD);
		setTickRandomly(true);
		setSoundType(SoundType.STONE);
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
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { STAGE });
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		if (!this.canBlockStay(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	private boolean canBlockStay(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		int current_stage = 2;
		if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.ICE || worldIn.getBlockState(pos.down()).getBlock() == Blocks.SNOW || worldIn.getBlockState(pos.down()).getBlock() == Blocks.PACKED_ICE) {
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (heldItem != null && heldItem.getItem() == CuisineItems.chocolate_bowl_hot) {
			if ((Integer) worldIn.getBlockState(pos).getValue(STAGE) == 0) {
				worldIn.setBlockState(pos, state.withProperty(STAGE, 1), 2);

				--heldItem.stackSize;
				if (heldItem.stackSize <= 0) {
					playerIn.inventory.addItemStackToInventory(new ItemStack(Items.BOWL));
				}
			}
		}

		return true;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return MOULD_AABB;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return state.getSelectedBoundingBox(worldIn, pos);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}
}
