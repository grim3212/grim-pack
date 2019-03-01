package com.grim3212.mc.pack;

import java.io.File;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.part.PartRegistry;
import com.grim3212.mc.pack.core.util.GrimLog.LogTimer;
import com.grim3212.mc.pack.core.util.generator.CommandGenerate;
import com.grim3212.mc.pack.core.util.generator.GenerateRendererHandler;
import com.grim3212.mc.pack.cuisine.GrimCuisine;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.util.GrimUtil;
import com.grim3212.mc.pack.world.GrimWorld;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = GrimPack.modID)
public class GrimPack {

	public static GrimPack INSTANCE;

	public static final String modID = "grimpack";
	public static final String modName = "Grim Pack";

	public static File configDir;

	public GrimPack() {
		INSTANCE = this;
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);
	}

	public void setup(final FMLCommonSetupEvent event) {
		LogTimer.start("Setup");

		//configDir = event.getModConfigurationDirectory();

		// Init creative tabs that should be loaded
		GrimCreativeTabs.initTabs();

		PartRegistry.registerPart(GrimCore.INSTANCE);
		/*if (CoreConfig.useCuisine)
			PartRegistry.registerPart(GrimCuisine.INSTANCE);
		if (CoreConfig.useDecor)
			PartRegistry.registerPart(GrimDecor.INSTANCE);
		if (CoreConfig.useIndustry)
			PartRegistry.registerPart(GrimIndustry.INSTANCE);
		if (CoreConfig.useTools)
			PartRegistry.registerPart(GrimTools.INSTANCE);
		if (CoreConfig.useUtil)
			PartRegistry.registerPart(GrimUtil.INSTANCE);
		if (CoreConfig.useWorld)
			PartRegistry.registerPart(GrimWorld.INSTANCE);*/

		// Register GUI handler for the Instruction Manual
		NetworkRegistry.INSTANCE.registerGuiHandler(GrimPack.INSTANCE, new PackGuiHandler());

		PartRegistry.setup(event);

		// Only allow in debug environments
		if (Loader.instance().activeModContainer().getVersion().equals("@MOD_VERSION@")) {
			if (event.getSide().isClient())
				MinecraftForge.EVENT_BUS.register(new GenerateRendererHandler());
		}

		LogTimer.stop();
	}

	public void serverSetup(final FMLDedicatedServerSetupEvent event) {
		LogTimer.start("Server Setup event");

		// Only allow in debug environments
		if (Loader.instance().activeModContainer().getVersion().equals("@MOD_VERSION@")) {
			event.registerServerCommand(new CommandGenerate());
		}

		PartRegistry.serverSetup(event);

		LogTimer.stop();
	}
}
