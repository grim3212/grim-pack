package com.grim3212.mc.pack.util.event;

import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.grave.DigGrave;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityDeathEvent {

	@SubscribeEvent
	public void OnEntityDeath(LivingDeathEvent event) {
		if ((event.entityLiving instanceof EntityPlayerMP) && UtilConfig.spawnGraves) {
			DigGrave.digGrave((EntityPlayerMP) event.entityLiving);
		}
	}
}