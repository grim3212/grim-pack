package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BlockIceMaker extends BlockManual {

	public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 15);

	protected BlockIceMaker() {
		super(IndustryNames.ICE_MAKER, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).tickRandomly().hardnessAndResistance(1.0f));
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(STAGE, 0);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(STAGE);
	}

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		if (!worldIn.isRemote) {
			int l = 2;

			double temp = worldIn.getBiomeBody(pos).getTemperature(pos);
			if (temp < 0.5D) {
				l = 10;
			}

			int meta = worldIn.getBlockState(pos).get(STAGE);
			if (meta >= 5) {
				l = (l + (15 - meta)) - l;
			}
			if (meta == 0) {
				worldIn.setBlockState(pos, state, 2);
			} else if (meta == 15) {
				worldIn.setBlockState(pos, state.with(STAGE, 15), 2);
			} else {
				worldIn.setBlockState(pos, state.with(STAGE, meta + l), 2);
			}
		}
	}

	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		if (worldIn.getBlockState(pos).get(STAGE) == 15) {
			if (!worldIn.isRemote) {
				worldIn.setBlockState(pos, getDefaultState(), 2);
				float f = 0.7F;
				double d = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.20000000000000001D + 0.59999999999999998D;
				double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				ItemEntity entityitem = new ItemEntity(worldIn, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(Blocks.ICE, worldIn.rand.nextInt(5) + 2));
				entityitem.setPickupDelay(5);
				worldIn.addEntity(entityitem);
			}
		}
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
		ItemStack heldItem = playerIn.getHeldItem(handIn);

		if (heldItem.isEmpty()) {
			return false;
		}

		if (heldItem.getItem() == Items.WATER_BUCKET) {
			if (state.get(STAGE) == 0) {
				worldIn.setBlockState(pos, state.with(STAGE, 1), 3);
				Utils.consumePlayerItem(playerIn, new ItemStack(heldItem.getItem()));
				playerIn.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET));
			}
		} else if (Utils.hasFluidHandler(heldItem)) {
			if (state.get(STAGE) == 0) {
				IFluidHandler fluidBucket = Utils.getFluidHandler(heldItem);
				FluidStack fluid = fluidBucket.drain(Fluid.BUCKET_VOLUME, false);
				// Make sure FluidStack itself is not null or it leads to
				// problems
				if (fluid != null) {
					if (fluid.getFluid() != null && fluid.getFluid() == FluidRegistry.WATER) {
						worldIn.setBlockState(pos, state.with(STAGE, 1), 2);
						fluidBucket.drain(Fluid.BUCKET_VOLUME, true);
					}
				}
			}
		}

		return true;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.iceMaker_page;
	}
}
