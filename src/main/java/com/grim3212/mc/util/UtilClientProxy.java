package com.grim3212.mc.util;

import com.grim3212.mc.core.proxy.ClientProxy;
import com.grim3212.mc.util.client.KeyBindHelper;
import com.grim3212.mc.util.client.event.AutoItemTickHandler;
import com.grim3212.mc.util.client.event.RenderTickHandler;

import net.minecraftforge.common.MinecraftForge;

public class UtilClientProxy extends ClientProxy {

	@Override
	public void registerModels() {
		MinecraftForge.EVENT_BUS.register(new AutoItemTickHandler());
		MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
		MinecraftForge.EVENT_BUS.register(new KeyBindHelper());
	}

}
