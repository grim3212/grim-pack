package com.grim3212.mc.pack.core.manual;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.network.MessageSyncManual;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class SyncManualEvent {
	public SyncManualEvent() {
		GrimLog.info(GrimPack.modName, "Registered SyncManualEvent");
	}

	@SubscribeEvent
	public void syncConfig(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.getPlayer().world.isRemote) {
			PacketDispatcher.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new MessageSyncManual());
		}
	}
}
