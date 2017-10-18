package com.grim3212.mc.pack.world;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.client.ManualWorld;
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.entity.WorldEntities;
import com.grim3212.mc.pack.world.gen.GrimWorldGenerator;
import com.grim3212.mc.pack.world.gen.WorldTerrainEvents;
import com.grim3212.mc.pack.world.gen.structure.cactusfield.StructureCactusField;
import com.grim3212.mc.pack.world.gen.structure.floatingislands.StructureFloatingIsland;
import com.grim3212.mc.pack.world.gen.structure.fountain.StructureFountain;
import com.grim3212.mc.pack.world.gen.structure.pyramid.StructurePyramid;
import com.grim3212.mc.pack.world.gen.structure.ruins.StructureRuins;
import com.grim3212.mc.pack.world.gen.structure.sandpit.StructureSandPit;
import com.grim3212.mc.pack.world.gen.structure.snowball.StructureSnowball;
import com.grim3212.mc.pack.world.gen.structure.spire.StructureSpire;
import com.grim3212.mc.pack.world.gen.structure.waterdome.StructureWaterDome;
import com.grim3212.mc.pack.world.gen.structure.wheatfield.StructureWheatField;
import com.grim3212.mc.pack.world.init.WorldRecipes;
import com.grim3212.mc.pack.world.init.WorldSounds;
import com.grim3212.mc.pack.world.items.WorldItems;
import com.grim3212.mc.pack.world.types.WorldTypes;
import com.grim3212.mc.pack.world.util.WorldLootTables;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrimWorld extends GrimPart {

	public static GrimWorld INSTANCE = new GrimWorld();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.world.client.WorldClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static final String partId = "world";
	public static final String partName = "Grim World";

	public GrimWorld() {
		super(GrimWorld.partId, GrimWorld.partName, new WorldConfig());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new WorldBlocks());
		MinecraftForge.EVENT_BUS.register(new WorldItems());

		if (WorldConfig.subpart8BitMobs)
			MinecraftForge.EVENT_BUS.register(new WorldSounds());

		GameRegistry.registerWorldGenerator(new GrimWorldGenerator(), 5);

		if (WorldConfig.subpartDesertWells)
			MinecraftForge.TERRAIN_GEN_BUS.register(new WorldTerrainEvents());
		WorldLootTables.initLootTables();
		WorldEntities.initEntities();

		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.initColors();

		WorldRecipes.initRecipes();
		WorldEntities.addSpawns();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);

		if (WorldConfig.subpartFloatingIslandWorldType)
			WorldTypes.registerTypes();

		if (WorldConfig.subpartRuins) {
			GameRegistry.registerWorldGenerator(StructureFountain.INSTANCE, 10);
			GameRegistry.registerWorldGenerator(StructurePyramid.INSTANCE, 10);
			GameRegistry.registerWorldGenerator(StructureRuins.INSTANCE, 10);
			GameRegistry.registerWorldGenerator(StructureSnowball.INSTANCE, 10);
			GameRegistry.registerWorldGenerator(StructureWaterDome.INSTANCE, 10);
			GameRegistry.registerWorldGenerator(StructureSpire.INSTANCE, 10);
		}

		if (WorldConfig.subpartFloatingIslands) {
			GameRegistry.registerWorldGenerator(StructureFloatingIsland.INSTANCE, 5);
		}

		if (WorldConfig.subpartWorldGenExpanded) {
			GameRegistry.registerWorldGenerator(StructureCactusField.INSTANCE, 25);
			GameRegistry.registerWorldGenerator(StructureSandPit.INSTANCE, 25);
			GameRegistry.registerWorldGenerator(StructureWheatField.INSTANCE, 25);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualWorld.INSTANCE;
	}

	@Override
	public List<String> getImageUrls() {
		return Lists.newArrayList("assets/grimpack/images/world_main.png");
	}
}