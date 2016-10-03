package com.grim3212.mc.pack.world;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.client.ManualWorld;
<<<<<<< HEAD
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.entity.WorldEntities;
import com.grim3212.mc.pack.world.gen.GrimWorldGenerator;
import com.grim3212.mc.pack.world.gen.WorldEvents;
import com.grim3212.mc.pack.world.items.WorldItems;
import com.grim3212.mc.pack.world.util.DesertWellLoot;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
=======
import com.grim3212.mc.pack.world.compat.jer.JERWorld;
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.entity.WorldEntities;
import com.grim3212.mc.pack.world.event.WorldPostInitEvent;
import com.grim3212.mc.pack.world.gen.GrimWorldGenerator;
import com.grim3212.mc.pack.world.gen.WorldEvents;
import com.grim3212.mc.pack.world.items.WorldItems;
import com.grim3212.mc.pack.world.util.LootTables;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrimWorld extends GrimPart {

	public static GrimWorld INSTANCE = new GrimWorld();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.world.client.WorldClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static final String partID = "world";
	public static final String partName = "Grim World";

	public GrimWorld() {
		super(GrimWorld.partID, GrimWorld.partName, new WorldConfig());
		addItem(new WorldBlocks());
		addItem(new WorldItems());
		addEntity(new WorldEntities());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		GameRegistry.registerWorldGenerator(new GrimWorldGenerator(), 5);
		MinecraftForge.TERRAIN_GEN_BUS.register(new WorldEvents());
<<<<<<< HEAD
		DesertWellLoot.init();
=======
		LootTables.initLootTables();
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37

		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.initColors();
<<<<<<< HEAD
=======

		WorldEntities.addSpawns();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.post(new WorldPostInitEvent());
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
	}

	@Override
	protected Item getCreativeTabIcon() {
		return WorldItems.gunpowder_reed_item;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualWorld.INSTANCE;
	}
<<<<<<< HEAD
=======

	@SideOnly(Side.CLIENT)
	@Optional.Method(modid = "jeresources")
	@SubscribeEvent
	public void JERInit(WorldPostInitEvent evt) {
		JERWorld jerWorld = new JERWorld();
		MinecraftForge.EVENT_BUS.register(jerWorld);
		jerWorld.register();
	}
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
}