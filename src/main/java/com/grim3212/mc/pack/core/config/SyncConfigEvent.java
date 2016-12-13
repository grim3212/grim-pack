package com.grim3212.mc.pack.core.config;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class SyncConfigEvent {

	public SyncConfigEvent() {
		GrimLog.info(GrimPack.modName, "Registered SyncConfigEvent");
	}

	@SubscribeEvent
	public void syncConfig(PlayerLoggedInEvent event) {
		if (!event.player.world.isRemote) {
			PacketDispatcher.sendTo(new MessageSyncConfig(), (EntityPlayerMP) event.player);
		}
	}
}
