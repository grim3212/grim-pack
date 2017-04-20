package com.grim3212.mc.pack.core.entity;

import net.minecraft.entity.Entity;

public class EntityUtils {

	public static boolean isTypePassenger(Entity vehicle, Class<? extends Entity> type) {
		for (Entity entity : vehicle.getPassengers()) {
			if (type.isInstance(entity)) {
				return true;
			}
		}
		return false;
	}

}
