package com.grim3212.mc.core.proxy;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.item.CoreItems;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return (ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx));
	}

	@Override
	public void registerModels() {
		// ModelLoaderRegistry.registerLoader(new MergedModelLoader());

		RenderHelper.renderItem(CoreItems.instruction_manual);
	}
}
