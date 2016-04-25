package com.grim3212.mc.world.entity;

import com.grim3212.mc.core.part.IPartEntities;
import com.grim3212.mc.world.GrimWorld;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class WorldEntities implements IPartEntities {

	@Override
	public void initEntities() {
		EntityRegistry.registerModEntity(EntityIcePixie.class, "IcePixie", 0, GrimWorld.INSTANCE, 80, 3, true, 13887466, 7121318);
		EntityRegistry.registerModEntity(EntityIceCube.class, "IceCube", 1, GrimWorld.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityTreasureMob.class, "Treasure", 2, GrimWorld.INSTANCE, 80, 3, true, 10777127, 16776192);
	}

	public void addSpawns() {
		// Ice pixie
		for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
			if (BiomeGenBase.getBiomeGenArray()[i] != null && BiomeGenBase.getBiomeGenArray()[i].isSnowyBiome()) {
				EntityRegistry.addSpawn(EntityIcePixie.class, 200, 5, 5, EnumCreatureType.MONSTER, BiomeGenBase.getBiomeGenArray()[i]);
			}
		}

		EntityRegistry.addSpawn(EntityTreasureMob.class, 1, 1, 1, EnumCreatureType.CREATURE, BiomeGenBase.getBiomeGenArray());
	}
}
