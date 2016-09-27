package com.grim3212.mc.pack.util.client;

import com.grim3212.mc.pack.core.proxy.ClientProxy;
import com.grim3212.mc.pack.util.client.event.AutoItemTickHandler;
import com.grim3212.mc.pack.util.client.event.RenderBoundingBoxEvent;
import com.grim3212.mc.pack.util.client.event.RenderTickHandler;

import net.minecraftforge.common.MinecraftForge;

public class UtilClientProxy extends ClientProxy {

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new AutoItemTickHandler());
		MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
		MinecraftForge.EVENT_BUS.register(new KeyBindHelper());
		MinecraftForge.EVENT_BUS.register(new RenderBoundingBoxEvent());
	}

}
