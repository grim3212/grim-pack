package com.grim3212.mc.pack.core;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.CoreClientProxy;
import com.grim3212.mc.pack.core.client.ManualCore;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.crafting.CoreConditionFactory;
import com.grim3212.mc.pack.core.crafting.PartConditionFactory;
import com.grim3212.mc.pack.core.crafting.TagConditionFactory;
import com.grim3212.mc.pack.core.event.InitEvent;
import com.grim3212.mc.pack.core.init.CoreInit;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.SyncManualEvent;
import com.grim3212.mc.pack.core.manual.event.GiveManualEvent;
import com.grim3212.mc.pack.core.network.MessageBetterExplosion;
import com.grim3212.mc.pack.core.network.MessageSyncManual;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.ServerProxy;
import com.grim3212.mc.pack.core.util.CrashHandler;
import com.grim3212.mc.pack.core.worldgen.CoreWorldGen;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.CrashReportExtender;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class GrimCore extends GrimPart {

	public static GrimCore INSTANCE = new GrimCore();
	public static ServerProxy proxy = DistExecutor.runForDist(() -> CoreClientProxy::new, () -> ServerProxy::new);

	public static final String partId = "core";
	public static final String partName = "Grim Core";

	// TODO: Remake all achievements into advancements
	public GrimCore() {
		super(GrimCore.partId, GrimCore.partName, new CoreConfig());

		CrashReportExtender.registerCrashCallable(new CrashHandler());

		// Initialize registry events
		FMLJavaModLoadingContext.get().getModEventBus().register(new CoreInit());
	}

	@Override
	public void setup(final FMLCommonSetupEvent event) {
		super.setup(event);

		MinecraftForge.EVENT_BUS.post(new InitEvent());

		CoreWorldGen.initWorldGen();

		// Register crafting helpers
		CraftingHelper.register(new ResourceLocation(GrimPack.modID, "part_enabled"), new PartConditionFactory());
		CraftingHelper.register(new ResourceLocation(GrimPack.modID, "core_subpart_enabled"), new CoreConditionFactory());
		CraftingHelper.register(new ResourceLocation(GrimPack.modID, "tag_exists"), new TagConditionFactory());

		// Register config syncing
		PacketDispatcher.registerMessage(MessageSyncManual.class);
		PacketDispatcher.registerMessage(MessageBetterExplosion.class);

		// Register LoginEvent for receiving the Instruction Manual
		MinecraftForge.EVENT_BUS.register(new GiveManualEvent());
		// Register Syncing manual
		MinecraftForge.EVENT_BUS.register(new SyncManualEvent());

		proxy.preInit();
	}

	/*
	 * @OnlyIn(Dist.CLIENT)
	 * 
	 * @Optional.Method(modid = "jeresources")
	 * 
	 * @SubscribeEvent public void JERInit(InitEvent evt) { new
	 * JERGrimPack().registerMobs(); }
	 */

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