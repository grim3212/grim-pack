package com.grim3212.mc.pack.industry.entity;

import com.grim3212.mc.pack.core.util.Utils;

public class IndustryEntities {

	public static void initEntities() {
		Utils.registerEntity(EntityExtruder.class, "Extruder", 64, 15, true);
	}

}
