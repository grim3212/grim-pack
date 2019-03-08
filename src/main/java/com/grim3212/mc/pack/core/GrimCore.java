package com.grim3212.mc.pack.core;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.client.CoreClientProxy;
import com.grim3212.mc.pack.core.client.ManualCore;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.common.CommonItems;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.event.InitEvent;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.event.GiveManualEvent;
import com.grim3212.mc.pack.core.network.MessageBetterExplosion;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.ServerProxy;
import com.grim3212.mc.pack.core.util.CrashHandler;
import com.grim3212.mc.pack.core.worldgen.CommonWorldGen;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.CrashReportExtender;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
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

		CrashReportExtender.registerCrashCallable(new CrashHandler());
		MinecraftForge.EVENT_BUS.register(new CommonItems());
		MinecraftForge.EVENT_BUS.post(new InitEvent());
		
		CommonWorldGen.initWorldGen();

		// Register config syncing
		//PacketDispatcher.registerMessage(MessageSyncConfig.class);
		PacketDispatcher.registerMessage(MessageBetterExplosion.class);
		
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> PackGuiHandler::openGui);

		// Register LoginEvent for receiving the Instruction Manual
		MinecraftForge.EVENT_BUS.register(new GiveManualEvent());

		//GameRegistry.registerWorldGenerator(new CommonWorldGen(), 50);
		proxy.preInit();
	}

	/*@OnlyIn(Dist.CLIENT)
	@Optional.Method(modid = "jeresources")
	@SubscribeEvent
	public void JERInit(InitEvent evt) {
		new JERGrimPack().registerMobs();
	}*/

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