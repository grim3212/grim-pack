package com.grim3212.mc.pack.world;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.world.client.ManualWorld;
import com.grim3212.mc.pack.world.compat.jer.JERWorld;
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.entity.WorldEntities;
import com.grim3212.mc.pack.world.event.WorldPostInitEvent;
import com.grim3212.mc.pack.world.gen.GrimWorldGenerator;
import com.grim3212.mc.pack.world.gen.WorldTerrainEvents;
import com.grim3212.mc.pack.world.init.WorldRecipes;
import com.grim3212.mc.pack.world.types.WorldTypes;
import com.grim3212.mc.pack.world.util.WorldLootTables;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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

		GameRegistry.registerWorldGenerator(new GrimWorldGenerator(), 5);
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

		MinecraftForge.EVENT_BUS.post(new WorldPostInitEvent());
		WorldTypes.registerTypes();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualWorld.INSTANCE;
	}

	@SideOnly(Side.CLIENT)
	@Optional.Method(modid = "jeresources")
	@SubscribeEvent
	public void JERInit(WorldPostInitEvent evt) {
		JERWorld jerWorld = new JERWorld();
		MinecraftForge.EVENT_BUS.register(jerWorld);
		jerWorld.register();
	}
}