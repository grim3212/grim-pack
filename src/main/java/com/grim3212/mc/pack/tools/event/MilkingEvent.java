package com.grim3212.mc.pack.tools.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.tools.items.ItemBetterBucket;
import com.grim3212.mc.pack.tools.items.ItemBetterMilkBucket;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fluids.Fluid;
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
	public void interact(EntityInteract event) {

		if (event.getEntityPlayer().getHeldItem(event.getHand()) != null) {
			if (event.getEntityPlayer().getHeldItem(event.getHand()).getItem() instanceof ItemBetterBucket || event.getEntityPlayer().getHeldItem(event.getHand()).getItem() instanceof ItemBetterMilkBucket) {
				this.tryMilk(event.getEntityPlayer().getHeldItem(event.getHand()), event);
			}
		}
	}

	private void tryMilk(ItemStack stack, EntityInteract event) {
		if (event.getTarget() instanceof EntityLivingBase && !event.getTarget().worldObj.isRemote) {
			if (!event.getEntityPlayer().capabilities.isCreativeMode && !((EntityLivingBase) event.getTarget()).isChild()) {
				if (stack.getItem() instanceof ItemBetterMilkBucket) {
					ItemBetterMilkBucket bucket = (ItemBetterMilkBucket) stack.getItem();
					int milkingLevel = bucket.getParent().milkingLevel;

					if (bucket != null) {
						for (int i = 0; i <= milkingLevel; i++) {
							for (int j = 0; j < levels.get(i).size(); j++) {
								if (levels.get(i).contains(event.getTarget().getClass())) {
									if (ItemBetterBucket.getAmount(stack) < bucket.getParent().maxCapacity) {
										event.getEntityPlayer().playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);

										int amount = ItemBetterBucket.getAmount(stack);
										ItemBetterBucket.setFluid(stack, "milk");
										ItemBetterBucket.setAmount(stack, amount + Fluid.BUCKET_VOLUME);

										bucket.pauseForMilk();
									}
								}
							}
						}
					}
				} else if (stack.getItem() instanceof ItemBetterBucket) {
					ItemBetterBucket bucket = (ItemBetterBucket) stack.getItem();
					int milkingLevel = bucket.milkingLevel;

					if (bucket != null) {
						for (int i = 0; i <= milkingLevel; i++) {
							for (int j = 0; j < levels.get(i).size(); j++) {
								if (levels.get(i).contains(event.getTarget().getClass())) {
									if (ItemBetterBucket.isEmptyOrContains(stack, "milk")) {
										if (ItemBetterBucket.getAmount(stack) < bucket.maxCapacity) {
											event.getEntityPlayer().playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);

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

											ItemBetterBucket.setFluid(milkBucket, "milk");
											ItemBetterBucket.setAmount(milkBucket, Fluid.BUCKET_VOLUME);

											if (event.getHand() == EnumHand.MAIN_HAND)
												event.getEntityPlayer().inventory.setInventorySlotContents(event.getEntityPlayer().inventory.currentItem, milkBucket);
											else
												event.getEntityPlayer().inventory.setInventorySlotContents(40, milkBucket);

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
