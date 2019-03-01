package com.grim3212.mc.pack.core;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.compat.jer.JERGrimPack;
import com.grim3212.mc.pack.core.client.CoreClientProxy;
import com.grim3212.mc.pack.core.client.ManualCore;
import com.grim3212.mc.pack.core.common.CommonItems;
import com.grim3212.mc.pack.core.common.CommonWorldGen;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.config.MessageSyncConfig;
import com.grim3212.mc.pack.core.config.SyncConfigEvent;
import com.grim3212.mc.pack.core.event.InitEvent;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.event.GiveManualEvent;
import com.grim3212.mc.pack.core.network.MessageBetterExplosion;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.ServerProxy;
import com.grim3212.mc.pack.core.util.CrashHandler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.CrashReportExtender;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class GrimCore extends GrimPart {

	public static GrimCore INSTANCE = new GrimCore();
	public static ServerProxy proxy = DistExecutor.runForDist(() -> CoreClientProxy::new, () -> ServerProxy::new);

	public static final String partId = "core";
	public static final String partName = "Grim Core";

	// TODO: Remake all achievements into advancements
	public GrimCore() {
		super(GrimCore.partId, GrimCore.partName, new CoreConfig());
	}

	@Override
	public void setup(final FMLCommonSetupEvent event) {
		super.setup(event);

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

		CrashReportExtender.registerCrashCallable(new CrashHandler());
		MinecraftForge.EVENT_BUS.post(new InitEvent());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);

		// Register Syncing config
		MinecraftForge.EVENT_BUS.register(new SyncConfigEvent());
	}

	@OnlyIn(Dist.CLIENT)
	@Optional.Method(modid = "jeresources")
	@SubscribeEvent
	public void JERInit(InitEvent evt) {
		new JERGrimPack().registerMobs();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public IManualPart getManual() {
		return ManualCore.INSTANCE;
	}

	@Override
	public List<String> getImageUrls() {
		return Lists.newArrayList("assets/grimpack/images/core_main.png");
	}

	@Override
	public String getExtraInfo() {
		return "grimpack.doc.coreinfo";
	}
}