package com.grim3212.mc.pack.util.client;

import com.grim3212.mc.pack.core.proxy.ClientProxy;
import com.grim3212.mc.pack.util.client.event.AutoItemTickHandler;
import com.grim3212.mc.pack.util.client.event.RenderBoundingBoxEvent;
import com.grim3212.mc.pack.util.client.event.RenderTickHandler;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.grave.TileEntityGrave;
import com.grim3212.mc.pack.util.grave.TileEntityGraveRenderer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class UtilClientProxy extends ClientProxy {

	@Override
	public void preInit() {
		if (UtilConfig.subpartAutoItemReplacer)
			MinecraftForge.EVENT_BUS.register(new AutoItemTickHandler());

		if (UtilConfig.subpartTime)
			MinecraftForge.EVENT_BUS.register(new RenderTickHandler());

		if (UtilConfig.subpartTime || UtilConfig.subpartFusRoDah)
			MinecraftForge.EVENT_BUS.register(new KeyBindHelper());

		if (UtilConfig.subpartDebug)
			MinecraftForge.EVENT_BUS.register(new RenderBoundingBoxEvent());

		if (UtilConfig.subpartGraves) {
			MinecraftForge.EVENT_BUS.register(new UtilModelHandler());

			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrave.class, new TileEntityGraveRenderer());
		}
	}

}
