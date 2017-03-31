package com.grim3212.mc.pack.util.client;

import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.core.proxy.ClientProxy;
import com.grim3212.mc.pack.util.client.event.AutoItemTickHandler;
import com.grim3212.mc.pack.util.client.event.RenderBoundingBoxEvent;
import com.grim3212.mc.pack.util.client.event.RenderTickHandler;
import com.grim3212.mc.pack.util.grave.TileEntityGrave;
import com.grim3212.mc.pack.util.grave.TileEntityGraveRenderer;
import com.grim3212.mc.pack.util.init.UtilBlocks;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class UtilClientProxy extends ClientProxy {

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new AutoItemTickHandler());
		MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
		MinecraftForge.EVENT_BUS.register(new KeyBindHelper());
		MinecraftForge.EVENT_BUS.register(new RenderBoundingBoxEvent());

		RenderHelper.renderBlock(UtilBlocks.grave);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrave.class, new TileEntityGraveRenderer());
	}

}
