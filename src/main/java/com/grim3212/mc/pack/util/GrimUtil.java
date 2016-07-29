package com.grim3212.mc.pack.util;

import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.event.BlockChangeEvents;
import com.grim3212.mc.pack.util.event.EntityDeathEvent;
import com.grim3212.mc.pack.util.network.MessageFusRoDah;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class GrimUtil extends GrimPart {

	public static GrimUtil INSTANCE = new GrimUtil();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.util.client.UtilClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static final String partID = "grimutil";
	public static final String partName = "Grim Util";

	public static SoundEvent fusrodahSound;
	public static SoundEvent fusrodahOldSound;

	public GrimUtil() {
		super(GrimUtil.partID, GrimUtil.partName, false);
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		PacketDispatcher.registerMessage(MessageFusRoDah.class);
		MinecraftForge.EVENT_BUS.register(new EntityDeathEvent());
		MinecraftForge.EVENT_BUS.register(new BlockChangeEvents());
		fusrodahSound = Utils.registerSound("fusrodah");
		fusrodahOldSound = Utils.registerSound("fusrodah-old");

		proxy.registerModels();
	}

	@Override
	public GrimConfig setConfig() {
		return new UtilConfig();
	}
}