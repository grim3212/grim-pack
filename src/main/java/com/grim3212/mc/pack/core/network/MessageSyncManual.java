package com.grim3212.mc.pack.core.network;

import java.util.function.Supplier;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractClientMessage;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.part.PartRegistry;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSyncManual extends AbstractClientMessage<MessageSyncManual> {

	public MessageSyncManual() {
	}

	@Override
	protected MessageSyncManual read(PacketBuffer buffer) {
		return this;
	}

	@Override
	protected void write(MessageSyncManual msg, PacketBuffer buffer) {
	}

	@Override
	public void process(MessageSyncManual msg, Supplier<Context> ctx) {
		if (ctx.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
			GrimLog.info(GrimPack.modName, "Updating manual pages...");
			// Clear all manual pages currently registered on the client
			ManualRegistry.clearManual();

			// Load manual pages after config has been synced
			for (GrimPart part : PartRegistry.partsToLoad)
				part.getManual().registerChapters(part.getManualPart());
		}
	}

}
