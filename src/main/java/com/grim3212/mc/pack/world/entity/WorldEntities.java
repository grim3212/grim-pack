package com.grim3212.mc.pack.world.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterables;
import com.grim3212.mc.pack.core.part.IPartEntities;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.world.GrimWorld;
import com.grim3212.mc.pack.world.config.WorldConfig;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEnd;
import net.minecraft.world.biome.BiomeHell;
import net.minecraft.world.biome.BiomeVoid;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class WorldEntities implements IPartEntities {

	@Override
	public void initEntities() {
		// Ice Pixie
		Utils.registerEntity(EntityIcePixie.class, "IcePixie", 80, 3, true, 13887466, 7121318);
		Utils.registerEntity(EntityIceCube.class, "IceCube", 80, 3, true);

		// Treasure Mob
		Utils.registerEntity(EntityTreasureMob.class, "Treasure", 80, 3, true, 10777127, 16776192);

		// More People
		Utils.registerEntity(EntityNotch.class, "Notch", 80, 3, true, 16744319, 848639);
		Utils.registerEntity(EntityPsycho.class, "Psychopath", 80, 3, true, 16744319, 16711680);
		Utils.registerEntity(EntityFarmer.class, "Farmer", 80, 3, true, 16744319, 8336128);
		Utils.registerEntity(EntityLumberJack.class, "LumberJack", 80, 3, true, 16744319, 10836174);
		Utils.registerEntity(EntityMiner.class, "Miner", 80, 3, true, 16744319, 16753920);
		Utils.registerEntity(EntityBomber.class, "SuicideBomber", 80, 3, true, 16744319, 4210752);
	}

	public static void addSpawns() {
		List<Biome> compatibleBiomes = new ArrayList<Biome>();
		Iterator<Biome> itr = Biome.REGISTRY.iterator();
		while (itr.hasNext()) {
			Biome biome = itr.next();

			if (!(biome instanceof BiomeHell) && !(biome instanceof BiomeEnd) && !(biome instanceof BiomeVoid))
				compatibleBiomes.add(biome);
		}
		Biome[] biomes = Iterables.toArray(compatibleBiomes, Biome.class);

		GrimLog.info(GrimWorld.partName, "Biome array size " + biomes.length);

		// Ice pixie
		if (WorldConfig.spawnIcePixies)
			EntityRegistry.addSpawn(EntityIcePixie.class, 100, 2, 5, EnumCreatureType.CREATURE, BiomeDictionary.getBiomes(Type.SNOWY).toArray(new Biome[BiomeDictionary.getBiomes(Type.SNOWY).size()]));

		// TreasureMob
		if (WorldConfig.spawnTreasureMobs)
			EntityRegistry.addSpawn(EntityTreasureMob.class, 10, 1, 1, EnumCreatureType.CREATURE, biomes);

		// More People
		if (WorldConfig.spawnMorePeople) {
			EntityRegistry.addSpawn(EntityNotch.class, 4, 0, 1, EnumCreatureType.CREATURE, biomes);
			EntityRegistry.addSpawn(EntityPsycho.class, 4, 1, 2, EnumCreatureType.CREATURE, biomes);
			EntityRegistry.addSpawn(EntityFarmer.class, 8, 1, 2, EnumCreatureType.CREATURE, biomes);
			EntityRegistry.addSpawn(EntityLumberJack.class, 8, 1, 2, EnumCreatureType.CREATURE, biomes);
			EntityRegistry.addSpawn(EntityMiner.class, 4, 1, 2, EnumCreatureType.CREATURE, biomes);
			EntityRegistry.addSpawn(EntityBomber.class, 4, 1, 2, EnumCreatureType.CREATURE, biomes);
		}
	}
}
