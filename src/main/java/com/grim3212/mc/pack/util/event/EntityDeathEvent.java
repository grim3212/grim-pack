package com.grim3212.mc.pack.util.event;

import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.grave.PlaceGrave;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityDeathEvent {

	@SubscribeEvent
	public void OnEntityDeath(LivingDeathEvent event) {
		if ((event.getEntityLiving() instanceof EntityPlayerMP) && UtilConfig.spawnGraves && !event.getEntityLiving().getEntityWorld().getGameRules().getBoolean("keepInventory")) {
			PlaceGrave.placeGrave((EntityPlayerMP) event.getEntityLiving());
		}
	}
}