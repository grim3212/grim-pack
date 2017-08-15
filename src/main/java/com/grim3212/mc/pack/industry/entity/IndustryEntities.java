package com.grim3212.mc.pack.industry.entity;

import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.industry.config.IndustryConfig;

public class IndustryEntities {

	public static void initEntities() {
		if (IndustryConfig.subpartExtruder)
			Utils.registerEntity(EntityExtruder.class, "Extruder", 64, 15, true);
	}

}
