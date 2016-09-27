package com.grim3212.mc.pack.core.util;

import java.util.function.Predicate;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.item.ItemManualBlock;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.PlayerOffhandInvWrapper;
import net.minecraftforge.items.wrapper.RangedWrapper;

public class Utils {

	private static int entityID = 0;
	public static final AxisAlignedBB NULL_AABB = new AxisAlignedBB(0f, 0f, 0f, 0f, 0f, 0f);

	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(entityClass, entityName, entityID++, GrimPack.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary) {
		EntityRegistry.registerModEntity(entityClass, entityName, entityID++, GrimPack.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates, eggPrimary, eggSecondary);
	}

	public static void registerBlock(Block block, String name) {
		GameRegistry.register(block, new ResourceLocation(GrimPack.modID, name));
		GameRegistry.register(new ItemManualBlock(block), block.getRegistryName());
	}

	public static void registerBlock(Block block, String name, ItemBlock item) {
		GameRegistry.register(block, new ResourceLocation(GrimPack.modID, name));
		GameRegistry.register(item, block.getRegistryName());
	}

	public static void registerItem(Item item, String name) {
		GameRegistry.register(item, new ResourceLocation(GrimPack.modID, name));
	}

	public static SoundEvent registerSound(String name) {
		ResourceLocation location = new ResourceLocation(GrimPack.modID, name);
		SoundEvent sound = new SoundEvent(location);
		GameRegistry.register(sound, location);
		return sound;
	}

	/**
	 * 
	 * From https://github.com/Choonster/TestMod3/blob/
	 * 77706b1507c7527a2bb944317b31bca4e3d65d2e/src/main/java/com/choonster/
	 * testmod3/item/ItemModBow.java#L55-L91
	 * 
	 * @param player
	 * @param checkStack
	 * @return
	 */
	public static IItemHandler findItemStackSlot(EntityPlayer player, Predicate<ItemStack> checkStack) {
		if (checkStack.test(player.getHeldItemOffhand())) {
			return new PlayerOffhandInvWrapper(player.inventory);
		}

		// Vertical facing = main inventory
		final EnumFacing mainInventoryFacing = EnumFacing.UP;
		if (player.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, mainInventoryFacing)) {
			final IItemHandler mainInventory = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, mainInventoryFacing);

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

	public static boolean consumeInventoryItem(EntityPlayer player, final Item item, int amount) {
		IItemHandler handler = findItemStackSlot(player, new Predicate<ItemStack>() {
			@Override
			public boolean test(ItemStack t) {
				if (t != null && item != null) {
					return t.getItem() == item;
				} else {
					return false;
				}
			}
		});

		if (handler != null) {
			handler.extractItem(0, amount, false);
			return true;
		}

		return false;
	}

	public static boolean consumeInventoryItem(EntityPlayer player, final Item item) {
		return Utils.consumeInventoryItem(player, item, 1);
	}

	public static IFluidHandler getFluidHandler(ItemStack stack) {
		if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
			return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		}
		return null;
	}

	public static boolean hasFluidHandler(ItemStack stack) {
		if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
			return true;
		}
		return false;
	}
}
