package com.grim3212.mc.tools.items;

import java.util.List;

import com.grim3212.mc.core.util.NBTHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * A universal bucket that can hold any liquid
 */
public class ItemBetterBucket extends Item implements IFluidContainerItem {

	public final int maxCapacity; // how much the bucket holds
	public final ItemStack empty; // empty item to return and recognize when
									// filling
	public final float maxPickupTemp;
	public final boolean pickupFire;
	public final int milkingLevel;

	public ItemBetterBucket(int maxCapacity, int milkingLevel) {
		this(maxCapacity, milkingLevel, 5000f);
	}

	public ItemBetterBucket(int maxCapacity, int milkingLevel, float maxPickupTemp) {
		this(maxCapacity, milkingLevel, maxPickupTemp, false);
	}

	public ItemBetterBucket(int maxCapacity, int milkingLevel, boolean pickupFire) {
		this(maxCapacity, milkingLevel, 5000f, pickupFire);
	}

	public ItemBetterBucket(int maxCapacity, int milkingLevel, float maxPickupTemp, boolean pickupFire) {
		this.maxCapacity = FluidContainerRegistry.BUCKET_VOLUME * maxCapacity;

		ItemStack stack = new ItemStack(this);
		NBTHelper.setString(stack, "FluidName", "empty");
		NBTHelper.setInteger(stack, "Amount", 0);
		this.empty = stack;

		this.setMaxStackSize(1);
		this.maxPickupTemp = maxPickupTemp;
		this.pickupFire = pickupFire;
		this.milkingLevel = milkingLevel;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (NBTHelper.getInt(stack, "Amount") == 0) {
			tooltip.add(StatCollector.translateToLocal("tooltip.buckets.empty"));
		} else {
			tooltip.add(StatCollector.translateToLocal("tooltip.buckets.contains") + ": " + NBTHelper.getInt(stack, "Amount") + "/" + maxCapacity);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
			// add all fluids that the bucket can be filled with
			FluidStack fs = new FluidStack(fluid, maxCapacity);
			if (fs.getFluid().getTemperature() < maxPickupTemp) {
				ItemStack stack = new ItemStack(this);
				if (fill(stack, fs, true) == fs.amount) {
					subItems.add(stack);
				}
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		FluidStack fluidStack = getFluid(stack);
		if (fluidStack == null) {
			if (empty != null) {
				return super.getItemStackDisplayName(empty);
			}
			return super.getItemStackDisplayName(stack);
		}

		String unloc = super.getItemStackDisplayName(stack);

		return unloc.replaceFirst("\\s", " " + fluidStack.getLocalizedName() + " ");
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		FluidStack fluidStack = getFluid(itemstack);

		// clicked on a block?
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, ItemStack.areItemStackTagsEqual(itemstack, empty));
		if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			BlockPos clickPos = mop.getBlockPos();

			if (fluidStack == null) {
				IBlockState state = world.getBlockState(clickPos);
				// Note that water and lava are NOT an instance of IFluidBlock!
				// They are
				// therefore not handled by this code!
				if (state.getBlock() instanceof IFluidBlock) {
					IFluidBlock fluidBlock = (IFluidBlock) state.getBlock();
					if (fluidBlock.canDrain(world, clickPos)) {
						FluidStack drained = fluidBlock.drain(world, clickPos, false);
						// check if it fits exactly
						if (drained != null && drained.amount == maxCapacity) {
							// check if the container accepts it
							ItemStack filledBucket = new ItemStack(this);
							int filled = this.fill(filledBucket, drained, false);
							if (filled == drained.amount) {
								// actually transfer the fluid
								drained = fluidBlock.drain(world, clickPos, true);
								this.fill(filledBucket, drained, true);

								return filledBucket;
							}
						}
					}
				} else {

					if (!world.isBlockModifiable(player, clickPos)) {

						return itemstack;
					}
					if (!player.canPlayerEdit(clickPos.offset(mop.sideHit), mop.sideHit, itemstack)) {

						return itemstack;
					}

					Material material = state.getBlock().getMaterial();
					if (material == Material.water && ((Integer) state.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
						world.setBlockToAir(clickPos);
						player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
						ItemStack filledStack = new ItemStack(this);
						this.fill(filledStack, new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME), true);
						return filledStack;
					}

					if (material == Material.lava && ((Integer) state.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
						world.setBlockToAir(clickPos);
						player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
						ItemStack filledStack = new ItemStack(this);
						this.fill(filledStack, new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME), true);
						return filledStack;
					}
				}
			} else if (world.isBlockModifiable(player, clickPos)) {
				// the block adjacent to the side we clicked on
				BlockPos targetPos = clickPos.offset(mop.sideHit);

				// can the player place there?
				if (player.canPlayerEdit(targetPos, mop.sideHit, itemstack)) {
					// try placing liquid
					if (this.tryPlaceFluid(fluidStack.getFluid().getBlock(), world, targetPos) && !player.capabilities.isCreativeMode) {
						// success!
						player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

						itemstack.stackSize--;
						ItemStack emptyStack = empty != null ? empty.copy() : new ItemStack(this);

						// check whether we replace the item or add the empty
						// one to the inventory
						if (itemstack.stackSize <= 0) {
							return emptyStack;
						} else {
							// add empty bucket to player inventory
							ItemHandlerHelper.giveItemToPlayer(player, emptyStack);
							return itemstack;
						}
					}
				}
			}
		}

		// couldn't place liquid there2
		return itemstack;
	}

	/**
	 * Check to see if an ItemStack contains empty or the type in its stored NBT
	 * 
	 * @param stack
	 *            The ItemStack to check
	 * @param type
	 *            The type to check on the ItemStack
	 * @return True if the ItemStack contains empty or the type in NBT
	 */
	public static boolean isEmptyOrContains(ItemStack stack, String type) {
		return NBTHelper.getString(stack, "FluidName").equals("empty") || NBTHelper.getString(stack, "FluidName").equals(type);
	}

	public boolean tryPlaceFluid(Block block, World worldIn, BlockPos pos) {
		if (block == null) {
			return false;
		}

		Material material = worldIn.getBlockState(pos).getBlock().getMaterial();
		boolean isSolid = material.isSolid();

		// can only place in air or non-solid blocks
		if (!worldIn.isAirBlock(pos) && isSolid) {
			return false;
		}

		// water goes poof?
		if (worldIn.provider.doesWaterVaporize() && (block == Blocks.flowing_water || block == Blocks.water)) {
			int i = pos.getX();
			int j = pos.getY();
			int k = pos.getZ();
			worldIn.playSoundEffect((double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), "random.fizz", 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

			for (int l = 0; l < 8; ++l) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
			}
		} else {
			if (!worldIn.isRemote && !isSolid && !material.isLiquid()) {
				worldIn.destroyBlock(pos, true);
			}

			worldIn.setBlockState(pos, block.getDefaultState(), 3);
		}
		return true;
	}

	public static ItemStack getFilledBucket(ItemBetterBucket item, Fluid fluid) {
		ItemStack stack = new ItemStack(item);
		item.fill(stack, new FluidStack(fluid, item.maxCapacity), true);
		return stack;
	}

	/* FluidContainer Management */

	@Override
	public FluidStack getFluid(ItemStack container) {
		return FluidStack.loadFluidStackFromNBT(container.getTagCompound());
	}

	@Override
	public int getCapacity(ItemStack container) {
		return maxCapacity;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill) {
		// has to be exactly 1, must be handled from the caller
		if (container.stackSize != 1) {
			return 0;
		}

		// can only fill exact capacity
		if (resource == null || resource.amount != maxCapacity) {
			return 0;
		}

		// fill the container
		if (doFill) {
			NBTTagCompound tag = container.getTagCompound();
			if (tag == null) {
				tag = new NBTTagCompound();
			}
			resource.writeToNBT(tag);
			container.setTagCompound(tag);
		}
		return maxCapacity;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
		// can only drain everything at once
		if (maxDrain < maxCapacity) {
			return null;
		}

		FluidStack fluidStack = getFluid(container);
		if (doDrain && fluidStack != null) {
			if (empty != null) {
				container.setItem(empty.getItem());
				container.setTagCompound(empty.getTagCompound());
				container.setItemDamage(empty.getItemDamage());
			} else {
				container.stackSize = 0;
			}
		}

		return fluidStack;
	}
}