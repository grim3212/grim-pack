package com.grim3212.mc.pack.industry.entity;

import com.grim3212.mc.pack.core.part.IPartEntities;
import com.grim3212.mc.pack.core.util.Utils;

public class IndustryEntities implements IPartEntities {

	@Override
	public void initEntities() {
		Utils.registerEntity(EntityExtruder.class, "Extruder", 64, 15, true);
	}

}
