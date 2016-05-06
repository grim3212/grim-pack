package com.grim3212.mc.industry.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidContainerItem;

public class BlockIceMaker extends Block {

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 15);

	protected BlockIceMaker() {
		super(Material.wood);
		setTickRandomly(true);
		setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (Integer) state.getValue(STAGE);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { STAGE });
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		if (!worldIn.isRemote) {
			int l = 2;

			double temp = worldIn.getBiomeGenForCoords(pos).getFloatTemperature(pos);
			if (temp < 0.5D) {
				l = 10;
			}

			int meta = (Integer) worldIn.getBlockState(pos).getValue(STAGE);
			if (meta >= 5) {
				l = (l + (15 - meta)) - l;
			}
			if (meta == 0) {
				worldIn.setBlockState(pos, state, 2);
			} else if (meta == 15) {
				worldIn.setBlockState(pos, state.withProperty(STAGE, 15), 2);
			} else {
				worldIn.setBlockState(pos, state.withProperty(STAGE, meta + l), 2);
			}
		}
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (((Integer) worldIn.getBlockState(pos).getValue(STAGE)) == 15) {
			if (!worldIn.isRemote) {
				worldIn.setBlockState(pos, getDefaultState(), 2);
				float f = 0.7F;
				double d = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.20000000000000001D + 0.59999999999999998D;
				double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				EntityItem entityitem = new EntityItem(worldIn, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(Blocks.ice, worldIn.rand.nextInt(5) + 2));
				entityitem.setPickupDelay(5);
				worldIn.spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (playerIn.getCurrentEquippedItem() == null) {
			return false;
		}

		if (playerIn.getCurrentEquippedItem().getItem() == Items.water_bucket) {
			if (((Integer) state.getValue(STAGE)) == 0) {
				worldIn.setBlockState(pos, state.withProperty(STAGE, 1), 3);
				playerIn.inventory.consumeInventoryItem(playerIn.getHeldItem().getItem());
				playerIn.inventory.addItemStackToInventory(new ItemStack(Items.bucket));
			}
		} else if (playerIn.getCurrentEquippedItem().getItem() instanceof IFluidContainerItem) {
			if (((Integer) state.getValue(STAGE)) == 0) {
				IFluidContainerItem fluidBucket = (IFluidContainerItem) playerIn.getCurrentEquippedItem().getItem();
				if (fluidBucket.getFluid(playerIn.getCurrentEquippedItem()).getFluid() != null) {
					if (fluidBucket.getFluid(playerIn.getCurrentEquippedItem()).getFluid() == FluidRegistry.WATER) {
						worldIn.setBlockState(pos, state.withProperty(STAGE, 1), 3);
						fluidBucket.drain(playerIn.getCurrentEquippedItem(), FluidContainerRegistry.BUCKET_VOLUME, true);
					}
				}
			}
		}

		return true;
	}
}
