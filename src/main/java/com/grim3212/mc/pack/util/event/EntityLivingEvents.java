package com.grim3212.mc.pack.util.event;

import com.grim3212.mc.pack.util.config.UtilConfig;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityLivingEvents {

	@SubscribeEvent
	public void dropFeather(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof EntityChicken) {
			EntityChicken chicken = (EntityChicken) event.getEntityLiving();

			if (!chicken.world.isRemote && !chicken.isChild() && !chicken.isChickenJockey()) {
				if (chicken.world.rand.nextInt(UtilConfig.featherRate) == 0) {
					chicken.dropItem(Items.FEATHER, 1);
				}
			}
		}
	}
}