package com.grim3212.mc.pack.world.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.part.IPartEntities;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.world.GrimWorld;
import com.grim3212.mc.pack.world.config.WorldConfig;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class WorldEntities implements IPartEntities {

	@Override
	public void initEntities() {
		// Ice Pixie
		EntityRegistry.registerModEntity(EntityIcePixie.class, "IcePixie", 0, GrimPack.INSTANCE, 80, 3, true, 13887466, 7121318);
		EntityRegistry.registerModEntity(EntityIceCube.class, "IceCube", 1, GrimPack.INSTANCE, 80, 3, true);

		// Treasure Mob
		EntityRegistry.registerModEntity(EntityTreasureMob.class, "Treasure", 2, GrimPack.INSTANCE, 80, 3, true, 10777127, 16776192);

		// More People
		if (WorldConfig.spawnMorePeople) {
			EntityRegistry.registerModEntity(EntityNotch.class, "Notch", 3, GrimPack.INSTANCE, 80, 3, true, 16744319, 848639);
			EntityRegistry.registerModEntity(EntityPsycho.class, "Psychopath", 4, GrimPack.INSTANCE, 80, 3, true, 16744319, 16711680);
			EntityRegistry.registerModEntity(EntityFarmer.class, "Farmer", 5, GrimPack.INSTANCE, 80, 3, true, 16744319, 8336128);
			EntityRegistry.registerModEntity(EntityLumberJack.class, "LumberJack", 6, GrimPack.INSTANCE, 80, 3, true, 16744319, 10836174);
			EntityRegistry.registerModEntity(EntityMiner.class, "Miner", 7, GrimPack.INSTANCE, 80, 3, true, 16744319, 16753920);
			EntityRegistry.registerModEntity(EntityBomber.class, "SuicideBomber", 8, GrimPack.INSTANCE, 80, 3, true, 16744319, 4210752);
		}

	}

	public void addSpawns() {
		Iterator<Biome> itr = Biome.REGISTRY.iterator();
		List<Biome> biomes = new ArrayList<Biome>();
		while (itr.hasNext()) {
			biomes.add(itr.next());
		}

		Biome[] biomeArray = (Biome[]) biomes.toArray();

		GrimLog.info(GrimWorld.partName, "Biome array size " + biomeArray.length);

		// Ice pixie
		EntityRegistry.addSpawn(EntityIcePixie.class, 100, 5, 5, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.SNOWY));

		// TreasureMob
		EntityRegistry.addSpawn(EntityTreasureMob.class, 10, 1, 1, EnumCreatureType.CREATURE, biomeArray);

		// More People
		if (WorldConfig.spawnMorePeople) {
			EntityRegistry.addSpawn(EntityNotch.class, 10, 0, 1, EnumCreatureType.CREATURE, biomeArray);
			EntityRegistry.addSpawn(EntityPsycho.class, 100, 1, 2, EnumCreatureType.CREATURE, biomeArray);
			EntityRegistry.addSpawn(EntityFarmer.class, 15, 1, 3, EnumCreatureType.CREATURE, biomeArray);
			EntityRegistry.addSpawn(EntityLumberJack.class, 15, 1, 2, EnumCreatureType.CREATURE, biomeArray);
			EntityRegistry.addSpawn(EntityMiner.class, 15, 1, 2, EnumCreatureType.AMBIENT, biomeArray);
			EntityRegistry.addSpawn(EntityBomber.class, 12, 1, 5, EnumCreatureType.MONSTER, biomeArray);
		}
	}
}
