package com.grim3212.mc.pack.compat.jer;

import java.util.Iterator;

import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.world.GrimWorld;
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.entity.EntityBomber;
import com.grim3212.mc.pack.world.entity.EntityFarmer;
import com.grim3212.mc.pack.world.entity.EntityIcePixie;
import com.grim3212.mc.pack.world.entity.EntityLumberJack;
import com.grim3212.mc.pack.world.entity.EntityMiner;
import com.grim3212.mc.pack.world.entity.EntityNotch;
import com.grim3212.mc.pack.world.entity.EntityParaBuzzy;
import com.grim3212.mc.pack.world.entity.EntityPsycho;
import com.grim3212.mc.pack.world.entity.EntityTreasureMob;
import com.grim3212.mc.pack.world.util.WorldLootTables;

import jeresources.api.IJERAPI;
import jeresources.api.JERPlugin;
import jeresources.api.conditionals.LightLevel;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class JERGrimPack {

	@JERPlugin
	public static IJERAPI jerAPI;

	public void registerMobs() {
		if (CoreConfig.useWorld) {
			GrimLog.debugInfo(GrimWorld.partName, "Registering JustEnoughResources compat");

			final World world = jerAPI.getWorld();

			if (WorldConfig.subpartIcePixie) {
				String[] snowyBiomes = new String[BiomeDictionary.getBiomes(Type.SNOWY).size()];
				Iterator<Biome> itr = BiomeDictionary.getBiomes(Type.SNOWY).iterator();
				int i = 0;
				while (itr.hasNext()) {
					snowyBiomes[i] = itr.next().getBiomeName();
					i++;
				}

				jerAPI.getMobRegistry().register(new EntityIcePixie(world), LightLevel.any, snowyBiomes, WorldLootTables.ENTITIES_ICE_PIXIE);
			}

			if (WorldConfig.subpartTreasureMobs)
				jerAPI.getMobRegistry().register(new EntityTreasureMob(world), WorldLootTables.ENTITIES_TREASURE_MOB);

			if (WorldConfig.subpartMorePeople) {
				jerAPI.getMobRegistry().register(new EntityBomber(world), WorldLootTables.ENTITIES_BOMBER);
				jerAPI.getMobRegistry().register(new EntityFarmer(world), WorldLootTables.ENTITIES_FARMER);
				jerAPI.getMobRegistry().register(new EntityLumberJack(world), WorldLootTables.ENTITIES_LUMBERJACK);
				jerAPI.getMobRegistry().register(new EntityMiner(world), WorldLootTables.ENTITIES_MINER);
				jerAPI.getMobRegistry().register(new EntityNotch(world), WorldLootTables.ENTITIES_NOTCH);
				jerAPI.getMobRegistry().register(new EntityPsycho(world), WorldLootTables.ENTITIES_PSYCHO);
			}

			if (WorldConfig.subpart8BitMobs)
				jerAPI.getMobRegistry().register(new EntityParaBuzzy(world), WorldLootTables.ENTITIES_PARABUZZY);

			if (WorldConfig.subpartDesertWells) {
				jerAPI.getDungeonRegistry().registerCategory("chests/desert_level_10", "grimpack.jer.dungeon.desert_level10");
				jerAPI.getDungeonRegistry().registerCategory("chests/desert_level_15", "grimpack.jer.dungeon.desert_level15");
				jerAPI.getDungeonRegistry().registerCategory("chests/desert_level_20", "grimpack.jer.dungeon.desert_level20");
				jerAPI.getDungeonRegistry().registerCategory("chests/desert_level_25", "grimpack.jer.dungeon.desert_level25");
				jerAPI.getDungeonRegistry().registerCategory("chests/desert_level_30", "grimpack.jer.dungeon.desert_level30");

				jerAPI.getDungeonRegistry().registerChest(WorldLootTables.CHESTS_DESERT_LEVEL_10.getResourcePath(), WorldLootTables.CHESTS_DESERT_LEVEL_10);
				jerAPI.getDungeonRegistry().registerChest(WorldLootTables.CHESTS_DESERT_LEVEL_15.getResourcePath(), WorldLootTables.CHESTS_DESERT_LEVEL_15);
				jerAPI.getDungeonRegistry().registerChest(WorldLootTables.CHESTS_DESERT_LEVEL_20.getResourcePath(), WorldLootTables.CHESTS_DESERT_LEVEL_20);
				jerAPI.getDungeonRegistry().registerChest(WorldLootTables.CHESTS_DESERT_LEVEL_25.getResourcePath(), WorldLootTables.CHESTS_DESERT_LEVEL_25);
				jerAPI.getDungeonRegistry().registerChest(WorldLootTables.CHESTS_DESERT_LEVEL_30.getResourcePath(), WorldLootTables.CHESTS_DESERT_LEVEL_30);
			}

			if (WorldConfig.subpartRuins) {
				jerAPI.getDungeonRegistry().registerCategory("chests/fountain", "grimpack.jer.dungeon.fountain");
				jerAPI.getDungeonRegistry().registerCategory("chests/pyramid", "grimpack.jer.dungeon.pyramid");
				jerAPI.getDungeonRegistry().registerCategory("chests/ruin", "grimpack.jer.dungeon.ruin");

				jerAPI.getDungeonRegistry().registerChest(WorldLootTables.CHESTS_RUIN.getResourcePath(), WorldLootTables.CHESTS_RUIN);
				jerAPI.getDungeonRegistry().registerChest(WorldLootTables.CHESTS_PYRAMID.getResourcePath(), WorldLootTables.CHESTS_PYRAMID);
				jerAPI.getDungeonRegistry().registerChest(WorldLootTables.CHESTS_FOUNTAIN.getResourcePath(), WorldLootTables.CHESTS_FOUNTAIN);
			}
		}
	}
}
