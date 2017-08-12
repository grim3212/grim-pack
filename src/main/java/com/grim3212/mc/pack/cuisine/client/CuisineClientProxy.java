package com.grim3212.mc.pack.cuisine.client;

import com.grim3212.mc.pack.core.proxy.ClientProxy;

import net.minecraftforge.common.MinecraftForge;

public class CuisineClientProxy extends ClientProxy {

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new CuisineModelHandler());
	}

}
