package com.grim3212.mc.pack.world.util;

import com.grim3212.mc.pack.GrimPack;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class WorldLootTables {

	public static ResourceLocation ENTITIES_ICE_PIXIE;
	public static ResourceLocation ENTITIES_TREASURE_MOB;
	public static ResourceLocation ENTITIES_BOMBER;
	public static ResourceLocation ENTITIES_FARMER;
	public static ResourceLocation ENTITIES_LUMBERJACK;
	public static ResourceLocation ENTITIES_MINER;
	public static ResourceLocation ENTITIES_NOTCH;
	public static ResourceLocation ENTITIES_PSYCHO;
	public static ResourceLocation ENTITIES_PARABUZZY;

	public static ResourceLocation CHESTS_DESERT_LEVEL_10;
	public static ResourceLocation CHESTS_DESERT_LEVEL_15;
	public static ResourceLocation CHESTS_DESERT_LEVEL_20;
	public static ResourceLocation CHESTS_DESERT_LEVEL_25;
	public static ResourceLocation CHESTS_DESERT_LEVEL_30;

	public static void initLootTables() {
		ENTITIES_ICE_PIXIE = LootTableList.register(new ResourceLocation(GrimPack.modID, "entities/ice_pixie"));
		ENTITIES_TREASURE_MOB = LootTableList.register(new ResourceLocation(GrimPack.modID, "entities/treasure_mob"));
		ENTITIES_BOMBER = LootTableList.register(new ResourceLocation(GrimPack.modID, "entities/bomber"));
		ENTITIES_FARMER = LootTableList.register(new ResourceLocation(GrimPack.modID, "entities/farmer"));
		ENTITIES_LUMBERJACK = LootTableList.register(new ResourceLocation(GrimPack.modID, "entities/lumberjack"));
		ENTITIES_MINER = LootTableList.register(new ResourceLocation(GrimPack.modID, "entities/miner"));
		ENTITIES_NOTCH = LootTableList.register(new ResourceLocation(GrimPack.modID, "entities/notch"));
		ENTITIES_PSYCHO = LootTableList.register(new ResourceLocation(GrimPack.modID, "entities/psycho"));
		ENTITIES_PARABUZZY = LootTableList.register(new ResourceLocation(GrimPack.modID, "entities/parabuzzy"));

		CHESTS_DESERT_LEVEL_10 = LootTableList.register(new ResourceLocation(GrimPack.modID, "chests/desert_level_10"));
		CHESTS_DESERT_LEVEL_15 = LootTableList.register(new ResourceLocation(GrimPack.modID, "chests/desert_level_15"));
		CHESTS_DESERT_LEVEL_20 = LootTableList.register(new ResourceLocation(GrimPack.modID, "chests/desert_level_20"));
		CHESTS_DESERT_LEVEL_25 = LootTableList.register(new ResourceLocation(GrimPack.modID, "chests/desert_level_25"));
		CHESTS_DESERT_LEVEL_30 = LootTableList.register(new ResourceLocation(GrimPack.modID, "chests/desert_level_30"));
	}

}
