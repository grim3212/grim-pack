package com.grim3212.mc.pack.tools.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.tools.items.ItemBetterBucket;
import com.grim3212.mc.pack.tools.items.ItemBetterMilkBucket;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MilkingEvent {

	public static List<List<Class<? extends Entity>>> levels = Lists.newArrayList();

	static {
		addMilkable(0, EntityCow.class);
		addMilkable(1, EntitySheep.class);
		addMilkable(2, EntityPig.class);
	}

	/**
	 * Adds a milkable entity to the specified level All levels above the
	 * specified level will also be able to milk the same entity
	 * 
	 * @param level
	 *            The milking level to add to
	 * @param milkable
	 *            The entity to add
	 * @return True if adding was a success and it wasn't a duplicate
	 */
	public static boolean addMilkable(int level, Class<? extends Entity> milkable) {
		if (levels.size() <= level)
			levels.add(level, new ArrayList<Class<? extends Entity>>());

		if (Collections.frequency(levels.get(level), milkable) > 1)
			return false;

		return levels.get(level).add(milkable);
	}

	@SubscribeEvent
	public void interact(EntityInteractEvent event) {
		if (event.target.interactFirst(event.entityPlayer)) {
			return;
		}
		if (event.entityPlayer.getHeldItem() != null && event.target instanceof EntityLivingBase && !event.entity.worldObj.isRemote) {
			if (!event.entityPlayer.capabilities.isCreativeMode && !((EntityLivingBase) event.target).isChild()) {
				if (event.entityPlayer.getHeldItem().getItem() instanceof ItemBetterMilkBucket) {
					ItemStack stack = event.entityPlayer.getHeldItem();
					ItemBetterMilkBucket bucket = (ItemBetterMilkBucket) stack.getItem();
					int milkingLevel = bucket.getParent().milkingLevel;

					if (bucket != null) {
						for (int i = 0; i <= milkingLevel; i++) {
							for (int j = 0; j < levels.get(i).size(); j++) {
								if (levels.get(i).contains(event.target.getClass())) {
									if (NBTHelper.getInt(stack, "Amount") < bucket.getParent().maxCapacity) {
										int amount = NBTHelper.getInt(stack, "Amount");
										NBTHelper.setInteger(stack, "Amount", amount + FluidContainerRegistry.BUCKET_VOLUME);
										NBTHelper.setString(stack, "FluidName", "milk");

										bucket.pauseForMilk();
									}
								}
							}
						}
					}
				} else if (event.entityPlayer.getHeldItem().getItem() instanceof ItemBetterBucket) {
					ItemStack stack = event.entityPlayer.getHeldItem();
					ItemBetterBucket bucket = (ItemBetterBucket) stack.getItem();
					int milkingLevel = bucket.milkingLevel;

					if (bucket != null) {
						for (int i = 0; i <= milkingLevel; i++) {
							for (int j = 0; j < levels.get(i).size(); j++) {
								if (levels.get(i).contains(event.target.getClass())) {
									if (ItemBetterBucket.isEmptyOrContains(stack, "milk")) {
										if (NBTHelper.getInt(stack, "Amount") < bucket.maxCapacity) {
											int amount = NBTHelper.getInt(stack, "Amount");

											ItemStack milkBucket = null;
											switch (bucket.bucketType) {
											case diamond:
												milkBucket = new ItemStack(ToolsItems.diamond_milk_bucket);
												break;
											case gold:
												milkBucket = new ItemStack(ToolsItems.golden_milk_bucket);
												break;
											case obsidian:
												milkBucket = new ItemStack(ToolsItems.obsidian_milk_bucket);
												break;
											case stone:
												milkBucket = new ItemStack(ToolsItems.stone_milk_bucket);
												break;
											case wood:
												milkBucket = new ItemStack(ToolsItems.wooden_milk_bucket);
												break;

											}
											NBTHelper.setInteger(milkBucket, "Amount", amount + FluidContainerRegistry.BUCKET_VOLUME);
											NBTHelper.setString(milkBucket, "FluidName", "milk");
											event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, milkBucket);

											bucket.pauseForMilk();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
