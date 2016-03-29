package com.grim3212.mc.decor.network;

import com.grim3212.mc.decor.entity.EntityFrame;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerFrameClient implements IMessageHandler<MessageFrame, IMessage> {

	@Override
	public IMessage onMessage(final MessageFrame message, MessageContext ctx) {
		if (ctx.side != Side.CLIENT) {
			System.err.println("MessageFrameClient received on wrong side:" + ctx.side);
			return null;
		}

		Minecraft minecraft = Minecraft.getMinecraft();
		final WorldClient worldClient = minecraft.theWorld;
		minecraft.addScheduledTask(new Runnable() {
			public void run() {
				processMessage(worldClient, message);
			}
		});

		return null;
	}

	private void processMessage(WorldClient worldClient, MessageFrame message) {
		Entity entity = worldClient.getEntityByID(message.getEntityID());

		if ((entity != null) && ((entity instanceof EntityFrame))) {
			((EntityFrame) entity).updateFrame(message.getId());
		}
	}

}
