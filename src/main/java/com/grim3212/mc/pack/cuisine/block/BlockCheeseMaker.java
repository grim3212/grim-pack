package com.grim3212.mc.pack.cuisine.block;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.init.CuisineNames;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.capability.IFluidHandler;

@SuppressWarnings("deprecation")
public class BlockCheeseMaker extends BlockManual {

	public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 15);

	protected BlockCheeseMaker() {
		super(CuisineNames.CHEESE_MAKER, Block.Properties.create(Material.GROUND).sound(SoundType.STONE).tickRandomly().hardnessAndResistance(2.0f));
	}

	@Override
	protected IBlockState getState() {
		return this.stateContainer.getBaseState().with(STAGE, 0);
	}

	@Override
	protected void fillStateContainer(Builder<Block, IBlockState> builder) {
		builder.add(STAGE);
	}

	@Override
	public void tick(IBlockState state, World worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		if (!worldIn.isRemote) {
			int meta = worldIn.getBlockState(pos).get(STAGE);
			if (meta == 0) {
				worldIn.setBlockState(pos, state.with(STAGE, 0), 2);
			} else if (meta == 9) {
				worldIn.setBlockState(pos, state.with(STAGE, 10), 2);
			} else if (meta == 15) {
				// Do nothing
				return;
			} else {
				if (meta + 8 > 15) {
					worldIn.setBlockState(pos, state.with(STAGE, 15), 2);
				} else {
					worldIn.setBlockState(pos, state.with(STAGE, meta + 8), 2);
				}
			}
		}
	}

	@Override
	public void onBlockClicked(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (state.get(STAGE) == 15) {
			if (!worldIn.isRemote) {
				worldIn.setBlockState(pos, this.getDefaultState());
				float f = 0.7F;
				double d = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.20000000000000001D + 0.59999999999999998D;
				double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				EntityItem entityitem = new EntityItem(worldIn, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(CuisineBlocks.cheese_block));
				entityitem.setPickupDelay(10);
				worldIn.spawnEntity(entityitem);
			}
		}
	}

	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.getHeldItem(hand);

		if (!heldItem.isEmpty()) {
			if (heldItem.getItem() == Items.MILK_BUCKET) {
				if (state.get(STAGE) == 0) {
					worldIn.setBlockState(pos, state.with(STAGE, 1), 2);

					heldItem.shrink(1);
					if (heldItem.getCount() <= 0) {
						player.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET));
					}
				}
			} else if (!ItemTags.getCollection().getRegisteredTags().contains(new ResourceLocation("forge:bucketMilk"))) {
				Tag<Item> milkTag = ItemTags.getCollection().get(new ResourceLocation("forge:bucketMilk"));
				if (milkTag.contains(heldItem.getItem())) {
					if (state.get(STAGE) == 0) {

						if (Utils.hasFluidHandler(heldItem)) {
							IFluidHandler fluidBucket = Utils.getFluidHandler(heldItem);
							worldIn.setBlockState(pos, state.with(STAGE, 1), 2);
							fluidBucket.drain(Fluid.BUCKET_VOLUME, true);
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