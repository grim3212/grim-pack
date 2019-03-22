package com.grim3212.mc.pack.core.manual;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.network.MessageSyncManual;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class SyncManualEvent {
	public SyncManualEvent() {
		GrimLog.info(GrimPack.modName, "Registered SyncManualEvent");
	}

	@SubscribeEvent
	public void syncConfig(PlayerLoggedInEvent event) {
		if (!event.getPlayer().world.isRemote) {
			PacketDispatcher.send(PacketDistributor.PLAYER.with(() -> (EntityPlayerMP) event.getPlayer()), new MessageSyncManual());
		}
	}
}
