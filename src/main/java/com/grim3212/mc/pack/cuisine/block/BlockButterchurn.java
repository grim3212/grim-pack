package com.grim3212.mc.pack.cuisine.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.init.CuisineNames;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;

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

public class BlockButterchurn extends BlockManual {

	public static final IntegerProperty ACTIVE = IntegerProperty.create("active", 0, 1);

	protected BlockButterchurn() {
		super(CuisineNames.BUTTER_CHURN, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f));
	}

	@Override
	protected IBlockState getState() {
		return this.stateContainer.getBaseState().with(ACTIVE, 0);
	}

	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.getHeldItem(hand);

		if (!heldItem.isEmpty()) {
			if (heldItem.getItem() == Items.MILK_BUCKET) {
				if (state.get(ACTIVE) == 0) {
					worldIn.setBlockState(pos, state.cycle(ACTIVE), 4);

					heldItem.shrink(1);
					if (heldItem.getCount() <= 0) {
						player.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET));
					}
				}
			} else if (!ItemTags.getCollection().getRegisteredTags().contains(new ResourceLocation("forge:bucketMilk"))) {
				Tag<Item> milkTag = ItemTags.getCollection().get(new ResourceLocation("forge:bucketMilk"));
				if (milkTag.contains(heldItem.getItem())) {
					if (state.get(ACTIVE) == 0) {

						if (Utils.hasFluidHandler(heldItem)) {
							IFluidHandler fluidBucket = Utils.getFluidHandler(heldItem);
							worldIn.setBlockState(pos, state.cycle(ACTIVE), 4);
							fluidBucket.drain(Fluid.BUCKET_VOLUME, true);
						}
					}
				}
			}
		}

		return true;
	}

	@Override
	public void onBlockClicked(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player) {
		if (!worldIn.isRemote) {
			if (state.get(ACTIVE) == 1) {
				worldIn.setBlockState(pos, this.getDefaultState());
				float f = 0.7F;
				double d = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.20000000000000001D + 0.59999999999999998D;
				double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;

				int amount = worldIn.rand.nextInt(4);
				if (amount == 0) {
					amount = 1;
				}

				EntityItem entityitem = new EntityItem(worldIn, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(CuisineItems.butter, amount));
				entityitem.setPickupDelay(10);
				worldIn.spawnEntity(entityitem);
			}
		}
	}

	@Override
	protected void fillStateContainer(Builder<Block, IBlockState> builder) {
		builder.add(ACTIVE);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualCuisine.butterChurn_page;
	}
}
