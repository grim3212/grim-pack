package com.grim3212.mc.util;

import com.grim3212.mc.core.GrimCore;
import com.grim3212.mc.core.config.GrimConfig;
import com.grim3212.mc.core.network.PacketDispatcher;
import com.grim3212.mc.core.part.GrimPart;
import com.grim3212.mc.core.proxy.CommonProxy;
import com.grim3212.mc.util.config.UtilConfig;
import com.grim3212.mc.util.event.BlockChangeEvents;
import com.grim3212.mc.util.event.EntityDeathEvent;
import com.grim3212.mc.util.network.MessageFusRoDah;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GrimUtil.modID, name = GrimUtil.modName, version = GrimCore.modVersion, dependencies = "required-after:grimcore", guiFactory = "com.grim3212.mc.util.config.ConfigGuiFactory")
public class GrimUtil extends GrimPart {

	@SidedProxy(clientSide = "com.grim3212.mc.util.client.UtilClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	@Instance(GrimUtil.modID)
	public static GrimUtil INSTANCE;

	public static final String modID = "grimutil";
	public static final String modName = "Grim Util";

	public GrimUtil() {
		super(GrimUtil.modID, GrimUtil.modName, GrimCore.modVersion, false);
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ModMetadata data = event.getModMetadata();
		data.description = "Grim Util adds in a bunch of small utilities that can make playing more fun..";
		data.url = "http://mods.grim3212.com/mc/" + "my-mods/grim-util/";
		data.credits = "Thanks to the following authors. Leesgowest, LFalch, mattop101, Nandonalt.";

		PacketDispatcher.registerMessage(MessageFusRoDah.class);
		MinecraftForge.EVENT_BUS.register(new EntityDeathEvent());
		MinecraftForge.EVENT_BUS.register(new BlockChangeEvents());

		proxy.registerModels();
	}

	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.registerManual(getModSection());
	}

	@Override
	protected GrimConfig setConfig() {
		return new UtilConfig();
	}
}