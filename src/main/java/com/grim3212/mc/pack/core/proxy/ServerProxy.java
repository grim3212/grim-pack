package com.grim3212.mc.pack.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ServerProxy {

	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(NetworkEvent.Context ctx) {
		return ctx.getSender();
	}

	public void preInit() {
	}

	public void initColors() {
	}

	public void displayDismountMessage(EntityPlayer player) {
	}
}
