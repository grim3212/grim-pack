package com.grim3212.mc.pack.world.compat.jer;

import java.util.Iterator;

import com.grim3212.mc.pack.world.entity.EntityBomber;
import com.grim3212.mc.pack.world.entity.EntityFarmer;
import com.grim3212.mc.pack.world.entity.EntityIcePixie;
import com.grim3212.mc.pack.world.entity.EntityLumberJack;
import com.grim3212.mc.pack.world.entity.EntityMiner;
import com.grim3212.mc.pack.world.entity.EntityNotch;
import com.grim3212.mc.pack.world.entity.EntityPsycho;
import com.grim3212.mc.pack.world.entity.EntityTreasureMob;
import com.grim3212.mc.pack.world.util.LootTables;

import jeresources.api.IJERAPI;
import jeresources.api.JERPlugin;
import jeresources.api.conditionals.LightLevel;
import jeresources.entry.DungeonEntry;
import jeresources.registry.DungeonRegistry;
import jeresources.util.LootTableHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class JERWorld {

	@JERPlugin
	public static IJERAPI jerAPI;

	public void register() {
		this.registerMobs();
	}

	private void registerMobs() {
		World world = Minecraft.getMinecraft().world;

		String[] snowyBiomes = new String[BiomeDictionary.getBiomes(Type.SNOWY).size()];
		Iterator<Biome> itr = BiomeDictionary.getBiomes(Type.SNOWY).iterator();
		int i = 0;
		while (itr.hasNext()) {
			snowyBiomes[i] = itr.next().getBiomeName();
			i++;
		}

		jerAPI.getMobRegistry().register(new EntityIcePixie(world), LightLevel.any, snowyBiomes, LootTables.ENTITIES_ICE_PIXIE);
		jerAPI.getMobRegistry().register(new EntityTreasureMob(world), LootTables.ENTITIES_TREASURE_MOB);
		jerAPI.getMobRegistry().register(new EntityBomber(world), LootTables.ENTITIES_BOMBER);
		jerAPI.getMobRegistry().register(new EntityFarmer(world), LootTables.ENTITIES_FARMER);
		jerAPI.getMobRegistry().register(new EntityLumberJack(world), LootTables.ENTITIES_LUMBERJACK);
		jerAPI.getMobRegistry().register(new EntityMiner(world), LootTables.ENTITIES_MINER);
		jerAPI.getMobRegistry().register(new EntityNotch(world), LootTables.ENTITIES_NOTCH);
		jerAPI.getMobRegistry().register(new EntityPsycho(world), LootTables.ENTITIES_PSYCHO);

		DungeonRegistry.addCategoryMapping("chests/desert_level_10", "grimpack.jer.dungeon.desert_level10");
		DungeonRegistry.addCategoryMapping("chests/desert_level_15", "grimpack.jer.dungeon.desert_level15");
		DungeonRegistry.addCategoryMapping("chests/desert_level_20", "grimpack.jer.dungeon.desert_level20");
		DungeonRegistry.addCategoryMapping("chests/desert_level_25", "grimpack.jer.dungeon.desert_level25");
		DungeonRegistry.addCategoryMapping("chests/desert_level_30", "grimpack.jer.dungeon.desert_level30");
	}

	@SubscribeEvent
	public void onJoinWorld(PlayerEvent.PlayerLoggedInEvent event) {
		DungeonRegistry.getInstance().registerDungeonEntry(new DungeonEntry(LootTables.CHESTS_DESERT_LEVEL_10.getResourcePath(), LootTableHelper.getManager(event.player.world).getLootTableFromLocation(LootTables.CHESTS_DESERT_LEVEL_10)));
		DungeonRegistry.getInstance().registerDungeonEntry(new DungeonEntry(LootTables.CHESTS_DESERT_LEVEL_15.getResourcePath(), LootTableHelper.getManager(event.player.world).getLootTableFromLocation(LootTables.CHESTS_DESERT_LEVEL_15)));
		DungeonRegistry.getInstance().registerDungeonEntry(new DungeonEntry(LootTables.CHESTS_DESERT_LEVEL_20.getResourcePath(), LootTableHelper.getManager(event.player.world).getLootTableFromLocation(LootTables.CHESTS_DESERT_LEVEL_20)));
		DungeonRegistry.getInstance().registerDungeonEntry(new DungeonEntry(LootTables.CHESTS_DESERT_LEVEL_25.getResourcePath(), LootTableHelper.getManager(event.player.world).getLootTableFromLocation(LootTables.CHESTS_DESERT_LEVEL_25)));
		DungeonRegistry.getInstance().registerDungeonEntry(new DungeonEntry(LootTables.CHESTS_DESERT_LEVEL_30.getResourcePath(), LootTableHelper.getManager(event.player.world).getLootTableFromLocation(LootTables.CHESTS_DESERT_LEVEL_30)));
	}
}
