package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.config.ToolsConfig;

public class ToolsEntities {

	public static void initEntities() {
		if (ToolsConfig.subpartKnives) {
			Utils.registerEntity(EntityBallisticKnife.class, "BallisticKnife", 32, 5, true);
			Utils.registerEntity(EntityKnife.class, "Knife", 32, 5, true);
			Utils.registerEntity(EntityTomahawk.class, "Tomahawk", 32, 5, true);
		}

		if (ToolsConfig.subpartPokeball)
			Utils.registerEntity(EntityPokeball.class, "Pokeball", 32, 5, true);

		if (ToolsConfig.subpartPowerstaff)
			Utils.registerEntity(EntityBlockPushPull.class, "BlockPushPull", 32, 5, true);

		if (ToolsConfig.subpartRayGuns) {
			Utils.registerEntity(EntityRayw.class, "RayShot", 32, 5, true);
			Utils.registerEntity(EntityAdvRayw.class, "AdvRayShot", 32, 5, true);
		}

		if (ToolsConfig.subpartSlingshots)
			Utils.registerEntity(EntitySlingPellet.class, "SlingPellet", 32, 5, true);

		if (ToolsConfig.subpartSpears) {
			Utils.registerEntity(EntitySpear.class, "spear", 32, 5, true);
			Utils.registerEntity(EntitySlimeSpear.class, "slime-spear", 32, 1, true);
		}

		if (ToolsConfig.subpartBoomerangs) {
			Utils.registerEntity(EntityBoomerang.class, "boomerang", 32, 1, true);
			Utils.registerEntity(EntityDiamondBoomerang.class, "diamond-boomerang", 32, 1, true);
		}

		if (ToolsConfig.subpartGrenadeLauncher)
			Utils.registerEntity(EntityGrenade.class, "grenade", 32, 5, true);

		if (ToolsConfig.subpartDetonators) {
			Utils.registerEntity(EntityDetonator.class, "detonators", 32, 5, true);
		}
	}

}
