package com.grim3212.mc.tools.entity;

import com.grim3212.mc.core.part.IPartEntities;
import com.grim3212.mc.tools.GrimTools;

import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ToolsEntities implements IPartEntities {

	@Override
	public void initEntities() {
		EntityRegistry.registerModEntity(EntityBallisticKnife.class, "BallisticKnife", 0, GrimTools.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(EntityKnife.class, "Knife", 1, GrimTools.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(EntityTomahawk.class, "Tomahawk", 2, GrimTools.INSTANCE, 32, 5, true);
	}

}
