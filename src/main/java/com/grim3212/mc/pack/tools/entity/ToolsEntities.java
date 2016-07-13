package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.part.IPartEntities;
import com.grim3212.mc.pack.core.util.Utils;

import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ToolsEntities implements IPartEntities {

	@Override
	public void initEntities() {
		EntityRegistry.registerModEntity(EntityBallisticKnife.class, "BallisticKnife", Utils.entityID++, GrimPack.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(EntityKnife.class, "Knife", Utils.entityID++, GrimPack.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(EntityTomahawk.class, "Tomahawk", Utils.entityID++, GrimPack.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(EntityPokeball.class, "Pokeball", Utils.entityID++, GrimPack.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(EntityBlockPushPull.class, "BlockPushPull", Utils.entityID++, GrimPack.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(EntityRayw.class, "RayShot", Utils.entityID++, GrimPack.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(EntityAdvRayw.class, "AdvRayShot", Utils.entityID++, GrimPack.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(EntitySlingpellet.class, "SlingPellet", Utils.entityID++, GrimPack.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(EntitySpear.class, "spear", Utils.entityID++, GrimPack.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(EntitySlimeSpear.class, "slime-spear", Utils.entityID++, GrimPack.INSTANCE, 32, 1, true);
	}

}
