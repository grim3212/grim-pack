package com.grim3212.mc.pack.cuisine.block;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

public class BlockCheeseMaker extends BlockManual {

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 15);

	protected BlockCheeseMaker() {
		super("cheese_maker", Material.GROUND, SoundType.STONE);
		setTickRandomly(true);
		setHardness(2.0F);
		setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	}

	@Override
	protected IBlockState getState() {
		return this.blockState.getBaseState().withProperty(STAGE, 0);
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
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		if (!worldIn.isRemote) {
			int meta = worldIn.getBlockState(pos).getValue(STAGE);
			if (meta == 0) {
				worldIn.setBlockState(pos, state.withProperty(STAGE, 0), 2);
			} else if (meta == 9) {
				worldIn.setBlockState(pos, state.withProperty(STAGE, 10), 2);
			} else if (meta == 15) {
				// Do nothing
				return;
			} else {
				if (meta + 8 > 15) {
					worldIn.setBlockState(pos, state.withProperty(STAGE, 15), 2);
				} else {
					worldIn.setBlockState(pos, state.withProperty(STAGE, meta + 8), 2);
				}
			}
		}
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (worldIn.getBlockState(pos).getValue(STAGE) == 15) {
			if (!worldIn.isRemote) {
				worldIn.setBlockState(pos, this.getDefaultState());
				float f = 0.7F;
				double d = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.20000000000000001D
						+ 0.59999999999999998D;
				double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				EntityItem entityitem = new EntityItem(worldIn, (double) pos.getX() + d, (double) pos.getY() + d1,
						(double) pos.getZ() + d2, new ItemStack(CuisineBlocks.cheese_block));
				entityitem.setPickupDelay(10);
				worldIn.spawnEntity(entityitem);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = playerIn.getHeldItem(hand);

		if (!heldItem.isEmpty()) {
			if (heldItem.getItem() == Items.MILK_BUCKET) {
				if (state.getValue(STAGE) == 0) {
					worldIn.setBlockState(pos, state.withProperty(STAGE, 1), 2);

					heldItem.shrink(1);
					if (heldItem.getCount() <= 0) {
						playerIn.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET));
					}
				}
			} else if (!OreDictionary.getOres("bucketMilk").isEmpty()) {
				for (int count = 0; count < OreDictionary.getOres("bucketMilk").size(); count++) {
					if (heldItem.getItem() == OreDictionary.getOres("bucketMilk").get(count).getItem()) {
						if (state.getValue(STAGE) == 0) {

							if (Utils.hasFluidHandler(heldItem)) {
								IFluidHandler fluidBucket = Utils.getFluidHandler(heldItem);
								worldIn.setBlockState(pos, state.withProperty(STAGE, 1), 2);
								fluidBucket.drain(Fluid.BUCKET_VOLUME, true);
							}
						}
					}
				}
			}
		}

		return true;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualCuisine.cheeseMaker_page;
	}
}