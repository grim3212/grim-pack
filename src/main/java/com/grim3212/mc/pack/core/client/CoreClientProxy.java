package com.grim3212.mc.pack.core.client;

import com.grim3212.mc.pack.core.item.CoreItems;
import com.grim3212.mc.pack.core.manual.event.RenderManualEntryEvent;
import com.grim3212.mc.pack.core.proxy.ClientProxy;

import net.minecraftforge.common.MinecraftForge;

public class CoreClientProxy extends ClientProxy {

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new RenderManualEntryEvent());
		RenderHelper.renderItem(CoreItems.instruction_manual);
	}
}
