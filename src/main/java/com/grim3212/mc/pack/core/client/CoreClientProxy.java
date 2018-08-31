package com.grim3212.mc.pack.core.client;

import com.grim3212.mc.pack.compat.jer.JERGrimPack;
import com.grim3212.mc.pack.core.manual.event.RenderManualEntryEvent;
import com.grim3212.mc.pack.core.proxy.ClientProxy;

import net.minecraftforge.common.MinecraftForge;

public class CoreClientProxy extends ClientProxy {

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new RenderManualEntryEvent());
	}
	
	@Override
	public void init() {
		new JERGrimPack().registerMobs();
	}
}
