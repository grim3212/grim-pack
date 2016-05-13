package com.grim3212.mc.pack.world.entity;

import com.grim3212.mc.pack.core.part.IPartEntities;
import com.grim3212.mc.pack.world.GrimWorld;
import com.grim3212.mc.pack.world.config.WorldConfig;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class WorldEntities implements IPartEntities {

	@Override
	public void initEntities() {
		// Ice Pixie
		EntityRegistry.registerModEntity(EntityIcePixie.class, "IcePixie", 0, GrimWorld.INSTANCE, 80, 3, true, 13887466, 7121318);
		EntityRegistry.registerModEntity(EntityIceCube.class, "IceCube", 1, GrimWorld.INSTANCE, 80, 3, true);

		// Treasure Mob
		EntityRegistry.registerModEntity(EntityTreasureMob.class, "Treasure", 2, GrimWorld.INSTANCE, 80, 3, true, 10777127, 16776192);

		// More People
		if (WorldConfig.spawnMorePeople) {
			EntityRegistry.registerModEntity(EntityNotch.class, "Notch", 3, GrimWorld.INSTANCE, 80, 3, true, 16744319, 848639);
			EntityRegistry.registerModEntity(EntityPsycho.class, "Psychopath", 4, GrimWorld.INSTANCE, 80, 3, true, 16744319, 16711680);
			EntityRegistry.registerModEntity(EntityFarmer.class, "Farmer", 5, GrimWorld.INSTANCE, 80, 3, true, 16744319, 8336128);
			EntityRegistry.registerModEntity(EntityLumberJack.class, "LumberJack", 6, GrimWorld.INSTANCE, 80, 3, true, 16744319, 10836174);
			EntityRegistry.registerModEntity(EntityMiner.class, "Miner", 7, GrimWorld.INSTANCE, 80, 3, true, 16744319, 16753920);
			EntityRegistry.registerModEntity(EntityBomber.class, "SuicideBomber", 8, GrimWorld.INSTANCE, 80, 3, true, 16744319, 4210752);
		}

	}

	public void addSpawns() {
		// Ice pixie
		EntityRegistry.addSpawn(EntityIcePixie.class, 100, 5, 5, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.SNOWY));

		// TreasureMob
		EntityRegistry.addSpawn(EntityTreasureMob.class, 10, 1, 1, EnumCreatureType.CREATURE, BiomeGenBase.getBiomeGenArray());

		// More People
		if (WorldConfig.spawnMorePeople) {
			EntityRegistry.addSpawn(EntityNotch.class, 10, 0, 1, EnumCreatureType.CREATURE, BiomeGenBase.getBiomeGenArray());
			EntityRegistry.addSpawn(EntityPsycho.class, 100, 1, 2, EnumCreatureType.CREATURE, BiomeGenBase.getBiomeGenArray());
			EntityRegistry.addSpawn(EntityFarmer.class, 15, 1, 3, EnumCreatureType.CREATURE, BiomeGenBase.getBiomeGenArray());
			EntityRegistry.addSpawn(EntityLumberJack.class, 15, 1, 2, EnumCreatureType.CREATURE, BiomeGenBase.getBiomeGenArray());
			EntityRegistry.addSpawn(EntityMiner.class, 15, 1, 2, EnumCreatureType.AMBIENT, BiomeGenBase.getBiomeGenArray());
			EntityRegistry.addSpawn(EntityBomber.class, 12, 1, 5, EnumCreatureType.MONSTER, BiomeGenBase.getBiomeGenArray());
		}
	}
}
