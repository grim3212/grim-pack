package com.grim3212.mc.pack.core.network;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketDispatcher {

	public static byte packetId = 0;
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(GrimPack.modID, "main_channel"), () -> "1.0", s -> true, s -> true);;
	
	public static final <MSG extends AbstractMessage<MSG>> void registerMessage(Class<MSG> msg) {
		try {
			MSG message = msg.newInstance();
			INSTANCE.registerMessage(packetId++, msg, message::toBytes, message::fromBytes, message::onMessage);
		} catch (Exception e) {
			GrimLog.error(GrimPack.modID, "Failed to register message for " + msg.getName());
		}
	}
	
	public static <MSG extends AbstractMessage<MSG>> void send(PacketDistributor.PacketTarget target, MSG message)
    {
		INSTANCE.send(target, message);
    }
	
	public static <MSG extends AbstractMessage<MSG>> void sendToServer(MSG message)
    {
		INSTANCE.sendToServer(message);
    }
}