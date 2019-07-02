package com.grim3212.mc.pack.core.util;

import java.util.Iterator;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Stopwatch;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.network.MessageBetterExplosion;
import com.grim3212.mc.pack.core.network.PacketDispatcher;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.wrappers.BlockWrapper;
import net.minecraftforge.fluids.capability.wrappers.FluidBlockWrapper;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.PlayerOffhandInvWrapper;
import net.minecraftforge.items.wrapper.RangedWrapper;

public class Utils {

	public static final AxisAlignedBB NULL_AABB = new AxisAlignedBB(0f, 0f, 0f, 0f, 0f, 0f);

	/*
	 * public static ToolMaterial addToolMaterial(ToolMaterialHolder material) {
	 * return EnumHelper.addToolMaterial(material.getName(),
	 * material.getHarvestLevel(), material.getMaxUses(), material.getEfficiency(),
	 * material.getDamage(), material.getEnchantability()); }
	 * 
	 * public static ArmorMaterial addArmorMaterial(String textureName, SoundEvent
	 * sound, ArmorMaterialHolder material) { return
	 * EnumHelper.addArmorMaterial(material.getName(), textureName,
	 * material.getDurability(), material.getReductionAmounts(),
	 * material.getEnchantability(), sound, material.getToughness()); }
	 */

	public static SoundEvent createSound(String name) {
		ResourceLocation location = new ResourceLocation(name);
		return new SoundEvent(location).setRegistryName(location);
	}

	/**
	 * From https://github.com/Choonster/TestMod3/blob/
	 * 77706b1507c7527a2bb944317b31bca4e3d65d2e/src/main/java/com/choonster/
	 * testmod3/item/ItemModBow.java#L55-L91
	 *
	 * @param player
	 * @param checkStack
	 * @return The handler for the itemstack
	 */
	public static IItemHandler findItemStackSlot(PlayerEntity player, Predicate<ItemStack> checkStack) {
		if (checkStack.test(player.getHeldItemOffhand())) {
			return new PlayerOffhandInvWrapper(player.inventory);
		}

		// Vertical facing = main inventory
		final Direction mainInventoryFacing = Direction.UP;
		if (player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, mainInventoryFacing).isPresent()) {
			final IItemHandler mainInventory = (IItemHandler) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, mainInventoryFacing).cast();

			if (checkStack.test(player.getHeldItemMainhand())) {
				final int currentItem = player.inventory.currentItem;
				return new RangedWrapper((IItemHandlerModifiable) mainInventory, currentItem, currentItem + 1);
			}

			for (int slot = 0; slot < mainInventory.getSlots(); ++slot) {
				final ItemStack itemStack = mainInventory.getStackInSlot(slot);

				if (checkStack.test(itemStack)) {
					return new RangedWrapper((IItemHandlerModifiable) mainInventory, slot, slot + 1);
				}
			}
		}

		return null;
	}

	public static IItemHandler findItemStackSlot(IItemHandler handler, Predicate<ItemStack> checkStack) {
		for (int slot = 0; slot < handler.getSlots(); ++slot) {
			final ItemStack itemStack = handler.getStackInSlot(slot);

			if (checkStack.test(itemStack)) {
				return new RangedWrapper((IItemHandlerModifiable) handler, slot, slot + 1);
			}
		}

		return null;
	}

	@Nullable
	public static ItemStack consumePlayerItem(PlayerEntity player, final ItemStack item, int amount, boolean simulate) {
		IItemHandler handler = findItemStackSlot(player, new Predicate<ItemStack>() {
			@Override
			public boolean test(ItemStack t) {
				if (!t.isEmpty()) {
					return ItemStack.areItemsEqual(t, item);
				} else {
					return false;
				}
			}
		});

		if (handler != null) {
			return handler.extractItem(0, amount, simulate);
		}

		return ItemStack.EMPTY;
	}

	@Nullable
	public static ItemStack consumePlayerItem(PlayerEntity player, final ItemStack item) {
		return Utils.consumePlayerItem(player, item, 1, false);
	}

	@Nullable
	public static ItemStack consumeHandlerItem(IItemHandler handler, final ItemStack item, int amount, boolean simulate, final boolean ignoreMeta) {
		IItemHandler itemHandler = findItemStackSlot(handler, new Predicate<ItemStack>() {
			@Override
			public boolean test(ItemStack t) {
				if (!t.isEmpty()) {

					if (ignoreMeta) {
						return t.getItem() == item.getItem();
					}

					return ItemStack.areItemsEqual(t, item);
				} else {
					return false;
				}
			}
		});

		if (itemHandler != null) {
			return itemHandler.extractItem(0, amount, simulate);
		}

		return ItemStack.EMPTY;
	}

	@Nullable
	public static ItemStack consumeHandlerItem(IItemHandler handler, final ItemStack item) {
		return Utils.consumeHandlerItem(handler, item, 1, false, false);
	}

	public static IItemHandler getItemHandler(ItemStack stack) {
		if (stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).isPresent()) {
			return (IItemHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).cast();
		}
		return null;
	}

	public static boolean hasItemHandler(ItemStack stack) {
		return stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).isPresent();
	}

	/**
	 * This is for the Item capability hence FLUID_HANDLER_ITEM_CAPABILITY Gets the
	 * IFluidHandler for this stack
	 *
	 * @param stack Stack to get capability from
	 * @return The capability if found otherwise null
	 */
	public static IFluidHandler getFluidHandler(ItemStack stack) {
		if (hasFluidHandler(stack)) {
			return (IFluidHandler) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null).cast();
		}
		return null;
	}

	/**
	 * This is for the Item capability hence FLUID_HANDLER_ITEM_CAPABILITY Check if
	 * this stack has the fluid capability
	 *
	 * @param stack Stack to check for capability
	 * @return True if this has the capability
	 */
	public static boolean hasFluidHandler(ItemStack stack) {
		return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null).isPresent();
	}

	/**
	 * This is for the Item capability hence FLUID_HANDLER_ITEM_CAPABILITY Gets the
	 * IFluidHandler for this stack
	 *
	 * @param stack Stack to get capability from
	 * @return The capability if found otherwise null
	 */
	public static IFluidHandler getFluidHandler(TileEntity te) {
		if (hasFluidHandler(te)) {
			return (IFluidHandler) te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).cast();
		}
		return null;
	}

	/**
	 * This is for the Item capability hence FLUID_HANDLER_ITEM_CAPABILITY Check if
	 * this stack has the fluid capability
	 *
	 * @param stack Stack to check for capability
	 * @return True if this has the capability
	 */
	public static boolean hasFluidHandler(TileEntity te) {
		return te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null).isPresent();
	}

	public static BetterExplosion createExplosion(World world, Entity entity, double x, double y, double z, float size, Mode destroyBlocks, boolean hurtEntities) {
		return newExplosion(world, entity, x, y, z, size, false, destroyBlocks, hurtEntities);
	}

	public static BetterExplosion newExplosion(World world, Entity entity, double x, double y, double z, float size, boolean flaming, Mode destroyBlocks, boolean hurtEntities) {
		BetterExplosion explosion = new BetterExplosion(world, entity, x, y, z, size, flaming, destroyBlocks, hurtEntities);
		if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(world, explosion)) {
			return explosion;
		}
		explosion.doExplosionA();
		explosion.doExplosionB(true);

		Iterator<? extends PlayerEntity> iterator = world.getPlayers().iterator();

		while (iterator.hasNext()) {
			PlayerEntity entityPlayer = (PlayerEntity) iterator.next();

			if (entityPlayer.getDistanceSq(x, y, z) < 4096.0D) {
				PacketDispatcher.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) entityPlayer), new MessageBetterExplosion(x, y, z, size, destroyBlocks, explosion.getAffectedBlockPositions(), (Vec3d) explosion.getPlayerKnockbackMap().get(entityPlayer)));
			}
		}

		// end new
		return explosion;
	}

	/**
	 * Checks if a string is an integer http://stackoverflow.com/a/5439547
	 *
	 * @param s
	 * @return True if it is an integer
	 */
	public static boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	public static float getDistance(int i, int j, int k, int l) {
		float f = MathHelper.abs(i - k);
		float f1 = MathHelper.abs(j - l);
		return MathHelper.sqrt(f * f + f1 * f1);
	}

	/**
	 * Tries to place a fluid in the world in block form and drains the container.
	 * Makes a fluid emptying sound when successful. Honors the amount of fluid
	 * contained by the used container. Checks if water-like fluids should vaporize
	 * like in the nether.
	 * <p>
	 * Modeled after
	 * {@link BucketItem#tryPlaceContainedLiquid(PlayerEntity, World, BlockPos)}
	 *
	 * @param player   Player who places the fluid. May be null for blocks like
	 *                 dispensers.
	 * @param world    World to place the fluid in
	 * @param pos      The position in the world to place the fluid block
	 * @param resource The fluidStack to place
	 * @return the container's ItemStack with the remaining amount of fluid if the
	 *         placement was successful, null otherwise
	 */
	@Nonnull
	public static void tryPlaceFluid(@Nullable PlayerEntity player, World world, BlockPos pos, IFluidHandler fluidHandler, FluidStack resource) {
		if (world == null || resource == null || pos == null) {
			return;
		}

		Fluid fluid = resource.getFluid();
		if (fluid == null || !fluid.canBePlacedInWorld()) {
			return;
		}

		// check that we can place the fluid at the destination
		BlockState destBlockState = world.getBlockState(pos);
		Material destMaterial = destBlockState.getMaterial();
		boolean isDestNonSolid = !destMaterial.isSolid();
		boolean isDestReplaceable = destMaterial.isReplaceable();
		if (!world.isAirBlock(pos) && !isDestNonSolid && !isDestReplaceable) {
			return; // Non-air, solid, unreplacable block. We can't put fluid
			// here.
		}

		if (world.getDimension().doesWaterVaporize() && fluid.doesVaporize(resource)) {
			fluid.vaporize(player, world, pos, resource);
		} else {
			if (!world.isRemote && (isDestNonSolid || isDestReplaceable) && !destMaterial.isLiquid()) {
				world.destroyBlock(pos, true);
			}

			// Defer the placement to the fluid block
			// Instead of actually "filling", the fluid handler method replaces
			// the block
			Block block = fluid.getBlock();

			IFluidHandler handler;
			if (block instanceof IFluidBlock) {
				handler = new FluidBlockWrapper((IFluidBlock) block, world, pos);
			} else if (block instanceof FlowingFluidBlock) {
				// handler = new BlockLiquidWrapper((BlockLiquid) block, world,
				// pos);
				world.setBlockState(pos, block.getDefaultState(), 11);
				return;
			} else {
				handler = new BlockWrapper(block, world, pos);
			}

			FluidUtil.tryFluidTransfer(handler, fluidHandler, Integer.MAX_VALUE, true);
		}
	}

	public static class UtilTimer {
		// Based off of JEI LoggedTimer
		private final Stopwatch stopWatch = Stopwatch.createUnstarted();
		private String message = "";
		private final boolean debug;

		public UtilTimer() {
			this(false);
		}

		public UtilTimer(boolean debug) {
			this.debug = debug;
		}

		public void start(String message) {
			this.message = message;
			if (debug)
				GrimLog.debugInfo(GrimPack.modName, message + " starting...");
			else
				GrimLog.info(GrimPack.modName, message + " starting...");
			stopWatch.reset();
			stopWatch.start();
		}

		public void stop() {
			stopWatch.stop();
			if (debug)
				GrimLog.debugInfo(GrimPack.modName, message + " completed in " + stopWatch);
			else
				GrimLog.info(GrimPack.modName, message + " completed in " + stopWatch);
		}
	}
}
