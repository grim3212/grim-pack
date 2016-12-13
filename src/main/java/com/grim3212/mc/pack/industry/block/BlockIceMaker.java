package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BlockIceMaker extends BlockManual {

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 15);

	protected BlockIceMaker() {
		super(Material.ROCK, SoundType.STONE);
		setTickRandomly(true);
		setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(STAGE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { STAGE });
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		if (!worldIn.isRemote) {
			int l = 2;

			double temp = worldIn.getBiomeForCoordsBody(pos).getFloatTemperature(pos);
			if (temp < 0.5D) {
				l = 10;
			}

			int meta = worldIn.getBlockState(pos).getValue(STAGE);
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
		if (worldIn.getBlockState(pos).getValue(STAGE) == 15) {
			if (!worldIn.isRemote) {
				worldIn.setBlockState(pos, getDefaultState(), 2);
				float f = 0.7F;
				double d = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.20000000000000001D + 0.59999999999999998D;
				double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				EntityItem entityitem = new EntityItem(worldIn, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(Blocks.ICE, worldIn.rand.nextInt(5) + 2));
				entityitem.setPickupDelay(5);
				worldIn.spawnEntity(entityitem);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = playerIn.getHeldItem(hand);

		if (heldItem.isEmpty()) {
			return false;
		}

		if (heldItem.getItem() == Items.WATER_BUCKET) {
			if (state.getValue(STAGE) == 0) {
				worldIn.setBlockState(pos, state.withProperty(STAGE, 1), 3);
				Utils.consumePlayerItem(playerIn, new ItemStack(heldItem.getItem()));
				playerIn.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET));
			}
		} else if (Utils.hasFluidHandler(heldItem)) {
			if (state.getValue(STAGE) == 0) {
				IFluidHandler fluidBucket = Utils.getFluidHandler(heldItem);
				FluidStack fluid = fluidBucket.drain(Fluid.BUCKET_VOLUME, false);
				// Make sure FluidStack itself is not null or it leads to
				// problems
				if (fluid != null) {
					if (fluid.getFluid() != null && fluid.getFluid() == FluidRegistry.WATER) {
						worldIn.setBlockState(pos, state.withProperty(STAGE, 1), 2);
						fluidBucket.drain(Fluid.BUCKET_VOLUME, true);
					}
				}
			}
		}

		return true;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.iceMaker_page;
	}
}
