package com.grim3212.mc.pack;

import java.io.File;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.core.part.PartRegistry;
import com.grim3212.mc.pack.core.util.GrimLog.LogTimer;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GrimPack.modID)
public class GrimPack {

	public static GrimPack INSTANCE;

	public static final String modID = "grimpack";
	public static final String modName = "Grim Pack";

	public static File configDir;

	public GrimPack() {
		INSTANCE = this;
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);

		PartRegistry.registerPart(GrimCore.INSTANCE);
		// if (CoreConfig.useCuisine.get())
		// PartRegistry.registerPart(GrimCuisine.INSTANCE);
		// if (CoreConfig.useDecor.get())
		// PartRegistry.registerPart(GrimDecor.INSTANCE);
		/*
		 * if (CoreConfig.useIndustry) PartRegistry.registerPart(GrimIndustry.INSTANCE);
		 * if (CoreConfig.useTools) PartRegistry.registerPart(GrimTools.INSTANCE); if
		 * (CoreConfig.useUtil) PartRegistry.registerPart(GrimUtil.INSTANCE); if
		 * (CoreConfig.useWorld) PartRegistry.registerPart(GrimWorld.INSTANCE);
		 */

		// Init creative tabs that should be loaded
		GrimItemGroups.initTabs();
	}

	public void setup(final FMLCommonSetupEvent event) {
		LogTimer.start("Setup");

		PartRegistry.setup(event);

		LogTimer.stop();
	}

	public void clientSetup(final FMLClientSetupEvent event) {
		LogTimer.start("Client Setup");

		PartRegistry.clientSetup(event);

		LogTimer.stop();
	}

	public void serverSetup(final FMLDedicatedServerSetupEvent event) {
		LogTimer.start("Server Setup event");

		PartRegistry.serverSetup(event);

		LogTimer.stop();
	}
}
