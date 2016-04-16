package com.grim3212.mc.tools.util;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.Lists;
import com.grim3212.mc.core.util.NBTHelper;
import com.grim3212.mc.tools.items.ItemBetterBucket;
import com.grim3212.mc.tools.items.ItemBucketExtended;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MilkingEvent {

	public static List<Set<Class<? extends Entity>>> levels = Lists.newArrayList();

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
		if (levels.get(level) == null)
			levels.add(level, new TreeSet<Class<? extends Entity>>());

		return levels.get(level).add(milkable);
	}

	@SubscribeEvent
	public void interact(EntityInteractEvent event) {
		if (event.entityPlayer.getHeldItem() != null && event.target instanceof EntityLivingBase) {
			if (event.entityPlayer.getHeldItem().getItem() instanceof ItemBetterBucket) {
				ItemBetterBucket bucket = (ItemBetterBucket) event.entityPlayer.getHeldItem().getItem();
				int milkingLevel = bucket.milkingLevel;
				
				
				for(int i = 0; i < milkingLevel; i++){
					
				}
				
				for (int i = 0; i < entities.length; i++) {
					if (event.target.getClass() == entities[i]) {
						if (ItemBucketExtended.isEmptyOrContains(bucket, "milk")) {
							if (NBTHelper.getInt(bucket, "amount") != ((ItemBucketExtended) bucket.getItem()).getMaxAmount()) {
								int amount = NBTHelper.getInt(bucket, "amount");
								NBTHelper.setInteger(bucket, "amount", ++amount);
								NBTHelper.setString(bucket, "stored", "milk");
								setHasMilked(true);
							}
						}
					}
				}
			}
		}
	}
}
