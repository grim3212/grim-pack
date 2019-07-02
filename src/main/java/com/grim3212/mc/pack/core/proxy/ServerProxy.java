package com.grim3212.mc.pack.core.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

public class ServerProxy {

	
	public PlayerEntity getPlayerEntity(NetworkEvent.Context ctx) {
		return ctx.getSender();
	}
	
	public void preInit() {
	}

	public void initColors() {
	}

	public void displayDismountMessage(PlayerEntity player) {
	}

	public void openManual() {
	}
}
