package com.grim3212.mc.pack.core;

import com.grim3212.mc.pack.compat.jer.JERGrimPack;
import com.grim3212.mc.pack.core.client.ManualCore;
import com.grim3212.mc.pack.core.common.CommonItems;
import com.grim3212.mc.pack.core.common.CommonWorldGen;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.config.MessageSyncConfig;
import com.grim3212.mc.pack.core.config.SyncConfigEvent;
import com.grim3212.mc.pack.core.event.PostInitEvent;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.event.GiveManualEvent;
import com.grim3212.mc.pack.core.network.MessageBetterExplosion;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;

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

public class GrimCore extends GrimPart {

	public static GrimCore INSTANCE = new GrimCore();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.core.client.CoreClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static final String partId = "core";
	public static final String partName = "Grim Core";

	// TODO: Remake all achievements into advancements

	public GrimCore() {
		super(GrimCore.partId, GrimCore.partName, new CoreConfig());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new CommonItems());

		// Register config syncing
		PacketDispatcher.registerMessage(MessageSyncConfig.class);
		PacketDispatcher.registerMessage(MessageBetterExplosion.class);

		// Register LoginEvent for receiving the Instruction Manual
		MinecraftForge.EVENT_BUS.register(new GiveManualEvent());

		GameRegistry.registerWorldGenerator(new CommonWorldGen(), 50);
		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);

		// Register Syncing config
		MinecraftForge.EVENT_BUS.register(new SyncConfigEvent());
		MinecraftForge.EVENT_BUS.post(new PostInitEvent());
	}

	@SideOnly(Side.CLIENT)
	@Optional.Method(modid = "jeresources")
	@SubscribeEvent
	public void JERInit(PostInitEvent evt) {
		new JERGrimPack().register();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualCore.INSTANCE;
	}
}