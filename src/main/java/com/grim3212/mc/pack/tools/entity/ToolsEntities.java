package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.core.part.IPartEntities;
import com.grim3212.mc.pack.core.util.Utils;

public class ToolsEntities implements IPartEntities {

	@Override
	public void initEntities() {
		Utils.registerEntity(EntityBallisticKnife.class, "BallisticKnife", 32, 5, true);
		Utils.registerEntity(EntityKnife.class, "Knife", 32, 5, true);
		Utils.registerEntity(EntityTomahawk.class, "Tomahawk", 32, 5, true);
		Utils.registerEntity(EntityPokeball.class, "Pokeball", 32, 5, true);
		Utils.registerEntity(EntityBlockPushPull.class, "BlockPushPull", 32, 5, true);
		Utils.registerEntity(EntityRayw.class, "RayShot", 32, 5, true);
		Utils.registerEntity(EntityAdvRayw.class, "AdvRayShot", 32, 5, true);
		Utils.registerEntity(EntitySlingpellet.class, "SlingPellet", 32, 5, true);
		Utils.registerEntity(EntitySpear.class, "spear", 32, 5, true);
		Utils.registerEntity(EntitySlimeSpear.class, "slime-spear", 32, 1, true);
		Utils.registerEntity(EntityBoomerang.class, "boomerang", 32, 1, true);
		Utils.registerEntity(EntityDiamondBoomerang.class, "diamond-boomerang", 32, 1, true);
	}

}
