package com.grim3212.mc.pack.core;

import com.grim3212.mc.pack.core.client.ManualCore;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.config.MessageSyncConfig;
import com.grim3212.mc.pack.core.config.SyncConfigEvent;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.event.GiveManualEvent;
import com.grim3212.mc.pack.core.network.MessageBetterExplosion;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrimCore extends GrimPart {

	public static GrimCore INSTANCE = new GrimCore();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.core.client.CoreClientProxy", serverSide = "com.grim3212.mc.pack.core.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final String partId = "core";
	public static final String partName = "Grim Core";

	//TODO: Remake all achievements into advancements
	
	public GrimCore() {
		super(GrimCore.partId, GrimCore.partName, new CoreConfig(), true);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		// Register config syncing
		PacketDispatcher.registerMessage(MessageSyncConfig.class);
		PacketDispatcher.registerMessage(MessageBetterExplosion.class);

		// Register LoginEvent for receiving the Instruction Manual
		MinecraftForge.EVENT_BUS.register(new GiveManualEvent());

		proxy.preInit();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);

		// Register Syncing config
		MinecraftForge.EVENT_BUS.register(new SyncConfigEvent());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualCore.INSTANCE;
	}
}