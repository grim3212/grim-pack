package com.grim3212.mc.pack;

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
import com.grim3212.mc.pack.industry.chunkloading.ChunkLoaderCommand;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.util.GrimUtil;
import com.grim3212.mc.pack.world.GrimWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@Mod(modid = GrimPack.modID, name = GrimPack.modName, version = "@MOD_VERSION@", acceptedMinecraftVersions = "[1.12,1.13)", guiFactory = "com.grim3212.mc.pack.core.config.ConfigGuiFactory", updateJSON = "https://raw.githubusercontent.com/grim3212/grim-pack/master/update.json")
public class GrimPack {

	@Instance(GrimPack.modID)
	public static GrimPack INSTANCE;

	public static final String modID = "grimpack";
	public static final String modName = "Grim Pack";

	public static File configDir;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LogTimer.start("PreInit");

		configDir = event.getModConfigurationDirectory();

		// Init creative tabs that should be loaded
		GrimCreativeTabs.initTabs();

		PartRegistry.registerPart(GrimCore.INSTANCE);
		if (CoreConfig.useCuisine)
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
			PartRegistry.registerPart(GrimWorld.INSTANCE);

		// Register GUI handler for the Instruction Manual
		NetworkRegistry.INSTANCE.registerGuiHandler(GrimPack.INSTANCE, new PackGuiHandler());

		PartRegistry.preInit(event);

		// Only allow in debug environments
		if (Loader.instance().activeModContainer().getVersion().equals("@MOD_VERSION@")) {
			if (event.getSide().isClient())
				MinecraftForge.EVENT_BUS.register(new GenerateRendererHandler());
		}

		LogTimer.stop();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		LogTimer.start("Init");

		PartRegistry.init(event);

		LogTimer.stop();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		LogTimer.start("PostInit");

		PartRegistry.postInit(event);

		LogTimer.stop();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
        LogTimer.start("Server Starting event");

		// Only allow in debug environments
		if (Loader.instance().activeModContainer().getVersion().equals("@MOD_VERSION@")) {
			event.registerServerCommand(new CommandGenerate());
		}

		PartRegistry.serverStarting(event);

        LogTimer.stop();
	}

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        LogTimer.start("Server Started event");

        PartRegistry.serverStarted(event);

        LogTimer.stop();
    }
}
