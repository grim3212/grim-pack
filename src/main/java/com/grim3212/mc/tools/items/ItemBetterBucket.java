package com.grim3212.mc.tools.items;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.core.util.NBTHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

/**
 * A universal bucket that can hold any liquid
 */
public class ItemBetterBucket extends Item implements IFluidContainerItem {

	public final int maxCapacity;
	public final ItemStack empty;
	public final float maxPickupTemp;
	public final boolean pickupFire;
	public final int milkingLevel;
	private boolean milkPause = false;

	public static List<String> extraPickups = new ArrayList<String>();

	static {
		extraPickups.add("milk");
		extraPickups.add("fire");
		extraPickups.add("empty");
	}

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
		if (NBTHelper.getInt(stack, "Amount") <= 0) {
			tooltip.add(StatCollector.translateToLocal("tooltip.buckets.empty"));
		} else {
			tooltip.add(StatCollector.translateToLocal("tooltip.buckets.contains") + ": " + NBTHelper.getInt(stack, "Amount") + "/" + maxCapacity);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		// Fluids
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

		// Non-Fluid pickups
		for (String other : extraPickups) {
			if (!other.equals("fire")) {
				ItemStack stack = new ItemStack(this);
				NBTHelper.setString(stack, "FluidName", other);
				NBTHelper.setInteger(stack, "Amount", other.equals("empty") ? 0 : maxCapacity);
				subItems.add(stack);
			} else {
				if (this.pickupFire) {
					ItemStack stack = new ItemStack(this);
					NBTHelper.setString(stack, "FluidName", other);
					NBTHelper.setInteger(stack, "Amount", maxCapacity);
					subItems.add(stack);
				}
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		FluidStack fluidStack = getFluid(stack);
		String unloc = super.getItemStackDisplayName(stack);

		if (fluidStack == null) {
			if (NBTHelper.getString(stack, "FluidName").equals("fire")) {
				return unloc.replaceFirst("\\s", " " + Blocks.fire.getLocalizedName() + " ");
			} else if (NBTHelper.getString(stack, "FluidName").equals("milk")) {
				return unloc.replaceFirst("\\s", " " + StatCollector.translateToLocal("tile.milk.name") + " ");
			}

			if (empty != null) {
				return super.getItemStackDisplayName(empty);
			}
			return super.getItemStackDisplayName(stack);
		}

		return unloc.replaceFirst("\\s", " " + fluidStack.getLocalizedName() + " ");
	}

	public void pauseForMilk() {
		milkPause = true;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		boolean canContainMore = NBTHelper.getInt(itemstack, "Amount") < maxCapacity;

		if (milkPause) {
			milkPause = false;
			return itemstack;
		}

		// clicked on a block?
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, canContainMore);
		if (mop == null) {
			// Check to see if you should drink milk
			if (NBTHelper.getString(itemstack, "FluidName").equals("milk")) {
				player.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
				return itemstack;
			}
		} else if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			BlockPos clickPos = mop.getBlockPos();

			if (canContainMore) {
				IBlockState state = world.getBlockState(clickPos);

				if (state.getBlock() instanceof IFluidBlock) {
					IFluidBlock fluidBlock = (IFluidBlock) state.getBlock();
					if (fluidBlock.canDrain(world, clickPos)) {
						FluidStack drained = fluidBlock.drain(world, clickPos, false);
						// check if it fits exactly
						if (drained != null && drained.amount % FluidContainerRegistry.BUCKET_VOLUME == 0) {
							// check if the container accepts it
							ItemStack filledBucket = new ItemStack(this);
							int filled = this.fill(filledBucket, drained, false);
							if (filled == drained.amount) {
								// actually transfer the fluid
								drained = fluidBlock.drain(world, clickPos, true);

								if (player.capabilities.isCreativeMode) {
									return itemstack;
								}

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

						if (player.capabilities.isCreativeMode) {
							return itemstack;
						}

						FluidStack fluidStack = new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);

						if (this.fill(itemstack, fluidStack, false) == fluidStack.amount) {
							this.fill(itemstack, fluidStack, true);
							return itemstack;
						}
					}

					if (material == Material.lava && ((Integer) state.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
						world.setBlockToAir(clickPos);
						player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

						if (player.capabilities.isCreativeMode) {
							return itemstack;
						}

						FluidStack fluidStack = new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME);

						if (this.fill(itemstack, fluidStack, false) == fluidStack.amount) {
							this.fill(itemstack, fluidStack, true);
							return itemstack;
						}
					}

					// New Pos to account for Fire
					BlockPos firePos = mop.getBlockPos().offset(mop.sideHit);
					if (world.getBlockState(firePos).getBlock() == Blocks.fire && isEmptyOrContains(itemstack, "fire") && this.pickupFire) {
						world.setBlockToAir(firePos);

						if (player.capabilities.isCreativeMode) {
							return itemstack;
						}

						if (isEmptyOrContains(itemstack, "fire")) {
							NBTHelper.setString(itemstack, "FluidName", "fire");
							NBTHelper.setInteger(itemstack, "Amount", NBTHelper.getInt(itemstack, "Amount") + FluidContainerRegistry.BUCKET_VOLUME);
							return itemstack;
						}
					}
				}
			}

			if (world.isBlockModifiable(player, clickPos)) {

				mop = this.getMovingObjectPositionFromPlayer(world, player, false);
				// Null check
				if (mop == null) {
					return itemstack;
				}

				BlockPos targetPos = mop.getBlockPos().offset(mop.sideHit);

				if (NBTHelper.getString(itemstack, "FluidName").equals("milk")) {
					player.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
					return itemstack;
				} else {

					// can the player place there?
					if (player.canPlayerEdit(targetPos, mop.sideHit, itemstack)) {
						int amount = NBTHelper.getInt(itemstack, "Amount");
						if (amount >= FluidContainerRegistry.BUCKET_VOLUME) {
							FluidStack fluidStack = getFluid(itemstack);

							// try placing liquid
							if (fluidStack != null) {
								if (this.tryPlaceFluid(fluidStack.getFluid().getBlock(), world, targetPos) && !player.capabilities.isCreativeMode) {
									// success!
									player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

									NBTHelper.setInteger(itemstack, "Amount", amount - FluidContainerRegistry.BUCKET_VOLUME);

									// check whether we replace the item or add
									// the
									// empty
									// one to the inventory
									if ((amount - FluidContainerRegistry.BUCKET_VOLUME) <= 0) {
										return empty.copy();
									}
								}
							} else if (NBTHelper.getString(itemstack, "FluidName").equals("fire")) {
								if (this.tryPlaceFluid(Blocks.fire, world, targetPos) && !player.capabilities.isCreativeMode) {
									// success!
									player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

									NBTHelper.setInteger(itemstack, "Amount", amount - FluidContainerRegistry.BUCKET_VOLUME);

									// check whether we replace the item or add
									// the
									// empty
									// one to the inventory
									if ((amount - FluidContainerRegistry.BUCKET_VOLUME) <= 0) {
										return empty.copy();
									}
								}
							}
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
			worldIn.notifyBlockOfStateChange(pos, block);
		}
		return true;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		// Everything in here is only used for milk buckets and what happens
		// when you are finished drinking them

		if (!playerIn.capabilities.isCreativeMode) {
			// Set amount and type, if empty
			int amount = NBTHelper.getInt(stack, "Amount");
			NBTHelper.setInteger(stack, "Amount", amount - FluidContainerRegistry.BUCKET_VOLUME);
			if (amount <= 0) {
				NBTHelper.setString(stack, "FluidName", "empty");
			}
		}

		if (!worldIn.isRemote) {
			// Cure PotionEffects like a regular bucket of milk
			playerIn.curePotionEffects(new ItemStack(Items.milk_bucket));
		}

		playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

		// Another check if the amount equals 0
		if (NBTHelper.getInt(stack, "Amount") <= 0) {
			return empty.copy();
		} else {
			return stack;
		}
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return NBTHelper.getInt(stack, "Amount") >= FluidContainerRegistry.BUCKET_VOLUME;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		// Only used for milk
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		// Only used for milk
		if (NBTHelper.getString(stack, "FluidName").equals("milk")) {
			return EnumAction.DRINK;
		} else {
			return EnumAction.NONE;
		}

	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		int amount = NBTHelper.getInt(itemStack, "Amount");
		NBTHelper.setInteger(itemStack, "Amount", amount - FluidContainerRegistry.BUCKET_VOLUME);
		if (amount <= 0) {
			return empty;
		} else {
			return itemStack;
		}
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		// Don't show if the bucket is empty
		if (NBTHelper.getInt(stack, "Amount") <= 0)
			return false;
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		// Get remainder calculations from stored and maxAmount
		int reversedAmount = this.maxCapacity - NBTHelper.getInt(stack, "Amount");
		return (double) reversedAmount / (double) this.maxCapacity;
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
		if (resource == null || resource.amount % FluidContainerRegistry.BUCKET_VOLUME != 0) {
			return 0;
		}

		// fill the container
		if (doFill) {
			NBTHelper.setString(container, "FluidName", FluidRegistry.getFluidName(resource.getFluid()));
			NBTHelper.setInteger(container, "Amount", NBTHelper.getInt(container, "Amount") + resource.amount);
		} else {
			if (!isEmptyOrContains(container, FluidRegistry.getFluidName(resource.getFluid()))) {
				return 0;
			}
		}
		return resource.amount;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
		if (maxDrain % FluidContainerRegistry.BUCKET_VOLUME != 0 || maxDrain > maxCapacity) {
			return null;
		}

		FluidStack fluidStack = getFluid(container);
		if (doDrain && fluidStack != null) {
			int amount = NBTHelper.getInt(container, "Amount");
			NBTHelper.setInteger(container, "Amount", amount - FluidContainerRegistry.BUCKET_VOLUME);

			if (NBTHelper.getInt(container, "Amount") <= 0) {
				container.setTagCompound(empty.getTagCompound());
			}
		}

		return fluidStack;
	}
}