package com.grim3212.mc.pack.tools.items;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.client.ManualTools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBetterBucket extends ItemManual {

	public final int maxCapacity;
	public final ItemStack empty;
	public final float maxPickupTemp;
	public final boolean pickupFire;
	public final int milkingLevel;
	private ItemStack onBroken = ItemStack.EMPTY;
	private boolean milkPause = false;
	public final BucketType bucketType;

	public enum BucketType {
		wood, stone, gold, diamond, obsidian
	}

	public static List<String> extraPickups = new ArrayList<String>();

	static {
		extraPickups.add("fire");
		extraPickups.add("milk");
	}

	public ItemBetterBucket(int maxCapacity, int milkingLevel, BucketType bucketType) {
		this(maxCapacity, milkingLevel, 5000f, null, bucketType);
	}

	public ItemBetterBucket(int maxCapacity, int milkingLevel, ItemStack stack, BucketType bucketType) {
		this(maxCapacity, milkingLevel, 5000f, stack, bucketType);
	}

	public ItemBetterBucket(int maxCapacity, int milkingLevel, float maxPickupTemp, ItemStack stack, BucketType bucketType) {
		this(maxCapacity, milkingLevel, maxPickupTemp, false, bucketType);
		setOnBroken(stack);
	}

	public ItemBetterBucket(int maxCapacity, int milkingLevel, boolean pickupFire, BucketType bucketType) {
		this(maxCapacity, milkingLevel, 5000f, pickupFire, bucketType);
	}

	public ItemBetterBucket(int maxCapacity, int milkingLevel, float maxPickupTemp, boolean pickupFire, BucketType bucketType) {
		this.maxCapacity = Fluid.BUCKET_VOLUME * maxCapacity;

		ItemStack stack = new ItemStack(this);
		setFluid(stack, "empty");
		setAmount(stack, 0);
		this.empty = stack;

		this.setMaxStackSize(1);
		this.maxPickupTemp = maxPickupTemp;
		this.pickupFire = pickupFire;
		this.milkingLevel = milkingLevel;
		this.bucketType = bucketType;
	}

	@Override
	public Page getPage(ItemStack stack) {
		if (stack.getItem() == ToolsItems.wooden_bucket) {
			return ManualTools.woodBucket_page;
		} else if (stack.getItem() == ToolsItems.stone_bucket) {
			return ManualTools.stoneBucket_page;
		} else if (stack.getItem() == ToolsItems.golden_bucket) {
			return ManualTools.goldBucket_page;
		} else if (stack.getItem() == ToolsItems.diamond_bucket) {
			return ManualTools.diamondBucket_page;
		}

		return ManualTools.obsidianBucket_page;
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		setFluid(stack, "empty");
		setAmount(stack, 0);
	}

	public Item setOnBroken(ItemStack onBroken) {
		this.onBroken = onBroken;
		return this;
	}

	public void pauseForMilk() {
		milkPause = true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (getAmount(stack) <= 0) {
			tooltip.add(I18n.format("tooltip.buckets.empty"));
		} else {
			tooltip.add(I18n.format("tooltip.buckets.contains") + ": " + getAmount(stack) + "/" + maxCapacity);
		}
	}

	public FluidStack getFluidStack(ItemStack container) {
		NBTTagCompound tagCompound = container.getTagCompound();
		if (tagCompound == null) {
			return null;
		}
		return FluidStack.loadFluidStackFromNBT(NBTHelper.getTagCompound(tagCompound, FluidHandlerItemStack.FLUID_NBT_KEY));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		// Add empty
		ItemStack emptyStack = new ItemStack(this);
		setFluid(emptyStack, "empty");
		setAmount(emptyStack, 0);
		subItems.add(emptyStack);

		// Fluids
		for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
			if (fluid.getUnlocalizedName().equals("fluid.milk"))
				continue;

			// add all fluids that the bucket can be filled with
			FluidStack fs = new FluidStack(fluid, maxCapacity);
			if (fs.getFluid().getTemperature() < maxPickupTemp) {
				ItemStack stack = new ItemStack(this);

				if (Utils.getFluidHandler(stack).fill(fs, true) == fs.amount) {
					subItems.add(stack);
				}
			}
		}

		// Non-Fluid pickups
		for (String other : extraPickups) {
			if (!other.equals("milk"))
				if (!other.equals("fire")) {
					ItemStack stack = new ItemStack(this);
					setFluid(stack, other);
					setAmount(stack, maxCapacity);
					subItems.add(stack);
				} else {
					if (this.pickupFire) {
						ItemStack stack = new ItemStack(this);
						setFluid(stack, other);
						setAmount(stack, maxCapacity);
						subItems.add(stack);
					}
				}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		FluidStack fluidStack = Utils.getFluidHandler(stack).drain(Fluid.BUCKET_VOLUME, false);
		String unloc = super.getItemStackDisplayName(stack);

		if (fluidStack == null) {
			if (getFluid(stack).equals("fire")) {
				return unloc.replaceFirst("\\s", " " + Blocks.FIRE.getLocalizedName() + " ");
			}

			if (!empty.isEmpty()) {
				return super.getItemStackDisplayName(empty);
			}
			return super.getItemStackDisplayName(stack);
		}

		return unloc.replaceFirst("\\s", " " + fluidStack.getLocalizedName() + " ");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		ItemStack itemStackIn = playerIn.getHeldItem(hand);

		boolean canContainMore = getAmount(itemStackIn) < maxCapacity;
		if (milkPause) {
			milkPause = false;
			return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
		}

		IFluidHandler fluidHandler = Utils.getFluidHandler(itemStackIn);

		// clicked on a block?
		RayTraceResult raytrace = this.rayTrace(worldIn, playerIn, canContainMore);
		if (raytrace == null) {
			return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
		} else if (raytrace.typeOfHit == RayTraceResult.Type.BLOCK) {
			BlockPos clickPos = raytrace.getBlockPos();

			if (canContainMore) {
				IBlockState state = worldIn.getBlockState(clickPos);

				if (state.getBlock() instanceof IFluidBlock) {
					IFluidBlock fluidBlock = (IFluidBlock) state.getBlock();
					if (fluidBlock.canDrain(worldIn, clickPos)) {
						FluidStack drained = fluidBlock.drain(worldIn, clickPos, false);
						// check if it fits exactly
						if (drained != null && drained.amount % Fluid.BUCKET_VOLUME == 0) {
							// Check to make sure the temperature isn't too hot
							if (this.maxPickupTemp >= drained.getFluid().getTemperature()) {
								// check if the container accepts it
								int filled = fluidHandler.fill(drained, false);
								if (filled == drained.amount) {
									// actually transfer the fluid
									drained = fluidBlock.drain(worldIn, clickPos, true);

									if (playerIn.capabilities.isCreativeMode) {
										return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
									}

									fluidHandler.fill(drained, true);

									return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
								}
							}
						}
					}
				} else {

					if (!worldIn.isBlockModifiable(playerIn, clickPos)) {
						return ActionResult.newResult(EnumActionResult.PASS, itemStackIn);
					}
					if (!playerIn.canPlayerEdit(clickPos.offset(raytrace.sideHit), raytrace.sideHit, itemStackIn)) {
						return ActionResult.newResult(EnumActionResult.PASS, itemStackIn);
					}

					Material material = state.getMaterial();
					if (material == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0) {
						if (this.maxPickupTemp >= FluidRegistry.WATER.getTemperature()) {
							worldIn.setBlockToAir(clickPos);
							playerIn.addStat(StatList.getObjectUseStats(this));

							if (playerIn.capabilities.isCreativeMode) {
								return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
							}

							FluidStack fluidStack = new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME);

							if (fluidHandler.fill(fluidStack, false) == fluidStack.amount) {
								fluidHandler.fill(fluidStack, true);
								return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
							}
						}
					}

					if (material == Material.LAVA && state.getValue(BlockLiquid.LEVEL) == 0) {
						if (this.maxPickupTemp >= FluidRegistry.LAVA.getTemperature()) {
							worldIn.setBlockToAir(clickPos);
							playerIn.addStat(StatList.getObjectUseStats(this));

							if (playerIn.capabilities.isCreativeMode) {
								return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
							}

							FluidStack fluidStack = new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME);

							if (fluidHandler.fill(fluidStack, false) == fluidStack.amount) {
								fluidHandler.fill(fluidStack, true);
								return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
							}
						}
					}

					// New Pos to account for Fire
					BlockPos firePos = raytrace.getBlockPos().offset(raytrace.sideHit);
					if (worldIn.getBlockState(firePos).getBlock() == Blocks.FIRE && isEmptyOrContains(itemStackIn, "fire") && this.pickupFire) {
						worldIn.setBlockToAir(firePos);

						if (playerIn.capabilities.isCreativeMode) {
							return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
						}

						if (isEmptyOrContains(itemStackIn, "fire")) {
							setFluid(itemStackIn, "fire");
							setAmount(itemStackIn, getAmount(itemStackIn) + Fluid.BUCKET_VOLUME);
							return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
						}
					}
				}
			}

			if (worldIn.isBlockModifiable(playerIn, clickPos)) {
				raytrace = this.rayTrace(worldIn, playerIn, false);
				// Null check
				if (raytrace == null) {
					return ActionResult.newResult(EnumActionResult.PASS, itemStackIn);
				}

				BlockPos targetPos = raytrace.getBlockPos().offset(raytrace.sideHit);
				// can the player place there?
				if (playerIn.canPlayerEdit(targetPos, raytrace.sideHit, itemStackIn)) {
					int amount = getAmount(itemStackIn);
					if (amount >= Fluid.BUCKET_VOLUME) {
						FluidStack fluidStack = getFluidStack(itemStackIn);

						// try placing liquid
						if (fluidStack != null) {
							if (this.tryPlaceFluid(fluidStack.getFluid().getBlock(), worldIn, targetPos) && !playerIn.capabilities.isCreativeMode) {
								// success!
								playerIn.addStat(StatList.getObjectUseStats(this));
								setAmount(itemStackIn, amount - Fluid.BUCKET_VOLUME);
								return ActionResult.newResult(EnumActionResult.SUCCESS, this.tryBreakBucket(itemStackIn));
							}
						} else if (getFluid(itemStackIn).equals("fire")) {
							if (this.tryPlaceFluid(Blocks.FIRE, worldIn, targetPos) && !playerIn.capabilities.isCreativeMode) {
								// success!
								playerIn.addStat(StatList.getObjectUseStats(this));
								setAmount(itemStackIn, amount - Fluid.BUCKET_VOLUME);
								return ActionResult.newResult(EnumActionResult.SUCCESS, this.tryBreakBucket(itemStackIn));
							}
						}
					}
				}
			}
		}
		// couldn't place liquid there2
		return ActionResult.newResult(EnumActionResult.PASS, itemStackIn);
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
		return getFluid(stack).equals("empty") || getFluid(stack).equals(type);
	}

	public boolean tryPlaceFluid(Block block, World worldIn, BlockPos pos) {
		if (block == null) {
			return false;
		}

		Material material = worldIn.getBlockState(pos).getMaterial();
		boolean isSolid = material.isSolid();

		// can only place in air or non-solid blocks
		if (!worldIn.isAirBlock(pos) && isSolid) {
			return false;
		}

		// water goes poof?
		if (worldIn.provider.doesWaterVaporize() && (block == Blocks.FLOWING_WATER || block == Blocks.WATER)) {
			int i = pos.getX();
			int j = pos.getY();
			int k = pos.getZ();
			worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

			for (int l = 0; l < 8; ++l) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
			}
		} else {
			if (!worldIn.isRemote && !isSolid && !material.isLiquid()) {
				worldIn.destroyBlock(pos, true);
			}

			worldIn.setBlockState(pos, block.getDefaultState(), 3);
			worldIn.notifyNeighborsOfStateChange(pos, block, true);
		}
		return true;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return getAmount(stack) >= Fluid.BUCKET_VOLUME;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		int amount = getAmount(itemStack);
		setAmount(itemStack, amount - Fluid.BUCKET_VOLUME);

		return this.tryBreakBucket(itemStack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		// Don't show if the bucket is empty
		if (getAmount(stack) <= 0)
			return false;
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		// Get remainder calculations from stored and maxAmount
		int reversedAmount = this.maxCapacity - getAmount(stack);
		return (double) reversedAmount / (double) this.maxCapacity;
	}

	public ItemStack tryBreakBucket(ItemStack stack) {
		if (getAmount(stack) <= 0) {
			if (!this.onBroken.isEmpty()) {
				return this.onBroken.copy();
			} else {
				return this.empty.copy();
			}
		}
		return stack.copy();
	}

	public static String getFluid(ItemStack stack) {
		NBTTagCompound tag = NBTHelper.getTagCompound(stack, FluidHandlerItemStack.FLUID_NBT_KEY);
		return NBTHelper.getString(tag, "FluidName");
	}

	public static void setFluid(ItemStack stack, String fluidName) {
		NBTTagCompound tag = NBTHelper.getTagCompound(stack, FluidHandlerItemStack.FLUID_NBT_KEY);
		NBTHelper.setString(tag, "FluidName", fluidName);
	}

	public static int getAmount(ItemStack stack) {
		NBTTagCompound tag = NBTHelper.getTagCompound(stack, FluidHandlerItemStack.FLUID_NBT_KEY);
		return NBTHelper.getInt(tag, "Amount");
	}

	public static void setAmount(ItemStack stack, int amount) {
		NBTTagCompound tag = NBTHelper.getTagCompound(stack, FluidHandlerItemStack.FLUID_NBT_KEY);
		NBTHelper.setInt(tag, "Amount", amount);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new BucketFluidHandler(stack, empty, maxCapacity);
	}

	public static class BucketFluidHandler extends FluidHandlerItemStack.SwapEmpty {

		public BucketFluidHandler(ItemStack container, ItemStack emptyContainer, int capacity) {
			super(container, emptyContainer, capacity);
		}

	}
}