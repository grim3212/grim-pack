package com.grim3212.mc.pack.core.config;

import java.io.IOException;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractClientMessage;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.part.PartRegistry;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Used for syncing server side config to the client
 */
public class MessageSyncConfig extends AbstractClientMessage<MessageSyncConfig> {

	public MessageSyncConfig() {
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		for (GrimPart part : PartRegistry.partsToLoad)
			part.getGrimConfig().readFromServer(buffer);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		for (GrimPart part : PartRegistry.partsToLoad)
			part.getGrimConfig().writeToClient(buffer);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		GrimLog.info(GrimPack.modName, "Syncing config from server...");

		if (side == Side.CLIENT) {
			GrimLog.info(GrimPack.modName, "Updating manual pages...");
			// Clear all manual pages currently registered on the client
			ManualRegistry.clearManual();

			// Load manual pages after config has been synced
			for (GrimPart part : PartRegistry.partsToLoad)
				part.getGrimConfig().updateManual();
		}
	}

}
